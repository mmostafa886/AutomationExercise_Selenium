package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MenuBar {
    private WebDriver driver;

    private By categories = By.className("card_travel");
    private By signupAndLogin = By.className("fa-lock");
    protected By logout = By.xpath("//a[@href='/logout']");
    protected By deleteAccount = By.className("fa-trash-o");

    public MenuBar(WebDriver driver) {this.driver = driver; }

//    @Step("Navigate to Products page")
//    public ProductsPage goToProductsPage() {
//        driver.element().click(categories);
//        return new ProductsPage(driver);
//    }

}
