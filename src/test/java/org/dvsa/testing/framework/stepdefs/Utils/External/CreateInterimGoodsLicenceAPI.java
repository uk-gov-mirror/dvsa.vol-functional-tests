package org.dvsa.testing.framework.stepdefs.Utils.External;

import activesupport.MissingRequiredArgument;
import activesupport.http.RestUtils;
import activesupport.system.Properties;
import enums.BusinessType;
import enums.LicenceType;
import enums.OperatorType;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.dvsa.testing.framework.stepdefs.Utils.Headers;
import org.dvsa.testing.framework.stepdefs.apiBuilders.*;
import activesupport.string.Str;
import activesupport.number.Int;
import org.dvsa.testing.lib.url.utils.EnvironmentType;
import org.dvsa.testing.lib.url.api.URL;

import java.net.MalformedURLException;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dvsa.testing.framework.stepdefs.Utils.Headers.getHeaders;

public class CreateInterimGoodsLicenceAPI {

    private static ValidatableResponse apiResponse;

    private static String businessVersion = "1";
    private static String adminUserHeader = "e91f1a255e01e20021507465a845e7c24b3a1dc951a277b874c3bcd73dec97a1";

    private String title = "title_mr";
    private String foreName = Str.randomWord(5);
    private String familyName = Str.randomWord(8);
    private String birthDate = String.valueOf(Int.random(1900, 2018) + "-" + Int.random(1, 12) + "-" + Int.random(1, 28));
    private String addressLine1 = "API House";
    private String town = "Nottingham";
    private String postcode = "NG23HX";
    private String countryCode = "GB";
    private String organisationName = Str.randomWord(10);
    private String emailAddress = Str.randomWord(6).concat(".tester@dvsa.com");
    private String transManEmailAddress = Str.randomWord(6).concat(".TM@dvsa.com");
    private String applicationNumber;
    private String userId;
    private String username = "apiTM";
    private String loginId;
    private String pid;
    private String organisationId;
    private String licenceNumber;
    private String transportManagerApplicationId;
    private String companyNumber = String.valueOf(Int.random(00000000, 99999999));
    private String licenceType = String.valueOf(LicenceType.getEnum("standard_international"));

    private int version = 1;
    private int noOfVehiclesRequired = 5;

    EnvironmentType env = EnvironmentType.getEnum(Properties.get("env", true));

    public CreateInterimGoodsLicenceAPI() throws MissingRequiredArgument { }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public String getLicenceNumber() { return licenceNumber; }

    public void setNoOfVehiclesRequired(int noOfVehiclesRequired) {
        this.noOfVehiclesRequired = noOfVehiclesRequired;
    }

