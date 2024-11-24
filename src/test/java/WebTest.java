import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.AccountCreatedPage;
import pages.HomePage;
import pages.MenuBar;
import pages.SignUpAndLoginPage;
import utils.TestSetUp;

import static utils.TestSetUp.getDriver;

@Slf4j
public class WebTest {
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

    @Test (groups = { "Smoke" },description = "TC-WEB-001 - Open the Web-App homepage")
    public void openHomePage() {
        new HomePage(getDriver()).assertHomePageOpened();
    }

    @Test(groups = { "Regression" },description = "TC-WEB-002 - Products' page navigation")
    public void openProductsPage() {
        new MenuBar(getDriver()).goToProductsPage().assertProductsPageOpened();
    }

    @Test(groups = { "Smoke", "Regression" },description = "TC-WEB-003 - Register new user account")
    public void registerNewUserAndLoginTest() {
        SignUpAndLoginPage signUpAndLogin = TestSetUp.getMenuBar().goToSignUpAndLoginPage();
        AccountCreatedPage accountCreatedPage = signUpAndLogin
                .createAccuntWithDataFromJson("src/test/resources/testDataFiles/AccountData.json");
        accountCreatedPage.assertSuccessMessageVisibility();
        HomePage homePage = accountCreatedPage.pressConitueButton().assertLoginSuccess();
       MenuBar menuBar = homePage.getMenuBar().logOut().getMenuBar().isLogOutNotDisplayed();
        menuBar.goToSignUpAndLoginPage()
                .userLogin(signUpAndLogin.getRandomName()+ "@email.com", signUpAndLogin.getRandomName())
                .assertLoginSuccess();
        homePage.getMenuBar().deleteAccount().isLogOutNotDisplayed();
    }

}
