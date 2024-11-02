package apis;

import static io.restassured.RestAssured.*;

import io.qameta.allure.Step;
import io.qameta.allure.testng.AllureTestNg;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

import org.json.JSONObject;
import org.testng.annotations.Listeners;

@Listeners({AllureTestNg.class})
public class ProductApi {

    private static final String productsList = "/productsList";
    private final String searchProducts = "/searchProduct";

    @Step("Getting API Response of requesting all products")
    public Response getProductsList() {
        baseURI = ApiBase.ApiBaseURL;
        Response response = get(productsList);
        response.then().statusCode(ApiBase.SUCCESS);
//        given().get(productsList).then().statusCode(ApiBase.SUCCESS);
        return response;
    }


@Step("Get List of products")
    public List<Object> getListOfProducts() {
        Response response = getProductsList();
//        System.out.println("Response: " + response.getBody().asString());
        return response.getBody().jsonPath().get("products");
    }

    @Step("Get Random Product Name")
    public String getRandomProductName() {
        List<Object> productsList = getListOfProducts();
        Random random = new Random();
        int productsListSize = productsList.size();
        int randomIndex = random.nextInt(productsListSize+1);
        return getProductNameAtSpecificIndex(randomIndex);
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
        int productsListSize = productsList.size();
        String lastProductName = getProductNameAtSpecificIndex(productsListSize);
        searchForAPproduct(lastProductName);
        asserProductNameAtSpecificIndex(0, lastProductName);
        return this;
    }

    @Step("Search for last product from the Products List")
    public ProductApi searchForRandomProductFromTheProductsList() {
        searchForAPproduct(getRandomProductName());
        asserProductNameAtSpecificIndex(0, getRandomProductName());
        return this;
    }
}
