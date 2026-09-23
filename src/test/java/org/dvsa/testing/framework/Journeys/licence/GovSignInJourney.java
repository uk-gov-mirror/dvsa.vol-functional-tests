package org.dvsa.testing.framework.Journeys.licence;

import activesupport.aws.s3.SecretsManager;
import activesupport.driver.Browser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dvsa.testing.framework.Injectors.World;
import org.dvsa.testing.framework.Utils.Generic.GenericUtils;
import org.dvsa.testing.framework.pageObjects.BasePage;
import org.dvsa.testing.framework.pageObjects.enums.SelectorType;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import static activesupport.driver.Browser.navigate;

/**
 * Drives the mock GOV.UK Sign In service.
 * The mock replaces the full GOV.UK One Login identity journey with a single page:
 * entering a test email and clicking Continue returns the browser straight back to VOL.
 */
public class GovSignInJourney extends BasePage {

    private static final Logger LOGGER = LogManager.getLogger(GovSignInJourney.class);

    public static final String MOCK_EMAIL_SECRET_KEY = "govSignInMockEmail";
    private static final String DEFAULT_MOCK_EMAIL = "test.success@mock.gov";

    private static final String EMAIL_FIELD_ID = "email";
    private static final String CONTINUE_BUTTON_ID = "sign-in-button";
    private static final String MOCK_PAGE_TITLE = "Sign in";
    private static final int MOCK_PAGE_TIMEOUT_SECONDS = 15;

    private static String mockEmail = null;

    private final World world;

    public GovSignInJourney(World world) {
        this.world = world;
    }

    public void navigateToGovUkSignIn() {
        if (isTextPresent("Declaration information")) {
            if (isElementPresent("sign-in-button", SelectorType.ID)) {
                waitAndClick("sign-in-button", SelectorType.ID);
            } else if (isElementPresent("sign", SelectorType.ID)) {
                waitAndClick("sign", SelectorType.ID);
            }
        }
    }

    public void signInGovAccount() {
        completeMockSignIn(getMockSignInEmail());
    }

    public void registerGovAccount() {
        completeMockSignIn(getMockSignInEmail());
    }

    /**
     * Mock sign in email held in AWS Secrets Manager under {@value #MOCK_EMAIL_SECRET_KEY}.
     * Falls back to the mock service default if the secret is unavailable.
     */
    public static synchronized String getMockSignInEmail() {
        if (mockEmail == null) {
            String secretValue = null;
            try {
                secretValue = SecretsManager.getSecretValue(MOCK_EMAIL_SECRET_KEY);
            } catch (Exception e) {
                LOGGER.warn("Could not read " + MOCK_EMAIL_SECRET_KEY + " from secrets manager: " + e.getMessage());
            }
            if (secretValue == null || secretValue.isBlank()) {
                LOGGER.warn("Secret " + MOCK_EMAIL_SECRET_KEY + " not set, using default mock email");
                mockEmail = DEFAULT_MOCK_EMAIL;
            } else {
                mockEmail = secretValue.trim();
            }
        }
        return mockEmail;
    }

    /**
     * Completes the single mock sign in page. Once Continue is clicked the mock
     * redirects straight back to the expected VOL page.
     */
    public void completeMockSignIn(String email) {
        if (!isMockSignInPageDisplayed()) {
            LOGGER.info("Mock GOV.UK Sign In page is not displayed - already returned to VOL");
            return;
        }
        LOGGER.info("Completing mock GOV.UK Sign In as " + email);
        waitAndEnterText(EMAIL_FIELD_ID, SelectorType.ID, email);
        waitAndClick(CONTINUE_BUTTON_ID, SelectorType.ID);
    }

    /**
     * The mock service decides the identity returned to VOL, so the signatory name is not
     * asserted - only that a signature was recorded against today's date.
     */
    public static boolean isDigitallySignedToday() {
        String signedDate = GenericUtils.getCurrentDate("d MMM yyyy");
        return isElementPresent(
                String.format("//*[contains(text(),'Signed by') and contains(text(),'%s')]", signedDate),
                SelectorType.XPATH);
    }

    public boolean isMockSignInPageDisplayed() {
        return isTitlePresent(MOCK_PAGE_TITLE, MOCK_PAGE_TIMEOUT_SECONDS)
                && isElementPresent(EMAIL_FIELD_ID, SelectorType.ID);
    }

    public void changeProtocolForSignInToWorkOnLocal() throws InterruptedException, MalformedURLException {
        Thread.sleep(1000);
        if (world.configuration.env.toString().equals("local")) {
            URL url = new URL(Objects.requireNonNull(navigate().getCurrentUrl()));
            String urlWithUnsecureProtocol = url.getProtocol().replace("s", "").concat("://" + url.getAuthority() + url.getFile());
            Browser.navigate().get(urlWithUnsecureProtocol);
        }
    }
}
