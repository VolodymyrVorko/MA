package pageObjects;

import Utility.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LogInPage extends BaseClass {
    private static WebElement element = null;

    public LogInPage(WebDriver driver) {
        super(driver);
    }

    public static WebElement inpfld_LoginEmailAddress() throws Exception {
        try {
            element = driver.findElement(By.id("LoginEmailAddress"));
            Log.info("LoginEmailAddress field is found on the Log in Page");
        } catch (Exception e) {
            Log.error("LoginEmailAddress is not found on the Log in Page");
            throw (e);
        }
        return element;
    }
    public static WebElement inpfld_LoginPassword() throws Exception {
        try {
            element = driver.findElement(By.id("LoginPassword"));
            Log.info("LoginPassword field is found on the Log in Page");
        } catch (Exception e) {
            Log.error("LoginPassword is not found on the Log in Page");
            throw (e);
        }
        return element;
    }
    public static WebElement btn_SignIn() throws Exception {
        try {
            element = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div[1]/div[1]/form/input[6]"));
            Log.info("SignIn button is found on the Log in Page");
        } catch (Exception e) {
            Log.error("SignIn is not found on the Log in Page");
            throw (e);
        }
        return element;
    }
}
