package TestCases;

import Utility.Constant;
import Utility.ExcelUtils;
import Utility.Log;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pageObjects.BaseClass;
import pageObjects.HomePage;

public class Sample {
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
    public void btn_logIn() throws Exception {
        try {
            HomePage.btn_LogIn().click();
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
