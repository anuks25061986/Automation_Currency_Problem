package smartgwtautomationtesting;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class BasePage {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeTest
    public void featuredTileFiltering() {

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://smartclient.com/smartgwt/showcase/#featured_tile_filtering");
    }

    @Test
    public void verifyingAnimalFiltering() {
        WebElement animal = driver.findElement(By.xpath("//input[@id='isc_2Q']"));
        animal.clear();
        animal.sendKeys("a");
        WebElement soryBy = driver.findElement(By.id("isc_3E"));
        soryBy.click();
        WebElement lifeSpan = driver.findElement(By.xpath("//div[text()='Life Span']"));
        lifeSpan.click();
        WebElement sliderThumb = driver.findElement(By.id("isc_2E"));
        Actions actions = new Actions(driver);
        actions.click(sliderThumb).sendKeys(Keys.HOME).perform();
        for (int i = 1; i < 14; i++) {
            actions.sendKeys(Keys.ARROW_RIGHT).perform();
        }
        WebElement ascending = driver.findElement(By.id("isc_3K"));
        ascending.click();
        List<WebElement> results=driver.findElements(By.className("simpleTile"));
        System.out.println("Total DOM elements: "+results.size());
        Assert.assertTrue(results.size()>12,"Expected more than 12 results, and found "+results);
    }
}