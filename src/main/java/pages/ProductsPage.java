package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class ProductsPage {
    private WebDriver driver;
    private MenuBar menuBar;

    private By advertiseArea = By.id("advertisement");
    private By allProductsArea = By.className("features_items");
    private By allProductsAreaTitle = By.className("title");
    private By serachProductField = By.id("search_product");
    private By searchButton = By.id("submit_search");
    private By firstProductCard = By.xpath("(//div[@class=\"product-image-wrapper\"])[1]");
//    private By firstProductCard2 = Locator.hasTagName("div").containsAttribute("class", "product-image-wrapper").isFirst().build();
    private By firstProductCardTextElement = By.xpath("(//div[@class=\"product-overlay\"])[1]//p");
//    private By firstProductCardTextElement2 = Locator.hasTagName("div")
//            .containsAttribute("class", "product-overlay")
//            .axisBy().followingSibling("p").isFirst().build();
//    private LocatorBuilder productCardsLocator = Locator.hasTagName("div")
//            .containsAttribute("class", "single-products");
//    private LocatorBuilder productCardTextLocator = Locator.hasTagName("div")
//            .containsAttribute("class", "product-overlay")
//            .axisBy().followingSibling("p");

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
//
//    @Step("Search For Product with [{searchText}]")
//    public ProductsPage searchForProducts(String searchText) {
//        driver.element().type(serachProductField, searchText).click(searchButton);
//        return this;
//    }
//
//
//    //Using By
//    @Step("Get the title of the first product Card1")
//    public String getFirstProductCardText1(String searchText) {
//        driver.browser().waitUntilUrlContains(searchText);
//        driver.element().hover(firstProductCard).performElementAction().waitToBeReady(firstProductCardTextElement);
//        return driver.element().getText(firstProductCardTextElement).toLowerCase();
//    }
//
//    //Using By & SHAFT Locators
//    @Step("Get the title of the first product Card2")
//    public String getFirstProductCardText2(String searchText) {
//        driver.browser().waitUntilUrlContains(searchText);
//        driver.element().hover(firstProductCard2).performElementAction().waitToBeReady(firstProductCardTextElement2);
//        return driver.element().getText(firstProductCardTextElement).toLowerCase();
//    }
//
//    //Using SHAFT Locators
//    @Step("Get the title of the product Card")
//    public String getProductCardTitle(String searchText) {
//        driver.browser().waitForLazyLoading().element().waitUntilPresenceOfAllElementsLocatedBy(productCardsLocator.build());
//        driver.element().hover(productCardsLocator.isLast().build());
//        return driver.element().getText(productCardTextLocator.isLast().build());
//    }
//
//    @Step("Compare product title to the search text")
//    public ProductsPage compareResultsToSearchText(String searchText) {
//        Validations.assertThat().object(getProductCardTitle(searchText))
//                .equalsIgnoringCaseSensitivity(searchText)
//                .withCustomReportMessage("Element doesn't contain The provided search text")
//                .perform();
//        return this;
//    }

}
