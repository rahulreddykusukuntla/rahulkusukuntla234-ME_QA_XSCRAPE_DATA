package demo.wrappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.time.Duration;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */
    // static WebDriver driver=new ChromeDriver();

    public static long getEpochTime(){
        long epochSeconds = Instant.now().getEpochSecond();
        // System.out.println("Epoch in Seconds: " + epochSeconds);
        return epochSeconds;
    }

    public static void clickNext(WebDriver driver) throws InterruptedException{
        WebElement ele=driver.findElement(By.xpath("//a[@aria-label='Next']"));
        ele.click();
        Thread.sleep(3000);
    }

    public static void clickHockeyTeams(WebDriver driver){
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement link=driver.findElement(By.xpath("//a[text()='Hockey Teams: Forms, Searching and Pagination']"));
        link.click();
        wait.until(ExpectedConditions.urlContains("/pages/forms/"));

    }
    
    public static void clickOscarWinning(WebDriver driver){
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement link=driver.findElement(By.xpath("//a[text()='Oscar Winning Films: AJAX and Javascript']"));
        link.click();
        wait.until(ExpectedConditions.urlContains("ajax-javascript"));

    }

    public static List<WebElement> getYears(WebDriver driver){
        List<WebElement> list=driver.findElements(By.xpath("//a[@class='year-link']"));
        return list;

    }

    public static void createJsonFile(ObjectMapper mapper,HashMap<Integer,Object> map) throws IOException{
         try {
            String Json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
            System.out.println(Json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        
        
    }

}
