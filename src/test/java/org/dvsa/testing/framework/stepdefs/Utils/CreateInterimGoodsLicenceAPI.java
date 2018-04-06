package org.dvsa.testing.framework.stepdefs.Utils;

import activesupport.http.RestUtils;
import enums.BusinessType;
import enums.LicenceType;
import enums.OperatorType;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.dvsa.testing.framework.stepdefs.builders.*;
import activesupport.string.Str;
import activesupport.number.Int;


import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dvsa.testing.framework.stepdefs.Utils.Headers.getHeaders;


public class CreateInterimGoodsLicenceAPI {

    private static ValidatableResponse apiResponse;

    private String loginId = Str.randomWord(5);
    private String title = "title_mr";
    private String foreName = Str.randomWord(5);
    private String familyName = Str.randomWord(8);
    private String birthDate = String.valueOf(Int.random(1900, 2018) + "-" + Int.random(1, 12) + "-" + Int.random(1, 28));
    private String addressLine1 = "API House";
    private String town = "Nottingham";
    private String postcode = "NG23HX";
    private String countryCode = "GB";
    private String emailAddress = Str.randomWord(6).concat("tester@dvsa.com");
    private String organisationName = Str.randomWord(10);


    private static String env = System.getProperty("env");
    private static String baseURL = String.format("http://api.olcs.%s.nonprod.dvsa.aws/api/", env);// TODO need to update uri library to include api url

    private static int version = 1;
    private static String applicationNumber;
    private static String userId;
    private static String pid;
    private static String organisationId;
    private static String licenceNumber;
    private static String transportManagerApplicationId;
    private static String companyNumber = String.valueOf(Int.random(00000000, 99999999));
    private static int noOfVehiclesRequired = 5;

    public static int getNoOfVehiclesRequired() {
        return noOfVehiclesRequired;
    }

    public static String getApplicationNumber() {
        return applicationNumber;
    }

    public void createGoodsApp() {
        registerUser();
        getUserDetails();
        createApplication();
        updateBusinessType();
        updateBusinessDetails();
        addAddressDetails();
        addPartners();
        addOperatingCentre();
        updateOperatingCentre();
        addFinancialEvidence();
        addTransportManager();
        submitTransport();
        vehicles();
        addFinancialHistory();
        addApplicationSafetyAndComplianceDetails();
        addSafetyInspector();
        addConvictionsDetails();
        addLicenceHistory();
        reviewAndDeclare();
        submitApplication();
    }

    public void registerUser() {
        String registerResource = "user/selfserve/register";
        Headers.headers.put("api", "dvsa");

        PersonBuilder personBuilder = new PersonBuilder().withTitle(title).withForename(foreName).withFamilyName(familyName).withBirthDate(birthDate);
        ContactDetailsBuilder contactDetailsBuilder = new ContactDetailsBuilder().withEmailAddress(emailAddress).withPerson(personBuilder);
        SelfServeUserRegistrationDetailsBuilder selfServeUserRegistrationDetailsBuilder = new SelfServeUserRegistrationDetailsBuilder().withLoginId(loginId).withContactDetails(contactDetailsBuilder)
                .withOrganisationName(organisationName).withBusinessType(String.valueOf(BusinessType.getEnum("limited_company")));
        apiResponse = RestUtils.post(selfServeUserRegistrationDetailsBuilder, baseURL.concat(registerResource), getHeaders());
        assertThat(apiResponse.statusCode(HttpStatus.SC_CREATED));
        userId = apiResponse.extract().jsonPath().getString("id.user");
    }

    public void getUserDetails() {
        Headers.headers.put("x-pid", "e91f1a255e01e20021507465a845e7c24b3a1dc951a277b874c3bcd73dec97a1");

        String userDetailsResource = String.format("user/selfserve/%s", userId);
        apiResponse = RestUtils.get(baseURL.concat(userDetailsResource), getHeaders());
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
        pid = apiResponse.extract().jsonPath().getString("pid");
        organisationId = apiResponse.extract().jsonPath().prettyPeek().getString("organisationUsers.organisation.id");
    }