    public int getNoOfVehiclesRequired() {
        return noOfVehiclesRequired;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setOrganisationId(String organisationId) {
        this.organisationId = organisationId;
    }

    public String getOrganisationId() {
        return organisationId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getForeName() {
        return foreName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String licenceId;

    public String getLicenceId() {
        return licenceId;
    }

    public void setLicenceId(String licenceId) {
        this.licenceId = licenceId;
    }

    public String getLicenceType() { return licenceType; }

    public void setLicenceType(String licenceType) { this.licenceType = licenceType; }

    public String getTransportManagerApplicationId() { return transportManagerApplicationId; }

    public void setTransportManagerApplicationId(String transportManagerApplicationId) { this.transportManagerApplicationId = transportManagerApplicationId; }

    public void createAndSubmitGoodsApp() throws Exception {
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
        getApplicationLicenceDetails();
    }

    public void registerUser() throws MalformedURLException {
        String registerResource = URL.build(env,"user/selfserve/register").toString();

        Headers.headers.put("api", "dvsa");
        setLoginId(Str.randomWord(8));
        PersonBuilder personBuilder = new PersonBuilder().withTitle(title).withForename(getForeName()).withFamilyName(getFamilyName()).withBirthDate(birthDate);
        ContactDetailsBuilder contactDetailsBuilder = new ContactDetailsBuilder().withEmailAddress(emailAddress).withPerson(personBuilder);
        SelfServeUserRegistrationDetailsBuilder selfServeUserRegistrationDetailsBuilder = new SelfServeUserRegistrationDetailsBuilder().withLoginId(getLoginId()).withContactDetails(contactDetailsBuilder)
                .withOrganisationName(organisationName).withBusinessType(String.valueOf(BusinessType.getEnum("limited_company")));
        apiResponse = RestUtils.post(selfServeUserRegistrationDetailsBuilder, registerResource, getHeaders());
        assertThat(apiResponse.statusCode(HttpStatus.SC_CREATED));
        userId = apiResponse.extract().jsonPath().getString("id.user");
    }

    public void getUserDetails() throws MalformedURLException {
        Headers.headers.put("x-pid", adminUserHeader);

        String userDetailsResource = URL.build(env,String.format("user/selfserve/%s",userId)).toString();
        apiResponse = RestUtils.get(userDetailsResource, getHeaders());
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
        setPid(apiResponse.extract().jsonPath().getString("pid"));
        organisationId = apiResponse.extract().jsonPath().prettyPeek().getString("organisationUsers.organisation.id");
        setOrganisationId(organisationId);
    }

    public void createApplication() throws MalformedURLException {
        String niFlag = "N";
        String createApplicationResource = URL.build(env,"application").toString();
        Headers.headers.put("x-pid", pid);
        HashMap<String, String> headers = getHeaders();
        ApplicationBuilder applicationBuilder = new ApplicationBuilder().withOperatorType(String.valueOf(OperatorType.getEnum("goods")))
                .withLicenceType(licenceType).withNiFlag(niFlag).withOrganisation(organisationId);
        apiResponse = RestUtils.post(applicationBuilder, createApplicationResource, headers);
        assertThat(apiResponse.statusCode(HttpStatus.SC_CREATED));
        applicationNumber = apiResponse.extract().jsonPath().getString("id.application");
        setApplicationNumber(applicationNumber);
        licenceNumber = apiResponse.extract().jsonPath().getString("id.licence");
    }

    public void updateBusinessType() throws MalformedURLException {
        String updateBusinessTypeResource = URL.build(env,String.format("organisation/%s/business-type/", organisationId)).toString();
        BusinessTypeBuilder businessTypeBuilder = new BusinessTypeBuilder().withBusinessType(String.valueOf(BusinessType.getEnum("limited_company"))).withVersion(businessVersion)
                .withId(organisationId).withApplication(applicationNumber);
        apiResponse = RestUtils.put(businessTypeBuilder, updateBusinessTypeResource, getHeaders());
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
    }

    public void updateBusinessDetails() throws MalformedURLException {
        String natureOfBusiness = "apiTesting";
        String updateBusinessDetailsResource = URL.build(env,String.format("organisation/business-details/application/%s", licenceNumber)).toString();
        AddressBuilder address = new AddressBuilder().withAddressLine1(addressLine1).withTown(town).withPostcode(postcode);
        UpdateBusinessDetailsBuilder businessDetails = new UpdateBusinessDetailsBuilder()
                .withId(applicationNumber).withCompanyNumber(companyNumber).withNatureOfBusiness(natureOfBusiness).withLicence(licenceNumber)
                .withVersion(businessVersion).withName(natureOfBusiness).withAddress(address);
        apiResponse = RestUtils.put(businessDetails, updateBusinessDetailsResource, getHeaders());
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
    }

    public void addAddressDetails() throws MalformedURLException {
        String phoneNumber = "0712345678";
        String applicationAddressResource = URL.build(env,String.format("application/%s/addresses/", applicationNumber)).toString();
        AddressBuilder address = new AddressBuilder().withAddressLine1(addressLine1).withTown(town).withPostcode(postcode).withCountryCode(countryCode);
        ContactDetailsBuilder contactDetailsBuilder = new ContactDetailsBuilder().withPhoneNumber(phoneNumber).withEmailAddress(emailAddress);
        ApplicationAddressBuilder addressBuilder = new ApplicationAddressBuilder().withId(applicationNumber).withConsultant("Consult").withContact(contactDetailsBuilder)
                .withCorrespondenceAddress(address).withEstablishmentAddress(address);
        apiResponse = RestUtils.put(addressBuilder, applicationAddressResource, getHeaders());
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
    }

    public void addPartners() throws MalformedURLException {
        String addPersonResource = URL.build(env,String.format("application/%s/people/", applicationNumber)).toString();
        PersonBuilder addPerson = new PersonBuilder().withId(applicationNumber).withTitle(title).withForename(foreName).withFamilyName(familyName).withBirthDate(birthDate);
        apiResponse = RestUtils.post(addPerson, addPersonResource, getHeaders());
        assertThat(apiResponse.statusCode(HttpStatus.SC_CREATED));
    }

    public void addOperatingCentre() throws MalformedURLException {
        String operatingCentreResource = URL.build(env,String.format("application/%s/operating-centre/", applicationNumber)).toString();
        String permissionOption = "Y";
        AddressBuilder address = new AddressBuilder().withAddressLine1(addressLine1).withTown(town).withPostcode(postcode).withCountryCode(countryCode);
        OperatingCentreBuilder operatingCentreBuilder = new OperatingCentreBuilder().withApplication(applicationNumber).withNoOfVehiclesRequired(String.valueOf(noOfVehiclesRequired))
                .withNoOfTrailersRequired(String.valueOf(noOfVehiclesRequired)).withPermission(permissionOption).withAddress(address);
        apiResponse = RestUtils.post(operatingCentreBuilder, operatingCentreResource, getHeaders());
        assertThat(apiResponse.statusCode(HttpStatus.SC_CREATED));
    }

    public void updateOperatingCentre() throws MalformedURLException {
        String trafficArea = "D";
        String enforcementArea = "EA-D";
        String updateOperatingCentreResource = URL.build(env,String.format("application/%s/operating-centres", applicationNumber)).toString();

        do {
            int operatingCentreVersion = version;
            OperatingCentreUpdater updateOperatingCentre = new OperatingCentreUpdater().withId(applicationNumber).withTotAuthVehicles(noOfVehiclesRequired)
                    .withTrafficArea(trafficArea).withEnforcementArea(enforcementArea).withTAuthTrailers(Integer.parseInt(String.valueOf(noOfVehiclesRequired))).withTotCommunityLicences(noOfVehiclesRequired).withVersion(operatingCentreVersion);
            apiResponse = RestUtils.put(updateOperatingCentre, updateOperatingCentreResource, getHeaders());
            version++;
            if (version > 20) {
                version = 1;
            }
        }
        while (apiResponse.extract().statusCode() == HttpStatus.SC_CONFLICT);
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
    }

    public void addFinancialEvidence() throws MalformedURLException {
        String financialEvidenceResource = URL.build(env,String.format("application/%s/financial-evidence", applicationNumber)).toString();

        do {
            FinancialEvidenceBuilder financialEvidenceBuilder = new FinancialEvidenceBuilder().withId(applicationNumber).withVersion(version).withFinancialEvidenceUploaded(0);
            apiResponse = RestUtils.put(financialEvidenceBuilder, financialEvidenceResource, getHeaders());
            version++;
            if (version > 20) {
                version = 1;
            }
        } while (apiResponse.extract().statusCode() == HttpStatus.SC_CONFLICT);
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
    }

    public void addTransportManager() throws MalformedURLException {
        String hasEmail = "Y";
        String addTransportManager = URL.build(env,"transport-manager/create-new-user/").toString();
        TransportManagerBuilder transportManagerBuilder = new TransportManagerBuilder().withApplication(applicationNumber).withFirstName(foreName)
                .withFamilyName(familyName).withHasEmail(hasEmail).withUsername(username.concat(getLoginId())).withEmailAddress(transManEmailAddress).withBirthDate(birthDate);
        apiResponse = RestUtils.post(transportManagerBuilder, addTransportManager, getHeaders());
        assertThat(apiResponse.statusCode(HttpStatus.SC_CREATED));
        setTransportManagerApplicationId(apiResponse.extract().jsonPath().getString("id.transportManagerApplicationId"));
    }

    public void submitTransport() throws MalformedURLException {
        String submitTransportManager = URL.build(env,String.format("transport-manager-application/%s/submit", applicationNumber)).toString();
        GenericBuilder genericBuilder = new GenericBuilder().withId(transportManagerApplicationId).withVersion(1);
        apiResponse = RestUtils.put(genericBuilder, submitTransportManager, getHeaders());
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
    }

    public void vehicles() throws MalformedURLException {
        String hasEnteredReg = "N";
        String psvVehiclesResource = URL.build(env,String.format("application/%s/vehicles", applicationNumber)).toString();

        do {
            VehiclesBuilder psvVehiclesBuilder = new VehiclesBuilder().withId(applicationNumber).withHasEnteredReg(hasEnteredReg).withVersion(version);
            apiResponse = RestUtils.put(psvVehiclesBuilder, psvVehiclesResource, getHeaders());
            version++;
            if (version > 20) {
                version = 1;
            }
        } while (apiResponse.extract().statusCode() == HttpStatus.SC_CONFLICT);
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
    }

    public void addFinancialHistory() throws MalformedURLException {
        String financialHistoryAnswer = "N";
        String insolvencyAnswer = "false";
        String financialHistoryResource = URL.build(env,String.format("application/%s/financial-history", applicationNumber)).toString();

        do {
            FinancialHistoryBuilder financialHistoryBuilder = new FinancialHistoryBuilder().withId(applicationNumber).withVersion(String.valueOf(version)).withBankrupt(financialHistoryAnswer)
                    .withLiquidation(financialHistoryAnswer).withReceivership(financialHistoryAnswer).withAdministration(financialHistoryAnswer).withAdministration(financialHistoryAnswer)
                    .withDisqualified(financialHistoryAnswer).withInsolvencyDetails(insolvencyAnswer).withInsolvencyConfirmation(insolvencyAnswer);
            apiResponse = RestUtils.put(financialHistoryBuilder, financialHistoryResource, getHeaders());
            version++;
            if (version > 20) {
                version = 1;
            }
        } while (apiResponse.extract().statusCode() == HttpStatus.SC_CONFLICT);
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
    }

    public void addApplicationSafetyAndComplianceDetails() throws MalformedURLException {
        String tachographIns = "tach_na";
        String safetyInsVaries = "N";
        String safetyConfirmationOption = "Y";
        String applicationSafetyResource = URL.build(env,String.format("application/%s/safety", applicationNumber)).toString();

        do {
            LicenceBuilder licence = new LicenceBuilder().withId(licenceNumber).withVersion(version).withSafetyInsVaries(safetyInsVaries).withSafetyInsVehicles(String.valueOf(noOfVehiclesRequired))
                    .withSafetyInsTrailers(String.valueOf(noOfVehiclesRequired)).withTachographIns(tachographIns);
            ApplicationSafetyBuilder applicationSafetyBuilder = new ApplicationSafetyBuilder().withId(applicationNumber).withVersion(version)
                    .withSafetyConfirmation(safetyConfirmationOption).withLicence(licence);
            apiResponse = RestUtils.put(applicationSafetyBuilder, applicationSafetyResource, getHeaders());
            version++;
            if (version > 20) {
                version = 1;
            }
        } while (apiResponse.extract().statusCode() == HttpStatus.SC_CONFLICT);
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
    }

    public void addSafetyInspector() throws MalformedURLException {
        String safetyInspectorResource = URL.build(env,String.format("application/%s/workshop", applicationNumber)).toString();
        AddressBuilder addressBuilder = new AddressBuilder().withAddressLine1(addressLine1).withTown(town).withPostcode(postcode).withCountryCode(countryCode);
        ContactDetailsBuilder contactDetailsBuilder = new ContactDetailsBuilder().withFao(foreName).withAddress(addressBuilder);
        SafetyInspectorBuilder safetyInspectorBuilder = new SafetyInspectorBuilder().withApplication(applicationNumber).withLicence(licenceNumber).withIsExternal("N")
                .withContactDetails(contactDetailsBuilder);
        apiResponse = RestUtils.post(safetyInspectorBuilder, safetyInspectorResource, getHeaders());
        assertThat(apiResponse.statusCode(HttpStatus.SC_CREATED));
    }

    public void addConvictionsDetails() throws MalformedURLException {
        String previousConvictionsResource = URL.build(env,String.format("application/%s/previous-convictions", applicationNumber)).toString();

        do {
            ConvictionsPenaltiesBuilder convictionsPenaltiesBuilder = new ConvictionsPenaltiesBuilder().withId(applicationNumber).withConvictionsConfirmation("Y")
                    .withPrevConviction("N").withVersion(version);
            apiResponse = RestUtils.put(convictionsPenaltiesBuilder, previousConvictionsResource, getHeaders());
            version++;
            if (version > 20) {
                version = 1;
            }
        } while (apiResponse.extract().statusCode() == HttpStatus.SC_CONFLICT);
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
    }

    public void addLicenceHistory() throws MalformedURLException {
        String optionResponse = "N";
        String licenceHistoryResource = URL.build(env,String.format("application/%s/licence-history", applicationNumber)).toString();

        do {
            LicenceHistoryBuilder licenceHistoryBuilder = new LicenceHistoryBuilder().withId(applicationNumber).withPrevHadLicence(optionResponse).withPrevHasLicence(optionResponse)
                    .withPrevBeenAtPi(optionResponse).withPrevBeenDisqualifiedTc(optionResponse).withPrevBeenRefused(optionResponse).withPrevBeenRevoked(optionResponse).withPrevPurchasedAssets(optionResponse)
                    .withVersion(version);
            apiResponse = RestUtils.put(licenceHistoryBuilder, licenceHistoryResource, getHeaders());
            version++;
            if (version > 20) {
                version = 1;
            }
        } while (apiResponse.extract().statusCode() == HttpStatus.SC_CONFLICT);
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
    }

    public void reviewAndDeclare() throws MalformedURLException {
        String interimReason = "Testing through the API";
        String isInterim = "Y";
        String declarationConfirmation = "Y";
        String signatureRequired = "sig_physical_signature";
        String reviewResource = URL.build(env,String.format("application/%s/declaration/", applicationNumber)).toString();

        do {
            DeclarationsAndUndertakings undertakings = new DeclarationsAndUndertakings().withId(applicationNumber).withVersion(String.valueOf(version)).withInterimRequested(isInterim)
                    .withInterimReason(interimReason).withSignatureType(signatureRequired).withDeclarationConfirmation(declarationConfirmation);
            apiResponse = RestUtils.put(undertakings, reviewResource, getHeaders());
            version++;
            if (version > 20) {
                version = 1;
            }
        } while (apiResponse.extract().statusCode() == HttpStatus.SC_CONFLICT);
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
    }

    public void submitApplication() throws MalformedURLException {
        String submitResource = URL.build(env,String.format("application/%s/submit", applicationNumber)).toString();

        do {
            GenericBuilder genericBuilder = new GenericBuilder().withId(applicationNumber).withVersion(version);
            apiResponse = RestUtils.put(genericBuilder, submitResource, getHeaders());
            version++;
            if (version > 20) {
                version = 1;
            }
        } while (apiResponse.extract().statusCode() == HttpStatus.SC_CONFLICT);
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
    }

    public void getApplicationLicenceDetails() throws MalformedURLException {
        Headers.headers.put("x-pid", adminUserHeader);

        String getApplicationResource = URL.build(env,String.format("application/%s", applicationNumber)).toString();
        apiResponse = RestUtils.get(getApplicationResource, getHeaders());
        assertThat(apiResponse.statusCode(HttpStatus.SC_OK));
        setLicenceId(apiResponse.extract().jsonPath().getString("licence.id"));
        setLicenceNumber(apiResponse.extract().jsonPath().getString("licence.licNo"));
    }
}