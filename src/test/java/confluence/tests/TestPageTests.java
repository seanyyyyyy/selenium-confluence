package confluence.tests;

import confluence.pageobjects.LoginPage;
import confluence.pageobjects.TestPage;
import io.github.bonigarcia.seljup.SeleniumExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
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
    private final TestPage testPage;
    private final LoginPage loginPage;

    public TestPageTests(WebDriver driver) {
        this.driver = driver;
        this.testPage = PageFactory.initElements(driver, TestPage.class);
        this.loginPage = PageFactory.initElements(driver, LoginPage.class);
    }

    @AfterEach
    void storageCleanup() {
        ((JavascriptExecutor) driver).executeScript("window.localStorage.clear()");
    }

    @Test
    @DisplayName("Verify can login and loads wiki Test page")
    void loginAndLoadTestPage() {
        loginPage.navigateTo();
        loginPage.userLogin("szliaw@gmail.com", "Testing123");
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
        loginAndLoadTestPage();
        testPage.openRestrictionsModal();
        //check modal is open by verifying Inspect permissions button is there
        assertTrue(testPage.getInspectPermsButton().isDisplayed(), "Inspect permissions button displayed");
        testPage.closeRestrictionsModal();
        //check modal is now closed
        try{
            testPage.openInspectPermsModal();
        }
        catch (StaleElementReferenceException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Open and close Restrictions modal")
    void setPermissionsToAnyoneCanView() {
        loginAndLoadTestPage();
        testPage.openRestrictionsModal();
        //check modal is open by verifying Inspect permissions button is there
        WebElement inspectButton = driver.findElement(By.cssSelector("button[data-test-id='inspect-perms-entry-button']"));
        assertTrue(inspectButton.isDisplayed(), "Inspect permissions button displayed");
        testPage.selectRestrictionsOption("Anyone can view and edit");
        //check modal is now closed
    }

}
