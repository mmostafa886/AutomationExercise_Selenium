package pages;

import io.qameta.allure.Step;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import utils.TestSetUp;

@Slf4j
public class MenuBar {
    private WebDriver driver;

    private By products = By.className("card_travel");
    private By signupAndLogin = By.className("fa-lock");
    @Getter
    protected By logout = By.xpath("//a[@href='/logout']");
    protected By deleteAccountButton = By.className("fa-trash-o");

    public MenuBar(WebDriver driver) {this.driver = driver; }

    @Step("Navigate to Products page")
    public ProductsPage goToProductsPage() {
        driver.findElement(products).click();
        TestSetUp.getFluentWait().until(ExpectedConditions.titleContains("Products"));
        return new ProductsPage(driver);
    }

    @Step("Navigate to Signup/Login page")
    public SignUpAndLoginPage goToSignUpAndLoginPage() {
        driver.findElement(signupAndLogin).click();
        return new SignUpAndLoginPage(driver);
    }

    @Step("Perform Logout")
    public SignUpAndLoginPage logOut() {
        driver.findElement(logout).click();
        return new SignUpAndLoginPage(driver);
    }

    @Step("Verify the Logout button is not displayed")
    public MenuBar isLogOutNotDisplayed() {
        log.info("Verify the Logout button is not displayed");
        Assert.assertTrue(driver.findElements(logout).isEmpty(), "Logout button is displayed");
        return this;
    }

    //TODO: Modify the method to return "DeleteAccountPage" (which is not created yet),
    // wait till the delete button is not displayed
    // & assert this page display
    @Step("Delete Account")
    public MenuBar deleteAccount() {
        log.info("Deleting Account ....");
        driver.findElement(deleteAccountButton).click();
        return this;
    }

}
