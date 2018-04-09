package appModules;

import Utility.Log;
import Utility.Utils;
import org.testng.Reporter;
import pageObjects.HomePage;

public class LogOutAction {
    public static void Execute() throws Exception {
        Log.info("LogOut action started");
        Utils.waitForElement(HomePage.btn_navuser());
        HomePage.btn_navuser().click();
        Log.info("Click action is performed on Nav-User button");
        Utils.waitForElement(HomePage.btn_LogOut());
        HomePage.btn_LogOut().click();
        Log.info("Click action is performed on LogOut button");
        Reporter.log("LogOut Action is successfully performed and finished");
    }
}
