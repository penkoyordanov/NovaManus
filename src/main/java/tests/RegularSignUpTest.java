package tests;

import helpers.TestDataFaker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import pageObjects.*;
import pageObjects.Common.Browser;
import pageObjects.LiveFeed.LiveFeedPage;
import pageObjects.Profile.ProfilePage;
import pageObjects.Signup.SignUpPage;
import pageObjects.Signup.SignUpStep2Page;

import java.util.Map;

public class RegularSignUpTest {
	private String password = "$aLamura234";
	// private String language = null;
	SignUpPage sup;
//	GetTestData testData;

	LoginPage loginPage;
	WebDriver driver;
	WebDriverWait wait;
	EventFiringWebDriver eDriver;

	/*@BeforeTest
	public static void setUpBrowse() {
		Browser.initChrome();
	}*/

	@BeforeMethod
	public void setUp () throws InterruptedException {
		Browser.initChrome();
		this.eDriver = Browser.driver();
		loginPage = new LoginPage(eDriver);
	}

	@Test
	public void signUPStep1() throws InterruptedException {
		final Map<String, String> registerFormValues= TestDataFaker.getReistrationFormValues();
		FrontPage frontPage=new FrontPage(eDriver);

		SignUpPage sup = frontPage.clickSignUpBtn();
		sup=sup.setFirstName_field(registerFormValues.get("firstName"));
		sup=sup.setLastName_field(registerFormValues.get("lastName"));
		sup=sup.setBirth(registerFormValues.get("birth"));
		sup=sup.setGender(registerFormValues.get("gender"));
		sup=sup.setPhone_field("+" + registerFormValues.get("phone"));
		sup=sup.setEmail_field(registerFormValues.get("email"));
		sup=sup.setAddress_field(registerFormValues.get("address"));

		sup=sup.setPassword_field(password);
		sup=sup.setConfirmPassword_field(password);
		sup=sup.acceptTermsAndConditions();
		sup=sup.clickNextBtn();
		Thread.sleep(5000);

		SignUpStep2Page step2=sup.applyConfirmationCode(registerFormValues.get("email"));
//		step2.assertNamesDisplayed(firstName,lastName);
		step2.assertCityToFollowIsThere("Pernik");
		step2=step2.followSuggested("Pernik");
//		step2=step2.followSuggested("Gregory");
		LiveFeedPage feed=step2.clickLetsGoBtn();
		ProfilePage profile=feed.clickProfileImage();
		profile.assertProfileNames(registerFormValues.get("firstName"),registerFormValues.get("lastName"));
		/*FollowPage follow=profile.selectFollowFromProfileMenu();
		follow.assertIsFollowed("Sofia");
		follow.assertIsFollowed("Pernik");
		follow.assertIsFollowed("Gregory");*/




		/*sup.clickNextBtn();

		Thread.sleep(5000);
		ManageUsedEmails.getLatestRegUser(this.email);
		ManageUsedEmails.printRecords();*/
		/*// Assert.assertTrue(sup.isTermsAndConditionsAccepted());
		assertTrue(this.firstName.equals(ManageUsedEmails.latestRegUser[0]),
				"First name of last registered user in DB does not match First name from Test case");
		assertTrue(this.lastName.equals(ManageUsedEmails.latestRegUser[1]),
				"First name of last registered user in DB does not match Last name from Test case");
		assertTrue(this.email.equals(ManageUsedEmails.latestRegUser[3]),
				"Email of last registered user in DB does not match Email name from Test case");

		assertTrue(this.gender.equals(ManageUsedEmails.latestRegUser[5]),
				"Gender does not match record for the last registered user in DB");

		assertTrue(this.phone.equals(ManageUsedEmails.latestRegUser[5]),
				"Phone does not match record for the last registered user in DB");*/

	}

	@AfterMethod
	public void tearDown(ITestResult result) {

		eDriver.close();
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
		}finally {
			eDriver.quit();
		}
	}
	}
