import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task1 {
    static WebDriver WEBDRIVER;
    List<WebElement> names;
    List<WebElement> pages;
    Pattern  namePattern = Pattern.compile("Canon");
    boolean nameCheck = true;

    public void nameChecking(int g){
        for(int j = g; j < names.size(); j++){
            Matcher nameMatcher = namePattern.matcher(names.get(j).getText());
            if(nameMatcher.find()) {
                System.out.print(names.get(j).getText() + " - ");
                System.out.println("true");
            }else{
                System.out.print(names.get(j).getText() + " - ");
                System.out.println("false");
                nameCheck = false;
            }
        }
    }

    @BeforeClass
    public void setWebDriver(){
        System.setProperty("webdriver.gecko.driver", "/home/schennamon/Документы/QA/Лаб5/geckodriver/geckodriver");
        WEBDRIVER = new FirefoxDriver();
    }

    @Test
    public void testCheckNames() throws InterruptedException {
        int size = 0;
        WEBDRIVER.get("https://pn.com.ua/ct/1221/");
        Thread.sleep(1000);
        WEBDRIVER.findElement(By.xpath(".//a[@href='/ct/1221/?fo=187']")).click();
        Thread.sleep(1000);
        pages = WEBDRIVER.findElements(By.xpath(".//div[1]/div[@class='pagination']/ul[1]/li[@class='page']/a"));
        names = WEBDRIVER.findElements(By.xpath(".//article[@class='item td-table']/div[2]/div/div/a"));
        nameChecking(size);
        size = names.size();
        for(int i = 0; i < pages.size(); i++) {
            WEBDRIVER.findElement(By.xpath(".//div[1]/div[@class='pagination']/ul[1]/li[@class='page-next']/a")).click();
            Thread.sleep(1000);
            names.addAll(WEBDRIVER.findElements(By.xpath(".//article[@class='item td-table']/div[2]/div/div/a")));
            nameChecking(size);
            size = names.size();
        }
        Assert.assertTrue(nameCheck);
    }

    @Test
    public void testCheckCount() throws InterruptedException {
        WEBDRIVER.get("https://pn.com.ua/ct/1221/");
        WebElement count = WEBDRIVER.findElement(By.xpath(".//div[1]/div/div[2]/div/div/div[2]/div[1]/div[2]/div/div[4]/div[2]/span[1]/a/small"));
        int num = Integer.parseInt(count.getText());
        WEBDRIVER.findElement(By.xpath(".//a[@href='/ct/1221/?fo=187']")).click();
        Thread.sleep(1000);
        pages = WEBDRIVER.findElements(By.xpath(".//div[1]/div[@class='pagination']/ul[1]/li[@class='page']/a"));
        names = WEBDRIVER.findElements(By.xpath(".//article[@class='item td-table']/div[2]/div/div/a"));
        for(int i = 0; i < pages.size(); i++){
            WEBDRIVER.findElement(By.xpath(".//div[1]/div[@class='pagination']/ul[1]/li[@class='page-next']/a")).click();
            Thread.sleep(1000);
            names.addAll(WEBDRIVER.findElements(By.xpath(".//article[@class='item td-table']/div[2]/div/div/a")));
        }
        Assert.assertEquals(names.size(), num);
    }

    @Test
    public void testCheckCompare() throws InterruptedException {
        List<WebElement> items;
        boolean checkURL = true;
        WEBDRIVER.get("https://pn.com.ua/ct/1221/");
        WEBDRIVER.findElement(By.xpath(".//div[1]/div/div[2]/div/div/div[2]/div[2]/section/div[3]/article[1]/div[2]/div[3]/span[2]/a")).click();
        WEBDRIVER.findElement(By.xpath(".//div[1]/div/div[2]/div/div/div[2]/div[2]/section/div[3]/article[2]/div[2]/div[3]/span[2]/a")).click();
        WEBDRIVER.findElement(By.xpath(".//div[1]/div/div[2]/div/div/div[2]/div[2]/section/div[1]/div[1]/a")).click();
        Thread.sleep(1000);
        items = WEBDRIVER.findElements(By.xpath(".//tr[@class='goods-line']/th"));
        Pattern  namePatternCompare = Pattern.compile("compare");
        Matcher nameMatcher = namePatternCompare.matcher(WEBDRIVER.getCurrentUrl());
        if(nameMatcher.find()) {
            System.out.println("URL - true");
        }else{
            System.out.println("URL - false");
            checkURL = false;
        }
        Assert.assertTrue(checkURL);
        Assert.assertEquals(items.size()-1, 2);
    }

    @Test
    public void testAlert() throws InterruptedException {
        WEBDRIVER.get("https://pn.com.ua/ct/1221/");
        WEBDRIVER.findElement(By.xpath(".//div[1]/div/div[2]/div/div/div[2]/div[2]/section/div[3]/article[1]/div[2]/div[3]/span[2]/a")).click();
        WEBDRIVER.findElement(By.xpath(".//div[1]/div/div[2]/div/div/div[2]/div[2]/section/div[3]/article[2]/div[2]/div[3]/span[2]/a")).click();
        WEBDRIVER.findElement(By.xpath(".//div[1]/div/div[2]/div/div/div[2]/div[2]/section/div[3]/article[3]/div[2]/div[3]/span[2]/a")).click();
        WEBDRIVER.findElement(By.xpath(".//div[1]/div/div[2]/div/div/div[2]/div[2]/section/div[3]/article[4]/div[2]/div[3]/span[2]/a")).click();
        WEBDRIVER.findElement(By.xpath(".//div[1]/div/div[2]/div/div/div[2]/div[2]/section/div[1]/div[1]/a")).click();
        WEBDRIVER.findElement(By.xpath(".//div[1]/div/section/div[2]/div/table/thead/tr/th[1]/a")).click();
        WEBDRIVER.switchTo().alert().accept();
        Thread.sleep(1000);
    }

    @Test
    public void testSorting() throws InterruptedException {
        WEBDRIVER.get("https://pn.com.ua/ct/1221/");
        WEBDRIVER.findElement(By.xpath(".//div[1]/div/div[2]/div/div/div[2]/div[2]/section/div[2]/ul/li[2]/a")).click();
        WEBDRIVER.findElement(By.xpath(".//div[1]/div/div[2]/div/div/div[2]/div[2]/section/div[2]/ul/li[2]/ul/li[2]/a")).click();
        Thread.sleep(2000);
    }

    @AfterClass
    public void closeWebDriver(){
        WEBDRIVER.close();
    }
}

