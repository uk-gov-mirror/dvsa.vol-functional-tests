package org.dvsa.testing.framework.stepdefs.Utils;


import activesupport.MissingRequiredArgument;
import activesupport.aws.s3.S3;
import activesupport.string.Str;
import activesupport.system.Properties;
import org.dvsa.testing.framework.stepdefs.Utils.CreateInterimGoodsLicenceAPI;
import org.dvsa.testing.lib.Environment;
import org.dvsa.testing.lib.URI;
import org.dvsa.testing.lib.browser.Browser;
import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;
import org.dvsa.testing.lib.utils.ApplicationType;
import org.dvsa.testing.lib.utils.EnvironmentType;




public class SelfserveSiteObjects extends BasePage {



    public static void loginSS() throws MissingRequiredArgument {
        CreateInterimGoodsLicenceAPI CreateInGoods = new CreateInterimGoodsLicenceAPI();

        CreateInGoods.registerUser();

        EnvironmentType env = Environment.enumType(Properties.get("env", true));
        String URL = org.dvsa.testing.lib.URI.build(ApplicationType.EXTERNAL, env);
        Browser.go(URL);
       enterField(nameAttribute("input","username"), CreateInGoods.getLoginId());
       enterField(nameAttribute("input","password"),S3.getTempPassword(CreateInGoods.getEmailAddress()));
    }

    public static void userNameChange(String FIRST_NAME, String LAST_NAME) {
        enterField(nameAttribute("input","main[loginId]"),FIRST_NAME);
         enterField(nameAttribute("input", "main[familyName]"), LAST_NAME);
         click(nameAttribute("button","form-actions[submit]"));
    }

    public static void yourAccountLink () {
        clickByLinkText("Your account");
    }
    //Must be on a valid license to use this method
    //TODO:tell wondy this had to be one method as you can only create the variation within the area you are changing.
    public static void createVariationForNewTransportManager() {
     clickByLinkText("Transport Manager");
     clickByLinkText("change your licence");
     isElementPresent("//*[contains(text(), 'Applying to change a licence')]", SelectorType.XPATH);
     click(nameAttribute("button", "form-actions[submit]"));
     click(nameAttribute("button", "table[action]"));
     clickByLinkText("Or add a new Transport Manager");
     enterField(nameAttribute("input", "data[forename]"),Str.randomWord(7));
     enterField(nameAttribute("input", "data[familyname]"),Str.randomWord(7));
     enterDOB(12,06,1994);
     select("//*[@id=\"User\"]/fieldset[1]/fieldset/label[2]");
     click(nameAttribute("button","form-actions[continue]"));
     clickByLinkText("Back");
     clickByLinkText("Review and declarations");
     click(nameAttribute("input","declarationsAndUndertakings[declarationConfirmation]"));
     click(nameAttribute("button", "form-actions[submit]"));
    }

}
