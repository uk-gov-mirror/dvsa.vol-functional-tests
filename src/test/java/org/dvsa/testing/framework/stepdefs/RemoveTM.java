package org.dvsa.testing.framework.stepdefs;

import activesupport.aws.s3.S3;
import activesupport.system.Properties;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.dvsa.testing.framework.stepdefs.Utils.External.CreateInterimGoodsLicenceAPI;
import org.dvsa.testing.framework.stepdefs.Utils.Internal.GenericUtils;
import org.dvsa.testing.framework.stepdefs.Utils.Internal.GrantApplicationAPI;
import org.dvsa.testing.lib.Login;
import org.dvsa.testing.lib.browser.Browser;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;
import org.dvsa.testing.lib.pages.internal.SearchNavBar;
import org.dvsa.testing.lib.url.utils.EnvironmentType;
import org.dvsa.testing.lib.url.webapp.URL;
import org.dvsa.testing.lib.url.webapp.utils.ApplicationType;

import static junit.framework.TestCase.assertTrue;
import static org.dvsa.testing.framework.stepdefs.Utils.Internal.GenericUtils.payGoodsFeesAndGrantLicence;
import static org.dvsa.testing.framework.stepdefs.Utils.Internal.GenericUtils.variationApplicationNumber;
import static org.dvsa.testing.framework.stepdefs.Utils.Internal.LoginInternalUser.USER_EMAIL;
import static org.dvsa.testing.framework.stepdefs.Utils.Internal.LoginInternalUser.USER_PASSWORD;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class RemoveTM extends BasePage implements En {

    private CreateInterimGoodsLicenceAPI goodsApp = new CreateInterimGoodsLicenceAPI();
    private GrantApplicationAPI grantApp = new GrantApplicationAPI();

    private static String oldAlertValue = "You are removing your last Transport Manager. If you haven't yet made an application to appoint a replacement, " +
            "you must contact us on 0300 123 9000 or at notifications@vehicle-operator-licensing.service.gov.uk";
    private static String newAlertValue = "'You are about to remove the last transport manager for this licence.\n" +
            "Do you want to send a letter about this to the operator to all known addresses?\n" +
            "\n" + "If yes, this will be automatically issued tomorrow.'";
    private static String alertHeaderValue = "Are you sure you want to remove this Transport Manager?";
    private static String applicationTMAlertContent = "This action is permanent and cannot be undone.";

    public RemoveTM() throws Exception {
        Given("^i have an application with a transport manager$", () -> {
            if (goodsApp.getApplicationNumber() == null) {
                goodsApp.createAndSubmitGoodsApp();
            }
        });
        When("^the last transport manager has been removed by an internal user$", () -> {
            EnvironmentType env = EnvironmentType.getEnum(Properties.get("env", true));
            String myURL = URL.build(ApplicationType.INTERNAL, env).toString();

            if (Browser.isInitialised()) {
                //Quit Browser and open a new window
                Browser.quit();
            }
            Browser.go(myURL);
            Login.signIn(USER_EMAIL, USER_PASSWORD);
            selectValueFromDropDown("//select[@id='search-select']", SelectorType.XPATH, "Applications");

            if (variationApplicationNumber != null) {
                do {
                    SearchNavBar.search(variationApplicationNumber);
                } while (!isLinkPresent(variationApplicationNumber, 60));
                clickByLinkText(variationApplicationNumber);
                assertTrue(Boolean.parseBoolean(Browser.getURL().concat("variation")));
            }
            else {
                do {
                    SearchNavBar.search(goodsApp.getApplicationNumber());
                } while (!isLinkPresent(goodsApp.getApplicationNumber(), 60));
                clickByLinkText(goodsApp.getApplicationNumber());
            }
                assertTrue(isTextPresent("Overview", 60));
                clickByLinkText("Transport");
                isTextPresent("TransPort Managers", 60);
                click("//*[@value='Remove']", SelectorType.XPATH);
            });
            Then("^a pop up message should be displayed$", () -> {
                waitForTextToBePresent(alertHeaderValue);

                String alertContent = getElementValueByText("//*[@class='js-content']/p", SelectorType.XPATH);
                assertEquals(alertContent, oldAlertValue);
            });
            Given("^i add a transport manager to an existing licence$", () -> {
                goodsApp.setUsername("newTmApi");
                goodsApp.addTransportManager();
                goodsApp.submitTransport();
            });
            Then("^the remove last TM popup should not be displaying new TM remove text$", () -> {
                waitForTextToBePresent(alertHeaderValue);
                assertFalse(isTextPresent(newAlertValue, 60));
            });
            Given("^a self-serve user removes the last TM$", () -> {
                EnvironmentType env = EnvironmentType.getEnum(Properties.get("env", true));
                String myURL = URL.build(ApplicationType.EXTERNAL, env).toString();

                if (Browser.isInitialised()) {
                    //Quit Browser and open a new window
                    Browser.quit();
                }
                Browser.go(myURL);
                String password = S3.getTempPassword(goodsApp.getEmailAddress());

                if (isTextPresent("Username", 60))
                    Login.signIn(goodsApp.getLoginId(), password);
                else if (isTextPresent("Current password", 60)) {
                    enterField(nameAttribute("input", "oldPassword"), password);
                    enterField(nameAttribute("input", "newPassword"), "Password1");
                    enterField(nameAttribute("input", "confirmPassword"), "Password1");
                    click(nameAttribute("input", "submit"));
                }
                clickByLinkText(goodsApp.getLicenceNumber());
                clickByLinkText("Transport Managers");
                click("//*[@value='Remove']", SelectorType.XPATH);
            });
            Given("^the licence has been granted$", () -> {
                payGoodsFeesAndGrantLicence(grantApp, goodsApp);
                grantApp.payGrantFees(goodsApp.getOrganisationId(), goodsApp.getApplicationNumber());
            });
            When("^i create a variation$", () -> {
                GenericUtils genericUtils = new GenericUtils();
                genericUtils.createVariation(goodsApp.getLicenceId());
                genericUtils.updateLicenceType(goodsApp.getLicenceId());
            });
        }
    }