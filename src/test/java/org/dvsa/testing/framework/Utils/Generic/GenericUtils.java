package org.dvsa.testing.framework.Utils.Generic;

import activesupport.MissingRequiredArgument;
import activesupport.aws.s3.S3;
import activesupport.http.RestUtils;
import activesupport.string.Str;
import activesupport.system.Properties;
import enums.LicenceType;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.dvsa.testing.framework.Utils.API_CreateAndGrantAPP.CreateInterimGoodsLicenceAPI;
import org.dvsa.testing.framework.Utils.API_CreateAndGrantAPP.CreateInterimPsvLicenceAPI;
import org.dvsa.testing.framework.Utils.API_Headers.Headers;
import org.dvsa.testing.framework.Utils.API_Builders.GenericBuilder;
import org.dvsa.testing.framework.Utils.API_Builders.VariationBuilder;
import org.dvsa.testing.framework.Utils.API_CreateAndGrantAPP.GrantLicenceAPI;
import org.dvsa.testing.lib.Login;
import org.dvsa.testing.lib.browser.Browser;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;
import org.dvsa.testing.lib.url.utils.EnvironmentType;
import org.dvsa.testing.lib.url.webapp.URL;
import org.dvsa.testing.lib.url.webapp.utils.ApplicationType;
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
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.dvsa.testing.framework.Utils.API_Headers.Headers.getHeaders;

public class GenericUtils extends BasePage {

    private static EnvironmentType env;

    static {
        try {
            env = EnvironmentType.getEnum(Properties.get("env", true));
        } catch (MissingRequiredArgument missingRequiredArgument) {
            missingRequiredArgument.printStackTrace();
        }
    }

    private ValidatableResponse apiResponse;
    private String registrationNumber;
    private static final String DA_USER = "usr271";
    private static final String DA_PASSWORD = "password";
    private static final String USER = "usr336";
    private static final String USER_PASSWORD = "Password1";
    private static final String zipFilePath = "/src/test/resources/ESBR.zip";
    private String trafficAreaName;
    public static String variationApplicationNumber;

    public static int tmCount;

    public GenericUtils() throws MissingRequiredArgument {
    }

    private String getRegistrationNumber() { return registrationNumber; }
    private String getTrafficAreaName() {
        return trafficAreaName;
    }
    private void setTrafficAreaName(String trafficAreaName) {
        this.trafficAreaName = trafficAreaName;
    }

