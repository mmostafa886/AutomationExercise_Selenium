# Automation Exercise (using Selenium)
### This is the same project as (https://github.com/mmostafa886/AutomationExercise) but this one is using the native Selenium
## Project Contents
- A set of Web Tests under `src/test/java/WebTest.java` which contain only Web actions.
- A set of API Tests under `src/test/java/ApiTest.java` which contain only API actions/calls.
- A set of Mixed Tests under `src/test/java/MixedTest.java` which contain a mix of Web & API actions & calls.
## Test Execution
### Normal Execution
1. Execute All Tests: mvn clean test
2. Run Specific Test class: mvn clean test -Dtest=ApiTest
3. Run Specific Test inside a class: mvn clean test -Dtest=MixedTest#registerNewUserAPIAndLoginTest.
4. For Web tests, it will be executed using the `Browser` configured in properties file `src/main/resources/properties/config.properties`.
5. In case the user wants to execute on different browser, the command `mvn clean test -Dtest=MultiThreadedTest -DBrowser=safari` can be used to execute the target script on Safari for example.
6. It is also allowed to pass the browser as a parameter in the testng.xml inside the test Ex. `<parameter name="Browser" value="Chrome"/>` which requires tuning to the pom.xml in the surefire plugin section as in the below.
```dtd
 <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>${surefire.version}</version>
                <configuration>
                    <testFailureIgnore>true</testFailureIgnore>
                    <argLine>
                        -javaagent:"${settings.localRepository}/org/aspectj/aspectjweaver/${aspectj.version}/aspectjweaver-${aspectj.version}.jar"
                    </argLine>
                    <!--Use this section whenever we need specific configuration for the test execution-->
                    <suiteXmlFiles>
                        <suiteXmlFile>testng.xml</suiteXmlFile>
                    </suiteXmlFiles>
                </configuration>
            </plugin>
```
7. Combining the three options, here is how we can deal with the browser
   1. In case the browser is provided through the maven command `(mvn clean test -Dtest=MultiThreadedTest -DBrowser=safari)`, the test will be executed on the provided browser.
   2. If the browser is not provided through the maven command, we check for the browser provided in the `testng.xml`.
   3. If neither of the 2 previous points was provided, then we execute the test based on the `Browser` configured in properties file `src/main/resources/properties/config.properties`.
### Headless Execution
1. The script support both `Headless & Headed` execution modes through the `headless` property in the `config.properties` file which takes `true or false` boolean values.
2. The script was tuned to allow default value of `true (i.e: Headless)` in case of not providing a value or providing some trash value like `uwvfuyvuw` in the `config.properties` file.
3. By modifying the `AllureParameterListener>>onTestStart`, the `"Execution Mode" (Headless/Headed)` label is logged as a parameter to the Allure Report .
### Parallel Execution
#### Hints on ThreadLocal
1. Remember to use private static ThreadLocal<WebDriver> driver = new ThreadLocal<>(); to properly manage your driver in your test classes as following the usual driver initialization methods will result in problems in managing drivers' sessions & immature tests ending.
2. The ThreadLocal must be defined at the Class level to avoid repeated initialization.
3. We used ThreadLocal for the `WebDriver` which forces using ThreadLocal for corresponding entities `WebDriverWait & FluentWait`.
#### Testng.xml needed modifications
- The `Testng.xml` needs to be modified on the `<suite>` level as follows: `<suite name="All Test Suite" parallel="methods" thread-count="2">`
  1. `name`: The suite name which will be used to organize the execution & displayed in report (Ex. This will be the suite name in the generated allure report).
  2. `parallel`: This what we use to enable parallel execution & on what level, it can take the values `tests, classes, methods, instances(=both methods & classes)`.
  3. `thread-count`: the number of Threads/Instances that can be opened for the parallel execution.
- There is no a step-by-step guide for enabling the Parallel execution, but it depends on how the tests are organized so the configuration we used in the `Testng.xml` is cohesively related to the example setup we use for `src/test/java/MultiThreadedTest.java`.
### Execution based on Tags
- The TestNG groups Ex. `[@Test(groups = { "Smoke", "Regression" })]` are used to define tags at which we can use to control the execution.
- XML files were added `[Regression.xml & Smoke.xml]` in order to be used later in the execution command in order to only execute specific group of tests at which we need to add the snippets below to execute only the tests with the groups' tag contain this group (in the case below: Regression).
  ```
          <groups>
            <run>
                <include name="Regression"/>
            </run>
        </groups>
  ````
- The used execution command in this case will be `[mvn clean test -Dsurefire.suiteXmlFiles=Regression.xml]`.
### Sample Execution Comparison
- Executing the `MultiThreadedTest` for `Chrome, FirFox & Edge` is the sample test used in Testng.xml `(<class name="MultiThreadedTest"/>)`.
- Serial execution (not parallel) took around ~ 16 seconds.
- Parallel Execution took around ~ 10 seconds only with the config of `(<suite name="All Test Suite" parallel="methods" thread-count="2">)`.

## Allure Report
1. Create the `generate_allure_report.sh` file at the project root with the contents below
```dtd
#!/bin/bash

