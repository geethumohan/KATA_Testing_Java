package pages;

import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.text.SimpleDateFormat;
import java.util.*;

import static utils.CommonUtils.*;
import static utils.DateUtils.getMonthNumberBasedOnMonthNumber;
import static utils.DateUtils.selectDates;
import static utils.DriversUtils.getDriver;

public class HomePage extends BasePage {

    @FindBy(tagName = "h2")
    private WebElement roomCategoryIdentifier;

    @FindBy(css = "[name='firstname']")
    private WebElement firstName;

    @FindBy(css = "[name='lastname']")
    private WebElement lastName;

    @FindBy(css = "[name='email']")
    private WebElement email;

    @FindBy(css = "[name='phone']")
    private WebElement phone;

    @FindBy(xpath = "//div[contains(@class,'rbc-calendar')]")
    private WebElement datePicker;

    @FindBy(xpath = "//*[contains(@class, 'rbc-btn-group')]/button[1]")
    private WebElement todayButton;

    @FindBy(xpath = "//*[contains(@class, 'rbc-btn-group')]/button[2]")
    private WebElement backButton;

    @FindBy(xpath = "//*[contains(@class, 'rbc-btn-group')]/button[3]")
    private WebElement nextButton;

    @FindBy(css = ".rbc-toolbar-label")
    private WebElement datePickerHeader;

    @FindBy(css = "div.rbc-date-cell:not(.rbc-off-range)")
    private List<WebElement> getEnabledCalendarDays;

    @FindBy(css = "div.rbc-date-cell")
    private List<WebElement> getAllCalendarDays;

    @FindBy(css = "button.btn-outline-primary.book-room")
    private WebElement bookButton;

    @FindBy(css = "button.openBooking")
    private WebElement bookThisRoomButton;

    @FindBy(css = "div.confirmation-modal")
    private WebElement bookingConfirmationModal;

    @FindBy(css = "div.confirmation-modal .btn-outline-primary")
    private WebElement closeConfirmationModalButton;

    public HomePage() {
        PageFactory.initElements(getDriver(), this);
    }

    public void goToRoomsCategory() {
        try {
            scrollToElement(roomCategoryIdentifier);
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println();
            System.out.println("Error in the rooms category method");
        }
    }

    public void navigateToHomePage() {
        getDriver().get("https://automationintesting.online/#/");
        getDriver().navigate().refresh();
    }

    public void clickOnBookThisRoom() {
        clickElement(bookThisRoomButton);
    }

    public void clickOnConfirmBooking() {
        clickElement(bookButton);
    }

    public void bookTheRoomWithDetails(String firstName, String lastName, String email, String phone) {
        sendKeysToElement(this.firstName, firstName);
        sendKeysToElement(this.lastName, lastName);
        sendKeysToElement(this.email, email);
        sendKeysToElement(this.phone, phone);
    }

    public void selectBookingDates(Date checkInDate, Date checkoutDate)  {

        String startDateMonth = new SimpleDateFormat("MMMM").format(checkInDate);
        String startDateDay = new SimpleDateFormat("dd").format(checkInDate);
        String startDateYear = new SimpleDateFormat("yyyy").format(checkInDate);
        String endDateDay = new SimpleDateFormat("dd").format(checkoutDate);

        selectCalenderYear(startDateYear);
        selectCalenderMonth(startDateMonth);

        WebElement startDateElement = getEnabledCalendarDays.stream()
                .filter(webElement -> isEquals(startDateDay, webElement)).findFirst()
                .orElseThrow(() -> new NoSuchElementException("No WebElement found containing " + startDateDay));


        WebElement endDateElement = getAllCalendarDays.stream()
                .filter(i -> getAllCalendarDays.indexOf(startDateElement) < getAllCalendarDays.indexOf(i))
                .filter(webElement -> isEquals(endDateDay, webElement)).findFirst()
                .orElseThrow(() -> new NoSuchElementException("End date not visible on the screen." + endDateDay));

        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", this.datePickerHeader);

        selectDates(startDateElement, endDateElement);
    }

    public void assertSuccessfulBooking(String startDate, String endDate) {
        Assert.assertTrue(bookingConfirmationModal.getText().contains("Booking Successful!"));
        String bookingDates = bookingConfirmationModal.getText();
        closeTheConfirmationPopUp();
        Assert.assertTrue("Check in date is not correct", bookingDates.contains(startDate) );
        Assert.assertTrue("Check out date is not correct",bookingDates.contains(endDate));
    }

    public void assertBookButtonDisplayed() {
        Assert.assertEquals(true, bookThisRoomButton.isDisplayed());
    }

    private void closeTheConfirmationPopUp() {
        clickElement(closeConfirmationModalButton);
    }
    
    private void selectCalenderMonth(String startDateMonth) {
        String monthAndYear = this.datePickerHeader.getText();
        String calendarMonth = monthAndYear.split(" ")[0];

        while (!this.datePickerHeader.getText().contains(startDateMonth)) {
            if (getMonthNumberBasedOnMonthNumber(startDateMonth) > getMonthNumberBasedOnMonthNumber(calendarMonth)) {
                clickElement(this.nextButton);
            } else {
                clickElement(this.backButton);
            }
        }
    }

    private void selectCalenderYear(String startDateYear) {
        String monthAndYear = this.datePickerHeader.getText();
        int calendarYear = Integer.parseInt(monthAndYear.split(" ")[1]);

        while (!this.datePickerHeader.getText().contains(startDateYear)) {
            if (Integer.parseInt(startDateYear) > calendarYear)
                clickElement(this.nextButton);
            else
                clickElement(this.backButton);
        }
    }

}

