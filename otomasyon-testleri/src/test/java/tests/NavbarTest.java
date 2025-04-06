package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.Log;

public class NavbarTest extends BaseTest {

    @Test
    public void testNavBarLinks() throws InterruptedException {
        Log.info("Starting navbar menu test...");
        HomePage homePage = new HomePage(driver, wait);
        homePage.testAllMenuItems();
    }
}
