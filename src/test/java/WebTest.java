import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.HomePage;
import pages.MenuBar;
import utils.TestSetUp;

import static utils.TestSetUp.getDriver;

@Slf4j
public class WebTest {
    String browser;
    String randomName = RandomStringUtils.randomAlphanumeric(10).toLowerCase();

    @BeforeClass
    public void getBrowser(ITestContext context) {
        TestSetUp.setExecutionBrowser(context);
        browser = TestSetUp.getExecutionBrowser();
    }

    @BeforeMethod
    public void beforeTest() {
        TestSetUp.setUp(browser);
        Allure.step("Browser: " + browser);
        TestSetUp.startWebAppInstance();
    }

    @AfterMethod
    public void removeDriver(ITestResult result) {
        TestSetUp.screenshotOnFailure(result);
        TestSetUp.teardown();
    }

    @Test
    public void openHomePage() {
        new HomePage(getDriver()).assertHomePageOpened();
    }

    @Test
    public void openProductsPage() {
        new MenuBar(getDriver()).goToProductsPage().assertProductsPageOpened();
    }
}
