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

    /* ---------------------------------------------------------------- */
    /*  Group 3 — edit existing rows                                    */
    /* ---------------------------------------------------------------- */

    @When("I edit the transport manager's processing note to {string}")
    public void iEditTheTransportManagersProcessingNoteTo(String newComment) {
        world.internalNavigation.editTmProcessingNote(newComment);
    }

    @When("I edit the transport manager's competence serial to {string}")
    public void iEditTheTransportManagersCompetenceSerialTo(String newSerial) {
        world.internalNavigation.editTmCompetenceSerial(newSerial);
    }

    @When("I open the transport manager's responsibility edit page for the first licence")
    public void iOpenTheTransportManagerResponsibilityEditPage() {
        world.internalNavigation.openTmResponsibilityEditForFirstLicence();
    }

    @When("I set the transport manager's responsibility manager type to {string} and save")
    public void iSetTheTransportManagerResponsibilityManagerTypeAndSave(String tmTypeValue) {
        world.internalNavigation.setTmResponsibilityManagerTypeAndSave(tmTypeValue);
    }

    @Then("the browser should be on a transport manager responsibility edit page")
    public void theBrowserShouldBeOnAResponsibilityEditPage() {
        String url = getDriver().getCurrentUrl();
        assertTrue(url.contains("/details/responsibilities/edit-tm-licence/"),
                "Browser should be on the responsibility edit page, was: " + url);
    }

    @Then("the transport manager Responsibilities table Manager type should be {string}")
    public void theTransportManagerResponsibilitiesTableManagerTypeShouldBe(String expected) {
        assertTrue(isElementPresent(
                        "//table//td[@data-heading='Manager type' and contains(normalize-space(),\""
                                + expected + "\")]",
                        SelectorType.XPATH),
                "Responsibilities table Manager type column should show: " + expected);
    }

    @When("I add a case {string} to the transport manager")
    public void iAddACaseToTheTransportManager(String description) {
        world.internalNavigation.addTmCase(description);
    }

    @When("I edit the transport manager's case description to {string}")
    public void iEditTheTransportManagerCaseDescriptionTo(String newDescription) {
        world.internalNavigation.editTmCaseDescription(newDescription);
    }

    @Then("the transport manager Cases table should contain {string}")
    public void theTransportManagerCasesTableShouldContain(String description) {
        // After add / edit the browser is on the case detail page — assert on its
        // read-only definition list rather than the TM cases index table.
        assertTrue(isElementPresent(
                        "//li[contains(@class,'definition-list__item')]"
                                + "//dt[normalize-space()='Description']"
                                + "/following-sibling::dd[contains(normalize-space(),\""
                                + description + "\")]",
                        SelectorType.XPATH),
                "Case overview Description should show: " + description);
    }

    private void assertTransportManagerPage(String verticalNavTitle, String urlTail) {
        String expectedUrlFragment = String.format("/transport-manager/%s/%s",
                world.internalNavigation.transportManagerId, urlTail);
        assertTrue(getDriver().getCurrentUrl().contains(expectedUrlFragment),
                "Browser should be on TM path ending with '" + urlTail + "', was: " + getDriver().getCurrentUrl());

        // The active tab may be either the vertical-nav (sub-tab under TM details or Processing)
        // or the horizontal-nav (top-level: TM details / Cases / Documents / Processing).
        String currentNavXpath = String.format(
                "//li[(contains(@class,'vertical-navigation__item') or contains(@class,'horizontal-navigation__item'))"
                        + " and contains(@class,'current')]//*[normalize-space()='%s']",
                verticalNavTitle);
        assertTrue(isElementPresent(currentNavXpath, SelectorType.XPATH),
                "'" + verticalNavTitle + "' should be the current navigation item");

        assertTrue(isElementNotPresent("//p[contains(@class,'error') or contains(@class,'notice--error')]",
                        SelectorType.XPATH),
                "Page should not display an error banner");
    }
}

