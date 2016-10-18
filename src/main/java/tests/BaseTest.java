package tests;

import org.openqa.selenium.support.events.EventFiringWebDriver;
import pageObjects.Common.Browser;
import pageObjects.FrontPage;
import pageObjects.Feed.FeedPage;
import pageObjects.LoginPage;

public class BaseTest {


	LoginPage loginPage;
	public FeedPage feed;
	public EventFiringWebDriver eDriver;

	protected void setUpBrowser() {
		Browser.initChrome();
		this.eDriver = Browser.driver();
	}

	protected FeedPage signIn(String userName, String password) {
        FrontPage frontPage = new FrontPage(eDriver);
        loginPage = frontPage.clickSignInBtn();
		loginPage=loginPage.typeUsername(userName);
		loginPage=loginPage.typePassword(password);
		 return feed= loginPage.submitSignIn();
	}

	protected void shutDown() {
		eDriver.close();
		try {
			Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e);
		}finally {
			eDriver.quit();
		}
	}

}
