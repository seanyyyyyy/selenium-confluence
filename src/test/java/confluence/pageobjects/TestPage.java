package confluence.pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class TestPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public TestPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver,5);
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

    private final By userPickerBy = By.cssSelector("#react-select-restrictions\\:user-and-group-search\\:user-and-group-picker-option-0");
    private final By removeUserInsideBy = By.cssSelector("button[data-testid='close-button-undefined']");
    private final By permissionDropdownBy = By.cssSelector("div.css-4avucx-control");
    private final By dropdownSelectionEditBy = By.id("react-select-3-option-1");
    private final By addUserButtonBy = By.cssSelector("button.e3nhwts5.css-17mybho");
    private final By dropdownSelectionViewBy = By.id("react-select-4-option-0");

    private final By userRemoveButtonBy = By.cssSelector("button.css-msjm0");
    public List<WebElement> getUserRemoveButton() {
        return driver.findElements(userRemoveButtonBy);
    }

    private final By learnMoreButtonBy = By.cssSelector("a.css-abo4z9");
    private final By restrictionsModalCancelButtonBy = By.cssSelector("button.css-k5brqb");
    private final By restrictionsModalApplyButtonBy = By.cssSelector("button.css-18ztuab");

    public void navigateTo() {
        driver.get("https://szliaw.atlassian.net/wiki/spaces/HOME/pages/262146/Test+Page");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[data-testid='app-navigation-wordmark']")));
    }

    public void openRestrictionsModal() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(restrictionsButtonBy));
        // wait for overlay to get removed
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.sc-hmXxxW.doZtJh")));
        driver.findElement(restrictionsButtonBy).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(inspectPermsButtonBy));
    }

    /** Implementation Notes:
     * Cannot locate dropdown items using css likely due to some React magic so had to use xpath
     */
    public void selectRestrictionsOption(String option) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(restrictionsDropdownBy));
        driver.findElement(restrictionsDropdownBy).click();
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
        // click on user search field and type some text
        driver.findElement(userSearchFieldBy).click();
        action.sendKeys("Trello").perform();
        // select user - press enter?
        wait.until(ExpectedConditions.visibilityOfElementLocated(userPickerBy));
        action.sendKeys(Keys.ENTER).perform();
        // remove added user from user search field using the x button
        wait.until(ExpectedConditions.visibilityOfElementLocated(removeUserInsideBy));
        driver.findElement(removeUserInsideBy).click();
        // select same user again
        driver.findElement(userSearchFieldBy).click();
        action.sendKeys("Trello").perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(userPickerBy));
        action.sendKeys(Keys.ENTER).perform();
        //(Only specific people scenario) if the edit dropdown is visible set status as Edit
        List<WebElement> dropdowns = driver.findElements(permissionDropdownBy);
        if (dropdowns.size() == 2) {
            dropdowns.get(1).click();
            driver.findElement(dropdownSelectionEditBy).click();
        }
        driver.findElement(addUserButtonBy).click();
        //(Only specific people scenario) set added user status as View
        dropdowns = driver.findElements(permissionDropdownBy);
        if (dropdowns.size() == 3) {
            dropdowns.get(2).click();
            driver.findElement(dropdownSelectionViewBy).click();
        }
    }

    /** Implementation Notes:
     * for some reason driver cannot click the Apply button directly so used JavascriptExecutor
     * (seems to be a data-focus-lock element overlapping
     * ...although direct works for the Cancel button 🤷)
     */
    public void clickRestrictionsModalApply() {
        List<WebElement> buttons = driver.findElements(restrictionsModalApplyButtonBy);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", buttons.get(1));
    }

    public void removeUserInPanel() {
        // removes top user can modify later if need to remove specific
        driver.findElement(userRemoveButtonBy).click();
    }

    public void cancelRestrictionsModal() {
        driver.findElement(restrictionsModalCancelButtonBy).click();
    }

    public void openInspectPermsModal() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inspectPermsButtonBy));
        driver.findElement(inspectPermsButtonBy).click();
    }

    public String openLearnMoreGetUrl() {
        driver.findElement(learnMoreButtonBy).click();
        ArrayList<String> tabs = new ArrayList<> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        return driver.getCurrentUrl();
    }
}
