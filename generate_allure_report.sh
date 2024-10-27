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