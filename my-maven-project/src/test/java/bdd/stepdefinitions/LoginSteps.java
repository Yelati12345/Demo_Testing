package bdd.stepdefinitions;

import browsersetup.StartBrowser;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.logging.Logger;

import static org.junit.Assert.assertNotNull;

public class LoginSteps {

    WebDriver driver;
    StartBrowser startBrowser = new StartBrowser();
    private static final Logger logger = Logger.getLogger(LoginSteps.class.getName());

    @Given("the user is on the Google homepage")
    public void theUserIsOnTheGoogleHomepage() {
        if (driver == null) {
            driver = startBrowser.startBrowser("Chrome");
            logger.info("Chrome options set.");
        }
        assertNotNull("Driver initialization failed", driver);
        driver.get("https://www.google.co.in/");
        logger.info("Navigated to Google homepage.");
    }

    @When("the user navigates to {string}")
    public void theUserNavigatesTo(String url) {
        driver.get(url);
        logger.info("Navigated to URL: " + url);
    }

    @Then("the user click on the continue button")
    public void theUserClickOnTheContinueButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement continueButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"step1ContinueBtn\"]")));
        assertNotNull("Continue button not found", continueButton);
        continueButton.click();
        logger.info("Clicked on the continue button.");
    }

    @And("Select the age as {int} from the dropdown")
    public void selectTheAgeAsFromTheDropdown(int age) throws InterruptedException {
        WebElement ageDropdown = driver.findElement(By.id("Self"));
        Thread.sleep(6000);
        ageDropdown.click();
        Thread.sleep(6000);
        Select select = new Select(ageDropdown);
        select.selectByVisibleText(String.valueOf(age));
        logger.info("Selected age: " + age);
    }

    @Then("the user closes the browser")
    public void theUserClosesTheBrowser() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed.");
        }
    }
}