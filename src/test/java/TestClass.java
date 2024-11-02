import io.qameta.allure.Step;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({AllureTestNg.class})
public class TestClass {
    @Test(description = "Verify steps in report")
    public void verifyAllureSteps() {
        exampleStep();
    }

    @Step("Example step in Allure")
    public void exampleStep() {
        System.out.println("Example step executed");

    }
}