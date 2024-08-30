package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.io.File;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;
import java.time.Duration;

public class TestCases {
    ChromeDriver driver;

    /*
     * TODO: Write your tests here with testng @Test annotation. 
     * Follow `testCase01` `testCase02`... format or what is provided in instructions
     */
    @Test
    public void testCase01() throws InterruptedException, IOException{
        ObjectMapper mapper = new ObjectMapper();
        driver.get("https://www.scrapethissite.com/pages/");
        HashMap<Integer,Object> map=new HashMap<Integer,Object>();
        Wrappers.clickHockeyTeams(driver);
        int k=1,n=0;
        // Wrappers.getTableDetails(driver);
        // ArrayList<ArrayList<String>> li=new ArrayList<ArrayList<String>>();
        while(n<4){
            int rows=driver.findElements(By.xpath("//table/tbody/tr")).size();
            for(int i=2;i<rows;i++){
                String val=driver.findElement(By.xpath("//table/tbody/tr["+i+"]/td[6]")).getText();
                float f=Float.parseFloat(val);
                if(f<0.4){
                    ArrayList<String> list=new ArrayList<>();
                    String team=driver.findElement(By.xpath("//table/tbody/tr["+i+"]/td[1]")).getText();
                    String year=driver.findElement(By.xpath("//table/tbody/tr["+i+"]/td[2]")).getText();
                // System.out.println(team);
                // System.out.println(year);
                // System.out.println(val);
                // System.out.println(Long.toString(getEpochTime()));
                    list.add(Long.toString(Wrappers.getEpochTime()));
                    list.add(team);
                    list.add(year);
                    list.add(val);
                // li.add(list);
                    map.put(k,list);
                    k++;

                
                }

            }
            Wrappers.clickNext(driver);
            n++;
        }
        for(Integer i:map.keySet()){
            System.out.println(map.get(i));
        }
        Wrappers.createJsonFile(mapper,map);
        String userDir = System.getProperty("user.dir");
        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(userDir + "\\src\\test\\resources\\hockey-team-data.json"), map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    @Test
    public void testCase02() throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        int q=1;
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.get("https://www.scrapethissite.com/pages/");
        HashMap<Integer,Object> map=new HashMap<Integer,Object>();
        Wrappers.clickOscarWinning(driver);
        for(WebElement i:Wrappers.getYears(driver)){
            String year=i.getText();
            i.click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//th[text()='Title']")));
            for(int j=1;j<=5;j++){
                ArrayList<String> list=new ArrayList<>();
                list.add(Long.toString(Wrappers.getEpochTime()));
                list.add(year);
                for(int k=1;k<=4;k++){
                    
                    if(k<4){
                        String str=driver.findElement(By.xpath("//table/tbody/tr["+j+"]/td["+k+"]")).getText();
                        list.add(str);

                    }
                    else{
                        try{
                            Boolean isWinner=driver.findElement(By.xpath("//table/tbody/tr["+j+"]/td["+k+"]/i")).isDisplayed();
                            list.add(String.valueOf(isWinner));
                        }catch (Exception e) {
                            list.add("false");
                        }

                        
                    }

                }
                map.put(q,list);
                q++;
            }

        }
        for(Integer i:map.keySet()){
            System.out.println(map.get(i));
        }
        Wrappers.createJsonFile(mapper,map);
        String userDir = System.getProperty("user.dir");
        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(userDir + "\\src\\test\\resources\\oscar-winner-data.json"), map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     
    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */
    @BeforeTest
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest()
    {
        driver.close();
        driver.quit();

    }
}