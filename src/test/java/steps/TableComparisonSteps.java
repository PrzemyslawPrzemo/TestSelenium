package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import pages.TablesPage;
import utils.BrowserActions;
import utils.ThreadLocalWebDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableComparisonSteps {
    private static final Logger logger = LogManager.getLogger(TableComparisonSteps.class);
    private final WebDriver driver;
    private final TablesPage tablesPage;
    private final BrowserActions browserActions;
    private Map<String, List<Map<String, String>>> tablesData;

    public TableComparisonSteps() {
        this.driver = ThreadLocalWebDriver.getDriver();
        tablesPage = new TablesPage(driver);
        browserActions = new BrowserActions(driver);
        this.tablesData = new HashMap<>();
    }

    @Given("I am on the tables comparison page")
    public void iAmOnTheTablesComparisonPage() {
        browserActions.navigateToUrl("https://ui-automation-app.web.app/");
        logger.info("Main table comparison page loaded successfully");
    }

    @When("I collect data from {string}")
    public void iCollectDataFromTable(String tableName) {
        logger.info("Collecting data from {}", tableName);
        tablesPage.selectTable(tableName);
        List<Map<String, String>> tableData = tablesPage.getTableData();
        tablesData.put(tableName, tableData);
        logger.info("Collected {} rows from {}", tableData.size(), tableName);
    }

    @Then("I should see differences between tables")
    public void iShouldSeeDifferencesBetweenTables() {
        List<Map<String, String>> table1Data = tablesData.get("Table 1");
        List<Map<String, String>> table2Data = tablesData.get("Table 2");

        List<String> differences = findDifferences(table1Data, table2Data);

        if (differences.isEmpty()) {
            logger.info("\nNo differences found between tables");
        } else {
            logger.info("\nFound differences:");
            differences.forEach(diff -> logger.info(diff));
        }
    }

    private List<String> findDifferences(List<Map<String, String>> table1Data,
                                         List<Map<String, String>> table2Data) {
        List<String> differences = new ArrayList<>();

        for (int i = 0; i < Math.min(table1Data.size(), table2Data.size()); i++) {
            Map<String, String> row1 = table1Data.get(i);
            Map<String, String> row2 = table2Data.get(i);

            if (!row1.equals(row2)) {
                differences.add(String.format("\nDifferences in row %d:", i + 1));
                differences.add("Table 1: " + row1);
                differences.add("Table 2: " + row2);

                row1.forEach((key, value1) -> {
                    String value2 = row2.get(key);
                    if (!value1.equals(value2)) {
                        differences.add(String.format("Column '%s': Table1='%s', Table2='%s'",
                                key, value1, value2));
                    }
                });
            }
        }
        return differences;
    }
}