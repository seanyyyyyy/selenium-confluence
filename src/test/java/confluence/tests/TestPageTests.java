package confluence.tests;

import confluence.pageobjects.LoginPage;
import confluence.pageobjects.TestPage;
import io.github.bonigarcia.seljup.SeleniumExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SeleniumExtension.class)
@DisplayName("Confluence Page Tests")
public class TestPageTests {
    private final TestPage testPage;
    private final LoginPage loginPage;

    public TestPageTests(WebDriver driver) {
        this.testPage = PageFactory.initElements(driver, TestPage.class);
        this.loginPage = PageFactory.initElements(driver, LoginPage.class);
    }

    @Test
    @DisplayName("Verify can login and loads wiki Test page")
    void loginAndLoadTestPage() {
        loginPage.navigateTo();
        loginPage.userLogin("szliaw@gmail.com", "Testing123");
        testPage.navigateTo();
    }

    @Test
    @DisplayName("Open and close Restrictions modal")
    void openAndCloseRestrictionsModal() {
        loginAndLoadTestPage();
        testPage.openRestrictionsModal();
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
    @DisplayName("Set page permissions to 'Anyone can view and edit'")
    void setPermissionsAnyoneCanView() {
        loginAndLoadTestPage();
        testPage.openRestrictionsModal();
        assertTrue(testPage.getInspectPermsButton().isDisplayed(), "Inspect permissions button displayed");
        testPage.selectRestrictionsOption("Anyone can view and edit");
        assertTrue(testPage.getRestrictionsIconUnlocked().isDisplayed(), "Restrictions icon should be unlocked");
    }

    @Test
    @DisplayName("Set page permissions to 'Anyone can view, only some can edit' and add a User")
    void setPermissionsOnlySomeCanEdit() {
        loginAndLoadTestPage();
        testPage.openRestrictionsModal();
        assertTrue(testPage.getInspectPermsButton().isDisplayed(), "Inspect permissions button displayed");
        testPage.selectRestrictionsOption("Anyone can view, only some can edit");
        assertTrue(testPage.getUserSearchField().isDisplayed(), "User search field should appear when Anyone can view option selected");
        testPage.addUserField();
        assertEquals(1, testPage.getUserRemoveButton().size(), "Verify user has been added if remove button visible");
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
    @DisplayName("Set page permissions to 'Only specific people can view or edit' and add a User")
    void setPermissionsOnlySpecificPeople() {
        loginAndLoadTestPage();
        testPage.openRestrictionsModal();
        assertTrue(testPage.getInspectPermsButton().isDisplayed(), "Inspect permissions button displayed");
        testPage.selectRestrictionsOption("Only specific people can view or edit");
        assertTrue(testPage.getUserSearchField().isDisplayed(), "User search field should appear when Anyone can view option selected");
        testPage.addUserField();
        assertEquals(1, testPage.getUserRemoveButton().size(), "Verify user has been added if remove button visible");
        testPage.clickRestrictionsModalApply();
        assertTrue(testPage.getRestrictionsIconLocked().isDisplayed(), "Restrictions icon should be locked");
        //remove user to keep test data clean
        testPage.openRestrictionsModal();
        testPage.removeUserInPanel();
        assertEquals(0, testPage.getUserRemoveButton().size(), "should be no Remove button as user removed");
        testPage.clickRestrictionsModalApply();
        assertTrue(testPage.getRestrictionsIconLocked().isDisplayed(), "Restrictions icon should be locked");
    }

}
