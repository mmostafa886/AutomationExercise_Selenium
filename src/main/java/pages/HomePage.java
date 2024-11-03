package pages;

import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class HomePage {
    private WebDriver driver;
    private static String webBaseUrl = System.getProperty("baseUrl");
    @Getter
    private MenuBar menuBar;

    private By homeConcent = By.className("fc-cta-consent");
    private By appLogo = By.className("logo");
    private By categories = By.className("card_travel");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.menuBar = new MenuBar(driver);
    }

    @Step("Navigate to the App homepage")
    public HomePage openAutomationExerciseWebSite() {
        driver.navigate().to(webBaseUrl);
        if (driver.findElement(homeConcent).isDisplayed()) {
            driver.findElement(homeConcent).click();
        }
        return this;
    }

    @Step("Verify that the Home page is already opened")
    public HomePage assertHomePageOpened() {
       Assert.assertTrue(driver.findElement(appLogo).isDisplayed());
        return this;
    }

}
