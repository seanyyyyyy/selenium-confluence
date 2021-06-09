package confluence.pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    public TestPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver,10);
    }

    @FindBy(css = "button[data-test-id='restrictions.dialog.button']")
    private WebElement restrictionsButton;

    @FindBy(css = "button.css-k5brqb")
    private WebElement restrictionsModalCancelButton;

    @FindBy(css = "button.css-18ztuab")
    private WebElement restrictionsModalApplyButton;

    @FindBy(css = "button[data-test-id='inspect-perms-entry-button']")
    private WebElement inspectPermsButton;
    public WebElement getInspectPermsButton() {
        return this.inspectPermsButton;
    }

    @FindBy(css = "div[data-test-id='restrictions-dialog.content-mode-select']")
    private WebElement restrictionsDropdown;

    public void navigateTo() {
        driver.get("https://szliaw.atlassian.net/wiki/spaces/HOME/pages/262146/Test+Page");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[data-testid='app-navigation-wordmark']")));
    }

    public void openRestrictionsModal() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-test-id='restrictions.dialog.button']")));
        restrictionsButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-test-id='inspect-perms-entry-button']")));
    }

    public void selectRestrictionsOption(String option) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[data-test-id='restrictions-dialog.content-mode-select']")));
        restrictionsDropdown.click();
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
        hover.moveToElement(restrictionsModalApplyButton).click().perform();
    }

    public void closeRestrictionsModal() {
        restrictionsModalCancelButton.click();
    }

    public void openInspectPermsModal() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-test-id='inspect-perms-entry-button']")));
        inspectPermsButton.click();
    }
}
