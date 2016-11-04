package base;

import org.openqa.selenium.support.events.EventFiringWebDriver;
import Pages.Browser.Browser;
import Pages.FrontPage;
import Pages.Feed.FeedPage;
import Pages.LoginPage;

public class BaseTest {


	public LoginPage loginPage;
	public FeedPage feed;
	public EventFiringWebDriver eDriver;

	protected void setUpBrowser() {
		Browser.initChrome();
		this.eDriver = Browser.eDriver();
	}

	protected FeedPage signIn(String userName, String password) {
        FrontPage frontPage = new FrontPage(eDriver);
        loginPage = frontPage.clickSignInBtn();
		loginPage=loginPage.typeUsername(userName);
		loginPage=loginPage.typePassword(password);
		 return feed= loginPage.submitSignIn();
	}

	protected void shutDown() {
		eDriver.quit();
	}

}
