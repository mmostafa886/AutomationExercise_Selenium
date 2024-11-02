# Automation Exercise (using Selenium)
### This is the same project as (https://github.com/mmostafa886/AutomationExercise) but this one is using the native Selenium
## Test Execution
### Normal Execution
1. Execute All Tests: mvn clean test
2. Run Specific Test class: mvn clean test -Dtest=ApiTest
3. Run Specific Test inside a class: mvn clean test -Dtest=MixedTest#registerNewUserAPIAndLoginTest.
### Headless Execution
### Parallel Execution
1. Hint:
   Remember to use private static ThreadLocal<WebDriver> driver = new ThreadLocal<>(); to properly manage your driver in your test classes as following the usual driver initialization methods will result in problems in managing drivers' sessions & immature tests ending.

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