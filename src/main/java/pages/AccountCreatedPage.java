package pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

@Slf4j
public class AccountCreatedPage {

    private WebDriver driver;
    private MenuBar menuBar;

    private By successMessage = By.xpath("//h2[@data-qa='account-created']");
    private By continueButton = By.xpath("//a[@data-qa='continue-button']");

    public AccountCreatedPage(WebDriver driver) {
        this.driver = driver;
        menuBar = new MenuBar(driver);
    }

    @Step("Account created Successfully")
    public AccountCreatedPage assertSuccessMessageVisibility() {
        log.info("Verify the Success Message display after New account creation");
        Assert.assertTrue(driver.findElement(successMessage).isDisplayed(), "The Success Message wasn't displayed");
        return this;
    }

    @Step("Press the Continue button")
    public HomePage pressConitueButton() {
        driver.findElement(continueButton).click();
        return new HomePage(driver);
    }
}
