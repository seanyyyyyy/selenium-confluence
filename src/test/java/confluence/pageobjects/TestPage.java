package confluence.pageobjects;

import org.openqa.selenium.*;
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

    @FindBy(className = "css-k5brqb")
    private WebElement restrictionsModalCancelButton;

    @FindBy(css = "button[data-test-id='inspect-perms-entry-button']")
    private WebElement inspectPermsButton;

    public void navigateTo() {
        driver.get("https://szliaw.atlassian.net/wiki/spaces/HOME/pages/262146/Test+Page");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[data-testid='app-navigation-wordmark']")));
    }

    public void openRestrictionsModal() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-test-id='restrictions.dialog.button']")));
        restrictionsButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-test-id='inspect-perms-entry-button']")));
    }

    public void closeRestrictionsModal() {
        restrictionsModalCancelButton.click();
    }

    public void openInspectPermsModal() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-test-id='inspect-perms-entry-button']")));
        inspectPermsButton.click();
    }
}
