package org.dvsa.testing.framework.stepdefs.Utils.Internal;

import activesupport.MissingRequiredArgument;
import activesupport.http.RestUtils;
import activesupport.system.Properties;
import org.apache.http.HttpStatus;
import org.dvsa.testing.framework.stepdefs.Utils.External.CreateInterimGoodsLicenceAPI;
import org.dvsa.testing.framework.stepdefs.Utils.External.CreateInterimPsvLicenceAPI;
import org.dvsa.testing.framework.stepdefs.Utils.Headers;
import org.dvsa.testing.framework.stepdefs.builders.GenericBuilder;
import org.dvsa.testing.lib.Environment;
import org.dvsa.testing.lib.Login;
import org.dvsa.testing.lib.browser.Browser;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;
import org.dvsa.testing.lib.pages.internal.SearchNavBar;
import org.dvsa.testing.lib.utils.ApplicationType;
import org.dvsa.testing.lib.utils.EnvironmentType;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.dvsa.testing.framework.stepdefs.ESBRupload.DA_USER;
import static org.dvsa.testing.framework.stepdefs.Utils.External.CreateInterimPsvLicenceAPI.adminUserHeader;
import static org.dvsa.testing.framework.stepdefs.Utils.Headers.getHeaders;
import static org.dvsa.testing.framework.stepdefs.Utils.Internal.LoginInternalUser.USER_PASSWORD;

public class GenericUtils extends BasePage {

    private String registrationNumber;
    private static final String DA_USER = "usr271";
    private static final String DA_PASSWORD = "password";
    private static final String USER = "usr336";
    private static final String USER_PASSWORD = "Password1";

    private String getRegistrationNumber() {
        return registrationNumber;
    }
    private void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    CreateInterimPsvLicenceAPI psvLicenceAPI = new CreateInterimPsvLicenceAPI();

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

    public void internalUserLogin() throws MissingRequiredArgument {
        EnvironmentType env = Environment.enumType(Properties.get("env", true));
        String URL = org.dvsa.testing.lib.URI.build(ApplicationType.INTERNAL, env);

        if (Browser.isInitialised()) {
            //Quit Browser and open a new window
            Browser.quit();
        }
        Browser.go(URL);

        if(Browser.getURL().contains("da")){
            Login.signIn(DA_USER, DA_PASSWORD);
        }
        else{Login.signIn(USER, USER_PASSWORD);}
    }

    public void getLicenceTrafficArea(){

        Headers.headers.put("x-pid", psvLicenceAPI.getAdminUserHeader());

        String getApplicationResource = String.format("application/%s", psvLicenceAPI.getApplicationNumber());
//        apiResponse = RestUtils.get(baseURL.concat(getApplicationResource), getHeaders());
//        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
//        setLicenceNumber(apiResponse.extract().jsonPath().getString("licence.licNo"));
//        setLicenceId(apiResponse.extract().jsonPath().getString("licence.id"));
    }
    }
}