package org.dvsa.testing.framework.Journeys;

import activesupport.MissingRequiredArgument;
import activesupport.http.RestUtils;
import activesupport.system.Properties;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.dvsa.testing.framework.Utils.API_Builders.GenericBuilder;
import org.dvsa.testing.framework.Utils.API_CreateAndGrantAPP.CreateLicenceAPI;
import org.dvsa.testing.framework.Utils.API_CreateAndGrantAPP.GrantLicenceAPI;
import org.dvsa.testing.framework.Utils.API_Headers.Headers;
import Injectors.World;
import org.dvsa.testing.lib.url.utils.EnvironmentType;

import java.net.MalformedURLException;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.dvsa.testing.framework.Utils.API_Headers.Headers.getHeaders;

public class APIJourneySteps {

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
    private static final String zipFilePath = "/src/test/resources/ESBR.zip";
    private String trafficAreaName;
    private static String variationApplicationNumber;
    private static String operatorType = System.getProperty("operatorType");
    private String goodOrPsv;
    private String licenceStatus;

    public String getLicenceType() {
        return licenceType;
    }

    public void setLicenceType(String licenceType) {
        this.licenceType = licenceType;
    }

    private String licenceType;

    public static int tmCount;

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    private String businessType;
    public String getGoodOrPsv() {
        return goodOrPsv;
    }

    public void setGoodOrPsv(String goodOrPsv) {
        this.goodOrPsv = goodOrPsv;
    }

    public void setLicenceStatus(String licenceStatus) {
        this.licenceStatus = licenceStatus;
    }

