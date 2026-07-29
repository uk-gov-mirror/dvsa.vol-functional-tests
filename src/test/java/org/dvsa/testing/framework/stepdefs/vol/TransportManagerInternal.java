package org.dvsa.testing.framework.stepdefs.vol;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.dvsa.testing.framework.Injectors.World;
import org.dvsa.testing.framework.pageObjects.BasePage;
import org.dvsa.testing.framework.pageObjects.enums.SelectorType;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransportManagerInternal extends BasePage {
    private final World world;

    public TransportManagerInternal(World world) {
        this.world = world;
    }

    @Given("I capture the transport manager id from the licence")
    public void iCaptureTheTransportManagerIdFromTheLicence() {
        String tmId = world.internalNavigation.captureFirstTransportManagerIdFromLicence();
        assertNotNull(tmId, "Transport Manager id should have been captured from the licence Transport Managers tab");
        assertTrue(tmId.matches("\\d+"),
                "Transport Manager id should be numeric, was: " + tmId);
    }

    @When("I navigate to the transport manager Details tab")
    public void iNavigateToTheTransportManagerDetailsTab() {
        world.internalNavigation.getTmDetails();
    }

    @When("I navigate to the transport manager Competences tab")
    public void iNavigateToTheTransportManagerCompetencesTab() {
        world.internalNavigation.getTmCompetences();
    }

    @When("I navigate to the transport manager Employment tab")
    public void iNavigateToTheTransportManagerEmploymentTab() {
        world.internalNavigation.getTmEmployment();
    }

    @When("I navigate to the transport manager Previous History tab")
    public void iNavigateToTheTransportManagerPreviousHistoryTab() {
        world.internalNavigation.getTmPreviousHistory();
    }

    @When("I navigate to the transport manager Documents tab")
    public void iNavigateToTheTransportManagerDocumentsTab() {
        world.internalNavigation.getTmDocuments();
    }

    @When("I navigate to the transport manager Cases tab")
    public void iNavigateToTheTransportManagerCasesTab() {
        world.internalNavigation.getTmCases();
    }

    @When("I navigate to the transport manager Processing Notes tab")
    public void iNavigateToTheTransportManagerProcessingNotesTab() {
        world.internalNavigation.getTmProcessingNotes();
    }

    @When("I navigate to the transport manager Processing Event History tab")
    public void iNavigateToTheTransportManagerProcessingEventHistoryTab() {
        world.internalNavigation.getTmProcessingEventHistory();
    }

    @When("I navigate to the transport manager Processing Publication tab")
    public void iNavigateToTheTransportManagerProcessingPublicationTab() {
        world.internalNavigation.getTmProcessingPublication();
    }

    @When("I navigate to the transport manager Processing Read History tab")
    public void iNavigateToTheTransportManagerProcessingReadHistoryTab() {
        world.internalNavigation.getTmProcessingReadHistory();
    }

    @Then("the transport manager {string} page should be displayed at path {string}")
    public void theTransportManagerPageShouldBeDisplayedAtPath(String verticalNavTitle, String urlTail) {
        assertTransportManagerPage(verticalNavTitle, urlTail);
    }

    /* ---------------------------------------------------------------- */
    /*  Group 2 — add-then-assert flows                                 */
    /* ---------------------------------------------------------------- */

    @When("I add a processing note {string} to the transport manager with priority {string}")
    public void iAddAProcessingNoteToTheTransportManager(String comment, String priority) {
        world.internalNavigation.addTmProcessingNote(comment, "Y".equalsIgnoreCase(priority));
    }

    @Then("the transport manager Notes table should contain {string}")
    public void theTransportManagerNotesTableShouldContain(String comment) {
        assertTrue(isElementPresent(
                        "//table//td[contains(normalize-space(),\"" + comment + "\")]",
                        SelectorType.XPATH),
                "Notes table should contain a row with note text: " + comment);
    }

    @When("I add a competence {string} with serial {string} issued on {string} {string} {string} to the transport manager")
    public void iAddACompetenceToTheTransportManager(String qualificationType, String serial,
                                                     String day, String month, String year) {
        world.internalNavigation.addTmCompetence(qualificationType, serial, day, month, year);
    }

    @Then("the transport manager Competences table should contain serial {string}")
    public void theTransportManagerCompetencesTableShouldContain(String serial) {
        assertTrue(isElementPresent(
                        "//table//td[contains(normalize-space(),\"" + serial + "\")]",
                        SelectorType.XPATH),
                "Competences table should contain a row with serial: " + serial);
    }

    @When("I add an employer {string} to the transport manager with position {string} hours {string}")
    public void iAddAnEmployerToTheTransportManager(String employerName, String position, String hours) {
        world.internalNavigation.addTmEmployer(employerName, position, hours,
                "1 High Street", "London", "SW1A 1AA");
    }

    @Then("the transport manager Employment table should contain employer {string}")
    public void theTransportManagerEmploymentTableShouldContain(String employerName) {
        assertTrue(isElementPresent(
                        "//table//td[contains(normalize-space(),\"" + employerName + "\")]",
                        SelectorType.XPATH),
                "Employment table should contain a row with employer: " + employerName);
    }

    @When("I upload a document {string} to the transport manager")
    public void iUploadADocumentToTheTransportManager(String description) {
        String fixture = System.getProperty("user.dir")
                + "/src/test/resources/newspaperAdvert.jpeg";
        world.internalNavigation.uploadTmDocument(description, fixture);
    }

    @Then("the transport manager Documents table should contain {string}")
    public void theTransportManagerDocumentsTableShouldContain(String description) {
        assertTrue(isElementPresent(
                        "//table//td//a[contains(normalize-space(),\"" + description + "\")]",
                        SelectorType.XPATH),
                "Documents table should contain a link with description: " + description);
    }

    private void assertTransportManagerPage(String verticalNavTitle, String urlTail) {
        String expectedUrlFragment = String.format("/transport-manager/%s/%s",
                world.internalNavigation.transportManagerId, urlTail);
        assertTrue(getDriver().getCurrentUrl().contains(expectedUrlFragment),
                "Browser should be on TM path ending with '" + urlTail + "', was: " + getDriver().getCurrentUrl());

        String currentNavXpath = String.format(
                "//li[contains(@class,'vertical-navigation__item') and contains(@class,'current')]//*[normalize-space()='%s']",
                verticalNavTitle);
        assertTrue(isElementPresent(currentNavXpath, SelectorType.XPATH),
                "'" + verticalNavTitle + "' should be the current vertical-navigation item");

        assertTrue(isElementNotPresent("//p[contains(@class,'error') or contains(@class,'notice--error')]",
                        SelectorType.XPATH),
                "Page should not display an error banner");
    }
}

