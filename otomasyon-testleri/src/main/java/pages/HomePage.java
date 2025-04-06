package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.Log;

import java.util.List;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public HomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "ul[class='navbar-nav'] li[class='nav-item dropdown']")
    WebElement dropDownElement;

    @FindBy(css = "ul[class='navbar-nav'] li[class='nav-item dropdown'] .dropdown-item")
    List<WebElement> dropdownItems;

    @FindBy(css = "ul[class='navbar-nav'] li[class='nav-item'] .nav-link")
    List<WebElement> navLinks;

    @FindBy(css = "ul[class='navbar-nav'] div[class='offcanvas-button'] .nav-link")
    List<WebElement> navButtons;

    private boolean isPageLoaded(String currentUrl) {
        try {
            return !driver.getCurrentUrl().equals(currentUrl);
        } catch (Exception e) {
            Log.error("An error occurred while loading the page.");
            return false;
        }
    }

    private void clickAndVerify(WebElement element, String elementName) throws InterruptedException {
        String hrefBefore = driver.getCurrentUrl();

        Log.info(elementName + " is being clicked → " + element.getText().trim());
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        Thread.sleep(1000);

        if (isPageLoaded(hrefBefore)) {
            String pageTitle = driver.getTitle().trim();
            Log.info(" → Page navigation successful. Title: " + pageTitle);

            Assert.assertFalse(pageTitle.isEmpty(), "Page title is empty!");

            driver.navigate().back();
            Thread.sleep(1500);
        } else {
            Log.info(" → Possibly an in-page anchor or a popup.");
            Thread.sleep(500);
        }
    }

    public void testDropdownItems() throws InterruptedException {
        for (int i = 0; i < dropdownItems.size(); i++) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(dropDownElement));
                dropDownElement.click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By
                        .cssSelector("li[class='nav-item dropdown'] .dropdown-item")));

                List<WebElement> currentItems = driver.findElements(By
                        .cssSelector("li[class='nav-item dropdown'] .dropdown-item"));
                WebElement currentLink = currentItems.get(i);

                clickAndVerify(currentLink, "Dropdown item");

            } catch (StaleElementReferenceException e) {
                Log.info("Dropdown element has changed. Re-fetching...");
                dropdownItems = driver.findElements(By.cssSelector("li[class='nav-item dropdown'] .dropdown-item"));
                i--;
            } catch (Exception e) {
                Log.info("An error occurred: " + e.getMessage());
            }
        }
    }

    public void testNavLinks() throws InterruptedException {
        for (int i = 0; i < navLinks.size(); i++) {
            try {
                navLinks = driver.findElements(By.cssSelector("li[class='nav-item'] .nav-link"));
                WebElement currentLink = navLinks.get(i);

                if (currentLink.getAttribute("class").contains("dropdown-toggle")) {
                    continue;
                }

                clickAndVerify(currentLink, "Nav link");

            } catch (StaleElementReferenceException e) {
                Log.info("Nav link has changed. Re-fetching...");
                i--;
            } catch (Exception e) {
                Log.info("An error occurred: " + e.getMessage());
            }
        }
    }

    public void testOffcanvasButtons() throws InterruptedException {
        for (int i = 0; i < navButtons.size(); i++) {
            try {
                navButtons = driver.findElements(By.cssSelector("div[class='offcanvas-button'] .nav-link"));
                WebElement currentButton = navButtons.get(i);

                clickAndVerify(currentButton, "Offcanvas button");

            } catch (StaleElementReferenceException e) {
                Log.info("Offcanvas button has changed. Re-fetching...");
                i--;
            } catch (Exception e) {
                Log.info("An error occurred: " + e.getMessage());
            }
        }
    }

    public void testAllMenuItems() throws InterruptedException {
        testDropdownItems();
        testNavLinks();
        testOffcanvasButtons();
    }
}



