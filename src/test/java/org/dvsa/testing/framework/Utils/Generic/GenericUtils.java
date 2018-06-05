package org.dvsa.testing.framework.Utils.Generic;

import activesupport.MissingRequiredArgument;
import activesupport.aws.s3.S3;
import activesupport.http.RestUtils;
import activesupport.jenkins.Jenkins;
import activesupport.jenkins.JenkinsParameterKey;
import activesupport.string.Str;
import activesupport.system.Properties;
import enums.LicenceType;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.dvsa.testing.framework.Utils.API_CreateAndGrantAPP.CreateLicenceAPI;
import org.dvsa.testing.framework.Utils.API_Headers.Headers;
import org.dvsa.testing.framework.Utils.API_Builders.GenericBuilder;
import org.dvsa.testing.framework.Utils.API_Builders.VariationBuilder;
import org.dvsa.testing.framework.Utils.API_CreateAndGrantAPP.GrantLicenceAPI;
import org.dvsa.testing.framework.stepdefs.World;
import org.dvsa.testing.lib.Login;
import org.dvsa.testing.lib.browser.Browser;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;
import org.dvsa.testing.lib.pages.internal.SearchNavBar;
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
import java.util.HashMap;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.dvsa.testing.framework.Utils.API_Headers.Headers.getHeaders;

public class GenericUtils extends BasePage {

    private static EnvironmentType env;

    static {
        try {
            env = EnvironmentType.getEnum(Properties.get("env", false));
        } catch (MissingRequiredArgument missingRequiredArgument) {
            missingRequiredArgument.printStackTrace();
        }
    }

    private World world;
    private ValidatableResponse apiResponse;
    private String registrationNumber;
    private static final String DA_USER = "usr271";
    private static final String DA_PASSWORD = "password";
    private static final String USER = "usr336";
    private static final String USER_PASSWORD = "Password1";
    private static final String zipFilePath = "/src/test/resources/ESBR.zip";
    private String trafficAreaName;
    private static String variationApplicationNumber;
    private static String operatorType = System.getProperty("operatorType");

    public static int tmCount;

    private String getRegistrationNumber() {
        return registrationNumber;
    }

    private String getTrafficAreaName() {
        return trafficAreaName;
    }

    private void setTrafficAreaName(String trafficAreaName) {
        this.trafficAreaName = trafficAreaName;
    }

