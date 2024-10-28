# Selenium Test Automation Framework

## Introduction
This test automation framework is built using Selenium WebDriver, Cucumber, and TestNG. It provides a robust structure for creating and running automated tests for web applications.

## Key Features
- Utilization of Page Object Model (POM) design pattern
- Implementation of Page Factory pattern for page element initialization
- Support for multiple browsers (Chrome, Firefox, Edge)
- Management of browser configuration and screen resolution
- Multi-threaded test execution
- Reporting using Allure
- Video recording of test execution
- Screenshot capture on test failure
- Retry mechanism for failed scenarios
- Logging using Log4j

## Project Structure

### Packages
- `pages`: Contains Page Object classes for each application page
- `runners`: Contains classes responsible for test execution
- `steps`: Contains Cucumber step definitions
- `utils`: Contains utility and helper classes

### Key Classes

#### ThreadLocalWebDriver
Manages WebDriver instances for each thread, enabling safe multi-threaded test execution.

#### BrowserActions
Contains methods for performing browser-level actions such as navigation, alert handling, and cookie management.

#### ElementActions
Provides methods for interacting with page elements, such as clicking, entering text, and checking visibility.

#### WaitHelper
Implements various waiting strategies for elements and page states.

#### TestRunner
Configures and runs Cucumber tests using TestNG.

#### Hooks
Contains methods executed before and after each scenario, responsible for WebDriver initialization, state cleanup, and reporting handling.

### Page Factory Pattern
The framework utilizes the Page Factory pattern to initialize elements on pages. In Page Object classes, elements are declared with the @FindBy annotation, and initialization occurs in the constructor using PageFactory.initElements().

## Configuration and Execution

### Requirements
- Java JDK 17 or newer
- Maven

### Configuration
1. Clone the repository
2. Install Maven dependencies: `mvn clean install`

> NOTE! If you have a problem with downloading the Monte Screen Recorder plugin, go to step no. 3.
3. Install the recording plugin: `mvn initialize`

### Running Tests
- Run all tests: `mvn test`
- Run a specific tag: `mvn test -D"cucumber.filter.tags=@tableComparisonTests"`
- Set browser: `-D"browser=chrome"` (available options: chrome, firefox, edge)
- Set resolution: `-D"resolution=TABLET"` (`DESKTOP` is a default)

### Parallel Test Execution

The framework supports parallel test execution, which can significantly reduce the overall execution time of the test suite.

- You can control the number of parallel threads using the `-D"threadCount"` parameter:
  `mvn test -D"threadCount=4"`

### Rerun Failed Tests

The framework includes a mechanism to rerun failed tests. You can control the number of reruns using the `maxRetryCount` parameter.

- To set the number of reruns, use the `-DmaxRetryCount` parameter:
  `mvn test -D"maxRetryCount=3"`

Examples:
- Run all tests in 4 threads with 2 reruns for failed tests:
  `mvn test -D"threadCount=4" -D"maxRetryCount=2"`

### Run tests with all arguments

This is an example how to run tests using all arguments:
`mvn test -D"cucumber.filter.tags=@tableComparisonTests" -D"browser=edge" -D"threadCount=4" -D"resolution=TABLET" -D"maxRetryCount=3"`

## Reporting
  `to generate report, execute the command: mvn allure:report` or `mvn allure:serve` 

- Allure reports are generated in the `target/site/allure-maven-plugin/` directory
- Test video recordings are saved in the `recordings` directory
- Screenshots for failed tests are saved in the `screenshots` directory