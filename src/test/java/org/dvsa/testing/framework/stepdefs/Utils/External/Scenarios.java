package org.dvsa.testing.framework.stepdefs.Utils.External;

import activesupport.MissingRequiredArgument;
import activesupport.aws.s3.S3;
import activesupport.system.Properties;
import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.response.ValidatableResponse;

import org.dvsa.testing.framework.stepdefs.Utils.Headers;
import org.dvsa.testing.framework.stepdefs.Utils.Internal.GenericUtils;
import org.dvsa.testing.framework.stepdefs.Utils.Internal.GrantApplicationAPI;
import org.dvsa.testing.framework.stepdefs.builders.UploadDocumentBuilder;
import org.dvsa.testing.lib.Environment;
import org.dvsa.testing.lib.Login;
import org.dvsa.testing.lib.URI;
import org.dvsa.testing.lib.browser.Browser;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;
import org.dvsa.testing.lib.utils.ApplicationType;
import org.dvsa.testing.lib.utils.EnvironmentType;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Map;
import java.util.zip.ZipInputStream;

import static org.dvsa.testing.framework.stepdefs.Utils.Headers.getHeaders;
import static org.dvsa.testing.framework.stepdefs.Utils.Internal.GenericUtils.payPsvFees;
import static org.dvsa.testing.framework.stepdefs.Utils.Internal.GenericUtils.zipFolder;

public class Scenarios extends BasePage {

    private static ValidatableResponse apiResponse;
    private static ValidatableResponse response;
    private static String env = System.getProperty("env");
    private static String baseURL = String.format("http://api.olcs.%s.nonprod.dvsa.aws/api/", env);// TODO need to update uri library to include api url

    public void esbrUpload(CreateInterimPsvLicenceAPI psvLicence) throws IOException {
        Headers.headers.put("x-pid", "e91f1a255e01e20021507465a845e7c24b3a1dc951a277b874c3bcd73dec97a1");

        String docUploadResource = "document/upload";
        UploadDocumentBuilder docUpload = new UploadDocumentBuilder().withFilename("PB2007625.zip").withContent("content")
                .withIsExternal("1").withIsEbsrPack("1").withApplication(psvLicence.getApplicationNumber()).withLicence(psvLicence.getLicenceNumber());
        apiResponse = multiPartPost(docUpload, baseURL.concat(docUploadResource), getHeaders());
        System.out.println(apiResponse.extract().response().asString());
//        assertThat(apiResponse.statusCode(HttpStatus.SC_CREATED));
    }

    public InputStream stream() throws IOException {
        File initialFile = new File("./src/test/resources/ESBR.zip");
        ZipInputStream zipInputStream = null;
        try {
            zipInputStream = new ZipInputStream(new FileInputStream(initialFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zipInputStream;
    }


    public ValidatableResponse multiPartPost(@NotNull Object requestBody, @NotNull String serviceEndPoint, @NotNull Map<String, String> headers) throws IOException {

        response = RestAssured.given()
                .headers(headers)
                .config(RestAssuredConfig.config().sslConfig((new SSLConfig()).relaxedHTTPSValidation().allowAllHostnames()))
                .multiPart(new MultiPartSpecBuilder(stream()).build())
                .multiPart("ZIP", requestBody, "application/json")
                .when()
                .post(serviceEndPoint).then();
        return response;
    }

    public static void generateAndGrantPsvApplicationPerTrafficArea(CreateInterimPsvLicenceAPI psvApp, GrantApplicationAPI grantApp, String trafficArea) {
        psvApp.setTrafficArea(trafficArea);
        psvApp.createAndSubmitPsvApp();
        payPsvFees(grantApp, psvApp);
        grantApp.payGrantFees(psvApp.getOrganisationId(), psvApp.getApplicationNumber());
    }

    public static void uploadAndSubmitESBR(GenericUtils genericUtils, CreateInterimPsvLicenceAPI psvApp, String state, int month) throws MissingRequiredArgument {
        // for the date state the options are ['current','past','future'] and depending on your choice the months you want to add/remove
        genericUtils.modifyXML(psvApp, state, month);
        zipFolder();

        EnvironmentType env = Environment.enumType(Properties.get("env", true));
        String URL = URI.build(ApplicationType.EXTERNAL, env);

        if (Browser.isInitialised()) {
            //Quit Browser and open a new window
            Browser.quit();
        }
        Browser.go(URL);
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
        uploadFile("//*[@id='fields[files][file]']", workingDir + "/src/test/resources/ESBR.zip", "document.getElementById('fields[files][file]').style.left = 0", SelectorType.XPATH);
        waitAndClick("//*[@name='form-actions[submit]']", SelectorType.XPATH);
    }
}

