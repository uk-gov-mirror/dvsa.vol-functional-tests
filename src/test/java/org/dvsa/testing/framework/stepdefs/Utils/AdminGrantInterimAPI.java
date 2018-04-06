package org.dvsa.testing.framework.stepdefs.Utils;

import activesupport.http.RestUtils;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.dvsa.testing.framework.stepdefs.builders.AdminInterimBuilder;
import org.dvsa.testing.framework.stepdefs.builders.GenericBuilder;
import org.joda.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dvsa.testing.framework.stepdefs.Utils.CreateInterimGoodsLicenceAPI.getApplicationNumber;
import static org.dvsa.testing.framework.stepdefs.Utils.Headers.getHeaders;

public class AdminGrantInterimAPI {

    private static ValidatableResponse apiResponse;

    private static int version = 1;
    private static int interimVehicle = 1;
    private static int interimTrailer = 1;
    private static String env = System.getProperty("env");
    private static String baseURL = String.format("http://api.olcs.%s.nonprod.dvsa.aws/api/", env);

    public void addInterimDetails() {
        String reason = "Grant an interim as an internal admin through the API";
        String requested = "Y";
        String status = "int_sts_granted";
        String action = "grant";
        String startDate = LocalDate.now().toString("yyyy-MM-dd");
        String endDate = LocalDate.now().plusDays(10).plusMonths(1).toString("yyyy-MM-dd");

        String addInterimDetailsResource = String.format("application/%s/interim");

        do {
            AdminInterimBuilder grantInterim = new AdminInterimBuilder().withId(getApplicationNumber()).withVersion(version).withRequested(requested).withReason(reason)
                    .withStartDate(startDate).withEndDate(endDate).withAuthVehicles(interimVehicle).withAuthTrailers(interimTrailer);
            apiResponse = RestUtils.put(grantInterim, baseURL.concat(addInterimDetailsResource), getHeaders());
            version++;
        } while (apiResponse.extract().statusCode() == HttpStatus.SC_CONFLICT);
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
    }

    public void grantInterimApplication() {
        String grantInterimResource = String.format("application/%s/interim/grant");

        GenericBuilder genericBuilder = new GenericBuilder().withId(getApplicationNumber());
        apiResponse = RestUtils.post(genericBuilder, baseURL.concat(grantInterimResource), getHeaders());
        assertThat(apiResponse.statusCode(HttpStatus.SC_CREATED));
    }
}