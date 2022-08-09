package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriversUtils.*;

import java.util.Objects;

import static utils.DriversUtils.driver;
import static utils.DriversUtils.getDriver;

public class CommonUtils {

    public void scrollDown() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0,250)");
    }

    public static void scrollToElement(WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView();", element);
    }

    public static void clickElement(WebElement element) {
        WebDriverWait wait = new WebDriverWait(getDriver(), 10 );
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    public static void sendKeysToElement(WebElement element, String elementText) {
        WebDriverWait wait = new WebDriverWait(getDriver(), 10 );
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(elementText);
    }

    public static boolean isEquals(String startDateDay, WebElement webElement) {
        return Objects.equals(webElement.getText(), startDateDay);
    }
}
