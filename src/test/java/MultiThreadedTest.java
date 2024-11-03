import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.DriverFactory;
import utils.PropertiesLoader;

import java.net.MalformedURLException;
import java.time.Duration;

@Slf4j
public class MultiThreadedTest {
    private static final PropertiesLoader propertiesLoader = new PropertiesLoader();
    private static String environment = propertiesLoader.getProperty("environment");
    private static String gridUrl = propertiesLoader.getProperty("grid_url");
    private static String testBrowser =propertiesLoader.getProperty("browser");

    private static ThreadLocal<WebDriver> driverThreadLocal;

    By searchField = By.name("q");
    By gmailHyperLink = By.xpath("//a[contains(@href,\"/mail\")]");
    ////textarea[@title="Search"]
    By acceptAllButton = By.id("L2AGLb");

    //////////////TAU\\\\\\\\\\\\\\
    By certificates = By.xpath("//nav[@class=\"nav-links can-hide\"]/div/a[@href=\"/certificate/index.html\"]");
    By top100Students = By.xpath("//nav[@class=\"nav-links can-hide\"]/div/a[@href=\"/tau100.html\"]");
    By rankStudents = By.className("tau100-item");

    // Define the FluentWait
     FluentWait<WebDriver> fluentWait;
    WebDriverWait wait;

    public void initializeDriver(String providedBrowser) {
        String browser;
        if (!(providedBrowser == null)) {
            browser = providedBrowser;
        } else {
            browser = testBrowser;
        }
        driverThreadLocal = ThreadLocal.withInitial(() -> {
            try {
                return DriverFactory.createDriver(environment, gridUrl, browser);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        });
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
        fluentWait = new FluentWait<>(getDriver()) .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(Exception.class);
    }

    public void quitDriver() {
        driverThreadLocal.get().quit();
        driverThreadLocal.remove();
    }

    public WebDriver getDriver() {
        return driverThreadLocal.get();
    }


    @BeforeMethod
    public void startDriver() {
        String browser = System.getProperty("browser");
        initializeDriver(browser);
    }

    @AfterMethod
    public void removeThread() {
        quitDriver();
    }

    @Test(description = "Search Google For SHAFT_Engine")
    public void searchGoogleForShaftEngine() throws InterruptedException {
        log.info("Navigate to google");
        //Allure.addAttachment("Log", "text/plain", "Navigate to google");
        getDriver().navigate().to("https://www.google.com/");
        Allure.step("Navigate to google", Status.PASSED);
        //Allure.addAttachment("Log", "text/plain", "Browser title is Google");
        Assert.assertEquals(getDriver().getTitle(), "Google", "Browser Title doesn't equal \"Google\"");
        Allure.step("Browser title is Google", Status.PASSED);
        log.info("Browser Title: " + getDriver().getTitle());
        try {
            getDriver().findElement(acceptAllButton).click();
        } catch (Exception e) {
            log.info("The \"Accept All\" button is not displayed");
            Allure.step("The \"Accept All\" button is not displayed", Status.FAILED);
        }
        log.info("Search for SHAFT_Engine");
        //Allure.addAttachment("Log", "text/plain", "Search For SHAFT_Engine");
        getDriver().findElement(searchField).sendKeys("SHAFT_Engine");
        getDriver().findElement(searchField).sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(gmailHyperLink));
        Allure.step("Search For SHAFT_Engine");
        //Allure.addAttachment("Log", "text/plain", "Browser title contains SHAFT_Engine");
        Assert.assertTrue(getDriver().getTitle().contains("SHAFT_Engine"), "Browser Title doesn't contain \"SHAFT_Engine\"");
        Allure.step("Browser title contains \"SHAFT_Engine\"", Status.PASSED);
        log.info("Browser Title: " + getDriver().getTitle());
    }

    //
//    @Test(description = "Test Automation University Navigation")
//    public void testAutomationUniversityNavigation() {
//        getDriver().browser().navigateToURL("https://testautomationu.applitools.com/");
//        getDriver().browser().assertThat().title().contains("Test");
//        getDriver().element().click(certificates);
//        getDriver().element().click(top100Students);
//        int studentsNumber = getDriver().element().getElementsCount(rankStudents);
//        Validations.assertThat().object(studentsNumber).isNotNull();
//    }
//
    @Test(description = "Search Google For SHAFT_Engine#2")
    public void searchGoogleForShaftEngine2() {
        log.info("Navigate to google");
        Allure.addAttachment("Log", "text/plain", "Navigate to google");
        getDriver().navigate().to("https://www.google.com/");
        //Allure.step("Navigate to google", Status.PASSED);
        Allure.addAttachment("Log", "text/plain", "Browser title is Google");
        Assert.assertEquals(getDriver().getTitle(), "Google", "Browser Title doesn't equal \"Google\"");
        //Allure.step("Browser title is Google", Status.PASSED);
        log.info("Browser Title: " + getDriver().getTitle());
        try {
            getDriver().findElement(acceptAllButton).click();
        } catch (Exception e) {
            log.info("The \"Accept All\" button is not displayed");
            Allure.step("The \"Accept All\" button is not displayed", Status.FAILED);
        }
        log.info("Search for SHAFT_Engine");
        Allure.addAttachment("Log", "text/plain", "Search For SHAFT_Engine");
        getDriver().findElement(searchField).sendKeys("SHAFT_Engine");
        getDriver().findElement(searchField).sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(gmailHyperLink));
        //Allure.step("Search For SHAFT_Engine", Status.PASSED);
        Allure.addAttachment("Log", "text/plain", "Browser title contains SHAFT_Engine");
        Assert.assertTrue(getDriver().getTitle().contains("SHAFT_Engine"), "Browser Title doesn't contain \"SHAFT_Engine\"");
        //Allure.step("Browser title contains \"SHAFT_Engine\"", Status.PASSED);
        log.info("Browser Title: " + getDriver().getTitle());
    }
//
//    @Test(description = "Test Automation University Navigation#2")
//    public void testAutomationUniversityNavigation2() {
//        getDriver().browser().navigateToURL("https://testautomationu.applitools.com/");
//        getDriver().browser().assertThat().title().contains("Test");
//        getDriver().element().click(certificates);
//        getDriver().element().click(top100Students);
//        int studentsNumber = getDriver().element().getElementsCount(rankStudents);
//        Validations.assertThat().object(studentsNumber).isNotNull();
//    }


}
