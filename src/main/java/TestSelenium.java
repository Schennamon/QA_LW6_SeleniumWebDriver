import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class TestSelenium {
    static WebDriver WEBDRIVER;
    List<WebElement> prices;
    List<Integer> sortedPrices;

    @BeforeClass
    public void setWebDriver(){
        System.setProperty("webdriver.gecko.driver", "/home/schennamon/Документы/QA/Лаб5/geckodriver/geckodriver");
        WEBDRIVER = new FirefoxDriver();
        WEBDRIVER.get("https://pn.com.ua/");
    }

    @Test
    public void testCheckPrice(){
        sortedPrices = new ArrayList<Integer>();
        WEBDRIVER.findElement(By.xpath(".//*[@id-'column-center']/section/div[6]/a")).click();
        WEBDRIVER.findElement(By.xpath(".//li[position()-1]/a[@href-'/ct/4201/']")).click();
        WEBDRIVER.findElement(By.xpath(".//a[@class-'toggler']")).click();
        WEBDRIVER.findElement(By.xpath(".//a[href='/ct/4201/?sort=price']")).click();
        prices = WEBDRIVER.findElements(By.xpath(".//strong[contains(text(),' грн')]"));
        for(int i = 0; i < prices.size(); i++){
            prices.get(i).getText().replace("грн", "");
            System.out.println(prices.get(i).getText().replace(" грн", ""));
            sortedPrices.add(Integer.parseInt(prices.get(i).getText().replace(" грн", "")));
        }
        boolean condition = true;
        for(int i = 0; i < prices.size()-1; i++){
            System.out.println(sortedPrices.get(i));
            if (sortedPrices.get(i) > sortedPrices.get(i + 1)){
                condition = false;
                break;
            }
        }
        Assert.assertEquals(condition, true);
    }

    @AfterClass
    public void closeWebDriver(){
        WEBDRIVER.close();
    }
}
