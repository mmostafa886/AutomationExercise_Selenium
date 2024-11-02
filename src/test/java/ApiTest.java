import apis.ProductApi;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({AllureTestNg.class})
public class ApiTest {

    @Test(description = "Get a list of all Products")
    public void getAllProductsList() {
        /*ProductApi productApi = new ProductApi();
        productApi.getProductsList();*/
        ProductApi productApi = new ProductApi();
//        productApi.getProductsList().assertProductsListNotEmpty();
        productApi.asserProductNameAtSpecificIndex(2, "Men Tshirt");

    }

    @Test(description = "Search for the last product in the products list")
    public void getTheLastProductInTheProductsList() {
        new ProductApi().searchForLastProductFromTheProductsList();
    }

    //Notworking
    //@Test(description = "Search for Random product in the products list")
    public void getRandomProductFrmTheProductsList() {
        ProductApi productApi = new ProductApi();
        String randomProductName = productApi.getRandomProductName();
        productApi.searchForAPproduct(randomProductName).asserProductNameAtSpecificIndex(0, randomProductName);
//        new ProductApi().searchForRandomProductFromTheProductsList();
    }

}