    private void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public GenericUtils(World world) throws MissingRequiredArgument {
        this.world = world;
        world.createLicence = new CreateLicenceAPI();
        world.grantLicence = new GrantLicenceAPI(world);
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

    public void payGoodsFeesAndGrantLicence() throws MalformedURLException {
        if (variationApplicationNumber != null) {
            world.grantLicence.createOverview(variationApplicationNumber);
            world.grantLicence.variationGrant(variationApplicationNumber);
        } else {
            world.grantLicence.createOverview(world.createLicence.getApplicationNumber());
            world.grantLicence.getOutstandingFees(world.createLicence.getApplicationNumber());
            world.grantLicence.payOutstandingFees(world.createLicence.getOrganisationId(), world.createLicence.getApplicationNumber());
            world.grantLicence.grant(world.createLicence.getApplicationNumber());
        }
    }

    public void payPsvFeesAndGrantLicence() throws MalformedURLException {
        world.grantLicence.createOverview(world.createLicence.getApplicationNumber());
        world.grantLicence.getOutstandingFees(world.createLicence.getApplicationNumber());
        world.grantLicence.payOutstandingFees(world.createLicence.getOrganisationId(), world.createLicence.getApplicationNumber());
        world.grantLicence.grant(world.createLicence.getApplicationNumber());
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

    public void createVariation() throws MalformedURLException {
        String licenceId = world.createLicence.getLicenceId();
        String licenceHistoryResource = org.dvsa.testing.lib.url.api.URL.build(env, String.format("licence/%s/variation", licenceId)).toString();

        VariationBuilder variation = new VariationBuilder().withId(licenceId).withFeeRequired("N").withLicenceType("ltyp_si").withAppliedVia("applied_via_phone");
        apiResponse = RestUtils.post(variation, licenceHistoryResource, getHeaders());
        assertThat(apiResponse.statusCode(HttpStatus.SC_CREATED));
        variationApplicationNumber = String.valueOf(apiResponse.extract().jsonPath().getInt("id.application"));
    }

    public void updateLicenceType(String licenceId) throws MalformedURLException {
        Integer version = 1;
        String typeOfLicenceResource = org.dvsa.testing.lib.url.api.URL.build(env, String.format("variation/%s/type-of-licence", licenceId)).toString();

        do {
            GenericBuilder genericBuilder = new GenericBuilder().withId(variationApplicationNumber).withVersion(version).withLicenceType(String.valueOf(LicenceType.getEnum("standard_national")));
            apiResponse = RestUtils.put(genericBuilder, typeOfLicenceResource, getHeaders());
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

    public void externalUserLogin() throws MalformedURLException, MissingRequiredArgument {
        String myURL = URL.build(ApplicationType.EXTERNAL, env).toString();

        if (Browser.isInitialised()) {
            //Quit Browser and open a new window
            Browser.quit();
        }
        Browser.go(myURL);
        String password = S3.getTempPassword(world.createLicence.getEmailAddress());

        if (isTextPresent("Username", 60))
            Login.signIn(world.createLicence.getLoginId(), password);
        if (isTextPresent("Current password", 60)) {
            enterField(nameAttribute("input", "oldPassword"), password);
            enterField(nameAttribute("input", "newPassword"), "Password1");
            enterField(nameAttribute("input", "confirmPassword"), "Password1");
            click(nameAttribute("input", "submit"));
        }

    }

    public void getLicenceTrafficArea() throws MalformedURLException {
        Headers.headers.put("x-pid", world.createLicence.getAdminUserHeader());
        String getApplicationResource = org.dvsa.testing.lib.url.api.URL.build(env, String.format("licence/%s", world.createLicence.getLicenceId())).toString();

        apiResponse = RestUtils.get(getApplicationResource, getHeaders());
        setTrafficAreaName(apiResponse.extract().jsonPath().getString("trafficArea.name"));
    }

    public static void enterDate(int day, int month, int year) {
        enterText("receivedDate_day", String.valueOf(day),SelectorType.ID);
        enterText("receivedDate_month", String.valueOf(month),SelectorType.ID);
        enterText("receivedDate_year", String.valueOf(year),SelectorType.ID);
    }

    public static void internalSiteAddBusNewReg(int day, int month, int year) {
        waitForTextToBePresent("Service details");
        assertTrue(isTextPresent("Service No. & type", 5));
        enterText("serviceNo", "123",SelectorType.ID);
        enterText("startPoint", Str.randomWord(9),SelectorType.ID);
        enterText("finishPoint", Str.randomWord(11),SelectorType.ID);
        enterText("via", Str.randomWord(5),SelectorType.ID);
        selectServiceType("//ul[@class='chosen-choices']", "//*[@id=\"busServiceTypes_chosen\"]/div/ul/li[1]", SelectorType.XPATH);
        enterDate(getCurrentDayOfMonth(), getCurrentMonth(), getCurrentYear());
        enterText("effectiveDate_day", String.valueOf(day),SelectorType.ID);
        enterText("effectiveDate_month", String.valueOf(month),SelectorType.ID);
        enterText("effectiveDate_year", String.valueOf(year),SelectorType.ID);
        click(nameAttribute("button", "form-actions[submit]"));
    }

    public void generateAndGrantPsvApplicationPerTrafficArea(String trafficArea, String enforcementArea) throws Exception {
        world.createLicence.setTrafficArea(trafficArea);
        world.createLicence.setEnforcementArea(enforcementArea);
        world.createLicence.setOperatorType("public");
        world.createLicence.createAndSubmitApp();
        payPsvFeesAndGrantLicence();
        world.grantLicence.payGrantFees();
        getLicenceTrafficArea();
        System.out.println("--Licence-Number: " + world.createLicence.getLicenceNumber() + "--");
    }

    public void uploadAndSubmitESBR(String state, int interval) throws MissingRequiredArgument, MalformedURLException {
        // for the date state the options are ['current','past','future'] and depending on your choice the months you want to add/remove
        modifyXML(state, interval);
        zipFolder();
        externalUserLogin();
        clickByLinkText("Bus");
        waitAndClick("//*[@id='main']/div[2]/ul/li[2]/a", SelectorType.XPATH);
        click(nameAttribute("button", "action"));
        String workingDir = System.getProperty("user.dir");
        uploadFile("//*[@id='fields[files][file]']", workingDir + zipFilePath, "document.getElementById('fields[files][file]').style.left = 0", SelectorType.XPATH);
        waitAndClick("//*[@name='form-actions[submit]']", SelectorType.XPATH);
    }

    public void removeInternalTransportManager() {
        assertTrue(isTextPresent("Overview", 60));
        if (!isLinkPresent("Transport", 60) && isTextPresent("Granted", 60)) {
            clickByLinkText(world.createLicence.getLicenceNumber());
            tmCount = returnTableRows("//*[@id='lva-transport-managers']/fieldset/div/div[2]/table/tbody/tr", SelectorType.XPATH);
        }
        clickByLinkText("Transport");
        isTextPresent("TransPort Managers", 60);
        click("//*[@value='Remove']", SelectorType.XPATH);
    }

    public void updateLicenceStatus(String licenceId, String status) throws MalformedURLException {
        String typeOfLicenceResource = org.dvsa.testing.lib.url.api.URL.build(env, String.format("licence/%s/decisions/%s", licenceId, status)).toString();

        GenericBuilder genericBuilder = new GenericBuilder().withId(licenceId);
        apiResponse = RestUtils.post(genericBuilder, typeOfLicenceResource, getHeaders());
        assertThat(apiResponse.statusCode(HttpStatus.SC_CREATED));
    }

    public void searchAndViewApplication() {
        selectValueFromDropDown("//select[@id='search-select']", SelectorType.XPATH, "Applications");
        if (variationApplicationNumber != null) {
            do {
                SearchNavBar.search(variationApplicationNumber);
            } while (!isLinkPresent(variationApplicationNumber, 60));
            clickByLinkText(variationApplicationNumber);
            assertTrue(Boolean.parseBoolean(String.valueOf(Browser.getURL().contains("variation"))));
        } else {
            do {
                SearchNavBar.search(String.valueOf(world.createLicence.getApplicationNumber()));
            } while (!isLinkPresent(world.createLicence.getApplicationNumber(), 60));
            clickByLinkText(world.createLicence.getApplicationNumber());
            if (isLinkPresent("Interim", 60))
                clickByLinkText("Interim ");
        }
    }

    public CreateLicenceAPI createApp() throws MissingRequiredArgument {
        CreateLicenceAPI api = new CreateLicenceAPI();
        return api;
    }

    public GrantLicenceAPI grantLicence() throws MissingRequiredArgument {
        GrantLicenceAPI grantLicenceAPI = new GrantLicenceAPI(world);
        return grantLicenceAPI;
    }

    public void createApplication() throws Exception {
        if (world.createLicence.getApplicationNumber() == null) {
                world.createLicence.createAndSubmitApp();
            }
        }

    public void executeJenkinsBatchJob(String command) throws Exception {
        HashMap<String, String> jenkinsParams = new HashMap<>();
        jenkinsParams.put(JenkinsParameterKey.NODE.toString(), String.format("api&&%s&&olcs", Properties.get("env", true)));
        jenkinsParams.put(JenkinsParameterKey.JOB.toString(), command);

        Jenkins.trigger(Jenkins.Job.BATCH_PROCESS_QUEQUE, jenkinsParams);
    }

}