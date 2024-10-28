package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ElementActions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TablesPage extends BasePage {

    @FindBy(css = "table.table")
    private WebElement activeTable;

    @FindBy(css = "table.table th")
    private List<WebElement> tableHeaders;

    @FindBy(css = "table.table tbody tr")
    private List<WebElement> tableRows;

    @FindBy(xpath = "//button[text()='Table 1']")
    private WebElement table1Button;

    @FindBy(xpath = "//button[text()='Table 2']")
    private WebElement table2Button;

    private final ElementActions elementActions;

    public TablesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        this.elementActions = new ElementActions(driver);
    }

    public void selectTable(String tableName) {
        if (tableName.equals("Table 1")) {
            elementActions.click(table1Button);
        } else {
            elementActions.click(table2Button);
        }
        elementActions.waitForTableToLoad(activeTable);
    }

    public List<Map<String, String>> getTableData() {
        elementActions.scrollToBottom();
        List<Map<String, String>> tableData = new ArrayList<>();
        List<String> headers = getHeaders();

        for (WebElement row : tableRows) {
            Map<String, String> rowData = new HashMap<>();
            List<WebElement> cells = row.findElements(By.tagName("td"));
            for (int i = 0; i < cells.size(); i++) {
                rowData.put(headers.get(i), cells.get(i).getText());
            }
            tableData.add(rowData);
        }
        return tableData;
    }

    private List<String> getHeaders() {
        List<String> headers = new ArrayList<>();
        for (WebElement header : tableHeaders) {
            headers.add(header.getText().trim());
        }
        return headers;
    }
}