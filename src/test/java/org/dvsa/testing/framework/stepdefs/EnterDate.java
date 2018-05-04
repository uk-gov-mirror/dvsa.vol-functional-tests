package org.dvsa.testing.framework.stepdefs;

import static org.dvsa.testing.lib.pages.BasePage.enterText;

public class EnterDate {


    public void enterDate(int day, int month, int year) {
        enterText("receivedDate_day", String.valueOf(day));
        enterText("receivedDate_month", String.valueOf(month));
        enterText("receivedDate_year", String.valueOf(year));

    }

//    public void enterEfDate(int day, int month, int year){
//        enterText("effectiveDate_day", String.valueOf(LocalDate.now().plusDays(7)));
//        enterText("effectiveDate_month", String.valueOf(LocalDate.now().plusMonths(4)));
//        enterText("effectiveDate_year", String.valueOf(LocalDate.now().getYear()));
//
//    }

//    }
//

}
