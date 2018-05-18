package org.dvsa.testing.framework.stepdefs;

        import cucumber.api.java8.En;
        import org.dvsa.testing.framework.Utils.API_CreateAndGrantAPP.CreateInterimPsvLicenceAPI;
        import org.dvsa.testing.framework.Utils.API_CreateAndGrantAPP.GrantLicenceAPI;

        import static org.dvsa.testing.framework.Utils.Generic.GenericUtils.payPsvFeesAndGrantLicence;

public class psvGrantApplication implements En {
    public psvGrantApplication() {
        GrantLicenceAPI grantApp = new GrantLicenceAPI();
        CreateInterimPsvLicenceAPI psvApp = new CreateInterimPsvLicenceAPI();

        Given("^I have a psv application which is under consideration$", () -> {
            if (psvApp.getApplicationNumber() == null) {
                psvApp.createAndSubmitPsvApp();
            }
        });
        When("^I pay my application fees$", () -> {
            payPsvFeesAndGrantLicence(grantApp, psvApp);
        });
        Then("^my application should be granted$", () -> {
            grantApp.payGrantFees(psvApp.getOrganisationId(), psvApp.getApplicationNumber());
        });
    }
}