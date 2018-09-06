package org.dvsa.testing.framework.Utils.Generic;

import activesupport.IllegalBrowserException;
import activesupport.driver.Browser;
import activesupport.MissingRequiredArgument;
import activesupport.jenkins.Jenkins;
import activesupport.jenkins.JenkinsParameterKey;
import activesupport.system.Properties;
import org.dvsa.testing.framework.Utils.API_CreateAndGrantAPP.CreateLicenceAPI;
import org.dvsa.testing.framework.stepdefs.World;
import org.dvsa.testing.lib.pages.BasePage;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.zeroturnaround.zip.ZipUtil;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;


public class GenericUtils extends BasePage {

    private World world;
    private String registrationNumber;
    private static final String zipFilePath = "/src/test/resources/ESBR.zip";
    private String trafficAreaName;

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getTrafficAreaName() {
        return trafficAreaName;
    }

    public void setTrafficAreaName(String trafficAreaName) {
        this.trafficAreaName = trafficAreaName;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public GenericUtils(World world) throws MissingRequiredArgument {
        this.world = world;
        world.createLicence = new CreateLicenceAPI();
    }

    public void modifyXML(String dateState, int months) {
        try {
            String xmlFile = "./src/test/resources/ESBR/ESBR.xml";
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder xmlBuilder = documentBuilderFactory.newDocumentBuilder();
            Document xmlDoc = xmlBuilder.parse(xmlFile);
            //update licence number
            NodeList nodeList = xmlDoc.getElementsByTagName("*");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    // do something with the current element

                    if ("StartDate".equals(node.getNodeName())) {
                        node.setTextContent(getDates(dateState, months));
                    }
                    if ("LicenceNumber".equals(node.getNodeName())) {
                        node.setTextContent(world.createLicence.getLicenceNumber());
                    }
                    if ("RegistrationNumber".equals(node.getNodeName())) {
                        String getContent = node.getTextContent();
                        int newRegNumber = Integer.parseInt(getContent);
                        setRegistrationNumber(String.valueOf(newRegNumber + 1));
                        node.setTextContent(getRegistrationNumber());
                    }
                    if ("TrafficAreaName".equals(node.getNodeName())) {
                        switch (getTrafficAreaName()) {
                            case "Wales":
                                node.setTextContent("Welsh");
                                break;
                            case "Scotland":
                                node.setTextContent("Scottish");
                                break;
                            default:
                                node.setTextContent("WestMidlands");
                                break;
                        }

                    }
                }
            }
            // write the content on console
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(xmlDoc);
            System.out.println("-----------Modified File-----------");

            StreamResult result = new StreamResult(new File(xmlFile));
            transformer.transform(source, result);
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getDates(String state, int months) {

        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String myDate = null;

        switch (state) {
            case "futureMonth":
                myDate = date.format(now.plusMonths(months));
                break;
            case "futureDay":
                myDate = date.format(now.plusDays(months));
                break;
            case "past":
                myDate = date.format(now.minusMonths(months));
                break;
            case "current":
                myDate = date.format(now);
                break;
            default:
                System.out.println(state + ": does not exist, needs to either be 'current', 'past' or 'future'");
        }
        return myDate;
    }

    public static void zipFolder() {
        /*
        / Uses Open source util zt-zip https://github.com/zeroturnaround/zt-zip
         */
        ZipUtil.pack(new File("./src/test/resources/ESBR"), new File("./src/test/resources/ESBR.zip"));
    }

    public void executeJenkinsBatchJob(String command) throws Exception {
        HashMap<String, String> jenkinsParams = new HashMap<>();
        jenkinsParams.put(JenkinsParameterKey.NODE.toString(), String.format("api&&%s&&olcs", Properties.get("env", true)));
        jenkinsParams.put(JenkinsParameterKey.JOB.toString(), command);

        Jenkins.trigger(Jenkins.Job.BATCH_PROCESS_QUEQUE, jenkinsParams);
    }

    public boolean retryingFindClick(By by) {
        boolean result = false;
        int attempts = 0;
        while (attempts < 10) {
            try {
                Browser.navigate().findElement(by).click();
                result = true;
                break;
            } catch (Exception e) {
            }
            attempts++;
        }
        return result;
    }

    public String stripNonAlphanumericCharacters(String value) {
        return value.replaceAll("[^A-Za-z0-9]", "");

    }

    public String stripAlphaCharacters(String value) {
        return value.replaceAll("[^0-9]", "");
    }

    public void selectAllExternalRadioButtons(String radioButtonValue) throws IllegalBrowserException {
        List<WebElement> radioButtons = Browser.navigate().findElements(By.xpath("//label[@class='form-control form-control--radio form-control--inline']"));
        radioButtons.stream().filter(s -> s.getText().equals(radioButtonValue)).forEach(x -> x.click());
    }
    public void findAllRadioButtons(String value) throws IllegalBrowserException {
        List<WebElement> radioButtons = Browser.navigate().findElements(By.xpath("//*[@type='radio']"));
        radioButtons.stream().
                filter(x -> x.getAttribute("value").equals(value)).
                filter(isChecked -> !isChecked.isSelected()).
                forEach(x -> x.click());
    }
    public void selectFirstValueInList(String selector) throws IllegalBrowserException {
        Browser.navigate().findElements(By.xpath(selector)).stream().findFirst().get().click();
    }
    public boolean checkForValuesInTable(String searchTerm) throws IllegalBrowserException {
        return Browser.navigate().findElements(By.xpath("//table/tbody/tr[*]")).stream().allMatch(w -> w.getText().contains(searchTerm));
    }

    public static java.time.LocalDate getFutureDate(@NotNull int month) {
        java.time.LocalDate date = java.time.LocalDate.now().plusMonths(month);
        return date;
    }
}