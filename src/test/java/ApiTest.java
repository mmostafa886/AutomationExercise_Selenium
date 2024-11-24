import apis.ProductApi;
import apis.UserApi;
import io.qameta.allure.testng.AllureTestNg;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Slf4j
@Listeners({AllureTestNg.class})
public class ApiTest {

    @Test(groups = { "Smoke" },description = "TC-API-001 - Get a list of all Products")
    public void getAllProductsList() {
        log.info("TC-API-001 - Get a list of all Products");
        ProductApi productApi = new ProductApi();
        productApi.asserProductNameAtSpecificIndex(2, "Men Tshirt", productApi.getProductsResponse());

    }

    @Test(groups = { "Regression" },description = "TC-API-002 - Search for the last product in the products list")
    public void getTheLastProductInTheProductsList() {
        log.info("TC-API-002 - Search for the last product in the products list");
        new ProductApi().searchForLastProductFromTheProductsList();
    }

    @Test(groups = { "Smoke", "Regression" },
            description = "TC-API-003 - Search for Random product in the products list")
    public void getRandomProductFrmTheProductsList() {
        log.info("TC-API-003 - Search for Random product in the products list");
        ProductApi productApi = new ProductApi();
        productApi.searchForRandomProductFromTheProductsList();
    }

    @Test(groups = { "Smoke", "Regression" },
            description = "TC-API-004 - Create New user account through API then delete it through API Using Request Parameters")
    public void registerAndDeleteNewUserAPITest() {
        log.info("TC-API-004 - Create New user account through API then delete it through API Using Request Parameters");
        UserApi userApi = new UserApi();
        log.info("Get DataFile");
        userApi.createAccountUsingDataFile("src/test/resources/testDataFiles/AccountData.json");
        userApi.deleteAccount(userApi.getRandomName() + "@email.com", userApi.getRandomName());
    }

}
