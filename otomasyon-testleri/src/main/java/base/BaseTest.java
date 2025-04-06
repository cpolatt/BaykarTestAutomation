package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import utils.Log;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod
    public void setUp() {

        Log.info("Starting WebDriver...");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.manage().window().maximize();
        Log.info("Navigatings to URL...");
        driver.get("https://kariyer.baykartech.com/");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            Log.info("Closing Browser...");
            driver.quit();
        }
    }
}
