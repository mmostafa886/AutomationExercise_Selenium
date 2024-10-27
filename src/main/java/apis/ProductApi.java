package apis;

import static io.restassured.RestAssured.*;

import io.qameta.allure.Step;
import utilities.PropertiesLoader;

public class ProductApi {

    private static final String productsList = "/productsList";
    private final String searchProducts = "/searchProduct";
//    private static final PropertiesLoader propertiesLoader = new PropertiesLoader();

//    public ProductApi() {
//        new PropertiesLoader();
//        baseURI = System.getProperty("baseUrl") + "/api";
//    }

    @Step("Getting a list of all products in the shop")
    public static void getProductsList() {
        baseURI = ApiBase.ApiBaseURL;
        given().get(productsList).then().statusCode(ApiBase.SUCCESS);
    }
}
