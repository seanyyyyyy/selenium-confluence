package confluence.tests;

import confluence.pageobjects.LoginPage;
import confluence.pageobjects.TestPage;
import io.github.bonigarcia.seljup.SeleniumExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import static org.junit.jupiter.api.Assertions.*;

/** SCENARIOS:
 *  In my scenarios I focused on:
 *  - verifying main functionality (changing page permissions); and
 *  - ensuring every interactive element in the modal is touched at least once
 *
 *  1. Open and Cancel permissions modal
 *
 *  2. Change page permissions to 'Anyone can view'
 *
 *  3. Change page permissions to 'Anyone can view, only some can edit', covering:
 *      - add a specific user in user search
 *      - remove a specific user from user search
 *      - add permissions for a specific user
 *      - remove permissions for added user
 *
 *  4. Change page permissions to 'Only specific people can view or edit' for a specific user, covering:
 *      - add a specific user in user search
 *      - remove a specific user from user search
 *      - switch a selected user's permissions between edit and view
 *      - add permissions for a specific user
 *      - remove permissions for added user
 *
 *  4. Click Learn more link and verify URL
 *
 *  5. Inspect Permissions (blocked due to account type)
 *
 */

@ExtendWith(SeleniumExtension.class)
@DisplayName("Confluence Page Tests")
public class TestPageTests {
    private final TestPage testPage;
    private final LoginPage loginPage;

    public TestPageTests(WebDriver driver) {
        this.testPage = PageFactory.initElements(driver, TestPage.class);
        this.loginPage = PageFactory.initElements(driver, LoginPage.class);
    }

/** To run multiple browsers through selenium-jupiter would instantiate like so:

    private final TestPage testPageFirefox;
    private final LoginPage loginPageFirefox;
    public TestPageTests(ChromeDriver driver1, FirefoxDriver driver2) {
        this.testPage = PageFactory.initElements(driver1, TestPage.class);
        this.testPageFirefox = PageFactory.initElements(driver2, TestPage.class);
        this.loginPage = PageFactory.initElements(driver1, LoginPage.class);
        this.loginPageFirefox = PageFactory.initElements(driver2, LoginPage.class);
    }

 */

    @DisplayName("Login and load Confluence Test page")
    void loginAndLoadTestPage() {
        loginPage.navigateTo();
        loginPage.userLogin("szliaw@gmail.com", "Testing123");
        testPage.navigateTo();
    }

    @Test
    @DisplayName("Open and Cancel Restrictions modal")
    void openAndCloseRestrictionsModal() {
        loginAndLoadTestPage();
        testPage.openRestrictionsModal();
        assertTrue(testPage.getInspectPermsButton().isDisplayed(), "Inspect permissions button displayed");
        testPage.cancelRestrictionsModal();
        //check modal is now closed
        try{
            testPage.openInspectPermsModal();
        }
        catch (StaleElementReferenceException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("'Anyone can view and edit' set page permissions")
    void setPermissionsAnyoneCanView() {
        loginAndLoadTestPage();
        testPage.openRestrictionsModal();
        assertTrue(testPage.getInspectPermsButton().isDisplayed(), "Inspect permissions button displayed");
        testPage.selectRestrictionsOption("Anyone can view and edit");
        testPage.clickRestrictionsModalApply();
        assertTrue(testPage.getRestrictionsIconUnlocked().isDisplayed(), "Restrictions icon should be unlocked");
    }

    @Test
    @DisplayName("'Anyone can view, only some can edit' set page permissions for a specific User")
    void setPermissionsOnlySomeCanEdit() {
        loginAndLoadTestPage();
        testPage.openRestrictionsModal();
        assertTrue(testPage.getInspectPermsButton().isDisplayed(), "Inspect permissions button displayed");
        testPage.selectRestrictionsOption("Anyone can view, only some can edit");
        assertTrue(testPage.getUserSearchField().isDisplayed(), "User search field appears");
        testPage.addUserField();
        assertEquals(1, testPage.getUserRemoveButton().size(), "Verify user has been added if Remove button visible");
        testPage.clickRestrictionsModalApply();
        assertTrue(testPage.getRestrictionsIconUnlocked().isDisplayed(), "Restrictions icon should be unlocked");
        //remove user to keep test data clean
        testPage.openRestrictionsModal();
        testPage.removeUserInPanel();
        assertEquals(0, testPage.getUserRemoveButton().size(), "should be no Remove button as user removed");
        testPage.clickRestrictionsModalApply();
        assertTrue(testPage.getRestrictionsIconUnlocked().isDisplayed(), "Restrictions icon should be unlocked");
    }

    @Test
    @DisplayName("'Only specific people can view or edit' set page permissions for a specific User")
    void setPermissionsOnlySpecificPeople() {
        loginAndLoadTestPage();
        testPage.openRestrictionsModal();
        assertTrue(testPage.getInspectPermsButton().isDisplayed(), "Inspect permissions button displayed");
        testPage.selectRestrictionsOption("Only specific people can view or edit");
        assertTrue(testPage.getUserSearchField().isDisplayed(), "User search field should appear");
        testPage.addUserField();
        assertEquals(1, testPage.getUserRemoveButton().size(), "Verify user has been added if Remove button visible");
        testPage.clickRestrictionsModalApply();
        assertTrue(testPage.getRestrictionsIconLocked().isDisplayed(), "Restrictions icon should be locked");
        //remove user to keep test data clean
        testPage.openRestrictionsModal();
        testPage.removeUserInPanel();
        assertEquals(0, testPage.getUserRemoveButton().size(), "should be no Remove button as user removed");
        testPage.clickRestrictionsModalApply();
        assertTrue(testPage.getRestrictionsIconLocked().isDisplayed(), "Restrictions icon should be locked");
    }

    @Test
    @DisplayName("Verify Learn more links to correct page")
    void openLearnMore() {
        loginAndLoadTestPage();
        testPage.openRestrictionsModal();
        assertTrue(testPage.getInspectPermsButton().isDisplayed(), "Inspect permissions button displayed");
        String learnMoreUrl = testPage.openLearnMoreGetUrl();
        assertEquals("https://support.atlassian.com/confluence-cloud/docs/add-or-remove-page-restrictions/", learnMoreUrl);
    }

}