    public void createApplication() {
        String niFlag = "N";
        String createApplicationResource = "application";
        Headers.headers.put("x-pid", pid);
        HashMap<String, String> headers = getHeaders();
        ApplicationBuilder applicationBuilder = new ApplicationBuilder().withOperatorType(String.valueOf(OperatorType.getEnum("goods")))
                .withLicenceType(String.valueOf(LicenceType.getEnum("standard_international"))).withNiFlag(niFlag).withOrganisation(organisationId);
        apiResponse = RestUtils.post(applicationBuilder, baseURL.concat(createApplicationResource), headers);
        assertThat(apiResponse.statusCode(HttpStatus.SC_CREATED));
        applicationNumber = apiResponse.extract().jsonPath().getString("id.application");
        licenceNumber = apiResponse.extract().jsonPath().getString("id.licence");
    }

    public void updateBusinessType() {
        String updateBusinessTypeResource = String.format("organisation/%s/business-type/", organisationId);
            BusinessTypeBuilder businessTypeBuilder = new BusinessTypeBuilder().withBusinessType(String.valueOf(BusinessType.getEnum("limited_company"))).withVersion(String.valueOf(version))
                    .withId(organisationId).withApplication(applicationNumber);
            apiResponse = RestUtils.put(businessTypeBuilder, baseURL.concat(updateBusinessTypeResource), getHeaders());
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
    }

    public void updateBusinessDetails() {
        String natureOfBusiness = "apiTesting";
        String updateBusinessDetailsResource = String.format("organisation/business-details/application/%s", licenceNumber);
            AddressBuilder address = new AddressBuilder().withAddressLine1(addressLine1).withTown(town).withPostcode(postcode);
            UpdateBusinessDetailsBuilder businessDetails = new UpdateBusinessDetailsBuilder()
                    .withId(applicationNumber).withCompanyNumber(companyNumber).withNatureOfBusiness(natureOfBusiness).withLicence(licenceNumber)
                    .withVersion(String.valueOf(version)).withName(natureOfBusiness).withAddress(address);
            apiResponse = RestUtils.put(businessDetails, baseURL.concat(updateBusinessDetailsResource), getHeaders());
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
    }

    public void addAddressDetails() {
        String phoneNumber = "0712345678";
        String applicationAddressResource = String.format("application/%s/addresses/", applicationNumber);
        AddressBuilder address = new AddressBuilder().withAddressLine1(addressLine1).withTown(town).withPostcode(postcode).withCountryCode(countryCode);
        ContactDetailsBuilder contactDetailsBuilder = new ContactDetailsBuilder().withPhoneNumber(phoneNumber).withEmailAddress(emailAddress);
        ApplicationAddressBuilder addressBuilder = new ApplicationAddressBuilder().withId(applicationNumber).withConsultant("Consult").withContact(contactDetailsBuilder)
                .withCorrespondenceAddress(address).withEstablishmentAddress(address);
        apiResponse = RestUtils.put(addressBuilder, baseURL.concat(applicationAddressResource), getHeaders());
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
    }

    public void addPartners() {
        String addPersonResource = String.format("application/%s/people/", applicationNumber);
        PersonBuilder addPerson = new PersonBuilder().withId(applicationNumber).withTitle(title).withForename(foreName).withFamilyName(familyName).withBirthDate(birthDate);
        apiResponse = RestUtils.post(addPerson, baseURL.concat(addPersonResource), getHeaders());
        assertThat(apiResponse.statusCode(HttpStatus.SC_CREATED));
    }

    public void addOperatingCentre() {
        String operatingCentreResource = String.format("application/%s/operating-centre/", applicationNumber);
        String permissionOption = "Y";
        AddressBuilder address = new AddressBuilder().withAddressLine1(addressLine1).withTown(town).withPostcode(postcode).withCountryCode(countryCode);
        OperatingCentreBuilder operatingCentreBuilder = new OperatingCentreBuilder().withApplication(applicationNumber).withNoOfVehiclesRequired(String.valueOf(noOfVehiclesRequired))
                .withNoOfTrailersRequired(String.valueOf(noOfVehiclesRequired)).withPermission(permissionOption).withAddress(address);
        apiResponse = RestUtils.post(operatingCentreBuilder, baseURL.concat(operatingCentreResource), getHeaders());
        assertThat(apiResponse.statusCode(HttpStatus.SC_CREATED));
    }

