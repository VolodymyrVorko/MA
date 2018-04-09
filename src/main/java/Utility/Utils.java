package Utility;

import java.io.File;
import java.util.concurrent.TimeUnit;

import Utility.Constant;
import Utility.ExcelUtils;
import Utility.Log;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Utils {
    public static WebDriver driver = null;
    public static WebDriver OpenBrowser(int iTestCaseRow) throws Exception{
        String sBrowserName;
        try{
            sBrowserName = ExcelUtils.getCellData(iTestCaseRow, Constant.Col_Browser);
            if(sBrowserName.equals("Firefox")){
                System.setProperty("webdriver.gecko.driver", "C:\\Users\\vorko\\IdeaProjects\\MechanicAdvisor\\Lib\\Drivers\\geckodriver.exe");
                driver = new FirefoxDriver();
                Log.info("New Firefox driver instantiated");
                driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
                Log.info("Implicit wait applied on the driver for 10 seconds");
                driver.get(Constant.URL);
                Log.info("Web application launched successfully");
            }else if (sBrowserName.equals("Chrome")){
                System.setProperty("webdriver.chrome.driver", "C:\\Users\\vorko\\IdeaProjects\\MechanicAdvisor\\Lib\\Drivers\\chromedriver.exe");
                driver = new ChromeDriver();
                Log.info("New Chrome driver instantiated");
                driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
                Log.info("Implicit wait applied on the driver for 10 seconds");
                driver.get(Constant.URL);
                Log.info("Web application launched successfully");
            }
        }catch (Exception e){
            Log.error("Class Utils | Method OpenBrowser | Exception desc : "+e.getMessage());
        }
        return driver;
    }

    public static String getTestCaseName(String sTestCase)throws Exception{
        String value = sTestCase;
        try{
            int posi = value.indexOf("@");
            value = value.substring(0, posi);
            posi = value.lastIndexOf(".");
            value = value.substring(posi + 1);
            return value;
        }catch (Exception e){
            Log.error("Class Utils | Method getTestCaseName | Exception desc : "+e.getMessage());
            throw (e);
        }
    }


    public static void waitForElement(WebElement element){

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void takeScreenshot(WebDriver driver, String sTestCaseName) throws Exception{
        try{
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(Constant.Path_ScreenShot + sTestCaseName +".jpg"));
        } catch (Exception e){
            Log.error("Class Utils | Method takeScreenshot | Exception occured while capturing ScreenShot : "+e.getMessage());
            throw new Exception();
        }
    }


}
