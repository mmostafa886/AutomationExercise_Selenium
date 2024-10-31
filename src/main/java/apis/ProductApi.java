package apis;

import static io.restassured.RestAssured.*;

import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.codehaus.groovy.reflection.ParameterTypes;
import org.testng.Assert;
import org.testng.asserts.Assertion;
import utilities.PropertiesLoader;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

public class ProductApi {

    private static final String productsList = "/productsList";
    private final String searchProducts = "/searchProduct";

    @Step("Getting a list of all products in the shop")
    public Response getProductsList() {
        baseURI = ApiBase.ApiBaseURL;
        Response response = get(productsList);
        response.then().statusCode(ApiBase.SUCCESS);
//        given().get(productsList).then().statusCode(ApiBase.SUCCESS);
        return response;
    }


    @Step("Get ist of products")
    public List<Object> getListOfProducts() {
        Response response = getProductsList();
        System.out.println("Response: " + response.getBody().asString());
        return response.getBody().jsonPath().get("products");
    }

    @Step("Get the name of he product at [{index}] location")
    public String getProductNameAtSpecificIndex(int index) {
        return getProductsList().jsonPath().get("products[" + (index - 1) + "].name");
    }

    @Step("Search for [{productName}] Product")
    public ProductApi searchForAPproduct(String productName) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("search_product", productName);
        given()
                .body(requestBody.toString())
                .contentType(ContentType.URLENC)
                .then()
                .statusCode(ApiBase.SUCCESS);
        return this;
    }

    @Step("Verify the ProductName at Location [{index}] equals to [{productName}]")
    public ProductApi asserProductNameAtSpecificIndex(int index, String productName) {
        String productItem = getProductsList().jsonPath().get("products[" + (index - 1) + "].name");
        Assert.assertEquals(productItem, productName);
        return this;
    }

    @Step("Search for last product from the Products List")
    public ProductApi searchForLastProductFromTheProductsList() {
        List<Object> productsList = getListOfProducts();
        System.out.println("Product List: " + productsList.toString());
        int productsListSize = productsList.size();
        System.out.println("Product List Size: " + productsListSize);
        String lastProductName = getProductNameAtSpecificIndex(productsListSize);
        System.out.println("Product Name: " + lastProductName);
        searchForAPproduct(lastProductName);
        asserProductNameAtSpecificIndex(0, lastProductName);
        return this;
    }
}
