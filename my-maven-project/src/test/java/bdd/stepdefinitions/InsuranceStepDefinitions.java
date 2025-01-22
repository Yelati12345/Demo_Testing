package bdd.stepdefinitions;

import browsersetup.StartBrowser;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static org.junit.Assert.*;
import static org.openqa.selenium.Keys.TAB;

public class InsuranceStepDefinitions {
    WebDriver driver;
    StartBrowser browser = new StartBrowser();

    @Given("the user is on the registration form")
    public void the_user_is_on_the_registration_form() {
        driver = browser.startBrowser("chrome");
        driver.get("http://127.0.0.1:8080/");
    }

    @When("the user enters {string} in the First Name field")
    public void the_user_enters_in_the_First_Name_field(String firstName) {
        WebElement firstNameField = driver.findElement(By.id("first-name"));
        firstNameField.clear();
        firstNameField.sendKeys(firstName);
    }

    @Then("the First Name field should accept the input")
    public void the_First_Name_field_should_accept_the_input() {
        WebElement firstNameField = driver.findElement(By.id("first-name"));
        assertNotNull(firstNameField.getAttribute("value"));
    }

    @Then("the First Name field should not be empty")
    public void the_First_Name_field_should_not_be_empty() {
        WebElement firstNameField = driver.findElement(By.id("first-name"));
        assertFalse(firstNameField.getAttribute("value").isEmpty());
    }

    @Then("the First Name field should have a maximum length of 50 characters")
    public void the_First_Name_field_should_have_a_maximum_length_of_50_characters() {
        WebElement firstNameField = driver.findElement(By.id("first-name"));
        assertTrue(firstNameField.getAttribute("value").length() <= 50);
    }

    @When("the user enters {string} in the Last Name field")
    public void the_user_enters_in_the_Last_Name_field(String lastName) {
        WebElement lastNameField = driver.findElement(By.id("last-name"));
        lastNameField.clear();
        lastNameField.sendKeys(lastName);
    }

    @Then("the Last Name field should accept the input")
    public void the_Last_Name_field_should_accept_the_input() {
        WebElement lastNameField = driver.findElement(By.id("last-name"));
        assertNotNull(lastNameField.getAttribute("value"));
    }

    @Then("the Last Name field should not be empty")
    public void the_Last_Name_field_should_not_be_empty() {
        WebElement lastNameField = driver.findElement(By.id("last-name"));
        assertFalse(lastNameField.getAttribute("value").isEmpty());
    }

    @Then("the Last Name field should have a maximum length of 50 characters")
    public void the_Last_Name_field_should_have_a_maximum_length_of_50_characters() {
        WebElement lastNameField = driver.findElement(By.id("lastName"));
        assertTrue(lastNameField.getAttribute("value").length() <= 50);
    }

    @When("the user enters {string} in the Middle Name field")
    public void the_user_enters_in_the_Middle_Name_field(String middleName) {
        WebElement middleNameField = driver.findElement(By.id("middle-name"));
        middleNameField.clear();
        middleNameField.sendKeys(middleName);
    }

    @Then("the Middle Name field should accept the input")
    public void the_Middle_Name_field_should_accept_the_input() {
        WebElement middleNameField = driver.findElement(By.id("middle-name"));
        assertNotNull(middleNameField.getAttribute("value"));
    }

    @Then("the Middle Name field should have a maximum length of 50 characters")
    public void the_Middle_Name_field_should_have_a_maximum_length_of_50_characters() {
        WebElement middleNameField = driver.findElement(By.id("middle-name"));
        assertTrue(middleNameField.getAttribute("value").length() <= 50);
    }
    @When("the user click on the drop down")
    public void the_user_click_on_the_drop_down() {
        WebElement insuranceTypeField = driver.findElement(By.id("insurance-type"));
        insuranceTypeField.click();
    }


    @When("the user enters {string} in the Date of Birth field")
    public void the_user_enters_in_the_Date_of_Birth_field(String dob) {
        WebElement dobField = driver.findElement(By.id("dob"));
        dobField.clear();
        dobField.sendKeys(dob);

    }

    @Then("the Date of Birth field should accept the input")
    public void the_Date_of_Birth_field_should_accept_the_input() {
        WebElement dobField = driver.findElement(By.id("dob"));
        assertNotNull(dobField.getAttribute("value"));
    }

    @Then("the Date of Birth field should not be empty")
    public void the_Date_of_Birth_field_should_not_be_empty() {
        WebElement dobField = driver.findElement(By.id("dob"));
        assertFalse(dobField.getAttribute("value").isEmpty());
    }

    @Then("the Date of Birth field should be a valid date")
    public void the_Date_of_Birth_field_should_be_a_valid_date() {
        WebElement dobField = driver.findElement(By.id("dob"));
        // Add date validation logic here
    }

    @When("the user selects {string} from the Insurance Type drop-down menu")
    public void the_user_selects_from_the_Insurance_Type_drop_down_menu(String insuranceType) {
        WebElement insuranceTypeField = driver.findElement(By.id("insurance-type"));
        insuranceTypeField.click();
        Select select = new Select(insuranceTypeField);
        select.selectByVisibleText(insuranceType);
    }

    @Then("the Insurance Type field should accept the selection")
    public void the_Insurance_Type_field_should_accept_the_selection() {
        WebElement insuranceTypeField = driver.findElement(By.id("insuranceType"));
        assertNotNull(insuranceTypeField.getAttribute("value"));
    }

    @Then("the Insurance Type field should have options {string}, {string}, and {string}")
    public void the_Insurance_Type_field_should_have_options(String option1, String option2, String option3) {
        WebElement insuranceTypeField = driver.findElement(By.id("insuranceType"));
        // Add logic to verify options here
    }

    @Then("the Premium Message should display {string}")
    public void the_Premium_Message_should_display(String message) {
        WebElement premiumMessage = driver.findElement(By.id("premiumMessage"));
        assertEquals(message, premiumMessage.getText());
    }

    @Then("the Premium Message should automatically update upon insurance type selection")
    public void the_Premium_Message_should_automatically_update_upon_insurance_type_selection() {
        // Add logic to verify automatic update here
    }

    @When("user click on the Next button")
    public void userClickOnTheNextButton() {
        WebElement nextButton = driver.findElement(By.id("next-1"));
        nextButton.click();

    }
    @When("user click on the Next2 button")
    public void user_click_on_the_next2_button() {
        WebElement nextButton2 = driver.findElement(By.id("next-2"));
        nextButton2.click();
    }

}
