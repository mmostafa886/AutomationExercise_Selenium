import io.qameta.allure.Step;
import io.qameta.allure.testng.AllureTestNg;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Slf4j
@Listeners({AllureTestNg.class})
public class TestClass {
    @Test(description = "Verify steps in report")
    public void verifyAllureSteps() {
        exampleStep();
        log.info("Info log");
        log.error("Error log");
        log.debug("Debug log");
        log.warn("Warn log");
    }

    @Step("Example step in Allure")
    public void exampleStep() {
        System.out.println("Example step executed");

    }
}