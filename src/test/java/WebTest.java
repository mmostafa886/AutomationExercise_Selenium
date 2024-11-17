import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.AccountCreatedPage;
import pages.HomePage;
import pages.MenuBar;
import pages.SignUpAndLoginPage;
import utils.JsonDataReader;
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

    @Test
    public void registerNewUserAndLoginTest() {
        JsonDataReader testData = new JsonDataReader("src/test/resources/testDataFiles/AccountData.json");
        SignUpAndLoginPage signUpAndLogin = TestSetUp.getMenuBar().goToSignUpAndLoginPage();
        AccountCreatedPage accountCreatedPage = signUpAndLogin.createAccount(randomName, randomName + "@email.com", randomName
                , testData.getTestData("FirstName"), testData.getTestData("LastName")
                , testData.getTestData("Address"), testData.getTestData("Country")
                , testData.getTestData("State"), testData.getTestData("City")
                , testData.getTestData("Zipcode"), testData.getTestData("Mobile"));
        accountCreatedPage.assertSuccessMessageVisibility();
        HomePage homePage = accountCreatedPage.pressConitueButton().assertLoginSuccess();
       MenuBar menuBar = homePage.getMenuBar().logOut().getMenuBar().isLogOutNotDisplayed();
        menuBar.goToSignUpAndLoginPage(). userLogin(randomName + "@email.com", randomName).assertLoginSuccess();
        homePage.getMenuBar().deleteAccount().isLogOutNotDisplayed();
    }

}
