package org.dvsa.testing.framework.Journeys.licence;

import activesupport.aws.s3.SecretsManager;
import activesupport.driver.Browser;
import org.apache.hc.core5.http.HttpException;
import org.dvsa.testing.framework.Injectors.World;
import activesupport.system.Properties;
import org.dvsa.testing.framework.enums.SelfServeSection;
import org.dvsa.testing.framework.pageObjects.BasePage;
import org.dvsa.testing.framework.pageObjects.enums.SelectorType;
import org.dvsa.testing.lib.url.utils.EnvironmentType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.dvsa.testing.lib.url.webapp.webAppURL;
import org.dvsa.testing.lib.url.webapp.utils.ApplicationType;
import org.dvsa.testing.framework.pageObjects.enums.AdminOption;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static activesupport.driver.Browser.navigate;

public class InternalNavigation extends BasePage {

    World world;
    private final String url = webAppURL.build(ApplicationType.INTERNAL, EnvironmentType.getEnum(Properties.get("env", true))).toString();
    public String adminDropdown = "//li[@class='admin__title']";
    public String taskTitle = "//h2[text()='Edit task']";

    public InternalNavigation(World world) {
        this.world = world;
    }

    public void navigateToLogin(String username, String emailAddress) {
        world.globalMethods.navigateToLoginWithoutCookies(username, emailAddress, ApplicationType.INTERNAL);
    }

    public void logInAsAdmin() throws HttpException {
        if (world.updateLicence.getInternalUserId() == null) {
            world.APIJourney.createAdminUser();
            navigateToLogin(world.updateLicence.getInternalUserLogin(), world.updateLicence.getInternalUserEmailAddress());
        } else {
            navigateToLoginPage();
            if (isElementNotPresent(world.internalNavigation.adminDropdown, SelectorType.XPATH)) {
                world.globalMethods.signIn(world.registerUser.getUserName(), SecretsManager.getSecretValue("internalNewPassword"));
            }
        }
    }

    public void logInAndNavigateToApplicationDocsTable(boolean variation) throws HttpException {
        loginAndGetApplication(variation);
        waitAndClickByLinkText("Docs");
    }

    public void logInAndNavigateToApplicationProcessingPage(boolean variation) throws HttpException {
        loginAndGetApplication(variation);
        waitForTextToBePresent("Processing");
        waitAndClickByLinkText("Processing");
    }

    public void adminNavigation(@NotNull AdminOption option) {
        waitAndClick(adminDropdown, SelectorType.XPATH);
        waitAndClickByLinkText(option.toString());
        switch (option) {
            case CONTINUATIONS, PRESIDING_TCS -> {}
            case PUBLICATIONS, REPORTS, PRINTING, DATA_RETENTION, USER_MANAGEMENT -> waitForElementToBePresent(String.format("//h4[contains(text(),'%s')]", option));
            case BUS_REGISTRATIONS -> waitForElementToBePresent("//h4[contains(text(),'Bus Registrations')]");
            case FEATURE_TOGGLE -> waitForElementToBePresent("//h4[contains(text(),'Feature toggles')]");
            case FEE_RATES -> waitForElementToBePresent("//h4[contains(text(),'Fee Rates')]");
            case CONTENT_MANAGEMENT -> waitForElementToBePresent("//h4[contains(text(),'Templates')]");
            default -> waitForTitleToBePresent(option.toString());
        }
    }

    public void loginIntoInternal(String userRole) throws HttpException {
        navigateToLoginPage();
        if (isElementNotPresent(world.internalNavigation.adminDropdown, SelectorType.XPATH)) {
            switch (userRole) {
                case "limitedReadOnlyUser" -> world.globalMethods.signIn(SecretsManager.getSecretValue("limitedReadOnlyUser"), SecretsManager.getSecretValue("adminPassword"));
                case "readOnlyUser" -> world.globalMethods.signIn(SecretsManager.getSecretValue("readOnlyUser"), SecretsManager.getSecretValue("adminPassword"));
                case "intSystemAdmin" -> world.globalMethods.signIn(SecretsManager.getSecretValue("intSystemAdmin"), SecretsManager.getSecretValue("intEnvPassword"));
                case "intPrepUser" -> world.globalMethods.signIn(SecretsManager.getSecretValue("intPrepUser"), SecretsManager.getSecretValue("prepEnvPassword"));
                case "intProdUser" -> world.globalMethods.signIn(SecretsManager.getSecretValue("intProdUser"), SecretsManager.getSecretValue("prepEnvPassword"));
                default -> world.internalNavigation.logInAsAdmin();
            }
        }
    }
    public void navigateToAuthorisationPage() {
        if (world.licenceCreation.isLGVOnlyLicence())
            waitAndClickByLinkText("Licence authorisation");
        else
            waitAndClickByLinkText("Operating centres and authorisation");
    }

