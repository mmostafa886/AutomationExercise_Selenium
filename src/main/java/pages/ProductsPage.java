package pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import utils.TestSetUp;

@Slf4j
public class ProductsPage {
    private WebDriver driver;
    private MenuBar menuBar;

    private By advertiseArea = By.id("advertisement");
    private By allProductsArea = By.className("features_items");
    private By allProductsAreaTitle = By.className("title");
    private By searchProductField = By.id("search_product");
    private By searchButton = By.id("submit_search");
    private By firstProductCard = By.xpath("(//div[@class=\"product-image-wrapper\"])[1]");
    private By firstProductCardTextElement = By.xpath("(//div[@class=\"product-overlay\"])[1]//p");
    private By firstProductCardTextElement2 = By.xpath("(//div[@class=\"productinfo\"])[1]//p");


    public ProductsPage(WebDriver driver) {
        this.driver = driver ;
        this.menuBar = new MenuBar(driver);
    }

    @Step("Assert The Products page is opened")
    public ProductsPage assertProductsPageOpened() {
       String productsAreaTitle = driver.findElement(allProductsAreaTitle).getText();
        Assert.assertTrue(productsAreaTitle.contains("ALL PRODUCTS"));
        return this;
    }

    @Step("Search For Product with [{searchText}]")
    public ProductsPage searchForProducts(String searchText) {
        driver.findElement(searchProductField).clear();
        driver.findElement(searchProductField).sendKeys(searchText);
        driver.findElement(searchButton).click();
        return this;
    }

    @Step("Get the title of the first product Card1")
    public String getFirstProductCardTitle(String searchText) {
        log.info("Get product Card title");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(firstProductCard));
        // Scroll by an additional 20 pixels vertically
        js.executeScript("window.scrollBy(0, 20);");
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(firstProductCard)).perform();
        TestSetUp.getFluentWait().until(ExpectedConditions.elementToBeClickable(firstProductCardTextElement));
        Allure.step("Driver Title: "+driver.findElement(firstProductCardTextElement).getText());
        log.info("Driver Title: "+driver.findElement(firstProductCardTextElement).getText());
        return driver.findElement(firstProductCardTextElement).getText();
    }

    @Step("Compare product title to the search text")
    public ProductsPage compareResultsToSearchText(String searchText) {
        Assert.assertTrue(getFirstProductCardTitle(searchText).equalsIgnoreCase(searchText)
                , "Element doesn't contain The provided search text");
        return this;
    }

}
