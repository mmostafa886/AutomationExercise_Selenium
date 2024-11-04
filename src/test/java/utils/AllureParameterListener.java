package utils;

import io.qameta.allure.Allure;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class AllureParameterListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        String browser = TestSetUp.getExecutionBrowser();
        Allure.parameter("Browser: ", browser);
        Allure.label("tag" , browser);
        //Allure.description("Browser: " + browser);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // No-op
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // No-op
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // No-op
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // No-op
    }

    @Override
    public void onStart(ITestContext context) {
        // No-op
    }

    @Override
    public void onFinish(ITestContext context) {
        // No-op
    }

}