    public void loginAndGetApplication(boolean variation) throws HttpException {
        logInAsAdmin();
        if (variation) {
            getVariationApplication();
        } else {
            getApplication();
        }
    }

    public void urlViewUsers() {
        var myURL = webAppURL.build(ApplicationType.INTERNAL, world.configuration.env, "/search/user/search/").toString();
    }

    public void getCase() {
        var caseUrl = world.configuration.env.equals(EnvironmentType.PREPRODUCTION) ?
                this.url.concat("licence/318365/cases/") :
                this.url.concat(String.format("case/details/%s", world.updateLicence.getCaseId()));
        get(caseUrl);
    }

    public void getCase(String caseId) {
        get(this.url.concat(String.format("case/details/%s", caseId)));
    }

    public void getCaseNote() {
        get(this.url.concat(String.format("case/%s/processing/notes", world.updateLicence.getCaseId())));
    }

    public void getApplication() {
        get(this.url.concat(String.format("application/%s", world.createApplication.getApplicationId())));
    }

    public void getApplication(String applicationId) {
        get(this.url.concat(String.format("application/%s", applicationId)));
    }

    public void getTransportManagerDetails(String transportManagerId) {
        get(this.url.concat(String.format("transport-manager/%s/details/", transportManagerId)));
    }

    public void getLicence() {
        get(this.url.concat(String.format("licence/%s", world.createApplication.getLicenceId())));
    }

    public void getLicence(String licenceId) {
        get(this.url.concat(String.format("licence/%s", licenceId)));
    }

    public void getVariationApplication() {
        get(this.url.concat(String.format("variation/%s", world.updateLicence.getVariationApplicationId())));
    }

    public void getHearingAppeal() {
        get(this.url.concat(String.format("case/%s/hearing-appeal/", world.updateLicence.getCaseId())));
    }

    public void getPublicInquiry() {
        get(this.url.concat(String.format("case/%s/pi/", world.updateLicence.getCaseId())));
    }

    public void getNonPublicInquiry() {
        get(this.url.concat(String.format("case/%s/non-pi/details/", world.updateLicence.getCaseId())));
    }

    public void getImpoundings() {
        get(this.url.concat(String.format("case/%s/impounding/", world.updateLicence.getCaseId())));
    }


    public void getAdminEditFee(String feeNumber) {
        get(this.url.concat(String.format("admin/payment-processing/fees/edit-fee/%s", feeNumber)));
    }

    public void getEditUserAccount(String adminUserId) {
        get(this.url.concat(String.format("admin/user-management/users/edit/%s", adminUserId)));
    }

    public void getVariationFinancialEvidencePage() {
        get(this.url.concat(String.format("variation/%s/financial-evidence", world.updateLicence.getVariationApplicationId())));
    }

    public void logIntoInternalAndClickOnTask(String taskLinkText) throws HttpException {
        logInAndNavigateToApplicationProcessingPage(false);
        clickByXPath(taskLinkText);
        waitForElementToBePresent(taskTitle);
    }

