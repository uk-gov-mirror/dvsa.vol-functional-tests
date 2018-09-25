package org.dvsa.testing.framework.stepdefs;

import Injectors.World;
import activesupport.driver.Browser;
import cucumber.api.java8.En;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;
import org.dvsa.testing.lib.url.webapp.utils.ApplicationType;
import org.junit.Assert;

public class ExternalSearchFilters extends BasePage implements En {
    public ExternalSearchFilters(World world) {
        And("^i have searched for a licence$", () -> {
            String env = System.getProperty("env");
            String myURL = org.dvsa.testing.lib.url.webapp.URL.build(ApplicationType.EXTERNAL, env,"/search/find-lorry-bus-operators/").toString();
            Browser.navigate().get(myURL);
            click("//*[@class='form-control form-control--radio'][2]", SelectorType.XPATH);
            enterText("//*[@name='search']",world.createLicence.getLicenceNumber(),SelectorType.XPATH);
            click(nameAttribute("input","submit"));
            do { click(nameAttribute("button","submit"));}
            while(!isElementPresent("//*[@class='table__wrapper']",SelectorType.XPATH));

        });
        Then("^the Organisation Type filter should be displayed$", () -> {
            String opName = getText(String.format("//*[@id='filter[orgTypeDesc]']/option[2]"), SelectorType.XPATH);
             Assert.assertEquals(world.APIJourneySteps.getBusinessTypeDetails().toUpperCase(),opName.toUpperCase());
        });
        Then("^the Licence Type filter should be displayed$", () -> {
            String licType = getText(String.format("//*[@id='filter[licTypeDesc]']/option[2]"), SelectorType.XPATH);
            Assert.assertEquals(world.APIJourneySteps.getLicenceTypeDetails().toUpperCase(),licType.toUpperCase());
        });
        Then("^the Licence Status filter should be displayed$", () -> {
            String licStatus = getText(String.format("//*[@id='filter[licStatusDesc]']/option[2]"), SelectorType.XPATH);
            Assert.assertEquals(world.APIJourneySteps.getLicenceStatusDetails(),licStatus);
        });
        Then("^the Traffic Area filter should be displayed$", () -> {
            String traffArea = getText(String.format("//*[@id='filter[licenceTrafficArea]']/option[2]"), SelectorType.XPATH);
            Assert.assertEquals(world.APIJourneySteps.getLicenceTrafficArea(),traffArea);
        });
        Then("^the Goods or PSV filter should be displayed$", () -> {
            String opType = getText(String.format("//*[@id='filter[goodsOrPsvDesc]']/option[2]"), SelectorType.XPATH);
            Assert.assertEquals(world.APIJourneySteps.getOperatorTypeDetails(), opType);
        });

    }
}