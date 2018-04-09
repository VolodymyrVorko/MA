package pageObjects;

import Utility.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BaseClass {
    private static WebElement element = null;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public static WebElement btn_LogIn() throws Exception {
        try {
            element = driver.findElement(By.id("loginLink"));
            Log.info("Log In button is found on the Home Page");
        } catch (Exception e) {
            Log.error("Log In button is not found on the Home Page");
            throw (e);
        }
        return element;
    }

    public static WebElement btn_navuser() throws Exception {
        try {
            element = driver.findElement(By.linkText("WELCOME BACK,"));
            Log.info("nav-user button is found on the Home Page");
        } catch (Exception e) {
            Log.error("nav-user button is not found on the Home Page");
            throw (e);
        }
        return element;
    }
    public static WebElement btn_LogOut() throws Exception {
        try {
            element = driver.findElement(By.linkText("Logout"));
            Log.info("Log Out button is found on the Home Page");
        } catch (Exception e) {
            Log.error("Log Out button is not found on the Home Page");
            throw (e);
        }
        return element;
    }
}
