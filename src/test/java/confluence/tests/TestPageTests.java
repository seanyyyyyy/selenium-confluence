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
        assertTrue(testPage.getInspectPermsButton().isDisplayed(), "Inspect permissions button displayed");
        testPage.selectRestrictionsOption("Anyone can view and edit");
        //check icon is correct for selection

    }

}
