package enums;

import activesupport.IllegalBrowserException;
import activesupport.driver.Browser;
import org.dvsa.testing.lib.pages.BasePage;

import java.net.MalformedURLException;

import static junit.framework.TestCase.assertTrue;

public class test extends BasePage {
    public static void main(String[] args) throws MalformedURLException, IllegalBrowserException {
        Browser.navigate().get("https://dvsa:leeds@dvsa-olcs.herokuapp.com/selfserve/surrenders/v3/confirmation");
        assertTrue(isTextPresent("notifications@vehicle-operator-licensing.service.gov.uk",10));
    }

}
