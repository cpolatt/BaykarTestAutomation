package tests;

import ConfigReader.ConfigPropReader;
import factory.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.LanguagePage;
import utils.Log;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class LanguageTest {

    DriverFactory df;
    ConfigPropReader cp;
    Properties prop;
    WebDriver driver;
    LanguagePage languagePage;

    @BeforeTest
    public void setUp() {
        Log.info("Starting WebDriver...");
        cp = new ConfigPropReader();
        prop = cp.initLangProp("english");//turkish
        df = new DriverFactory();
        driver = df.initDriver(prop);
        languagePage = new LanguagePage(driver);
    }

    @Test
    public void headerTest() {
        Log.info("Verifying Header text");
        Assert.assertTrue(languagePage.isHeaderExist(prop.getProperty("header")));
        Assert.assertTrue(languagePage.isHeader2Exist(prop.getProperty("header2")));

    }

    @Test
    public void navMenuItemTest() {
        List<String> navItems = Arrays.asList(
                prop.getProperty("navMenuItem1"),
                prop.getProperty("navMenuItem2"),
                prop.getProperty("navMenuItem3"),
                prop.getProperty("navMenuItem4")
        );
        for (String item : navItems) {
            Log.info("Verifying Nav Menu Item text");
            Assert.assertTrue(languagePage.isNavMenuItemExist(item));

        }
    }

    @AfterTest
    public void tearDown() {
        Log.info("Closing Browser...");
        driver.quit();
    }
}
