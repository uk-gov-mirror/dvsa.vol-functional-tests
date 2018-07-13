//package org.dvsa.testing.framework.stepdefs;
//
//import activesupport.aws.s3.S3;
//import activesupport.system.Properties;
//import cucumber.api.java8.En;
//import org.dvsa.testing.lib.browser.Browser;
//import org.dvsa.testing.lib.pages.BasePage;
//import org.dvsa.testing.lib.pages.enums.SelectorType;
//
//public class CheckPrecenceofPDFNI extends BasePage implements En {
//
//
//
//    public static void main(String[]args){
//
//
//        GrantApplicationAPI grantApplication = new GrantApplicationAPI();
//        CreateInterimPsvLicenceAPI psvLicence = new CreateInterimPsvLicenceAPI();
//        if (psvLicence.getApplicationNumber() == null) {
//            psvLicence.setNiFlag("Y");
//            psvLicence.setNoOfVehiclesRequired("1");
//            psvLicence.setPostcode("BT60PR");
//            psvLicence.setTown("Belfast");
//            psvLicence.createAndSubmitPsvApp();
//            payPsvFees(grantApplication, psvLicence);
//
//        }
//
//    }
//
//    public CheckPrecenceofPDFNI() {
//        GrantApplicationAPI grantApplication = new GrantApplicationAPI();
//        CreateInterimPsvLicenceAPI psvLicence = new CreateInterimPsvLicenceAPI();
//
//        Given("^I am on add Transport Manager Page -  NI$", () -> {
//            if (psvLicence.getApplicationNumber() == null) {
//                psvLicence.setNiFlag("Y");
//                psvLicence.setNoOfVehiclesRequired("1");
//                psvLicence.setPostcode("BT60PR");
//                psvLicence.setTown("Belfast");
//                psvLicence.createAndSubmitPsvApp();
//                payPsvFees(grantApplication, psvLicence);
//            }
//
//            EnvironmentType env = Environment.enumType(Properties.get("env", true));
//
//            String URL = build(ApplicationType.EXTERNAL, env);
//            Browser.go(URL);
//
//            if (isTextPresent("Username", 60)) {
//                enterField(nameAttribute("input", "username"), psvLicence.getLoginId());
//                enterField(nameAttribute("input", "password"), S3.getTempPassword(psvLicence.getEmailAddress()));
//                click(nameAttribute("input", "submit"));
//            } else {
//                //TODO Replace with logger
//                System.out.println("Already logged In");
//            }
//
////            enterField(nameAttribute("input", "username"), psvLicence.getLoginId());
////            enterField(nameAttribute("input", "username"), USER_EMAIL);
////            enterField(nameAttribute("input", "password"), USER_PASSWORD);
////            click(nameAttribute("input", "submit"));
//
//            clickByLinkText(psvLicence.getLicenceNumber());
//            clickByLinkText("Transport Managers");
//            waitForTextToBePresent("change your licence");
//            clickByLinkText("change your licence");
//            waitAndClick("//*[@id=\"form-actions[submit]\"]", SelectorType.XPATH);
//        });
//        Then("^I open TM(\\d+) Form - NI$", (Integer arg0) -> {
//            waitAndClick("//*[@id=\"add\"]", SelectorType.XPATH);
//            waitForTextToBePresent("Or add a new Transport Manager");
//            clickByLinkText("Or add a new Transport Manager");
//        });
//        Given("^I am on add Safety Inspectors Page - NI$", () -> {
//            clickByLinkText(psvLicence.getApplicationNumber());
//            clickByLinkText("Safety and compliance");
//        });
//        Then("^I open Maintenance Form - NI$", () -> {
//            waitAndClick("//*[@id=\"add\"]", SelectorType.XPATH);
//        });
//    }
//}
