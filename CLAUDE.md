# CLAUDE.md

Guidance for AI assistants working in `vol-functional-tests` (Cucumber + Selenium regression suite for VOL).

## Architecture: Journey Object Model (mandatory)

This repo uses the **Journey Object Model**, not the Page Object Model. All new and changed tests must follow it.

```
Feature file (.feature)  →  Step definitions (stepdefs/)  →  Journey (Journeys/)  →  BasePage / UniversalActions (driver helpers)
```

### Layers

| Layer | Location | Responsibility |
|---|---|---|
| Feature | `src/test/resources/org/dvsa/testing/framework/features/...` | Business-readable scenarios and tags only. |
| Step definitions | `src/test/java/org/dvsa/testing/framework/stepdefs/...` | Thin glue: **one call** into a journey on `world`. No selectors, no Selenium, no assertions on page elements. |
| Journey | `src/test/java/org/dvsa/testing/framework/Journeys/...` | Owns the user journey: navigation, actions, page state captured for later checks, **and** the verifications for that journey. |
| Driver helpers | `pageObjects/BasePage`, `pageObjects/enums/SelectorType`, `Utils/Generic/UniversalActions` | Low-level Selenium primitives only (click, enter text, wait, find). Used by journeys, never by step defs. |

### Rules

1. **Step definitions only call journeys.** A step body should look like `world.addressesJourney.verifySomething();`. Step def classes must not extend `BasePage`, hold xpaths/ids/names, call `findElement`, or assert on raw element values.
2. **Locators are private to the journey.** Declare them as `private static final String` inside the journey. Never expose locators, field names, or element getters as public API (that is the page-object pattern we are avoiding).
3. **Journey methods are intent-level.** Name them by what the user does or what must be true, e.g. `clearMandatoryCorrespondenceFieldsAndSave()`, `verifyMandatoryFieldValidationErrorsAreDisplayed()`, not `getAddressLine1Field()` / `clickSaveButton()`.
4. **Verifications live in the journey.** Use JUnit `assertEquals` / `assertTrue` with clear failure messages inside `verify...` methods. State needed for later verification (e.g. original values before an edit) is stored in private journey fields.
5. **Do not add or use classes under `pageObjects/external` or `pageObjects/internal`** for new work. Use journeys instead.
6. **Register every new journey** in both:
   - `Injectors/World.java` as a public field (e.g. `public AddressesJourney addressesJourney;`)
   - `stepdefs/vol/Initialisation.java` (e.g. `world.addressesJourney = new AddressesJourney(world);`)
7. **Reuse before you add.** Check existing journeys (`SelfServeNavigation`, `InternalNavigation`, `UniversalActions`, etc.) for navigation and common actions before writing new ones.

### Example

```java
// Step definition: thin, no selectors
@Then("the addresses page should display the mandatory field validation errors")
public void theAddressesPageShouldDisplayTheMandatoryFieldValidationErrors() {
    world.addressesJourney.verifyMandatoryFieldValidationErrorsAreDisplayed();
}

// Journey: owns locators, actions and assertions
public class AddressesJourney extends BasePage {
    private static final String VALIDATION_SUMMARY = "//*[@id='validationSummary']";

    public void verifyMandatoryFieldValidationErrorsAreDisplayed() {
        waitForElementToBePresent(VALIDATION_SUMMARY);
        assertTrue(isElementPresent(VALIDATION_SUMMARY, SelectorType.XPATH), "...");
    }
}
```

## Test-writing conventions

- **Action then assert.** Scenarios must perform the real action on the page (add, edit, upload, save, etc.) and assert the outcome. Do not write "page loads" or "button is present" only tests.
- **Do not guess selectors.** If the DOM for a page or state (including error states) is unknown, ask for the HTML and URL from the target environment before writing locators.
- **Prefer stable locators:** `id`, then `name`, then text-based XPath. Note that some VOL pages reuse ids across sections (e.g. `addressLine1`, `postcode` in the Addresses form), so use `name` there.
- Tag new scenarios with a unique `@tag` so they can be run in isolation.

## Build and run

```bash
mvn clean test-compile                                          # compile (do this after adding steps to avoid "undefined step")
mvn clean test -Dcucumber.filter.tags="@your-tag"               # run a single scenario/tag
```
