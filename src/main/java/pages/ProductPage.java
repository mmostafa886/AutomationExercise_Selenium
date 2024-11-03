package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage {
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

    public ProductPage(WebDriver driver) {
        this.driver = driver ;
        this.menuBar = new MenuBar(driver);
    }
}
