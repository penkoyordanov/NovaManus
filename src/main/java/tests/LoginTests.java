package tests;

import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.Common.Browser;
import pageObjects.FrontPage;
import pageObjects.Feed.FeedPage;
import pageObjects.LoginPage;
import pageObjects.Profile.ProfilePage;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginTests {
	private static LoginPage loginPage;
	private static EventFiringWebDriver eDriver;
    private static String username="test@novamanus.com";
    private static String password="123456";
    private static String firstNameExpected="Gregory";
    private static String lastNameExpected="Wagner";
    private static String phone="+35912345678";
    private static String contactID="1";
    private static String userID="1";

	@BeforeMethod
	public static void setUpBrowse() {
//		Browser.initChrome();
		Browser.initChrome();
		eDriver = Browser.driver();
		FrontPage frontPage=new FrontPage(eDriver);
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
		loginPage.typeUsername(username);
		loginPage.typePassword(password);
        FeedPage liveFeed = loginPage.submitSignIn();
       /* try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        ProfilePage profile=liveFeed.clickProfileImage();
		profile.assertProfileNames(firstNameExpected,lastNameExpected);

        //Assert SessionStorage
        JavascriptExecutor jsExecutor = eDriver;
        String userJSON = (String) jsExecutor
                .executeScript("return sessionStorage.user;");
        assertEquals(lastNameExpected, loginPage.getAttributeValueJSON(userJSON,"lastName"));
        assertEquals(firstNameExpected, loginPage.getAttributeValueJSON(userJSON,"firstName"));
        assertEquals(username, loginPage.getAttributeValueJSON(userJSON,"email"));
        assertEquals(contactID, loginPage.getAttributeValueJSON(userJSON,"contactId"));
        assertEquals(userID, loginPage.getAttributeValueJSON(userJSON,"id"));
        assertEquals(phone, loginPage.getAttributeValueJSON(userJSON,"phone"));

	}

	/*@AfterMethod
	public void shutDown() {
		Browser.driver().close();
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
		}
	}*/

}
