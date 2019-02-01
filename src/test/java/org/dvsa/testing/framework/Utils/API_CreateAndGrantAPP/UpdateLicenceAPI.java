package org.dvsa.testing.framework.Utils.API_CreateAndGrantAPP;

import activesupport.MissingRequiredArgument;
import activesupport.http.RestUtils;
import activesupport.string.Str;
import activesupport.system.Properties;
import enums.LicenceType;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.dvsa.testing.framework.Utils.API_Builders.*;
import org.dvsa.testing.framework.Utils.API_Headers.Headers;
import Injectors.World;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.url.utils.EnvironmentType;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.dvsa.testing.framework.Utils.API_Headers.Headers.getHeaders;

public class UpdateLicenceAPI extends BasePage {
    private ValidatableResponse apiResponse;
    private static String variationApplicationNumber;
    private World world;
    private int caseId;
    public String adminUserEmailAddress = "adminUser@dvsavol.org";
    public String adminUserLogin = String.format("vol" + "%s", Str.randomWord(10));
    private String internalAdminHeader = "e91f1a255e01e20021507465a845e7c24b3a1dc951a277b874c3bcd73dec97a1";
    private String adminUserId;


    public String getVariationApplicationNumber() {
        return variationApplicationNumber;
    }

    public static void setVariationApplicationNumber(String variationApplicationNumber) {
        UpdateLicenceAPI.variationApplicationNumber = variationApplicationNumber;
    }

