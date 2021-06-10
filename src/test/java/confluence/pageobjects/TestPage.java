package confluence.pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class TestPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public TestPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver,10);
    }

    private final By restrictionsButtonBy = By.cssSelector("button[data-test-id='restrictions.dialog.button']");
    private final By restrictionsIconUnlockedBy = By.cssSelector("img[data-test-id='unlocked-icon']");
    public WebElement getRestrictionsIconUnlocked() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(restrictionsIconUnlockedBy));
        return driver.findElement(restrictionsIconUnlockedBy);
    }
    private final By restrictionsIconLockedBy = By.cssSelector("img[data-test-id='locked-icon']");
    public WebElement getRestrictionsIconLocked() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(restrictionsIconLockedBy));
        return driver.findElement(restrictionsIconLockedBy);
    }
    private final By restrictionsModalCancelButtonBy = By.cssSelector("button.css-k5brqb");

    private final By restrictionsModalApplyButtonBy = By.cssSelector("button.css-18ztuab");

    private final By inspectPermsButtonBy = By.cssSelector("button[data-test-id='inspect-perms-entry-button']");
    public WebElement getInspectPermsButton() {
        return driver.findElement(inspectPermsButtonBy);
    }

    private final By restrictionsDropdownBy = By.cssSelector("div[data-test-id='restrictions-dialog.content-mode-select']");
    private final By dropdownAnyoneCanViewBy = By.xpath("//*[@id=\"react-select-2-option-0\"]/div/span");
    private final By dropdownOnlySomeCanEditBy = By.xpath("//*[@id=\"react-select-2-option-1\"]/div/span");
    private final By dropdownOnlySpecificPeopleBy = By.xpath("//*[@id=\"react-select-2-option-2\"]/div/span");

    private final By userSearchFieldBy = By.cssSelector("div[data-test-id='user-and-group-search']");
    public WebElement getUserSearchField() {
        return driver.findElement(userSearchFieldBy);
    }
    private final By userRemoveButtonBy = By.cssSelector("button.css-msjm0");
    public List<WebElement> getUserRemoveButton() {
        return driver.findElements(userRemoveButtonBy);
    }

    public void navigateTo() {
        driver.get("https://szliaw.atlassian.net/wiki/spaces/HOME/pages/262146/Test+Page");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[data-testid='app-navigation-wordmark']")));
    }

    public void openRestrictionsModal() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-test-id='restrictions.dialog.button']")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.sc-hmXxxW.doZtJh")));
        driver.findElement(restrictionsButtonBy).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-test-id='inspect-perms-entry-button']")));
    }

    public void selectRestrictionsOption(String option) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[data-test-id='restrictions-dialog.content-mode-select']")));
        driver.findElement(restrictionsDropdownBy).click();
        /** Implementation Notes:
         * cannot locate dropdown items using css likely due to some React magic so had to use xpath
         */
        switch (option) {
            case "Anyone can view and edit":
                driver.findElement(dropdownAnyoneCanViewBy).click();
                break;
            case "Anyone can view, only some can edit":
                driver.findElement(dropdownOnlySomeCanEditBy).click();
                break;
            case "Only specific people can view or edit":
                driver.findElement(dropdownOnlySpecificPeopleBy).click();
                break;
        }
    }

    public void addUserField() {
        Actions action = new Actions(driver);
        // click on user field and type some text
        driver.findElement(userSearchFieldBy).click();
        action.sendKeys("Trello").perform();
        // select user - press enter?
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#react-select-restrictions\\:user-and-group-search\\:user-and-group-picker-option-0")));
        action.sendKeys(Keys.ENTER).perform();
        // remove that user from user field using the x button
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-testid='close-button-undefined']")));
        driver.findElement(By.cssSelector("button[data-testid='close-button-undefined']")).click();
        // select user again
        driver.findElement(userSearchFieldBy).click();
        action.sendKeys("Trello").perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#react-select-restrictions\\:user-and-group-search\\:user-and-group-picker-option-0")));
        action.sendKeys(Keys.ENTER).perform();
        // click add button to add user to field
        driver.findElement(By.cssSelector("button.e3nhwts5.css-17mybho")).click();
    }

    public void clickRestrictionsModalApply() {
        /** Implementation Notes:
         * for some reason driver cannot click the Apply button directly (seems to be a data-focus-lock element
         * overlapping... although it works for the Cancel button 🤷)
         * So used JavascriptExecutor but when using findElement on the class value
         * the Share button on the main page has the exact same value
         * so used findElements to get list of all the elements with that ID and selected second one
         */
        List<WebElement> buttons = driver.findElements(restrictionsModalApplyButtonBy);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", buttons.get(1));
    }

    public void removeUserInPanel() {
        // remove top user can modify later if need to remove specific
        driver.findElement(userRemoveButtonBy).click();
    }

    public void closeRestrictionsModal() {
        driver.findElement(restrictionsModalCancelButtonBy).click();
    }

    public void openInspectPermsModal() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-test-id='inspect-perms-entry-button']")));
        driver.findElement(inspectPermsButtonBy).click();
    }
}
