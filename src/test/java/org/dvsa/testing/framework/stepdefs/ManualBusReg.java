//package org.dvsa.testing.framework.stepdefs;
//
//import cucumber.api.java8.En;
//import org.dvsa.testing.lib.pages.BasePage;
//import org.dvsa.testing.lib.pages.enums.SelectorType;
//
//public class ManualBusReg extends BasePage implements En {
//
//    public ManualBusReg() {
//        When("^I pay the fee$", () -> {
//            waitForTextToBePresent("Service details");
//            clickByLinkText("Fees");
//            waitAndClick("Fees", SelectorType.ID);
//            waitForTextToBePresent("Fee No.");
//        });
//        Then("^I should see the success message$", () -> {
////
//        });
//    }
//}
//