    public void setAdminUserId(String adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getAdminUserLogin() {
        return adminUserId;
    }

    public void setAdminUserEmailAddress(String adminUserEmailAddress) {
        this.adminUserEmailAddress = adminUserEmailAddress;
    }

    public int getCaseId() {
        return caseId;
    }

    private void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    private int complaintId;

    public int getComplaintId() {
        return complaintId;
    }

    private void setComplaintId(int complaintId) {
        this.complaintId = complaintId;
    }

    private int convictionId;

    public int getConvictionId() {
        return convictionId;
    }

    private void setConvictionId(int convictionId) {
        this.convictionId = convictionId;
    }

    private int conditionUndertaking;

    public int getConditionUndertaking() {
        return conditionUndertaking;
    }

    private void setConditionUndertaking(int conditionUndertaking) {
        this.conditionUndertaking = conditionUndertaking;
    }

    private int submissionsId;

    public int getSubmissionsId() {
        return submissionsId;
    }

    private void setSubmissionsId(int submissionsId) {
        this.submissionsId = submissionsId;
    }

    private int caseNoteId;

    public int getCaseNoteId() {
        return caseNoteId;
    }

    private void setCaseNoteId(int caseNoteId) {
        this.caseNoteId = caseNoteId;
    }

    private static int version = 1;


    private static EnvironmentType env;

    static {
        try {
            env = EnvironmentType.getEnum(Properties.get("env", false));
        } catch (MissingRequiredArgument missingRequiredArgument) {
            missingRequiredArgument.printStackTrace();
        }
    }

    public UpdateLicenceAPI(World world) {
        this.world = world;
    }

    public void createVariation(String variationType) throws MalformedURLException {
        String licenceId = world.createLicence.getLicenceId();
        String licenceHistoryResource = org.dvsa.testing.lib.url.api.URL.build(env, String.format("licence/%s/variation", licenceId)).toString();

        VariationBuilder variation = new VariationBuilder().withId(licenceId).withFeeRequired("N").withAppliedVia("applied_via_phone").withVariationType(variationType);
        apiResponse = RestUtils.post(variation, licenceHistoryResource, getHeaders());
        assertThat(apiResponse.statusCode(HttpStatus.SC_CREATED));
        setVariationApplicationNumber(String.valueOf(apiResponse.extract().jsonPath().getInt("id.application")));
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
        String description = "Sent through the API";
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
        String personFirstname = "API";
        String personLastname = "Director";
        String birthDate = "99-6-10";
        String convictionCategory = "conv_c_cat_1065";
        String categoryText = "Driver correcting entry in driver's record book in wrong fashion";
        String offenceDate = "18-4-1";
        String convictionDate = "18-6-10";
        String msi = "Y";
        String court = "CourtAPI";
        String penalty = "Heavy";
        String costs = "1000";
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
        setConvictionId(apiResponse.extract().jsonPath().getInt("id.conviction"));
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
        String driverForename = "Skish";
        String driverFamilyName = "Dotell";
        String complaintResource = org.dvsa.testing.lib.url.api.URL.build(env, "complaint").toString();
        CaseComplaintBuilder complaintBuilder = new CaseComplaintBuilder().withCase(caseId).withComplainantForename(complainantForename).withComplainantFamilyName(complainantFamilyName).withComplaintType(complaintType).withStatus(status).withIsCompliance(isCompliance)
                .withComplaintDate(complaintDate).withInfringementDate(infringementDate).withDescription(description).withDriverForename(driverForename).withDriverFamilyName(driverFamilyName);
        apiResponse = RestUtils.post(complaintBuilder, complaintResource, getHeaders());

        Assertions.assertThat(apiResponse.statusCode(HttpStatus.SC_CREATED));
        setComplaintId(apiResponse.extract().jsonPath().getInt("id.complaint"));
    }

    public void addConditionsUndertakings() throws MalformedURLException {
        String type = "cdt_con";
        String conditionCategory = "cu_cat_fin";
        String fulfilled = "N";
        String attachedTo = "cat_lic";
        String description = "This undertaken has not been fulfilled";
        String conditionsUndertaking = org.dvsa.testing.lib.url.api.URL.build(env, "condition-undertaking").toString();
        CaseConditionsBuilder conditionsBuilder = new CaseConditionsBuilder().withLicence(world.createLicence.getLicenceId()).withApplication(world.createLicence.getApplicationNumber()).withCase(Integer.toString(caseId)).withType(type).withConditionCategory(conditionCategory)
                .withFulfilled(fulfilled).withAttachedTo(attachedTo).withNotes(description);
        apiResponse = RestUtils.post(conditionsBuilder, conditionsUndertaking, getHeaders());

        Assertions.assertThat(apiResponse.statusCode(HttpStatus.SC_CREATED));
        setConditionUndertaking(apiResponse.extract().jsonPath().getInt("id.conditionUndertaking"));
    }

    public void createSubmission() throws MalformedURLException {
        String submissionType = "submission_type_o_env";
        String submissionResource = org.dvsa.testing.lib.url.api.URL.build(env, "submission").toString();
        CaseSubmissionBuilder submissionBuilder = new CaseSubmissionBuilder().withCase(Integer.toString(caseId)).withSubmissionType(submissionType);
        apiResponse = RestUtils.post(submissionBuilder, submissionResource, getHeaders());

        Assertions.assertThat(apiResponse.statusCode(HttpStatus.SC_CREATED));
        setSubmissionsId(apiResponse.extract().jsonPath().getInt("id.submission"));
    }

    public void createCaseNote() throws MalformedURLException {
        String comment = "case note submitted through the API";
        String priority = "Y";
        String caseNoteResource = org.dvsa.testing.lib.url.api.URL.build(env, "processing/note").toString();
        CaseNotesBuilder caseNotesBuilder = new CaseNotesBuilder().withCase(Integer.toString(caseId)).withLicence(world.createLicence.getLicenceId()).withApplication(world.createLicence.getApplicationNumber())
                .withComment(comment).withPriority(priority);
        apiResponse = RestUtils.post(caseNotesBuilder, caseNoteResource, getHeaders());

        Assertions.assertThat(apiResponse.statusCode(HttpStatus.SC_CREATED));
        setCaseNoteId(apiResponse.extract().jsonPath().getInt("id.note"));
    }

    public ValidatableResponse getCaseDetails(String resource, int id) throws MalformedURLException {
        String caseResource = org.dvsa.testing.lib.url.api.URL.build(env, String.format("%s/%s", resource, id)).toString();
        apiResponse = RestUtils.get(caseResource, getHeaders());
        return apiResponse;
    }

    public ValidatableResponse variationUpdateOperatingCentre() throws MalformedURLException {
        String noOfVehiclesRequired = "5";
        String licenceId = world.createLicence.getLicenceId();
        String updateOperatingCentreResource = org.dvsa.testing.lib.url.api.URL.build(env, String.format("application/%s/variation-operating-centre/%s", licenceId, variationApplicationNumber)).toString();
        OperatingCentreVariationBuilder updateOperatingCentre = new OperatingCentreVariationBuilder();

        do {
            if (world.createLicence.getOperatorType().equals("goods") && (!world.createLicence.getLicenceType().equals("special_restricted"))) {
                updateOperatingCentre.withId(variationApplicationNumber).withApplication(variationApplicationNumber)
                        .withNoOfVehiclesRequired(noOfVehiclesRequired).withVersion(version);
            }
            if (world.createLicence.getOperatorType().equals("public") && (!world.createLicence.getLicenceType().equals("special_restricted"))) {
                updateOperatingCentre.withId(variationApplicationNumber).withApplication(variationApplicationNumber)
                        .withNoOfVehiclesRequired(noOfVehiclesRequired).withVersion(version);
            }
            if (world.createLicence.getOperatorType().equals("public") && (world.createLicence.getLicenceType().equals("restricted"))) {
                updateOperatingCentre.withId(variationApplicationNumber).withApplication(variationApplicationNumber)
                        .withNoOfVehiclesRequired(noOfVehiclesRequired).withVersion(version);
            }
            if (!world.createLicence.getLicenceType().equals("special_restricted")) {
                apiResponse = RestUtils.put(updateOperatingCentre, updateOperatingCentreResource, getHeaders());
                version++;
                if (version > 20) {
                    version = 1;
                }
            }
        }
        while (apiResponse.extract().statusCode() == HttpStatus.SC_CONFLICT);
        if (apiResponse.extract().statusCode() != HttpStatus.SC_OK) {
            System.out.println(apiResponse.extract().response().asString());
        }
        return apiResponse;
    }

    public void createInternalAdminUser() {
        List<String> roles = new ArrayList<>();
        roles.add("internal-admin");
        String team = "1";
        String userType = "internal";
        Headers.headers.put("x-pid", internalAdminHeader);
        String internalAdminUserResource = org.dvsa.testing.lib.url.api.URL.build(env, "user/internal").toString();

        AddressBuilder addressBuilder = new AddressBuilder().withAddressLine1("AXIS Building").withTown("Nottingham").withPostcode("LS28 5LY").withCountryCode("GB");
        PersonBuilder personBuilder = new PersonBuilder().withForename("Kish").withFamilyName("Ann").withBirthDate(getPastYear(30) + "-" + getCurrentMonth() + "-" + getCurrentDayOfMonth());

        ContactDetailsBuilder contactDetails = new ContactDetailsBuilder().withEmailAddress(adminUserEmailAddress).withAddress(addressBuilder).withPerson(personBuilder);
        CreateInternalAdminUser internalAdminUser = new CreateInternalAdminUser().withContactDetails(contactDetails).withLoginId(adminUserLogin).withRoles(roles).withTeam(team).withUserType(userType);
        apiResponse = RestUtils.post(internalAdminUser, internalAdminUserResource, getHeaders());

        if (apiResponse.extract().statusCode() != HttpStatus.SC_CREATED) {
            System.out.println("+++ERROR+++" + apiResponse.extract().response().asString());
        }
        else {

            setAdminUserId(apiResponse.extract().response().jsonPath().getString("id.user"));
        }

    }

    public ValidatableResponse grantVariation(String resource) throws MalformedURLException {
        String grantVariation = org.dvsa.testing.lib.url.api.URL.build(env, String.format("variation/%s/%s", variationApplicationNumber, resource)).toString();

        GenericBuilder genericBuilder = new GenericBuilder().withId(variationApplicationNumber);
        apiResponse = RestUtils.put(genericBuilder, grantVariation, getHeaders());
        return apiResponse;
    }
}