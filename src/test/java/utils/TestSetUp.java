package utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import pages.HomePage;
import pages.MenuBar;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class TestSetUp {

    private TestSetUp() {
    }

    private static final PropertiesLoader propertiesLoader = new PropertiesLoader();
    private static String environment = propertiesLoader.getProperty("environment");
    private static String gridUrl = propertiesLoader.getProperty("grid_url");
    public static String testBrowser = propertiesLoader.getProperty("Browser");

    //ThreadLocal Initialization: Ensures driver, wait, and fluentWait are thread-safe by using ThreadLocal.
    //ThreadLocal should be initialized at class level to avoid repeated initialization.
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<WebDriverWait> waitThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<FluentWait<WebDriver>> fluentWaitThreadLocal = new ThreadLocal<>();

    @Getter
    @Setter
    private static String ExecutionBrowser;

    protected HomePage homePage;
    protected MenuBar menuBar;

    // Getter Methods: Fetches the instances of(driver, wait & fluentWait) from their ThreadLocals ensuring thread safety.
    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }
    public static WebDriverWait getWait() {
        return waitThreadLocal.get();
    }
    public static FluentWait<WebDriver> getFluentWait() {
        return fluentWaitThreadLocal.get();
    }

    /**
     * ThreadLocal.withInitial(): Defines the initialization logic for the ThreadLocal variable.
     * Lambda Expression () -> {...}: Inside withInitial, the lambda initializes the WebDriver instance.
     * DriverFactory.createDriver(): Creates the WebDriver instance, considering the environment, grid URL, and browser type.
     * ThreadLocal.set(): Assigns the initialized WebDriver to the current thread.
     * ThreadLocal.get(): Retrieves the WebDriver instance for the current thread.
     * This combo ensures each test thread works with its independent WebDriver instance, maintaining isolation and preventing conflicts.
     * waitThreadLocal.set: Assigns the initialized wait to the current thread.
     * fluentWaitThreadLocal.set: Assigns the initialized fluentWait to the current thread.
     */
    public static void setUp(String browser) {
        driverThreadLocal.set(ThreadLocal.withInitial(() -> {
            try {
                return DriverFactory.createDriver(environment, gridUrl, browser);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }).get());
        waitThreadLocal.set(new WebDriverWait(getDriver(), Duration.ofSeconds(30)));
        fluentWaitThreadLocal.set(new FluentWait<>(getDriver()).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofMillis(300)).ignoring(Exception.class));
    }

    public void startWebAppInstance() {
        homePage = new HomePage(getDriver());
        homePage.openAutomationExerciseWebSite();
        menuBar = new MenuBar(getDriver());
    }

    // Teardown Method: Properly cleans up each thread’s instances.
    @Step("Driver Teardown")
    public static void teardown() {
        if (getDriver() != null) {
            getDriver().quit();
            driverThreadLocal.remove();
            waitThreadLocal.remove();
            fluentWaitThreadLocal.remove();
            System.out.println("Driver Closed");
        }
    }

    public static void screenshotOnFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            log.info("Take Screenshot on failure");
            Allure.step("Take Screenshot on failure");
            captureScreenShot(result.getName());
        }
    }

    public WindowManager getWindowManager() {
        return new WindowManager(getDriver());
    }

    /**
     * @return formattedDateTime in order to use it whenever needed to have unique name
     * we will use it later to name the screenshots
     */
    public static String getCurrentDatTime() {
        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Define a custom date-time format if needed
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        // Format the current date and time using the specified formatter
        String formattedDateTime = currentDateTime.format(formatter);

        return formattedDateTime;
    }

    /**
     * This method is used to Capture the Screenshot & then attach it to the Allure report.
     */
    @Step("Capture Screenshot")
    public static void captureScreenShot(String methodName) {
        try {
            // Take a screenshot using WebDriver
            TakesScreenshot screenshot = (TakesScreenshot) getDriver();
            byte[] screenshotBytes = screenshot.getScreenshotAs(OutputType.BYTES);
            File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);

            // Define the directory within your project
            File screenshotDir = new File("screenshots");
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs(); // Create the directory if it doesn't exist
            }
            // Save the screenshot to the project directory
            File destinationFile = new File(screenshotDir, "test_" + getCurrentDatTime() + ".png");
            Files.move(screenshotFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // Attach the screenshot to Allure report
            attachScreenshotToAllure(screenshotBytes);
            //Used whenever we want to log the screenshot taking into the Allure report
            //Allure.step("Screenshot saved at:  " + screenshotDir + "test_" + getCurrentDatTime() + ".png");
            log.info("Screenshot saved at:  " + screenshotDir + "/test_" + getCurrentDatTime() + ".png");
        } catch (Exception e) {
            log.info("Screenshot Not Created");
            e.printStackTrace();
        }
    }

    /**
     * This is a standard allure method to attach images to the report
     */
    @Step("Attach Screenshot to Report")
    @Attachment(value = "Failure Screenshot", type = "image/png")
    private static byte[] attachScreenshotToAllure(byte[] screenshot) {
        log.info("Attach Screenshot to Report");
        return screenshot;
    }

}