    public void navigateToPage(String type, SelfServeSection page) throws HttpException {
        if (isElementNotPresent(world.internalNavigation.adminDropdown, SelectorType.XPATH)) {
            world.internalNavigation.logInAsAdmin();
        }
        switch (type) {
            case "application" -> getApplication();
            case "licence" -> getLicence();
            case "variation" -> getVariationApplication();
        }
        switch (page.toString()) {
            case "View" -> {}
            case "Vehicles" -> {
                waitAndClickByLinkText("Vehicles");
                waitForTextToBePresent("Vehicle details");
            }
            case "Convictions and penalties" -> {
                waitAndClickByLinkText("Convictions and penalties");
                waitForTextToBePresent("Convictions and Penalties");
            }
            default -> {
                waitAndClickByLinkText(page.toString());
                waitForTextToBePresent(page.toString());
            }
        }
    }

    public void navigateToPrintIRHPPermits() {
        clickById("menu-admin-dashboard/admin-printing/irhp-permits");
    }

    public void searchForIRHPPermitsToPrint() {
        selectValueFromDropDownByIndex("irhpPermitType", SelectorType.ID, 1);
        waitAndClick("irhpPermitStock", SelectorType.ID);
        selectValueFromDropDownByIndex("irhpPermitStock", SelectorType.ID, 1);
        clickById("form-actions[search]");
    }

    public void navigateToLoginPage() {
        var myURL = webAppURL.build(ApplicationType.INTERNAL, world.configuration.env, "auth/login/").toString();
        navigate().get(myURL);
    }

    public String appealNumber;
    public String appealDate;
    public String appealDeadline;
    public String appealReason;
    public String appealOutlineGround;

    public void addAppeal() {
        long uniqueId = System.currentTimeMillis() % 100000;
        appealNumber = String.format("APP-%d", uniqueId);
        LocalDate today = LocalDate.now();
        LocalDate deadline = today.plusDays(java.util.concurrent.ThreadLocalRandom.current().nextInt(7, 30));
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        appealDate = today.format(fmt);
        appealDeadline = deadline.format(fmt);
        appealOutlineGround = "Automated test appeal outline ground - " + uniqueId;

        waitAndClickByLinkText("Add appeal");
        waitForElementToBePresent("//form[@id='appeal']");

        enterDateParts("appealDate", today);
        enterDateParts("deadlineDate", deadline);

        waitAndEnterText("//input[@id='fields[appealNo]']", SelectorType.XPATH, appealNumber);
        appealReason = selectRandomOption("//select[@id='fields[reason]']");
        waitAndEnterText("//textarea[@id='fields[outlineGround]']", SelectorType.XPATH, appealOutlineGround);

        waitAndClick("//button[@id='form-actions[submit]']", SelectorType.XPATH);
    }

    public String piAgreedDate;
    public String piAgreedByTc;
    public String piAgreedByRole;
    public String piAssignedCaseworker;
    public String piType;
    public String piLegislation;
    public String piComment;

    public void addPublicInquiry() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        piAgreedDate = today.format(fmt);
        piComment = "Automated test PI comment - " + System.currentTimeMillis();

        waitAndClickByLinkText("Add Public Inquiry");
        waitForElementToBePresent("//form[@id='traffic-commissioner-agreement-legislation']");

        enterDateParts("agreedDate", today);

        piAgreedByTc = selectRandomOption("//select[@id='fields[agreedByTc]']");
        piAgreedByRole = selectRandomOption("//select[@id='fields[agreedByTcRole]']");
        piAssignedCaseworker = selectRandomOption("//select[@id='assignedCaseworker']");

        piType = selectRandomOptionOnChosen("fields_piTypes__chosen");
        piLegislation = selectRandomOptionOnChosen("fields_reasons__chosen");

        waitAndEnterText("//textarea[@id='fields[comment]']", SelectorType.XPATH, piComment);

