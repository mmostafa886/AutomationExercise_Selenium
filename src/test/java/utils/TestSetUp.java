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

    private static final PropertiesLoader propertiesLoader = new PropertiesLoader();
    private static String environment = propertiesLoader.getProperty("environment");
    private static String gridUrl = propertiesLoader.getProperty("grid_url");
    public static String testBrowser = propertiesLoader.getProperty("Browser");
    public static String headless = propertiesLoader.getProperty("headless");

    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<WebDriverWait> waitThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<FluentWait<WebDriver>> fluentWaitThreadLocal = new ThreadLocal<>();

    @Getter
    @Setter
    private static String ExecutionBrowser;

    protected static HomePage homePage;
    protected static MenuBar menuBar;

    private TestSetUp() {
    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static WebDriverWait getWait() {
        return waitThreadLocal.get();
    }

    public static FluentWait<WebDriver> getFluentWait() {
        return fluentWaitThreadLocal.get();
    }

    public static void setUp(String browser) {
        boolean headlessBoolean = headless != null && headless.equalsIgnoreCase("false") ? false : true;
        if (driverThreadLocal.get() == null) {
            WebDriver driver = null;
            try {
                log.info("Creating WebDriver instance...");
                driver = DriverFactory.createDriver(environment, gridUrl, browser, headlessBoolean);
                log.info("WebDriver instance created successfully.");
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            driverThreadLocal.set(driver);
            waitThreadLocal.set(new WebDriverWait(driver, Duration.ofSeconds(30)));
            fluentWaitThreadLocal.set(new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(30))
                    .pollingEvery(Duration.ofMillis(300)).ignoring(Exception.class));
        }
    }

    @Step("Start the WebApp")
    public static void startWebAppInstance() {
        homePage = new HomePage(getDriver());
        homePage.openAutomationExerciseWebSite();
        menuBar = new MenuBar(getDriver());
    }

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

    public static String getCurrentDatTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return currentDateTime.format(formatter);
    }

    @Step("Capture Screenshot")
    public static void captureScreenShot(String methodName) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) getDriver();
            byte[] screenshotBytes = screenshot.getScreenshotAs(OutputType.BYTES);
            File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);

            File screenshotDir = new File("screenshots");
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            File destinationFile = new File(screenshotDir, "test_" + getCurrentDatTime() + ".png");
            Files.move(screenshotFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            attachScreenshotToAllure(screenshotBytes);
            log.info("Screenshot saved at: " + destinationFile.getAbsolutePath());
        } catch (Exception e) {
            log.info("Screenshot Not Created");
            e.printStackTrace();
        }
    }

    @Step("Attach Screenshot to Report")
    @Attachment(value = "Failure Screenshot", type = "image/png")
    private static byte[] attachScreenshotToAllure(byte[] screenshot) {
        log.info("Attach Screenshot to Report");
        return screenshot;
    }
}
