package appModules;

import Utility.Constant;
import Utility.ExcelUtils;
import Utility.Log;
import Utility.Utils;
import org.testng.Assert;
import org.testng.Reporter;
import pageObjects.HomePage;
import pageObjects.LogInPage;

public class LogInAction {

    public static void Execute(int iTestCaseRow) throws Exception {
        HomePage.btn_LogIn().click();
        Log.info("Click action is perfromed on LogIn button");
        String sEmail = ExcelUtils.getCellData(iTestCaseRow, Constant.Col_Email);
        Utils.waitForElement(LogInPage.inpfld_LoginEmailAddress());
        LogInPage.inpfld_LoginEmailAddress().sendKeys(sEmail);
        Log.info(sEmail + " is entered in Email text box");
        String sPassword = ExcelUtils.getCellData(iTestCaseRow, Constant.Col_Password);
        Utils.waitForElement(LogInPage.inpfld_LoginPassword());
        LogInPage.inpfld_LoginPassword().sendKeys(sPassword);
        Log.info(sPassword + " is entered in Password text box");
        LogInPage.btn_SignIn().click();
        Log.info("Click action is perfromed on SignIn button");
        String actualString = HomePage.txt_WLCBCK().getText();
        Assert.assertTrue(actualString.contains("WELCOME BACK,"));
        Log.info("Welcome Back text is found");
        Reporter.log("LogIn Action is successfully perfomred");
    }
}
