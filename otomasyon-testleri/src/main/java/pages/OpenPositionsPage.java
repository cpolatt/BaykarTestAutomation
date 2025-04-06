package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Log;

public class OpenPositionsPage {

    private final WebDriver driver;

    public OpenPositionsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "(//a[normalize-space()='Open Positions'])[2]")
    WebElement openPositionsButton;

    @FindBy(css = "div[class='footerContainer']")
    WebElement footer;

    @FindBy(css = "h3[id='filterIconText']")
    WebElement filters;

    @FindBy(css = "input[id='search']")
    WebElement searchPosition;

    @FindBy(css = "input[id='searchInput']")
    WebElement searhUnit;

    @FindBy(xpath = ".//a/button[contains(@class, 'exam')]")
    WebElement detailButton;

    public void scrollFooter() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", footer);
    }

    public void clickOpenPositionsButton() throws InterruptedException {
        openPositionsButton.click();
        Thread.sleep(500);
    }

    public void scrollFilters() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", filters);
        Thread.sleep(500);
    }

    public void enterSearchPosition(String position) throws InterruptedException {
        searchPosition.clear();
        searchPosition.sendKeys(position);
        Thread.sleep(1000);
    }

    public void enterSearchUnit(String unit) throws InterruptedException {
        searhUnit.clear();
        searhUnit.sendKeys(unit);
        Thread.sleep(1000);
    }

    public void checkUnit(String unit) throws InterruptedException {
        String labelXpath = "//label[contains(text(),'" + unit + "')]";
        driver.findElement(By.xpath(labelXpath)).click();
        Thread.sleep(1000);
    }

    public void clickDetailButton() {
        Log.info("Clicking detail button..");
        detailButton.click();

    }
}