    private void setRegistrationNumber(String registrationNumber) {
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

    public static void payGoodsFeesAndGrantLicence(GrantLicenceAPI grantApp, CreateInterimGoodsLicenceAPI goodsApp) {
        if (variationApplicationNumber != null) {
            grantApp.createOverview(variationApplicationNumber);
            grantApp.variationGrant(variationApplicationNumber);
        } else {
            grantApp.createOverview(goodsApp.getApplicationNumber());
            grantApp.getOutstandingFees(goodsApp.getApplicationNumber());
            grantApp.payOutstandingFees(goodsApp.getOrganisationId(), goodsApp.getApplicationNumber());
            grantApp.grant(goodsApp.getApplicationNumber());
        }
    }

    public static void payPsvFeesAndGrantLicence(GrantLicenceAPI grantApp, CreateInterimPsvLicenceAPI psvApp) {
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

    public void createVariation(String licenceId) throws MalformedURLException {
        String licenceHistoryResource = org.dvsa.testing.lib.url.api.URL.build(env,String.format("licence/%s/variation", licenceId)).toString();

        VariationBuilder variation = new VariationBuilder().withId(licenceId).withFeeRequired("N").withLicenceType("ltyp_si").withAppliedVia("applied_via_phone");
        apiResponse = RestUtils.post(variation, licenceHistoryResource, getHeaders());
        assertThat(apiResponse.statusCode(HttpStatus.SC_CREATED));
        variationApplicationNumber = String.valueOf(apiResponse.extract().jsonPath().getInt("id.application"));
    }

    public void updateLicenceType(String licenceId) throws MalformedURLException {
        Integer version = 1;
        String typeOfLicenceResource = org.dvsa.testing.lib.url.api.URL.build(env,String.format("variation/%s/type-of-licence", licenceId)).toString();

        do {
        GenericBuilder genericBuilder = new GenericBuilder().withId(variationApplicationNumber).withVersion(version).withLicenceType(String.valueOf(LicenceType.getEnum("standard_national")));
        apiResponse = RestUtils.put(genericBuilder,typeOfLicenceResource, getHeaders());
         version++;
            if (version > 20) {
                version = 1;
            }
        }
        while (apiResponse.extract().statusCode() == HttpStatus.SC_CONFLICT);
        Assertions.assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
    }

    public static void internalUserLogin() throws MissingRequiredArgument, MalformedURLException {
        String url = URL.build(ApplicationType.INTERNAL, env).toString();

        if (Browser.isInitialised()) {
            //Quit Browser and open a new window
            Browser.quit();
        }
        Browser.go(url);

        if (Browser.getURL().contains("da")) {
            Login.signIn(DA_USER, DA_PASSWORD);
        } else {
            Login.signIn(USER, USER_PASSWORD);
        }
    }

    public static void externalUserLogin() throws MalformedURLException, MissingRequiredArgument {
        String myURL = URL.build(ApplicationType.EXTERNAL, env).toString();

        if (Browser.isInitialised()) {
            //Quit Browser and open a new window
            Browser.quit();
        }
        Browser.go(myURL);
    }

    public void getLicenceTrafficArea(CreateInterimPsvLicenceAPI psvApp) throws MalformedURLException {
        Headers.headers.put("x-pid", psvApp.getAdminUserHeader());
        String getApplicationResource = org.dvsa.testing.lib.url.api.URL.build(env,String.format("licence/%s", psvApp.getLicenceId())).toString();

        apiResponse = RestUtils.get(getApplicationResource, getHeaders());
        setTrafficAreaName(apiResponse.extract().jsonPath().getString("trafficArea.name"));
    }

    public static void enterDate(int day, int month, int year) {
        enterText("receivedDate_day", String.valueOf(day));
        enterText("receivedDate_month", String.valueOf(month));
        enterText("receivedDate_year", String.valueOf(year));
    }

    public static void internalSiteAddBusNewReg(int day, int month, int year) {
        waitForTextToBePresent("Service details");
        assertTrue(isTextPresent("Service No. & type", 5));
        enterText("serviceNo", "123");
        enterText("startPoint", Str.randomWord(9));
        enterText("finishPoint", Str.randomWord(11));
        enterText("via", Str.randomWord(5));
        selectServiceType("//ul[@class='chosen-choices']", "//*[@id=\"busServiceTypes_chosen\"]/div/ul/li[1]", SelectorType.XPATH);
        enterDate(getCurrentDayOfMonth(), getCurrentMonth(), getCurrentYear());
        enterText("effectiveDate_day", String.valueOf(day));
        enterText("effectiveDate_month", String.valueOf(month));
        enterText("effectiveDate_year", String.valueOf(year));
        click(nameAttribute("button", "form-actions[submit]"));
    }

    public static void generateAndGrantPsvApplicationPerTrafficArea(CreateInterimPsvLicenceAPI psvApp, GrantLicenceAPI grantApp, String trafficArea, String enforcementArea, GenericUtils genericUtils) throws MalformedURLException {
        psvApp.setTrafficArea(trafficArea);
        psvApp.setEnforcementArea(enforcementArea);
        psvApp.createAndSubmitPsvApp();
        payPsvFeesAndGrantLicence(grantApp, psvApp);
        grantApp.payGrantFees(psvApp.getOrganisationId(), psvApp.getApplicationNumber());
        genericUtils.getLicenceTrafficArea(psvApp);
        System.out.println("--Licence-Number: " + psvApp.getLicenceNumber() + "--");
    }

    public static void uploadAndSubmitESBR(GenericUtils genericUtils, CreateInterimPsvLicenceAPI psvApp, String state, int interval) throws MissingRequiredArgument, MalformedURLException {
        // for the date state the options are ['current','past','future'] and depending on your choice the months you want to add/remove
        genericUtils.modifyXML(psvApp, state, interval);
        zipFolder();

        EnvironmentType env = EnvironmentType.getEnum(Properties.get("env", true));
        String myURL = URL.build(ApplicationType.EXTERNAL, env).toString();

        if (Browser.isInitialised()) {
            //Quit Browser and open a new window
            Browser.quit();
        }
        Browser.go(myURL);
        String password = S3.getTempPassword(psvApp.getEmailAddress());

        if (isTextPresent("Username", 60))
            Login.signIn(psvApp.getLoginId(), password);
        if (isTextPresent("Current password", 60)) {
            enterField(nameAttribute("input", "oldPassword"), password);
            enterField(nameAttribute("input", "newPassword"), "Password1");
            enterField(nameAttribute("input", "confirmPassword"), "Password1");
            click(nameAttribute("input", "submit"));
        }

        clickByLinkText("Bus");
        waitAndClick("//*[@id='main']/div[2]/ul/li[2]/a", SelectorType.XPATH);
        click(nameAttribute("button", "action"));
        String workingDir = System.getProperty("user.dir");
        uploadFile("//*[@id='fields[files][file]']", workingDir + zipFilePath, "document.getElementById('fields[files][file]').style.left = 0", SelectorType.XPATH);
        waitAndClick("//*[@name='form-actions[submit]']", SelectorType.XPATH);
    }

    public static void removeInternalTransportManager(CreateInterimGoodsLicenceAPI goodsApp) {
        assertTrue(isTextPresent("Overview", 60));
        if (!isLinkPresent("Transport", 60) && isTextPresent("Granted",60)) {
            clickByLinkText(goodsApp.getLicenceNumber());
            tmCount = returnTableRows("//*[@id='lva-transport-managers']/fieldset/div/div[2]/table/tbody/tr", SelectorType.XPATH);
        }
        clickByLinkText("Transport");
        isTextPresent("TransPort Managers", 60);
        click("//*[@value='Remove']", SelectorType.XPATH);
    }
}