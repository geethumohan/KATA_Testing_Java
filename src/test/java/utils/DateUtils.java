package utils;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.time.Month;
import java.util.*;

import static utils.DriversUtils.getDriver;

public class DateUtils {

    public static void selectDates(WebElement startDate, WebElement endDate) {

        Actions action = new Actions(getDriver());
        action.clickAndHold(startDate).moveByOffset(20,10).pause(1000)
                .moveToElement(endDate)
                .release(endDate)
                .build()
                .perform();
   }

    public static int getMonthNumberBasedOnMonthNumber(String monthName) {
        Month month = Month.valueOf(monthName.toUpperCase(Locale.ROOT));
        int monthNumber = month.getValue() ;
        return monthNumber;
    }
}
