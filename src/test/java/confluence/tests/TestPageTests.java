package confluence.tests;

import confluence.pageobjects.LoginPage;
import confluence.pageobjects.TestPage;
import io.github.bonigarcia.seljup.SeleniumExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Scenarios:
 * - Only specific people can view or edit > add new user, select edit,
 *   add and Apply, open window verify they exist and remove
 */

@ExtendWith(SeleniumExtension.class)
@DisplayName("Confluence Page Tests")
class TestPageTests {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final TestPage testPage;
    private final LoginPage loginPage;

    public TestPageTests(WebDriver driver) {
        this.driver = driver;
        this.testPage = PageFactory.initElements(driver, TestPage.class);
        this.loginPage = PageFactory.initElements(driver, LoginPage.class);
        this.wait = new WebDriverWait(driver,10);
        this.testPage.navigateTo();
    }

    @AfterEach
    void storageCleanup() {
        ((JavascriptExecutor) driver).executeScript("window.localStorage.clear()");
    }

    @Test
    @DisplayName("Verify can login and loads correct page")
    void checkPage() {
        loginPage.userLogin("szliaw@gmail.com", "Testing123");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[data-testid='app-navigation-wordmark']")));
        testPage.navigateTo();
        String expectedUrl="https://szliaw.atlassian.net/wiki/spaces/HOME/pages/262146/Test+Page";
        String actualUrl;
        actualUrl = driver.getCurrentUrl();
        assertAll(
            () -> assertEquals(expectedUrl, actualUrl),
            () -> assertEquals("Test Page - Home - Confluence", driver.getTitle())
        );
    }

    @Test
    @DisplayName("Open and close Restrictions modal")
    void openAndCloseRestrictionsModal() {
        checkPage();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-test-id='restrictions.dialog.button']")));
        testPage.openRestrictionsModal();
        //check modal is open by verifying Inspect permissions button is there
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-test-id='inspect-perms-entry-button']")));
        WebElement inspectButton = driver.findElement(By.cssSelector("button[data-test-id='inspect-perms-entry-button']"));
        // need to upgrade confluence account...
        assertTrue(!inspectButton.isDisplayed(), "Inspect button does not exist");
        testPage.closeRestrictionsModal();
        //check modal is now closed
    }

}
