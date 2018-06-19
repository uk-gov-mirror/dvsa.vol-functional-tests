package org.dvsa.testing.framework.Utils.API_CreateAndGrantAPP;

import activesupport.MissingRequiredArgument;
import activesupport.http.RestUtils;
import activesupport.system.Properties;
import enums.LicenceType;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.dvsa.testing.framework.Utils.API_Builders.*;
import org.dvsa.testing.framework.stepdefs.World;
import org.dvsa.testing.lib.url.utils.EnvironmentType;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.dvsa.testing.framework.Utils.API_Headers.Headers.getHeaders;

public class UpdateLicenceAPI {
    private ValidatableResponse apiResponse;
    private static String variationApplicationNumber;
    private World world;

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    private static int version = 1;
    private int caseId;

    private static EnvironmentType env;

    static {
        try {
            env = EnvironmentType.getEnum(Properties.get("env", false));
        } catch (MissingRequiredArgument missingRequiredArgument) {
            missingRequiredArgument.printStackTrace();
        }
    }

    public UpdateLicenceAPI(World world){
        this.world = world;
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

    public void createCase() throws MalformedURLException {
        String caseType = "case_t_lic";
        List<String> categories = new ArrayList<>();
        categories.add("case_cat_compl_conv");
        categories.add("case_cat_compl_proh");
        String description = "Sent thorugh the API";
        List<String> outcomes = new ArrayList<>();
        outcomes.add("case_o_other");
        outcomes.add("case_o_cur");
        String caseResource = org.dvsa.testing.lib.url.api.URL.build(env, "cases").toString();
        do {
            CaseBuilder caseBuilder = new CaseBuilder().withId(world.createLicence.getLicenceId()).withVersion(version).withCaseType(caseType).
                    withCategorys(categories).withDescription(description).withOutcomes(outcomes).withApplication(world.createLicence.getApplicationNumber());
            apiResponse = RestUtils.post(caseBuilder, caseResource, getHeaders());
            version++;
            if (version > 20) {
                version = 1;
            }
        }
        while (apiResponse.extract().statusCode() == HttpStatus.SC_CONFLICT);
        setCaseId(apiResponse.extract().body().jsonPath().get("id.case"));
        Assertions.assertThat(apiResponse.statusCode(HttpStatus.SC_CREATED));
    }

    public void addConviction() throws MalformedURLException {
        String defendantType = "def_t_dir";
        String personFirstname ="API";
        String personLastname = "Director";
        String birthDate = "99-6-10";
        String convictionCategory = "conv_c_cat_1065";
        String categoryText = "Driver correcting entry in driver's record book in wrong fashion";
        String offenceDate = "18-4-1";
        String convictionDate = "18-6-10";
        String msi = "Y";
        String court = "CourtAPI";
        String penalty = "Heavy";
        String costs= "1000";
        String notes = "This has been submitted";
        String isDeclared = "Y";
        String isDealtWith = "Y";
        String takenIntoConsideration = "Y";
        String convictionResource = org.dvsa.testing.lib.url.api.URL.build(env, "conviction").toString();
        do {
            CaseConvictionBuilder caseConvictionBuilder = new CaseConvictionBuilder().withCase(caseId).withConvictionCategory(convictionCategory).withConvictionDate(convictionDate).withBirthDate(birthDate).withCategoryText(categoryText).withCosts(costs)
                    .withCourt(court).withMsi(msi).withPenalty(penalty).withNotes(notes).withTakenIntoConsideration(takenIntoConsideration).withIsDeclared(isDeclared).withIsDealtWith(isDealtWith).withDefendantType(defendantType)
                    .withPersonFirstname(personFirstname).withPersonLastname(personLastname).withOffenceDate(offenceDate).withVersion(version);
            apiResponse = RestUtils.post(caseConvictionBuilder, convictionResource, getHeaders());
            version++;
            if (version > 20) {
                version = 1;
            }
        }
        while (apiResponse.extract().statusCode() == HttpStatus.SC_CONFLICT);
        Assertions.assertThat(apiResponse.statusCode(HttpStatus.SC_CREATED));

    }

    public void addComplaint() throws MalformedURLException {
        String complainantForename = "123456";
        String complainantFamilyName = "503";
        String complaintType = "ct_cov";
        String status = "cs_yst";
        String isCompliance = "true";
        String complaintDate = "18-4-1";
        String infringementDate = "17-4-1";
        String description = "Driver correcting entry in driver's record book in wrong fashion";
        String driverForename = "John";
        String driverFamilyName = "Skishan";
        String complaintResource = org.dvsa.testing.lib.url.api.URL.build(env, "complaint").toString();
            ComplaintBuilder complaintBuilder = new ComplaintBuilder().withCase(caseId).withComplainantForename(complainantForename).withComplainantFamilyName(complainantFamilyName).withComplaintType(complaintType).withStatus(status).withIsCompliance(isCompliance)
                    .withComplaintDate(complaintDate).withInfringementDate(infringementDate).withDescription(description).withDriverForename(driverForename).withDriverFamilyName(driverFamilyName);
            apiResponse = RestUtils.post(complaintBuilder, complaintResource, getHeaders());

        Assertions.assertThat(apiResponse.statusCode(HttpStatus.SC_CREATED));

    }
}
