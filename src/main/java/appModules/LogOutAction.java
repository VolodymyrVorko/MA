package appModules;

import Utility.Log;
import Utility.Utils;
import org.openqa.selenium.interactions.Actions;
import org.testng.Reporter;
import pageObjects.HomePage;

import static Utility.Utils.driver;

public class LogOutAction {
    public static void Execute() throws Exception {
        Log.info("LogOut action started");
        Utils.waitForElement(HomePage.btn_navuser());
        //added action moveTo 'cause of Mozilla is insane!
        Actions builder = new Actions(driver);
        builder.moveToElement(HomePage.btn_navuser()).click(HomePage.btn_navuser());
        builder.perform();
        Log.info("Click action is performed on Nav-User button");
        Utils.waitForElement(HomePage.btn_LogOut());
        HomePage.btn_LogOut().click();
        Log.info("Click action is performed on LogOut button");
        Log.info("LogOut Action is successfully performed and finished");
        Reporter.log("LogOut Action is successfully performed and finished");
    }
}
