import Pages.Browser.Browser;
import Pages.Feed.FeedPage;
import Pages.FrontPage;
import Pages.LoginPage;
import Pages.Profile.ProfilePage;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginTests {
	private static LoginPage loginPage;
	private static EventFiringWebDriver eDriver;

    @BeforeMethod
    public static void setUpBrowse() {
//		Browser.initChrome();
		Browser.initChrome();
        eDriver = Browser.eDriver();
        FrontPage frontPage = new FrontPage(eDriver);
        loginPage = frontPage.clickSignInBtn();
	}

	/*// Try to sign in without providing username and password
	@Test//(priority = 0)
	public void attemptLogginWtihoutUsernamePass() throws InterruptedException {
		loginPage.typeUsername("");
		loginPage.typePassword("");
		loginPage.submitSignInExpectingFailure();
		assertTrue(loginPage.assertPresenceValidationMessege(),"Validation message is not displayed");
	}

	// Try to sign in with wrong username and password
	@Test//(priority = 1)
	public void attemptLogginWrongUsernamePass() throws InterruptedException {
		loginPage.typeUsername("tralala@abv.bg");
		loginPage.typePassword("123");
		loginPage.submitSignInExpectingFailure();
		assertTrue(loginPage.assertPresenceValidationMessege(),"Validation message is not displayed");
	}*/

	// Successful Sign In
	@Test//(priority = 2)
	public void successfullSignIn() {
        String username = "test@novamanus.com";
        loginPage.typeUsername(username);
        String password = "123456";
        loginPage.typePassword(password);
        FeedPage liveFeed = loginPage.submitSignIn();
       /* try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        ProfilePage profile=liveFeed.clickProfileImage();
        String lastNameExpected = "Wagner";
        String firstNameExpected = "Gregory";
        profile.assertProfileNames(firstNameExpected, lastNameExpected);

        //Assert SessionStorage
        JavascriptExecutor jsExecutor = eDriver;
        String userJSON = (String) jsExecutor
                .executeScript("return sessionStorage.user;");
        assertEquals(lastNameExpected, loginPage.getAttributeValueJSON(userJSON,"lastName"));
        assertEquals(firstNameExpected, loginPage.getAttributeValueJSON(userJSON,"firstName"));
        assertEquals(username, loginPage.getAttributeValueJSON(userJSON,"email"));
        String contactID = "1";
        assertEquals(contactID, loginPage.getAttributeValueJSON(userJSON,"contactId"));
        String userID = "1";
        assertEquals(userID, loginPage.getAttributeValueJSON(userJSON,"id"));
        String phone = "+35912345678";
        assertEquals(phone, loginPage.getAttributeValueJSON(userJSON,"phone"));

	}

	/*@AfterMethod
    public void shutDown() {
		Browser.eDriver().close();
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
		}
	}*/

}
