package apis;

import io.qameta.allure.Step;
import io.qameta.allure.testng.AllureTestNg;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import utils.JsonDataReader;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

@Slf4j
@Listeners({AllureTestNg.class})
public class UserApi {

    private final String createAccount = "/createAccount";
    private final String deleteAccount = "/deleteAccount";
    private final String updateAccount = "/updateAccount";
    private final String accountDetails = "/getUserDetailByEmail";

    @Getter
    private String randomName = RandomStringUtils.randomAlphanumeric(10).toLowerCase();

    public UserApi() {
        baseURI = ApiBase.ApiBaseURL;
    }

//    @Step("Getting API Response of the UserApi")
//    public Response getProductsResponse() {
//        log.info("Getting API Response of requesting all products");
//        Response response = get(productsList);
//        response.then().statusCode(ApiBase.SUCCESS);
////        given().get(productsList).then().statusCode(ApiBase.SUCCESS);
//        return response;
//    }

    @Step("Create New user Account through API")
    public void createAccount(String name, String emailAddress, String password, String firstName, String lastName, String address
            , String country, String state, String city, String zipCode, String mobileNumber) {
        log.info("Create New user Account through API");
        // Create the form parameters
        Map<String, String> formParams = new HashMap<>();
        formParams.put("name", name);
        formParams.put("email", emailAddress);
        formParams.put("password", password);
        formParams.put("title", "Mr.");
        formParams.put("birth_date", "1");
        formParams.put("birth_month", "7");
        formParams.put("birth_year", "1980");
        formParams.put("firstname", firstName);
        formParams.put("lastname", lastName);
        formParams.put("company", "company");
        formParams.put("address1", address);
        formParams.put("address2", "");
        formParams.put("country", country);
        formParams.put("zipcode", zipCode);
        formParams.put("state", state);
        formParams.put("city", city);
        formParams.put("mobile_number", mobileNumber); // Make the POST request
        Response response = RestAssured.given()
                .contentType(ContentType.URLENC)
                .formParams(formParams)
                .post(createAccount);
        response.then().statusCode(ApiBase.SUCCESS);
        String responseMessage = response.jsonPath().getString("message");
        Assert.assertNotNull(responseMessage);
    }

    @Step("Create New user Account through API using JSON data file")
    public UserApi createAccountUsingDataFile(String jsonFilePath) {
        log.info("Create New user Account through API using JSON data file");
        JsonDataReader testData = new JsonDataReader(jsonFilePath);
        createAccount(randomName, randomName + "@email.com", randomName
                , testData.getTestData("FirstName"), testData.getTestData("LastName")
                , testData.getTestData("Address"), testData.getTestData("Country")
                , testData.getTestData("State"), testData.getTestData("City")
                , testData.getTestData("Zipcode"), testData.getTestData("Mobile"));
        return this;
    }

    @Step("Delete user account through API")
    public UserApi deleteAccount(String email, String password) {
        log.info("Delete user account through API");
        Map<String, String> formParams = new HashMap<>();
        formParams.put("email", email);
        formParams.put("password", password);
        RestAssured.given()
                .contentType(ContentType.URLENC)
                .formParams(formParams)
                .delete(deleteAccount)
                .then()
                .statusCode(200);
        return this;
    }



}
