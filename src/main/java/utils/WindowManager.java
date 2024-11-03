package utils;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WindowManager {
    private WebDriver driver;
    private WebDriver.Navigation navigate;

    public WindowManager(WebDriver driver) {
        this.driver = driver;
        navigate = driver.navigate();
    }

    @Step
    public void goBack(){
        navigate.back();
    }

    @Step
    public void goForward(){
        navigate.forward();
    }

    @Step
    public void refreshPage(){
        navigate.refresh();
    }

    @Step
    public void goTo(String url){
        navigate.to(url);
    }

    @Step
    public void switchToTab(String tabName){
      var windows =  driver.getWindowHandles();

      System.out.println("Number Of Tabs: "+windows.size());
      System.out.println("Window Handles: ");
      windows.forEach(System.out::println);

      for(String window : windows){
          System.out.println("Switching to Tab: "+window);
          driver.switchTo().window(window);

          System.out.println("Current Window Title: "+driver.getTitle());

          if(tabName.equals(driver.getTitle())){
              break;
          }
      }
    }

    /**
     * Switch between the tabs till verifying the existence of specific element
     * @param byElement is the element to verify for its existence
     */
    @Step
    public void switchToSpecificTab(By byElement){
        var windows =  driver.getWindowHandles();
        for(String window : windows){
            driver.switchTo().window(window);
            if(driver.findElement(byElement).isDisplayed()){
                break;
            }
        }
    }

    @Step
    public void switchToNewTab(){
        var windows = driver.getWindowHandles();
        windows.forEach(driver.switchTo()::window);
    }
}
