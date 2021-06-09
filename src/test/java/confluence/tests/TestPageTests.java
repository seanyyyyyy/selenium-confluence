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

    //@BeforeAll
    //need to reset the page's permission state to Anyone can view

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
    @DisplayName("Set page permissions to 'Anyone can view, only some can edit'")
    void setPermissionsOnlySomeCanEdit() {
        loginAndLoadTestPage();
        testPage.openRestrictionsModal();
        assertTrue(testPage.getInspectPermsButton().isDisplayed(), "Inspect permissions button displayed");
        testPage.selectRestrictionsOption("Anyone can view, only some can edit");
        //TODO select specific users
        assertTrue(testPage.getRestrictionsIconUnlocked().isDisplayed(), "Restrictions icon should be unlocked");
    }

    @Test
    @DisplayName("Set page permissions to 'Only specific people can view or edit'")
    void setPermissionsOnlySpecificPeople() {
        loginAndLoadTestPage();
        testPage.openRestrictionsModal();
        assertTrue(testPage.getInspectPermsButton().isDisplayed(), "Inspect permissions button displayed");
        testPage.selectRestrictionsOption("Only specific people can view or edit");
        //TODO select specific users
        assertTrue(testPage.getRestrictionsIconLocked().isDisplayed(), "Restrictions icon should be locked");
    }

}
