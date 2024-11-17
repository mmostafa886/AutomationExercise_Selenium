package pages;

import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;


public class SignUpAndLoginPage {

    private WebDriver driver;
    @Getter
    private MenuBar menuBar;

    private By loginButton = By.xpath("//button[@data-qa='login-button']");
    private By loginEmail = By.xpath("//input[@data-qa='login-email']");
    private By loginPassword = By.xpath("//input[@data-qa='login-password']");
    private By signUpButton = By.xpath("//button[@data-qa='signup-button']");
    private By signupName = By.xpath("//input[@data-qa='signup-name']");
    private By signupEmail = By.xpath("//input[@data-qa='signup-email']");
    private By mrGender = By.id("id_gender1");
    private By mrsGender = By.id("id_gender2");
    private By signupPassword = By.xpath("//input[@data-qa='password']");
    private By birthdateDay = By.id("days");
    private By birthdateMonth = By.id("months");
    private By birthdateYear = By.id("years");
    private By newsletterCheckbox = By.id("newsletter");
    private By specialOffersCheckbox = By.id("optin");
    private By addresFirstName = By.id("first_name");
    private By addresLastName = By.id("last_name");
    private By addresDetailedAdress = By.id("address1");
    private By addresCountry = By.id("country");
    private By addresState = By.id("state");
    private By addresCity = By.id("city");
    private By addresZipCode = By.id("zipcode");
    private By addresMobileNumber = By.id("mobile_number");
    private By createAccountButton = By.xpath("//button[@data-qa='create-account']");

    public SignUpAndLoginPage(WebDriver driver) {
        this.driver = driver;
        this.menuBar = new MenuBar(driver);
    }

    public SignUpAndLoginPage signUpFirstStep(String name, String emailAddress) {
        driver.findElement(signupName).clear();
        driver.findElement(signupName).sendKeys(name);
        driver.findElement(signupEmail).clear();
        driver.findElement(signupEmail).sendKeys(emailAddress);
        driver.findElement(signUpButton).click();
        return this;
    }

    @Step("Create New user account from Web")
    public AccountCreatedPage createAccount(String name, String emailAddress, String password, String firstName, String lastName, String address
            , String country, String state, String city, String zipCode, String mobileNumber) {
        signUpFirstStep(name, emailAddress);
        driver.findElement(mrGender).click();
        driver.findElement(signupPassword).clear();
        driver.findElement(signupPassword).sendKeys(password);
        new Select(driver.findElement(birthdateDay)).selectByVisibleText("1");
        new Select(driver.findElement(birthdateMonth)).selectByIndex(3);
        new Select(driver.findElement(birthdateYear)).selectByVisibleText("1998");
        driver.findElement(addresFirstName).clear();
        driver.findElement(addresFirstName).sendKeys(firstName);
        driver.findElement(addresLastName).clear();
        driver.findElement(addresLastName).sendKeys(lastName);
        driver.findElement(addresDetailedAdress).clear();
        driver.findElement(addresDetailedAdress).sendKeys(address);
        new Select(driver.findElement(addresCountry)).selectByVisibleText(country);
        driver.findElement(addresState).clear();
        driver.findElement(addresState).sendKeys(state);
        driver.findElement(addresCity).clear();
        driver.findElement(addresCity).sendKeys(city);
        driver.findElement(addresZipCode).clear();
        driver.findElement(addresZipCode).sendKeys(zipCode);
        driver.findElement(addresMobileNumber).clear();
        driver.findElement(addresMobileNumber).sendKeys(mobileNumber);
        driver.findElement(createAccountButton).click();

        return new AccountCreatedPage(driver);
    }

    @Step("Perform Login")
    public HomePage userLogin(String emailAddress, String password) {
        driver.findElement(loginEmail).sendKeys(emailAddress);
        driver.findElement(loginPassword).sendKeys(password);
        driver.findElement(loginButton).click();

        return new HomePage(driver);
    }

}
