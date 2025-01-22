package bdd.stepdefinitions;

import browsersetup.StartBrowser;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.Assert;

public class LoginHRM {
    WebDriver driver;
    StartBrowser startBrowser = new StartBrowser();


    @Given("the user is on the OrangeHRM login page")
    public void theUserIsOnTheOrangeHRMLoginPage() {

        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @When("the user enters username {string} and password {string}")
    public void theUserEntersUsernameAndPassword(String username, String password) {
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
    }

    @When("the user enters invalid credentials")
    public void theUserEntersInvalidCredentials() {
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        usernameField.sendKeys("invalidUser");
        passwordField.sendKeys("invalidPass");
    }

    @When("the user leaves the username and password fields empty")
    public void theUserLeavesTheUsernameAndPasswordFieldsEmpty() {
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        usernameField.sendKeys("");
        passwordField.sendKeys("");
    }

    @When("the user clicks on the login button")
    public void theUserClicksOnTheLoginButton() {
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
        loginButton.click();
    }

    @Then("the user should be redirected to the dashboard")
    public void theUserShouldBeRedirectedToTheDashboard() {
        String expectedUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard";
        Assert.assertEquals(expectedUrl, driver.getCurrentUrl());
        driver.quit();
    }

    @Then("an error message should be displayed")
    public void anErrorMessageShouldBeDisplayed() {
        WebElement errorMessage = driver.findElement(By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']"));
        Assert.assertTrue(errorMessage.isDisplayed());
        driver.quit();
    }
}