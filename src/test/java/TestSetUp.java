import io.qameta.allure.Attachment;
import lombok.Getter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pages.HomePage;
import pages.MenuBar;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.WindowManager;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class TestSetUp {
    private static String environment;
    private static String gridUrl;
    private static String testBrowser;
    private final static ThreadLocal<WebDriver> driverThreadLocal = ThreadLocal.withInitial(() -> {
        String browser = TestSetUp.testBrowser; // Get the TestBrowser value
        try {
            return DriverFactory.createDriver(environment, gridUrl, browser);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    });

    @Getter //Used Lombok getter instead of defining or writing my own getter
    private WebDriver driver;

    protected HomePage homePage;
    protected MenuBar menuBar;

    ConfigReader configReader;

    @Parameters({"TestBrowser"})
    @BeforeClass
    public void setUp(String TestBrowser) throws Exception {
        configReader = new ConfigReader();
        Properties props = configReader.loadConfig("config.properties");
        environment = props.getProperty("environment");
        gridUrl = props.getProperty("grid_url");

        if (getDriver() == null) {
            driver = DriverFactory.createDriver(environment, gridUrl, TestBrowser);
            driverThreadLocal.set(driver);
        }
    }

    public void startWebAppInstance() {
        homePage = new HomePage(getDriver());
        homePage.openAutomationExerciseWebSite();
        menuBar = new MenuBar(getDriver());
    }

//    @BeforeMethod
//    public void goHome() {
//        try {
//            getDriver().get("https://the-internet.herokuapp.com/");
//            homePage = new HomePage(getDriver());
//            setCookie();
//        } catch (Exception e) {
//            System.err.println("Exception during test setup: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }

    @AfterClass
    public void teardown() {
        if (getDriver() != null) {
            getDriver().quit();
            driverThreadLocal.remove();
            System.out.println("Driver Closed");
        }
    }

//    private void setCookie() {
//        Cookie cookie = new Cookie.Builder("tau", "123")
//                .domain("the-internet.herokuapp.com")
//                .build();
//        getDriver().manage().addCookie(cookie);
//    }
//
//    private void deleteCookie(String cookieName) {
//        Cookie cookie = new Cookie.Builder("optimizelyBuckets", "%7B%TD").build();
//        getDriver().manage().deleteCookie(cookie);
//    }

    public WindowManager getWindowManager() {
        return new WindowManager(getDriver());
    }

    /**
     * @return formattedDateTime in order to use it whenever needed to have unique name
     * we will use it later to name the screenshots
     */
    public String getCurrentDatTime(){
        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Define a custom date-time format if needed
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        // Format the current date and time using the specified formatter
        String formattedDateTime = currentDateTime.format(formatter);

        return formattedDateTime;
    }

    /**
     * To Capture the Screenshot
     * then we call another method to attach it to the Allure report
     */
    public void captureScreenShot(String methodName){
        try {
            // Take a screenshot using WebDriver
            TakesScreenshot screenshot = (TakesScreenshot) getDriver();
            byte[] screenshotBytes = screenshot.getScreenshotAs(OutputType.BYTES);
            File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);

            // Save the screenshot to a file or upload to a storage service
            Files.move(screenshotFile.toPath(), new File("resources/screenshots/test_"+getCurrentDatTime()+".png").toPath());
            // Attach the screenshot to Allure report
            attachScreenshotToAllure(screenshotBytes);
        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
        }
    }

    /**
     * This is a standard allure method to attach images to the report
     */
    @Attachment(value = "Failure Screenshot", type = "image/png")
    private byte[] attachScreenshotToAllure(byte[] screenshot) {
        return screenshot;
    }
}
