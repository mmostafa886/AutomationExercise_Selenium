package pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import utils.PropertiesLoader;

@Slf4j
public class HomePage {
    private WebDriver driver;
    private static final PropertiesLoader propertiesLoader = new PropertiesLoader();
    private static final String webBaseUrl = propertiesLoader.getProperty("baseUrl");
    @Getter
    private MenuBar menuBar;

    private By homeConcent = By.className("fc-cta-consent");
    private By appLogo = By.className("logo");
    private By categories = By.className("card_travel");

    public HomePage(WebDriver driver) {
        Allure.step("Open the Web App HomePage");
        this.driver = driver;
        log.info("Open the Web App HomePage");
    }

    @Step("Navigate to the App homepage")
    public HomePage openAutomationExerciseWebSite() {
        log.info("Navigating to URL: " + webBaseUrl);
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
