package TestCases.LogIn_LogOut.Chrome;

import Utility.Constant;
import Utility.ExcelUtils;
import Utility.Log;
import appModules.LogInAction;
import appModules.LogOutAction;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pageObjects.BaseClass;

public class Test_LogIn_LogOut_Chrome {
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
    public void Test_LogIn_LogOut_Chrome() throws Exception {
        try {
            LogInAction.Execute(1);
            LogOutAction.Execute();
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