    public void updateOperatingCentre() {
        String trafficArea = "D";
        String updateOperatingCentreResource = String.format("application/%s/operating-centres", applicationNumber);

        do {
            OperatingCentreUpdater updateOperatingCentre = new OperatingCentreUpdater().withId(applicationNumber).withTotAuthVehicles(noOfVehiclesRequired)
                    .withTrafficArea(trafficArea).withTAuthTrailers(Integer.parseInt(String.valueOf(noOfVehiclesRequired))).withTotCommunityLicences(noOfVehiclesRequired).withVersion(version);
            apiResponse = RestUtils.put(updateOperatingCentre, baseURL.concat(updateOperatingCentreResource), getHeaders());
            version++;
        } while (apiResponse.extract().statusCode() == HttpStatus.SC_CONFLICT);
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
    }

    public void addFinancialEvidence() {
        String financialEvidenceResource = String.format("application/%s/financial-evidence", applicationNumber);

        do {
            FinancialEvidenceBuilder financialEvidenceBuilder = new FinancialEvidenceBuilder().withId(applicationNumber).withVersion(version).withFinancialEvidenceUploaded(0);
            apiResponse = RestUtils.put(financialEvidenceBuilder, baseURL.concat(financialEvidenceResource), getHeaders());
            version++;
        } while (apiResponse.extract().statusCode() == HttpStatus.SC_CONFLICT);
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
    }

    public void addTransportManager() {
        String hasEmail = "Y";
        String addTransportManager = "transport-manager/create-new-user/";
        TransportManagerBuilder transportManagerBuilder = new TransportManagerBuilder().withApplication(applicationNumber).withFirstName(foreName)
                .withFamilyName(familyName).withHasEmail(hasEmail).withUsername("api".concat(foreName)).withEmailAddress(emailAddress).withBirthDate(birthDate);
        apiResponse = RestUtils.post(transportManagerBuilder, baseURL.concat(addTransportManager), getHeaders());
        assertThat(apiResponse.statusCode(HttpStatus.SC_CREATED));
        transportManagerApplicationId = apiResponse.extract().jsonPath().getString("id.transportManagerApplicationId");
    }

    public void submitTransport() {
        String submitTransportManager = String.format("transport-manager-application/%s/submit", applicationNumber);
        GenericBuilder genericBuilder = new GenericBuilder().withId(transportManagerApplicationId).withVersion(1);
        apiResponse = RestUtils.put(genericBuilder, baseURL.concat(submitTransportManager), getHeaders());
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
    }

    public void vehicles() {
        String hasEnteredReg = "N";
        String psvVehiclesResource = String.format("application/%s/vehicles", applicationNumber);

        do {
            VehiclesBuilder psvVehiclesBuilder = new VehiclesBuilder().withId(applicationNumber).withHasEnteredReg(hasEnteredReg).withVersion(version);
            apiResponse = RestUtils.put(psvVehiclesBuilder, baseURL.concat(psvVehiclesResource), getHeaders());
        } while (apiResponse.extract().statusCode() == HttpStatus.SC_CONFLICT);
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
    }

    public void addFinancialHistory() {
        String financialHistoryAnswer = "N";
        String insolvencyAnswer = "false";
        String financialHistoryResource = String.format("application/%s/financial-history", applicationNumber);

        do {
            FinancialHistoryBuilder financialHistoryBuilder = new FinancialHistoryBuilder().withId(applicationNumber).withVersion(String.valueOf(version)).withBankrupt(financialHistoryAnswer)
                    .withLiquidation(financialHistoryAnswer).withReceivership(financialHistoryAnswer).withAdministration(financialHistoryAnswer).withAdministration(financialHistoryAnswer)
                    .withDisqualified(financialHistoryAnswer).withInsolvencyDetails(insolvencyAnswer).withInsolvencyConfirmation(insolvencyAnswer);
            apiResponse = RestUtils.put(financialHistoryBuilder, baseURL.concat(financialHistoryResource), getHeaders());
            version++;
        } while (apiResponse.extract().statusCode() == HttpStatus.SC_CONFLICT);
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
    }

