package confluence.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public TestPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver,10);
    }

    private final By restrictionsButtonBy = By.cssSelector("button[data-test-id='restrictions.dialog.button']");

    private final By restrictionsModalCancelButtonBy = By.cssSelector("button.css-k5brqb");

    private final By restrictionsModalApplyButtonBy = By.cssSelector("button.css-18ztuab");

    private final By inspectPermsButtonBy = By.cssSelector("button[data-test-id='inspect-perms-entry-button']");
    public WebElement getInspectPermsButton() {
        return driver.findElement(inspectPermsButtonBy);
    }

    private final By restrictionsDropdownBy = By.cssSelector("div[data-test-id='restrictions-dialog.content-mode-select']");

    public void navigateTo() {
        driver.get("https://szliaw.atlassian.net/wiki/spaces/HOME/pages/262146/Test+Page");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[data-testid='app-navigation-wordmark']")));
    }

    public void openRestrictionsModal() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-test-id='restrictions.dialog.button']")));
        driver.findElement(restrictionsButtonBy).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-test-id='inspect-perms-entry-button']")));
    }

    public void selectRestrictionsOption(String option) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[data-test-id='restrictions-dialog.content-mode-select']")));
        driver.findElement(restrictionsDropdownBy).click();
        Actions keyDown = new Actions(driver);
        if(option.equals("Anyone can view and edit")) {
            // hacky way for now, will investigate whether can be done more smoothly if time permits
            keyDown.sendKeys(Keys.chord(Keys.DOWN, Keys.DOWN, Keys.DOWN, Keys.ENTER)).perform();
        }
        else if(option.equals("Anyone can view,only some can edit")) {
            keyDown.sendKeys(Keys.chord(Keys.DOWN, Keys.ENTER)).perform();
        }
        else{
            //option.equals("Only specific people can view or edit")
            keyDown.sendKeys(Keys.chord(Keys.DOWN, Keys.DOWN, Keys.ENTER)).perform();
        }
        Actions hover = new Actions(driver);
        hover.moveToElement(driver.findElement(restrictionsModalApplyButtonBy)).click().perform();
    }

    public void closeRestrictionsModal() {
        driver.findElement(restrictionsModalCancelButtonBy).click();
    }

    public void openInspectPermsModal() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-test-id='inspect-perms-entry-button']")));
        driver.findElement(inspectPermsButtonBy).click();
    }
}
