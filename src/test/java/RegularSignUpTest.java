import Pages.Browser.Browser;
import Pages.Feed.FeedPage;
import Pages.FrontPage;
import Pages.LoginPage;
import Pages.Profile.ProfilePage;
import Pages.Signup.SignUpPage;
import Pages.Signup.SignUpStep2Page;
import helpers.TestDataFaker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

public class RegularSignUpTest {
    // private String language = null;
    SignUpPage sup;
//	GetTestData testData;

    WebDriver driver;
    WebDriverWait wait;
	private EventFiringWebDriver eDriver;

	/*@BeforeTest
	public static void setUpBrowse() {
		Browser.initChrome();
	}*/

	@BeforeMethod
	public void setUp () throws InterruptedException {
		Browser.initChrome();
		this.eDriver = Browser.eDriver();
		LoginPage loginPage = new LoginPage(eDriver);
    }

    @Test(enabled = false)
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

        String password = "$aLamura234";
        sup = sup.setPassword_field(password);
        sup=sup.setConfirmPassword_field(password);
		sup=sup.acceptTermsAndConditions();
		sup=sup.clickNextBtn();
		Thread.sleep(5000);

		SignUpStep2Page step2=sup.applyConfirmationCode(registerFormValues.get("email"));
//		step2.assertNamesDisplayed(firstName,lastName);
		step2.assertCityToFollowIsThere("Pernik");
		step2=step2.followSuggested("Pernik");
//		step2=step2.followSuggested("Gregory");
		FeedPage feed = step2.clickLetsGoBtn();
		ProfilePage profile = feed.clickProfileImage();
		profile.assertProfileNames(registerFormValues.get("firstName"),registerFormValues.get("lastName"));
		/*FollowSettingsPage follow=profile.selectFollowFromProfileMenu();
		follow.assertIsFollowed("Sofia");
		follow.assertIsFollowed("Pernik");
		follow.assertIsFollowed("Gregory");*/


	}

	@AfterMethod
	public void tearDown(ITestResult result) {

		eDriver.close();
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			System.out.println(e);
		}finally {
			eDriver.quit();
		}
	}
	}
