import apis.ProductApi;
import org.testng.annotations.Test;
import utilities.PropertiesLoader;

import static io.restassured.RestAssured.baseURI;

public class ApiTest {

    @Test(description = "Get a list of all Products")
    public void getAllProductsList() {
        ProductApi.getProductsList();
    }

}
