package TestCases;

import Utility.Constant;
import Utility.ExcelUtils;
import Utility.Log;
import appModules.LogInAction;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjects.BaseClass;

public class Login_Firefox {
    public WebDriver driver;
    private String sTestCaseName;
    private int iTestCaseRow;

    @BeforeMethod
    public void beforeMethod() throws Exception {
        DOMConfigurator.configure("log4j.xml");
        sTestCaseName = this.toString();
        sTestCaseName = Utility.Utils.getTestCaseName(sTestCaseName);
        Log.startTestCase(sTestCaseName);
        ExcelUtils.setExcelFile(Constant.Path_TestData + Constant.File_TestData, "Sheet1");
        iTestCaseRow = ExcelUtils.getRowContains(sTestCaseName, Constant.Col_TestCaseName);
        driver = Utility.Utils.OpenBrowser(iTestCaseRow);
        new BaseClass(driver);
    }

    @Test
    public void LogIn() throws Exception {
        try {
            LogInAction.Execute(2);
            ExcelUtils.setCellData("Pass", iTestCaseRow, Constant.Col_Result);
        } catch (Exception e) {
            ExcelUtils.setCellData("Fail", iTestCaseRow, Constant.Col_Result);
            Utility.Utils.takeScreenshot(driver, sTestCaseName);
            Log.error(e.getMessage());
            throw (e);
        }
    }

    @AfterMethod
    public void afterMethod() {
        Log.endTestCase(sTestCaseName);
        driver.close();
    }
}
