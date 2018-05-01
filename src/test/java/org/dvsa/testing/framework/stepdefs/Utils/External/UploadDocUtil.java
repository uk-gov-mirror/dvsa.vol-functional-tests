package org.dvsa.testing.framework.stepdefs.Utils.External;

import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.dvsa.testing.framework.stepdefs.Utils.Headers;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dvsa.testing.framework.stepdefs.Utils.Headers.getHeaders;

public class UploadDocUtil {

    private static ValidatableResponse apiResponse;
    private static String env = System.getProperty("env");
    private static String baseURL = String.format("http://api.olcs.%s.nonprod.dvsa.aws/api/", env);// TODO need to update uri library to include api url
    CreateInterimPsvLicenceAPI psvLicence = new CreateInterimPsvLicenceAPI();

    public void esbrupload() throws FileNotFoundException {
        Headers.headers.put("x-pid", "e91f1a255e01e20021507465a845e7c24b3a1dc951a277b874c3bcd73dec97a1");

        String docUploadResource = "document/upload";
        UploadDocumentBuilder docUpload = new UploadDocumentBuilder().withFilename("PB2010099.zip").withContent("src/test/resources/PB2010099.zip").withCategory("3").withSubCategory("107")
                .withDescription("ESBR").withIsExternal("1").withIsEbsrPack("1").withApplication(psvLicence.getApplicationNumber()).withLicence(psvLicence.getLicenceNumber());
        apiResponse = multiPartPost(docUpload,baseURL.concat(docUploadResource), getHeaders());
        assertThat(apiResponse.statusCode(HttpStatus.SC_CREATED));
    }

    public InputStream stream() throws FileNotFoundException {
        File initialFile = new File("src/test/resources/PB2010099.zip");
        InputStream targetStream = new FileInputStream(initialFile);
        return targetStream;
    }

    public static ValidatableResponse multiPartPost(@NotNull Object requestBody, @NotNull String serviceEndPoint, @NotNull Map<String, String> headers) {
        apiResponse = (ValidatableResponse)((Response)((RequestSpecification)RestAssured.given()
                .urlEncodingEnabled(true).log().all()).contentType(ContentType.JSON).
                headers(headers).body(requestBody).when().
                multiPart("content", new File("src/test/resources/PB2010099.zip")).
                config(RestAssuredConfig.config().
                        sslConfig((new SSLConfig()).relaxedHTTPSValidation().allowAllHostnames()))
                .post(serviceEndPoint, new Object[0])).then();
        return apiResponse;
    }
}