# Kill any running Allure instances
pkill -f allure
echo "Killing Allure server instances in the background."

# Generate the allure report from the allure-results folder into the allure-report folder
allure generate --clean allure-results -o allure-report

# Start Allure in the background, redirect output to nohup.out, and disown the process
nohup allure open allure-report -h localhost > /dev/null 2>&1 & disown
echo "Allure server started in the background."

exit
```
2. Convert the file to executable by executing the command `chmod +x generate_allure_report.sh` in the terminal.
### Open the report after execution
1. Run any of the tests as described earlier in this README
2. Run the `generate_allure_report.sh` file by executing the command `./generate_allure_report.sh` in the terminal.
###   Open the "Allure Report" directly after execution
The user can open the generated report directly after the execution by combining both the commands `mvn clean test  ; ./generate_allure_report.sh
`.
### Attach image in case of failure to the report
1. First we need to take the screenshot through the method `TestSetup.captureScreenShot()` in the `@AfterMethod` so that we can check the result then take the screenshot in case of failure.
2. We can also attach it the allure report `TestSetup.attachScreenshotToAllure()`
### General Points.
1. The user can log any of the methods used during testing as a step in the generated Allure report by using the tag "@Step" on the method level Ex. `@Step("Getting API Response of requesting all products")`.

## Logging during execution
1. Slf4j was used in order to log the steps to the terminal during execution.
2. The "@Slf4j" tag should be used at the class level.
3. To log whatever needed, the user need use the logger inside a method Ex. `log.info("Getting API Response of requesting all products");`.
4. To control the properties of logging, a properties file `src/main/resources/properties/simplelogger.properties` is used & we need to configure the `build` section of the `pom.xml` to include the properties file by adding this section
````dtd
        <resources>
            <resource>
                <directory>src/main/resources/properties</directory>
            </resource>
        </resources>
````

## Check for the POM.xml dependencies 
- In order to automatically check for maven dependencies available updates, the file `.github/dependabot.yml` was created to allow the dependabot to check for dependency updates so that we can keep all the pom.xml dependencies updated.
- More info about the dependabot can be found under `https://github.com/dependabot`

## Design Patterns
- The script incorporates several design patterns effectively:
    ```
    **Page Object Model (POM):**
    Having a separate page implementation, which aligns with the principles of the Page Object Model. This design pattern helps in organizing and maintaining a clear separation between the test scripts and the underlying page implementation.
    
    **Singleton Pattern & Factory Pattern in DriverFactory Class:**
    The DriverFactory class modified to follow the Singleton pattern, ensuring that only one instance of the WebDriver is created and shared across the entire application & reset the "Driver Instance" after each test class. This is a good use of the Singleton pattern.
    Additionally, the createDriver method in the DriverFactory class serves as a Factory Method, encapsulating the creation logic for different types of WebDriver instances. This aligns with the Factory Method pattern.
    
    **ThreadLocal Pattern in BaseTest Class:**
    The use of ThreadLocal<WebDriver> in the BaseTest class is a good application of the ThreadLocal pattern. It ensures that each thread (test method) gets its own independent instance of the WebDriver, preventing interference between parallel test executions.
  
    In summary, the script seems to follow a good design pattern structure, incorporating:
  - POM for page organization.
  - Singleton and Factory patterns for WebDriver management in DriverFactory.
  - ThreadLocal for thread safety in the BaseTest class.
  These design patterns contribute to maintainability, scalability, and clarity in your test automation framework.
    ```
  
## General Comments
### PropertiesLoader
- A Java class `(src/main/java/utils/PropertiesLoader.java)` was added in order to enable reading whatever the properties found under the directory `(src/main/resources/properties)`.
- A main method was added inside that class in order to enable verifying the readability of any specific property (Testing purposes).
### JsonDataReader
- A Java class `(src/main/java/utils/JsonDataReader.java)` was added in order to handle the JSON data files.
- It can be used to pass the Source file to the responsible method then process data from this data file `(Ex. WebTest.registerNewUserAndLoginTest)`.
### Bringing an element to display
- when it is needed to bring an element into display, we can use either `Selenium Actions` or `JavaScript scrollIntoView`.
- Browsers like Firefox may not act as expected with `Actions` that's why it is better to use `Javascript`.