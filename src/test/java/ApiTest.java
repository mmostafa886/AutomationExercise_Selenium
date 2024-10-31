import apis.ProductApi;
import org.testng.annotations.Test;

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
        System.out.println();
    }

}
