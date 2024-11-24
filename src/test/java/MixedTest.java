import apis.ProductApi;
import apis.UserApi;
import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.MenuBar;
import utils.TestSetUp;
import static utils.TestSetUp.getDriver;

@Slf4j
public class MixedTest {

    String browser;

    @BeforeClass(alwaysRun = true)
    public void getBrowser(ITestContext context) {
        TestSetUp.setExecutionBrowser(context);
        browser = TestSetUp.getExecutionBrowser();
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        TestSetUp.setUp(browser);
        Allure.step("Browser: " + browser);
        TestSetUp.startWebAppInstance();
    }

    @AfterMethod(alwaysRun = true)
    public void removeDriver(ITestResult result) {
        TestSetUp.screenshotOnFailure(result);
        TestSetUp.teardown();
    }

    @Test(groups = { "Smoke" },
            description = "TC-MIX-001 - Create New user account through API then Login from Web and delete account through API Using Request Parameters")
    public void registerNewUserAPIAndLoginTest() {
        log.info("TC-MIX-001 - Create New user account through API then Login from Web " +
                "and delete account through API Using Request Parameters");
        UserApi userApi = new UserApi();
        log.info("Get DataFile");
        userApi.createAccountUsingDataFile("src/test/resources/testDataFiles/AccountData.json");
        new MenuBar(getDriver())
                .goToSignUpAndLoginPage()
                .userLogin(userApi.getRandomName() + "@email.com", userApi.getRandomName())
                .assertLoginSuccess()
                .getMenuBar().logOut()
                .getMenuBar().isLogOutNotDisplayed();
        userApi.deleteAccount(userApi.getRandomName() + "@email.com", userApi.getRandomName());
    }

    @Test(groups = { "Smoke", "Regression" },
            description = "TC-MIX-002 - Search for a Random Product")
    public void searchForProduct() {
        ProductApi productApi = new ProductApi();
        String randomProductName = productApi.getRandomProductName();
        new HomePage(getDriver()).getMenuBar().goToProductsPage()
                .searchForProducts(randomProductName)
                .compareResultsToSearchText(randomProductName);
    }
}
