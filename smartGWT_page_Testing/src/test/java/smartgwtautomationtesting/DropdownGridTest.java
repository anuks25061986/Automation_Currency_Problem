package smartgwtautomationtesting;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.List;

public class DropdownGridTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeTest
    public void dropdowngridSelectionTest() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(25));
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get("http://www.smartclient.com/smartgwt/showcase/#featured_dropdown_grid_category");
    }

    @Test
    public void selectDropdownGridCategoryTest() {
        WebElement item = wait.until(ExpectedConditions.elementToBeClickable(By.id("isc_2A")));
        item.click();


        Actions actions = new Actions(driver);
        for (int i = 0; i < 10; i++) {
            List<WebElement> items = driver.findElements(By.xpath("//tr[@role='option']"));
            boolean foundExercise = false;
            for (WebElement row : items) {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                if (cells.size() >= 3){
                    String itemName = cells.get(0).getText().trim();
                    String units = cells.get(1).getText().trim();
                    String unitCostText = cells.get(2).getText().trim();
                    if(!unitCostText.isEmpty()){
                        double unitCost = Double.parseDouble(unitCostText);
                        if (itemName.contains("Exercise") && units.equals("Ea") && unitCost>1.1){
                            System.out.println("Item: "+itemName+" Units: "+units+" Unit Cost: "+unitCost);
                            row.click();
                            foundExercise = true;
                            break;
                        }
                    }
                }
            }
          if (foundExercise){
              break;
          }
            actions.sendKeys(Keys.PAGE_DOWN).perform();
        }
    }
}
