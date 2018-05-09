package org.dvsa.testing.framework.stepdefs;

import activesupport.string.Str;
import activesupport.system.Properties;
import cucumber.api.java8.En;
import org.dvsa.testing.framework.stepdefs.Utils.External.CreateInterimPsvLicenceAPI;
import org.dvsa.testing.lib.Environment;
import org.dvsa.testing.lib.Login;
import org.dvsa.testing.lib.browser.Browser;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;
import org.dvsa.testing.lib.utils.ApplicationType;
import org.dvsa.testing.lib.utils.EnvironmentType;

import java.time.LocalDate;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class ESBRShortNotice extends BasePage implements En {
    CreateInterimPsvLicenceAPI psvLicence = new CreateInterimPsvLicenceAPI();
    EnterDate enterDate = new EnterDate();
    private String USER_EMAIL = "usr291";
    private String USER_PASSWORD = "Password1";

    public ESBRShortNotice() {
        Given("^I have an application for traffic area \"([^\"]*)\"$", (String arg0) -> {
            //TODO:API
        });

//        And("^I submit a bus registration with short notice$", () ->
//        {
//
//            EnvironmentType env = Environment.enumType(Properties.get("env", true));
//            String URL = URI.build(ApplicationType.INTERNAL, env, "licence/4/bus/");
////            String URL = String.format(URI.build(ApplicationType.INTERNAL, env, "/licence/%s/bus/"),psvLicence.getLicenceNumber());
//            Browser.go(URL);
//            //TODO Replace with logger
//            if (isTextPresent("Username", 60))
//                Login.signIn(USER_EMAIL, USER_PASSWORD);
//            else System.out.println("Already logged In");
//            Browser.go(URL);
//            if (isTextPresent("Bus registrations", 30))
//                click(nameAttribute("button", "action"));
//            waitForTextToBePresent("Service details");
//            assertTrue(isTextPresent("Service No. & type", 5));
//            enterText("serviceNo", "123");
//            enterText("startPoint", Str.randomWord(9));
//            enterText("finishPoint", Str.randomWord(11));
//            enterText("via", Str.randomWord(5));
//            selectServiceType("//ul[@class='chosen-choices']", "//*[@id=\"busServiceTypes_chosen\"]/div/ul/li[1]", SelectorType.XPATH);
//            enterDate.enterDate(getCurrentDayOfMonth(), getCurrentMonth(), getCurrentYear());
//            enterText("effectiveDate_day", String.valueOf(getCurrentDayOfMonth()));
//            enterText("effectiveDate_month", String.valueOf(getCurrentMonth()));
//            enterText("effectiveDate_year", String.valueOf(getCurrentYear()));
//            click(nameAttribute("button", "form-actions[submit]"));
//        });
//
//
//        Then("^The short notice text is displayed$", () -> {
//            EnvironmentType env = Environment.enumType(Properties.get("env", true));
//            String URL = URI.build(ApplicationType.INTERNAL, env, "/licence/482278/bus/510051/details/service/");
//            // String URL = String.format(URI.build(ApplicationType.INTERNAL, env, "/licence/%s/bus/"),psvLicence.getLicenceNumber());
//            Browser.go(URL);
//            //TODO Replace with logger
//            if (isTextPresent("Username", 60))
//                Login.signIn(USER_EMAIL, USER_PASSWORD);
//            else System.out.println("Already logged In");
//
//            do {
//                Browser.go(URL);
//                //action
//            }
//            while (!isTextPresent("Service details", 2));//condition
//            assertTrue(isTextPresent("Short notice", 30));
//            clickByLinkText("Short notice");
//        });


        And("^I submit a bus registration without short notice$", () -> {
            EnvironmentType env = Environment.enumType(Properties.get("env", true));
            String URL = org.dvsa.testing.lib.URI.build(ApplicationType.INTERNAL, env, "licence/4/bus/");
//            String URL = String.format(URI.build(ApplicationType.INTERNAL, env, "/licence/%s/bus/"),psvLicence.getLicenceNumber());
            Browser.go(URL);
            //TODO Replace with logger
            if (isTextPresent("Username", 60))
                Login.signIn(USER_EMAIL, USER_PASSWORD);
            else System.out.println("Already logged In");
            Browser.go(URL);
            if (isTextPresent("Bus registrations", 30))
                click(nameAttribute("button", "action"));
            waitForTextToBePresent("Service details");
            assertTrue(isTextPresent("Service No. & type", 5));
            enterText("serviceNo", "333");
            enterText("startPoint", Str.randomWord(9));
            enterText("finishPoint", Str.randomWord(11));
            enterText("via", Str.randomWord(5));
//            waitAndSelectByIndex("Service type", "//*[@value=\"Select Some Options\"]", SelectorType.XPATH, 2);
            selectServiceType("//ul[@class='chosen-choices']", "//*[@id=\"busServiceTypes_chosen\"]/div/ul/li[1]", SelectorType.XPATH);
            enterDate.enterDate(getCurrentDayOfMonth(), getCurrentMonth(), getCurrentYear());
            enterText("effectiveDate_day", String.valueOf(LocalDate.now().plusDays(7)));
            enterText("effectiveDate_month", String.valueOf(getFutureMonth(1)));
            enterText("effectiveDate_year", String.valueOf(LocalDate.now().getYear()));
            click(nameAttribute("button", "form-actions[submit]"));
        });


        Then("^The short notice text isn't displayed$", () -> {
            do {
                //do nothing
            } while (!isTextPresent("Service details", 2));//condition
            assertFalse(isTextPresent("Short notice", 30));
        });
    }
}
