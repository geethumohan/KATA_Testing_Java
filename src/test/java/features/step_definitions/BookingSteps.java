package features.step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.BasePage;
import pages.HomePage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BookingSteps extends BasePage {

    HomePage homePage = new HomePage();
    static Date checkInDate;
    static Date checkOutDate;

    @Given("I am on the home page")
    public void i_am_on_the_home_page() {
        homePage.navigateToHomePage();
    }

    @When("I browse through the page")
    public void i_browse_through_the_page() {
        homePage.goToRoomsCategory();
    }

    @Then("I have the option to book a room")
    public void i_have_the_option_to_book_a_room() {
       homePage.assertBookButtonDisplayed();
    }

    @When("I open the option to book a room")
    public void i_open_the_option_to_book_a_room() {
        homePage.clickOnBookThisRoom();
    }

    @When("I fill the form with {string}, {string}, {string} and {string}")
    public void i_fill_the_form_with_and(String firstName, String lastName, String email, String phone) {
        homePage.bookTheRoomWithDetails(firstName, lastName, email, phone);
    }

    @When("I select booking dates based {string} dates")
    public void i_select_and_dates(String checkinDate) throws ParseException {
        checkInDate = new SimpleDateFormat("yyyy-MM-dd").parse(checkinDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkInDate);
        calendar.add(Calendar.DATE, 2);

        checkOutDate = calendar.getTime();
        homePage.selectBookingDates(checkInDate, checkOutDate);
    }
    @When("I confirm the booking")
    public void i_book_the_room_with_provided_details() {
        homePage.clickOnConfirmBooking();
    }

    @Then("I get the booking successful confirmation for the provided dates")
    public void i_get_the_booking_successful_confirmation_for_the_provided_dates() {
        String startDate = new SimpleDateFormat("yyyy-MM-dd").format(checkInDate);
        String endDate = new SimpleDateFormat("yyyy-MM-dd").format(checkOutDate);
        homePage.assertSuccessfulBooking(startDate, endDate);
    }

}