    public void addApplicationSafetyAndComplianceDetails() {
        String tachographIns = "tach_na";
        String safetyInsVaries = "N";
        String safetyConfirmationOption = "Y";
        String applicationSafetyResource = String.format("application/%s/safety", applicationNumber);

        do {
            Licence licence = new Licence().withId(licenceNumber).withVersion(version).withSafetyInsVaries(safetyInsVaries).withSafetyInsVehicles(String.valueOf(noOfVehiclesRequired))
                    .withSafetyInsTrailers(String.valueOf(noOfVehiclesRequired)).withTachographIns(tachographIns);
            ApplicationSafetyBuilder applicationSafetyBuilder = new ApplicationSafetyBuilder().withId(applicationNumber).withVersion(version)
                    .withSafetyConfirmation(safetyConfirmationOption).withLicence(licence);
            apiResponse = RestUtils.put(applicationSafetyBuilder, baseURL.concat(applicationSafetyResource), getHeaders());
            version++;
        } while (apiResponse.extract().statusCode() == HttpStatus.SC_CONFLICT);
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
    }

    public void addSafetyInspector() {
        String safetyInspectorResource = String.format("application/%s/workshop", applicationNumber);
        AddressBuilder addressBuilder = new AddressBuilder().withAddressLine1(addressLine1).withTown(town).withPostcode(postcode).withCountryCode(countryCode);
        ContactDetailsBuilder contactDetailsBuilder = new ContactDetailsBuilder().withFao(foreName).withAddress(addressBuilder);
        SafetyInspectorBuilder safetyInspectorBuilder = new SafetyInspectorBuilder().withApplication(applicationNumber).withLicence(licenceNumber).withIsExternal("N")
                .withContactDetails(contactDetailsBuilder);
        apiResponse = RestUtils.post(safetyInspectorBuilder, baseURL.concat(safetyInspectorResource), getHeaders());
        assertThat(apiResponse.statusCode(HttpStatus.SC_CREATED));

    }

    public void addConvictionsDetails() {
        String previousConvictionsResource = String.format("application/%s/previous-convictions", applicationNumber);

        do {
            ConvictionsPenaltiesBuilder convictionsPenaltiesBuilder = new ConvictionsPenaltiesBuilder().withId(applicationNumber).withConvictionsConfirmation("Y")
                    .withPrevConviction("N").withVersion(version);
            apiResponse = RestUtils.put(convictionsPenaltiesBuilder, baseURL.concat(previousConvictionsResource), getHeaders());
            version++;
        } while (apiResponse.extract().statusCode() == HttpStatus.SC_CONFLICT);
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
    }

    public void addLicenceHistory() {
        String optionResponse = "N";
        String licenceHistoryResource = String.format("application/%s/licence-history", applicationNumber);

        do {
            LicenceHistoryBuilder licenceHistoryBuilder = new LicenceHistoryBuilder().withId(applicationNumber).withPrevHadLicence(optionResponse).withPrevHasLicence(optionResponse)
                    .withPrevBeenAtPi(optionResponse).withPrevBeenDisqualifiedTc(optionResponse).withPrevBeenRefused(optionResponse).withPrevBeenRevoked(optionResponse).withPrevPurchasedAssets(optionResponse)
                    .withVersion(version);
            apiResponse = RestUtils.put(licenceHistoryBuilder, baseURL.concat(licenceHistoryResource), getHeaders());
            version++;
        } while (apiResponse.extract().statusCode() == HttpStatus.SC_CONFLICT);
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
    }

    public void reviewAndDeclare() {
        String interimReason = "Testing through the API";
        String isInterim = "Y";
        String declarationConfirmation = "Y";
        String signatureRequired = "sig_physical_signature";
        String reviewResource = String.format("application/%s/declaration/", applicationNumber);

        do {
            DeclarationsAndUndertakings undertakings = new DeclarationsAndUndertakings().withId(applicationNumber).withVersion(String.valueOf(version)).withInterimRequested(isInterim)
                    .withInterimReason(interimReason).withSignatureType(signatureRequired).withDeclarationConfirmation(declarationConfirmation);
            apiResponse = RestUtils.put(undertakings, baseURL.concat(reviewResource), getHeaders());
            version++;
        } while (apiResponse.extract().statusCode() == HttpStatus.SC_CONFLICT);
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
    }

    public void submitApplication() {
        String submitResource = String.format("application/%s/submit", applicationNumber);

        do {
            GenericBuilder genericBuilder = new GenericBuilder().withId(applicationNumber).withVersion(version);
            apiResponse = RestUtils.put(genericBuilder, baseURL.concat(submitResource), getHeaders());
        } while (apiResponse.extract().statusCode() == HttpStatus.SC_CONFLICT);
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
    }
}