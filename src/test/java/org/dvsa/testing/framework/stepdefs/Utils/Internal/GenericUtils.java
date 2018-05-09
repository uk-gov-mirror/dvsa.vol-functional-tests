package org.dvsa.testing.framework.stepdefs.Utils.Internal;

import org.dvsa.testing.framework.stepdefs.Utils.External.CreateInterimGoodsLicenceAPI;
import org.dvsa.testing.framework.stepdefs.Utils.External.CreateInterimPsvLicenceAPI;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;
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

public class GenericUtils extends BasePage {

    public String registrationNumber;

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public static void generateLetter() {
        clickByLinkText("Docs & attachments");
        isTextPresent("1 Docs & attachments", 60);
        clickByName("New letter");
        findElement("//*[@id='modal-title']", SelectorType.XPATH, 600);


        waitAndSelectByIndex("Generate letter", "//*[@id='category']", SelectorType.XPATH, 1);
        waitAndSelectByIndex("Generate letter", "//*[@id='documentSubCategory']", SelectorType.XPATH, 1);
        waitAndSelectByIndex("Generate letter", "//*[@id='documentTemplate']", SelectorType.XPATH, 5);
        waitAndClick("//*[@id='form-actions[submit]']", SelectorType.XPATH);
    }

    public static void payGoodsFees(GrantApplicationAPI grantApp, CreateInterimGoodsLicenceAPI goodsApp) {
        grantApp.createOverview(goodsApp.getApplicationNumber());
        grantApp.getOutstandingFees(goodsApp.getApplicationNumber());
        grantApp.payOutstandingFees(goodsApp.getOrganisationId(), goodsApp.getApplicationNumber());
        grantApp.grant(goodsApp.getApplicationNumber());
    }

    public static void payPsvFees(GrantApplicationAPI grantApp, CreateInterimPsvLicenceAPI psvApp) {
        grantApp.createOverview(psvApp.getApplicationNumber());
        grantApp.getOutstandingFees(psvApp.getApplicationNumber());
        grantApp.payOutstandingFees(psvApp.getOrganisationId(), psvApp.getApplicationNumber());
        grantApp.grant(psvApp.getApplicationNumber());
    }

    public void modifyXML(CreateInterimPsvLicenceAPI psvApp, String dateState, int months) {
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
                        node.setTextContent(psvApp.getLicenceNumber());
                    }
                    if ("RegistrationNumber".equals(node.getNodeName())) {
                        String getContent = node.getTextContent();
                        int newRegNumber = Integer.parseInt(getContent);
                        setRegistrationNumber(String.valueOf(newRegNumber + 1));
                        node.setTextContent(getRegistrationNumber());
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
}