package apis;

import static io.restassured.RestAssured.*;

import io.qameta.allure.Step;
import io.qameta.allure.testng.AllureTestNg;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

import org.testng.annotations.Listeners;

@Slf4j
@Listeners({AllureTestNg.class})
public class ProductApi {

    private static final String productsList = "/productsList";
    private final String searchProducts = "/searchProduct";

    public ProductApi() {
        baseURI = ApiBase.ApiBaseURL;
        //RestAssured.filters(new RequestLoggingFilter()., new ResponseLoggingFilter());
    }

    @Step("Getting API Response of requesting all products")
    public Response getProductsResponse() {
        log.info("Getting API Response of requesting all products");
        Response response = get(productsList);
        response.then().statusCode(ApiBase.SUCCESS);
//        given().get(productsList).then().statusCode(ApiBase.SUCCESS);
        return response;
    }


    @Step("Get List of products")
    public List<Object> getListOfProducts(Response response) {
        log.info("Get List of products");
//        System.out.println("Response: " + response.getBody().asString());
        return response.getBody().jsonPath().get("products");
    }

    @Step("Get Random Product Name")
    public String getRandomProductName() {
        log.info("Get Random Product Name");
        List<Object> productsList = getListOfProducts(getProductsResponse());
        Random random = new Random();
        int productsListSize = productsList.size();
        int randomIndex = random.nextInt(productsListSize + 1);
        return getProductNameAtSpecificIndex(randomIndex, getProductsResponse());
    }

    @Step("Get the name of he product at [{index}] location")
    public String getProductNameAtSpecificIndex(int index, Response response) {
        log.info("Get the name of the product at [{}] location", index);
        return response.jsonPath().get("products[" + (index - 1) + "].name");
    }

    @Step("Search for [{productName}] Product")
    public Response searchForAPproduct(String productName) {
        log.info("Search for [{}] Product", productName);
        Response postResponse = given()
                .contentType(ContentType.URLENC)
                .formParam("search_product", productName)
                .when()
                .post(searchProducts);
        postResponse.then().statusCode(ApiBase.SUCCESS);
        return postResponse;
    }

    @Step("Verify the ProductName at Location [{index}] equals to [{productName}]")
    public ProductApi asserProductNameAtSpecificIndex(int index, String productName, Response response) {
        log.info("Verify the ProductName at Location [{}] equals to [{}]", index, productName);
        String productItem = response.jsonPath().get("products[" + (index - 1) + "].name");
        Assert.assertEquals(productItem, productName);
        return this;
    }

    @Step("Search for last product from the Products List")
    public ProductApi searchForLastProductFromTheProductsList() {
        log.info("Search for last product from the Products List");
        List<Object> productsList = getListOfProducts(getProductsResponse());
        int productsListSize = productsList.size();
        String lastProductName = getProductNameAtSpecificIndex(productsListSize, getProductsResponse());
        asserProductNameAtSpecificIndex(0, lastProductName, getProductsResponse());
        return this;
    }

    @Step("Search for random product from the Products List")
    public ProductApi searchForRandomProductFromTheProductsList() {
        log.info("Search for random product from the Products List");
        String randomProductName = getRandomProductName();
        Response response = searchForAPproduct(randomProductName);
        List<Object> ProductsList = getListOfProducts(response);
        System.out.println(ProductsList);
        // String productName = getProductNameAtSpecificIndex(0, response);
        asserProductNameAtSpecificIndex(0, randomProductName, response);
        return this;
    }
}
