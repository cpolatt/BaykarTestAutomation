package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.OpenPositionsPage;
import utils.ExcelUtils;
import utils.Log;

import java.io.IOException;


public class OpenPositionsTest extends BaseTest {

    @DataProvider(name = "PositionData")
    public Object[][] getPositionData() throws IOException {
        String filePath = System.getProperty("user.dir") + "/testdata/TestData.xlsx";
        ExcelUtils.loadExcel(filePath, "Sheet1");
        int rowCount = ExcelUtils.getRowCount();
        Object[][] data = new Object[rowCount - 1][2];

        for (int i = 1; i < rowCount; i++) {
            data[i - 1][0] = ExcelUtils.getCellData(i, 0);
            data[i - 1][1] = ExcelUtils.getCellData(i, 1);
        }
        ExcelUtils.closeExcel();
        return data;
    }


    @Test(dataProvider = "PositionData")
    public void testOpenPositions(String position, String unit) throws InterruptedException {

        Log.info("Starting open positions test...");
        OpenPositionsPage openPositionsPage = new OpenPositionsPage(driver);
        openPositionsPage.scrollFooter();
        openPositionsPage.clickOpenPositionsButton();
        openPositionsPage.scrollFilters();
        openPositionsPage.enterSearchPosition(position);
        openPositionsPage.enterSearchUnit(unit);
        openPositionsPage.checkUnit(unit);
        openPositionsPage.clickDetailButton();

        String actualPositionTitle = driver.findElement(By.xpath(".//h4")).getText().trim();
        Log.info("Verifying position title");
        Assert.assertEquals(actualPositionTitle, position);

    }
}
