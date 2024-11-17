import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.TestSetUp;

@Slf4j
public class MultiThreadedTest {
    String browser;
    By searchField = By.name("q");
    By gmailHyperLink = By.xpath("//a[contains(@href,\"/mail\")]");
    By acceptAllButton = By.id("L2AGLb");

    //////////////TAU\\\\\\\\\\\\\\
    By certificates = By.xpath("//nav[@class=\"nav-links can-hide\"]/div/a[@href=\"/certificate/index.html\"]");
    By top100Students = By.xpath("//nav[@class=\"nav-links can-hide\"]/div/a[@href=\"/tau100.html\"]");
    By rankStudents = By.className("tau100-item");

    public WebDriver getDriver() {
        return TestSetUp.getDriver();
    }

    @BeforeClass
    public void getBrowser(ITestContext context) {
        TestSetUp.setExecutionBrowser(context);
        browser = TestSetUp.getExecutionBrowser();
    }

    @BeforeMethod
    public void startDriver() {
        TestSetUp.setUp(browser);
        Allure.step("Browser: " + browser);
    }

    @AfterMethod
    public void removeDriver(ITestResult result) {
        TestSetUp.screenshotOnFailure(result);
        TestSetUp.teardown();
    }

    @Test(description = "Search Google For SHAFT_Engine")
    public void searchGoogleForShaftEngine() {
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
        TestSetUp.getWait().until(ExpectedConditions.invisibilityOfElementLocated(gmailHyperLink));
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
    //Intentionally failing
    @Test(description = "Search Google For SHAFT_Engine#2")
    public void searchGoogleForShaftEngine2() {
        log.info("Navigate to google");
        Allure.addAttachment("Log", "text/plain", "Navigate to google");
        getDriver().navigate().to("https://www.google.com/");
        //Allure.step("Navigate to google", Status.PASSED);

        Allure.addAttachment("Log", "text/plain", "Browser title is Google");
        //This where the test is failing, change the expected value to "Google"
        Assert.assertEquals(getDriver().getTitle(), "Google012", "Browser Title doesn't equal \"Google\"");
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
        TestSetUp.getWait().until(ExpectedConditions.invisibilityOfElementLocated(gmailHyperLink));
        //Allure.step("Search For SHAFT_Engine", Status.PASSED);

        Allure.addAttachment("Log", "text/plain", "Browser title contains SHAFT_Engine");
        Assert.assertTrue(getDriver().getTitle().contains("SHAFT_Engine"), "Browser Title doesn't contain \"SHAFT_Engine\"");
        //Allure.step("Browser title contains \"SHAFT_Engine\"", Status.PASSED);
        log.info("Browser Title: " + getDriver().getTitle());
    }

}