        waitAndClick("//button[@id='form-actions[submit]']", SelectorType.XPATH);
    }

    private String selectRandomOption(String selectXpath) {
        var select = new org.openqa.selenium.support.ui.Select(findElement(selectXpath, SelectorType.XPATH));
        var options = select.getOptions().stream()
                .filter(o -> {
                    String v = o.getAttribute("value");
                    if (v == null || v.isEmpty()) return false;
                    String lv = v.toLowerCase();
                    return !lv.equals("other") && !lv.equals("not-set");
                })
                .toList();
        if (options.isEmpty()) {
            throw new IllegalStateException("No selectable option found for " + selectXpath);
        }
        var chosen = options.get(java.util.concurrent.ThreadLocalRandom.current().nextInt(options.size()));
        String value = chosen.getAttribute("value");
        String text = chosen.getText().trim();
        select.selectByValue(value);
        return text;
    }

    private String selectRandomOptionOnChosen(String chosenContainerId) {
        waitAndClick(String.format("//div[@id='%s']//ul[@class='chosen-choices']", chosenContainerId), SelectorType.XPATH);
        String optionsXpath = String.format("//div[@id='%s']//ul[@class='chosen-results']/li[contains(concat(' ',normalize-space(@class),' '),' active-result ')]", chosenContainerId);
        waitForElementToBePresent(optionsXpath);
        var options = findElements(optionsXpath, SelectorType.XPATH);
        var target = options.get(java.util.concurrent.ThreadLocalRandom.current().nextInt(options.size()));
        String text = target.getText().trim();
        target.click();
        return text;
    }

    private void enterDateParts(String fieldName, LocalDate date) {
        waitAndEnterText(String.format("//input[@id='fields[%s]_day' or @id='%s_day']", fieldName, fieldName), SelectorType.XPATH,
                String.format("%02d", date.getDayOfMonth()));
        waitAndEnterText(String.format("//input[@id='fields[%s]_month' or @id='%s_month']", fieldName, fieldName), SelectorType.XPATH,
                String.format("%02d", date.getMonthValue()));
        waitAndEnterText(String.format("//input[@id='fields[%s]_year' or @id='%s_year']", fieldName, fieldName), SelectorType.XPATH,
                String.valueOf(date.getYear()));
    }

    public String nonPiAgreedByTcDate;
    public String nonPiHearingType;
    public String nonPiHearingDate;
    public String nonPiHearingTime;
    public String nonPiVenue;
    public String nonPiWitnessCount;
    public String nonPiPresidingStaff;
    public String nonPiOutcome;

    public void addNonPublicInquiry() {
        var rnd = java.util.concurrent.ThreadLocalRandom.current();
        LocalDate today = LocalDate.now();
        LocalDate hearing = today.plusDays(rnd.nextInt(3, 30));
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        nonPiAgreedByTcDate = today.format(fmt);
        nonPiHearingDate = hearing.format(fmt);
        String hour = String.format("%02d", rnd.nextInt(9, 17));
        String[] minuteValues = {"00", "15", "30", "45"};
        String minute = minuteValues[rnd.nextInt(minuteValues.length)];
        nonPiHearingTime = hour + ":" + minute;
        nonPiWitnessCount = String.valueOf(rnd.nextInt(1, 10));
        nonPiPresidingStaff = "Automated test presiding staff - " + System.currentTimeMillis();

        waitAndClickByLinkText("Add Non-Public Inquiry");
        waitForElementToBePresent("//form[@id='Non-Public Inquiry']");

        enterDateParts("agreedByTcDate", today);

        nonPiHearingType = selectRandomOption("//select[@id='hearingType']");

        enterDateParts("hearingDate", hearing);
        waitAndSelectValueFromDropDown("//select[@id='hearingDate_hour']", SelectorType.XPATH, hour);
        waitAndSelectValueFromDropDown("//select[@id='hearingDate_minute']", SelectorType.XPATH, minute);

        nonPiVenue = selectRandomOption("//select[@id='venue']");
        waitAndEnterText("//input[@id='fields[witnessCount]']", SelectorType.XPATH, nonPiWitnessCount);
        waitAndEnterText("//textarea[@id='fields[presidingStaffName]']", SelectorType.XPATH, nonPiPresidingStaff);
        nonPiOutcome = selectRandomOption("//select[@id='outcome']");

        waitAndClick("//button[@id='form-actions[submit]']", SelectorType.XPATH);
    }

    public String impoundingType;
    public String impoundingApplicationDate;
    public String impoundingVrm;
    public String impoundingLegislation;
    public String impoundingHearingDate;
    public String impoundingHearingTime;
    public String impoundingAgreedBy;
    public String impoundingOutcome;
    public String impoundingOutcomeSentDate;
    public String impoundingNotes;

    public void addImpounding() {
        var rnd = java.util.concurrent.ThreadLocalRandom.current();
        LocalDate today = LocalDate.now();
        LocalDate hearing = today.plusDays(rnd.nextInt(7, 60));
        LocalDate outcomeSent = today;
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        impoundingApplicationDate = today.format(fmt);
        impoundingHearingDate = hearing.format(fmt);
        impoundingOutcomeSentDate = outcomeSent.format(fmt);
        impoundingVrm = String.format("AB%05dCD", rnd.nextInt(100000));
        String hour = String.format("%02d", rnd.nextInt(9, 17));
        String[] minuteValues = {"00", "15", "30", "45"};
        String minute = minuteValues[rnd.nextInt(minuteValues.length)];
        impoundingHearingTime = hour + ":" + minute;
        impoundingNotes = "Automated test impounding notes - " + System.currentTimeMillis();

        waitAndClick("//button[@id='add' and @value='Add']", SelectorType.XPATH);
        waitForElementToBePresent("//form[@id='Impounding']");

        impoundingType = selectRandomOption("//select[@id='impoundingType']");
        enterDateParts("applicationReceiptDate", today);
        waitAndEnterText("//input[@id='vrm']", SelectorType.XPATH, impoundingVrm);

        impoundingLegislation = selectRandomOptionOnChosen("impoundingLegislationTypes_chosen");

        enterDateParts("hearingDate", hearing);
        waitAndSelectValueFromDropDown("//select[@id='hearingDate_hour']", SelectorType.XPATH, hour);
        waitAndSelectValueFromDropDown("//select[@id='hearingDate_minute']", SelectorType.XPATH, minute);

        impoundingAgreedBy = selectRandomOption("//select[@id='presidingTc']");
        impoundingOutcome = selectRandomOption("//select[@id='outcome']");
        enterDateParts("outcomeSentDate", outcomeSent);
        waitAndEnterText("//textarea[@id='fields[notes]']", SelectorType.XPATH, impoundingNotes);

        waitAndClick("//button[@id='form-actions[submit]']", SelectorType.XPATH);
    }

    public String piHearingVenue;
    public String piHearingDate;
    public String piHearingTime;
    public String piHearingLength;
    public String piHearingPresidingTc;
    public String piHearingPresidedByRole;
    public String piHearingWitnesses;
    public String piHearingDrivers;
    public String piHearingDefinition;
    public String piHearingDetails;

    public void addPiHearing() {
        var rnd = java.util.concurrent.ThreadLocalRandom.current();
        LocalDate hearing = LocalDate.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        piHearingDate = hearing.format(fmt);
        String hour = String.format("%02d", rnd.nextInt(9, 17));
        String[] minuteValues = {"00", "15", "30", "45"};
        String minute = minuteValues[rnd.nextInt(minuteValues.length)];
        piHearingTime = hour + ":" + minute;
        piHearingWitnesses = String.valueOf(rnd.nextInt(1, 10));
        piHearingDrivers = String.valueOf(rnd.nextInt(1, 10));
        piHearingDetails = "Automated test hearing details - " + System.currentTimeMillis();

        waitAndClickByLinkText("Add hearing");
        waitForElementToBePresent("//form[@id='Hearing']");

        piHearingVenue = selectRandomOption("//select[@id='venue']");
        enterDateParts("hearingDate", hearing);
        waitAndSelectValueFromDropDown("//select[@id='hearingDate_hour']", SelectorType.XPATH, hour);
        waitAndSelectValueFromDropDown("//select[@id='hearingDate_minute']", SelectorType.XPATH, minute);

        String[] lengthChoices = {"Half day", "Full day"};
        piHearingLength = lengthChoices[rnd.nextInt(lengthChoices.length)];
        waitAndClick(String.format("//div[contains(@class,'govuk-radios__item')]/label[normalize-space()='%s']", piHearingLength), SelectorType.XPATH);

        piHearingPresidingTc = selectRandomOption("//select[@id='presidingTc']");
        piHearingPresidedByRole = selectRandomOption("//select[@id='presidedByRole']");

        waitAndEnterText("//input[@id='fields[witnesses]']", SelectorType.XPATH, piHearingWitnesses);
        waitAndEnterText("//input[@id='fields[drivers]']", SelectorType.XPATH, piHearingDrivers);

        piHearingDefinition = selectRandomOptionOnUnderlyingSelect("//select[@id='fields[definition]']");
        waitAndEnterText("//textarea[@id='fields[details]']", SelectorType.XPATH, piHearingDetails);

        waitAndClick("//button[@id='form-actions[submit]']", SelectorType.XPATH);
    }

    private String selectRandomOptionOnChosenSingle(String chosenContainerId) {
        waitAndClick(String.format("//div[@id='%s']//a[contains(@class,'chosen-single')]", chosenContainerId), SelectorType.XPATH);
        String optionsXpath = String.format("//div[@id='%s']//ul[@class='chosen-results']/li[contains(concat(' ',normalize-space(@class),' '),' active-result ')]", chosenContainerId);
        waitForElementToBePresent(optionsXpath);
        var options = findElements(optionsXpath, SelectorType.XPATH);
        var target = options.get(java.util.concurrent.ThreadLocalRandom.current().nextInt(options.size()));
        String text = target.getText().trim();
        target.click();
        return text;
    }

    public String piDecisionPresidingTc;
    public String piDecisionPresidingTcRole;
    public String piDecisionDecision;
    public String piDecisionTmDecision;
    public String piDecisionWitnesses;
    public String piDecisionDate;
    public String piDecisionNotificationDate;
    public String piDecisionDefinition;
    public String piDecisionNotes;

    public void addPiDecision() {
        var rnd = java.util.concurrent.ThreadLocalRandom.current();
        LocalDate today = LocalDate.now();
        LocalDate notification = today;
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        piDecisionDate = today.format(fmt);
        piDecisionNotificationDate = notification.format(fmt);
        piDecisionWitnesses = String.valueOf(rnd.nextInt(1, 10));
        piDecisionNotes = "Automated test decision notes - " + System.currentTimeMillis();

        waitAndClickByLinkText("Add decision");
        waitForElementToBePresent("//form[@id='Register decision']");

        piDecisionPresidingTc = selectRandomOption("//select[@id='fields[decidedByTc]']");
        piDecisionPresidingTcRole = selectRandomOption("//select[@id='fields[decidedByTcRole]']");

        piDecisionDecision = selectRandomOptionOnChosen("fields_decisions__chosen");
        piDecisionTmDecision = selectRandomOptionOnChosen("fields_tmDecisions__chosen");

        waitAndEnterText("//input[@id='fields[witnesses]']", SelectorType.XPATH, piDecisionWitnesses);

        enterDateParts("decisionDate", today);
        enterDateParts("notificationDate", notification);

        piDecisionDefinition = selectRandomOptionOnUnderlyingSelect("//select[@id='fields[definition]']");
        waitAndEnterText("//textarea[@id='fields[decisionNotes]']", SelectorType.XPATH, piDecisionNotes);

        waitAndClick("//button[@id='form-actions[submit]']", SelectorType.XPATH);
    }

    private String selectRandomOptionOnUnderlyingSelect(String selectXpath) {
        var element = findElement(selectXpath, SelectorType.XPATH);
        var options = element.findElements(org.openqa.selenium.By.tagName("option")).stream()
                .filter(o -> {
                    String v = o.getAttribute("value");
                    return v != null && !v.isEmpty();
                })
                .toList();
        if (options.isEmpty()) {
            throw new IllegalStateException("No selectable option found for " + selectXpath);
        }
        var chosen = options.get(java.util.concurrent.ThreadLocalRandom.current().nextInt(options.size()));
        String value = chosen.getAttribute("value");
        String text = chosen.getText().trim();
        javaScriptExecutor(
                "var el = arguments[0]; el.value = arguments[1]; " +
                        "if (window.jQuery) { jQuery(el).val(arguments[1]).trigger('change').trigger('chosen:updated'); } " +
                        "else { el.dispatchEvent(new Event('change', {bubbles:true})); }",
                element, value);
        return text;
    }
}