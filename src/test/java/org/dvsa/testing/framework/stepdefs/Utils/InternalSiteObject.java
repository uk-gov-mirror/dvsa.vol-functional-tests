package org.dvsa.testing.framework.stepdefs.Utils;

import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.internal.Application;
import org.jetbrains.annotations.NotNull;

public class InternalSiteObject extends BasePage{

    public static void loginInternal(String USERNAME, String PASSWORD) {
        enterField(nameAttribute("input","username"), USERNAME);
        enterField(nameAttribute("input", "password"),PASSWORD);
        click(nameAttribute("input","submit"));
    }

    public static void checkHistTable(String subCategory, String usersName){
     isExpectedTextInElementWithin("//*[@id=\"main\"]/div[1]/div/div[2]/div/form/div[2]/table", subCategory, 0);
     isExpectedTextInElementWithin("//*[@id=\"main\"]/div[1]/div/div[2]/div/form/div[2]/table", usersName, 0);
    }

    public static void searchInternal(String searchValue){
        enterField(nameAttribute("input","search"), searchValue);
        click(nameAttribute("input","submit"));
    }


}
