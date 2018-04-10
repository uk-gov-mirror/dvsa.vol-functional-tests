package org.dvsa.testing.framework.stepdefs.Utils;

import org.dvsa.testing.lib.pages.BasePage;

public class GenerateLetter extends BasePage {

    public static void generateLetter(){
        clickByLinkText("Docs & attachments");
        isTextPresent("1 Docs & attachments", 60);
        clickByName("New letter");
        isTextPresent("Generate letter", 60);
        selectValueFromDropDown("details[category]","Application");
        selectValueFromDropDownByIndex("details[documentSubCategory]",1);
        selectValueFromDropDownByIndex("details[documentTemplate]",5);
        clickByName("form-actions[submit]");
    }
}
