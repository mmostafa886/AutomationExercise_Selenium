package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.MalformedURLException;
import java.net.URI;

public class DriverFactory {

    private DriverFactory() {
        //Empty private constructor to ensure the inability to created instances of the class
    }

   /**
     * @param environment comes from the config.properties
     * @param gridUrl     comes from the config.properties
     * @param testBrowser passed as parameter when starting the test which in turn will get it from the testng.xml
     * @return while the method returns a WebDriver but it can return both WebDriver (Chrome, Firefox & Edge) & RemoteWebDriver
     * This causes no problem (till the moment, it may need to be altered in the furture) as the RemoteWebDriver is an implementation of the WebDriver interface
     */
    public static WebDriver createDriver(String environment, String gridUrl, String testBrowser, boolean headless) throws MalformedURLException {
        if (environment.equalsIgnoreCase("grid")) { //This if statement checks, is the tests are executed on a grid according to the "environment" variable
            return getRemoteWebDriver(testBrowser, gridUrl, headless);
        } else if (environment.equalsIgnoreCase("local")) {
            return getLocalWebDriver(testBrowser, headless);
        } else {
            /*An expressive exception to be printed in the terminal in case the provided environment is not one of the handled cases
             * This approach helps in defining the source of the problem (in case there is one)*/
            throw new RuntimeException("No Valid Environment has been provided");
        }
    }

    /**
     * @param testBrowser as an input
     * @return the corresponding WebDriver based on the provided browser
     */
    private static WebDriver getLocalWebDriver(String testBrowser, boolean headless) {
        //This if statement checks, is the tests are executed on locally according to the "environment" variable
        if (testBrowser.equalsIgnoreCase("chrome")) {
            //return ChromeDriver with ChromeOptions if we need to execute the tests on chrome (according to the "testBrowser" variable)
            return new ChromeDriver(getChromeOptions(headless));
        } else if (testBrowser.equalsIgnoreCase("firefox")) {
            //return FirefoxDriver with FirefoxOptions if we need to execute the tests on firefox (according to the "testBrowser" variable)
            return new FirefoxDriver(getFirefoxOptions(headless));
        } else if (testBrowser.equalsIgnoreCase("safari")) {
            //return SafariDriver with SafariOptions if we need to execute the tests on firefox (according to the "testBrowser" variable)
            return new SafariDriver(getSafariOptions(headless));
        } else {
            //return EdgeDriver with EdgeOptions if we need to execute the tests on Edge (according to the "testBrowser" variable & if the provided browser wasn't chrome or firefox)
            return new EdgeDriver(getEdgeOptions(headless));
        }
    }

    /**
     * @param testBrowser as an input
     * @param gridUrl     from the config.properties
     * @return the corresponding RemoteWebDriver based on the provided browser
     */
    private static RemoteWebDriver getRemoteWebDriver(String testBrowser, String gridUrl, boolean headless) throws MalformedURLException {
        var URL = URI.create(gridUrl).toURL();
        if (testBrowser.equalsIgnoreCase("chrome")) {
            //return RemoteWebDriver with ChromeOptions if we need to execute the tests on chrome (according to the "testBrowser" variable)
            return new RemoteWebDriver(URL, getChromeOptions(headless));
        } else if (testBrowser.equalsIgnoreCase("firefox")) {
            //return RemoteWebDriver with FirefoxOptions if we need to execute the tests on firefox (according to the "testBrowser" variable)
            return new RemoteWebDriver(URL, getFirefoxOptions(headless));
        } else {
            //return RemoteWebDriver with EdgeOptions if we need to execute the tests on edge (according to the "testBrowser" variable & if the provided browser wasn't chrome or firefox)
            return new RemoteWebDriver(URL, getEdgeOptions(headless));
        }
    }


    /**
     * @return Chrome options in case we are using Chrome browser
     */
    private static ChromeOptions getChromeOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
        }
        // Add any additional Chrome options if needed
        return options;
    }

    /**
     * @return Firefox options in case we are using Firefox browser
     */
    private static FirefoxOptions getFirefoxOptions(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("--headless");
            options.addArguments("--window-size=1920,1080");
        }
        // Add any additional Firefox options if needed
        return options;
    }

    /**
     * @return Edge options in case we are using Edge browser
     */
    private static EdgeOptions getEdgeOptions(boolean headless) {
        EdgeOptions options = new EdgeOptions();
        if (headless) {
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
        }
        // Add any additional Edge options if needed
        return options;
    }

    /**
     * @return Edge options in case we are using Safari browser
     */
    private static SafariOptions getSafariOptions(boolean headless) {
        SafariOptions options = new SafariOptions();
        if (headless) { // Safari doesn't fully support headless mode yet.
             throw new UnsupportedOperationException("Safari doesn't support headless mode.");
        }
        // Add any additional Edge options if needed
        return options;
    }
}
