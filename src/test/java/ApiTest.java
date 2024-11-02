import apis.ProductApi;
import io.qameta.allure.testng.AllureTestNg;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Slf4j
@Listeners({AllureTestNg.class})
public class ApiTest {

    @Test(description = "TC001 - Get a list of all Products")
    public void getAllProductsList() {
        log.info("TC001 - Get a list of all Products Test & verify the Existence of specific Element");
        ProductApi productApi = new ProductApi();
//        productApi.getProductsList().assertProductsListNotEmpty();
        productApi.asserProductNameAtSpecificIndex(2, "Men Tshirt", productApi.getProductsResponse());

    }

    @Test(description = "TC002 - Search for the last product in the products list")
    public void getTheLastProductInTheProductsList() {
        log.info("TC002 - Search for the last product in the products list Test");
        new ProductApi().searchForLastProductFromTheProductsList();
    }

    @Test(description = "TC003 - Search for Random product in the products list")
    public void getRandomProductFrmTheProductsList() {
        log.info("TC003 - Search for Random product in the products list Test");
        ProductApi productApi = new ProductApi();
        productApi.searchForRandomProductFromTheProductsList();
    }

}