    public String getLicenceStatus() {
        return licenceStatus;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getTrafficAreaName() {
        return trafficAreaName;
    }

    public void setTrafficAreaName(String trafficAreaName) {
        this.trafficAreaName = trafficAreaName;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }


    public APIJourneySteps(World world) throws MissingRequiredArgument {
        this.world = world;
        world.createLicence = new CreateLicenceAPI();
        world.grantLicence = new GrantLicenceAPI(world);
    }

    public void payFeesAndGrantLicence() throws MalformedURLException {
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

    public void createAdminUser() throws MissingRequiredArgument {
        world.updateLicence.createInternalAdminUser();
    }

    public void nIAddressBuilder() {
        world.createLicence.setEnforcementArea("EA-N");
        world.createLicence.setTrafficArea("N");
        world.createLicence.setTown("Belfast");
        world.createLicence.setPostcode("BT28HQ");
        world.createLicence.setCountryCode("NI");
        world.createLicence.setNiFlag("Y");
    }

    public String getLicenceTrafficArea() {
        Headers.getHeaders().put("x-pid", adminApiHeader());
        String getApplicationResource = org.dvsa.testing.lib.url.api.URL.build(env, String.format("licence/%s", world.createLicence.getLicenceId())).toString();

        apiResponse = RestUtils.get(getApplicationResource, getHeaders());
        setTrafficAreaName(apiResponse.extract().jsonPath().getString("trafficArea.name"));
        return trafficAreaName;
    }

    public String getLicenceStatusDetails() {
        Headers.getHeaders().put("x-pid", adminApiHeader());
        String getApplicationResource = org.dvsa.testing.lib.url.api.URL.build(env, String.format("licence/%s", world.createLicence.getLicenceId())).toString();

        apiResponse = RestUtils.get(getApplicationResource, getHeaders());
        setLicenceStatus(apiResponse.extract().jsonPath().getString("status.description"));
        return licenceStatus;
    }
    public String getOperatorTypeDetails() {
        Headers.getHeaders().put("x-pid", adminApiHeader());
        String getApplicationResource = org.dvsa.testing.lib.url.api.URL.build(env, String.format("licence/%s", world.createLicence.getLicenceId())).toString();

        apiResponse = RestUtils.get(getApplicationResource, getHeaders());
        setGoodOrPsv(apiResponse.extract().jsonPath().getString("goodsOrPsv.description"));
        return goodOrPsv;
    }
    public String getBusinessTypeDetails() {
        Headers.getHeaders().put("x-pid", adminApiHeader());
        String getApplicationResource = org.dvsa.testing.lib.url.api.URL.build(env, String.format("licence/%s", world.createLicence.getLicenceId())).toString();

        apiResponse = RestUtils.get(getApplicationResource, getHeaders());
        setBusinessType(apiResponse.extract().jsonPath().getString("organisation.type.description"));
        return businessType;
    }  public String getLicenceTypeDetails() {
        Headers.getHeaders().put("x-pid", adminApiHeader());
        String getApplicationResource = org.dvsa.testing.lib.url.api.URL.build(env, String.format("licence/%s", world.createLicence.getLicenceId())).toString();

        apiResponse = RestUtils.get(getApplicationResource, getHeaders());
        setLicenceType(apiResponse.extract().jsonPath().getString("licenceType.description"));
        return licenceType;
    }

    public void generateAndGrantPsvApplicationPerTrafficArea(String trafficArea, String enforcementArea) throws Exception {
        world.createLicence.setTrafficArea(trafficArea);
        world.createLicence.setEnforcementArea(enforcementArea);
        world.createLicence.setOperatorType("public");
        world.APIJourneySteps.createAndSubmitApplication();
        payFeesAndGrantLicence();
        world.grantLicence.payGrantFees();
        getLicenceTrafficArea();
        System.out.println("--Licence-Number: " + world.createLicence.getLicenceNumber() + "--");
    }

    public void updateLicenceStatus(String licenceId, String status) {
        String typeOfLicenceResource = org.dvsa.testing.lib.url.api.URL.build(env, String.format("licence/%s/decisions/%s", licenceId, status)).toString();

        GenericBuilder genericBuilder = new GenericBuilder().withId(licenceId);
        apiResponse = RestUtils.post(genericBuilder, typeOfLicenceResource, getHeaders());
        assertThat(apiResponse.statusCode(HttpStatus.SC_CREATED));
    }

    public CreateLicenceAPI createApp() throws MissingRequiredArgument {
        CreateLicenceAPI api = new CreateLicenceAPI();
        return api;
    }

    public GrantLicenceAPI grantLicence() throws MissingRequiredArgument {
        return new GrantLicenceAPI(world);
    }

    public void createApplication(){
        if(world.createLicence.getApplicationNumber() == null) {
            world.createLicence.registerUser();
            world.createLicence.getUserDetails();
            world.createLicence.createApplication();
            world.createLicence.updateBusinessType();
            world.createLicence.updateBusinessDetails();
            world.createLicence.addAddressDetails();
            world.createLicence.addPartners();
            world.createLicence.submitTaxiPhv();
            world.createLicence.addOperatingCentre();
            world.createLicence.updateOperatingCentre();
            world.createLicence.addFinancialEvidence();
            world.createLicence.addTransportManager();
            world.createLicence.submitTransport();
            world.createLicence.vehicles();
            world.createLicence.submitVehicleDeclaration();
            world.createLicence.addFinancialHistory();
            world.createLicence.addApplicationSafetyAndComplianceDetails();
            world.createLicence.addSafetyInspector();
            world.createLicence.addConvictionsDetails();
            world.createLicence.addLicenceHistory();
            world.createLicence.applicationReviewAndDeclare();
        }
    }

    public void createAndSubmitApplication(){
        createApplication();
        world.createLicence.submitApplication();
        world.createLicence.getApplicationLicenceDetails();
    }

    public void createPartialApplication() {
        world.createLicence.registerUser();
        world.createLicence.getUserDetails();
        world.createLicence.createApplication();
        world.createLicence.addOperatingCentre();
        world.createLicence.updateOperatingCentre();
        world.createLicence.addFinancialEvidence();
    }

    public static String adminApiHeader(){
        return "e91f1a255e01e20021507465a845e7c24b3a1dc951a277b874c3bcd73dec97a1";
    }
}