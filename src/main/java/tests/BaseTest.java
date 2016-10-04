package tests;

import org.openqa.selenium.support.events.EventFiringWebDriver;
import pageObjects.Common.Browser;
import pageObjects.FrontPage;
import pageObjects.LiveFeed.LiveFeedPage;
import pageObjects.LoginPage;

public class BaseTest {


	LoginPage loginPage;
	public LiveFeedPage feed;
	FrontPage frontPage;
	public EventFiringWebDriver eDriver;

	public void setUpBrowser(){
		Browser.initChrome();
//		Browser.initPhantomJs();
		this.eDriver = Browser.driver();
	}

	public LiveFeedPage signIn(String userName, String password){
		frontPage=new FrontPage(eDriver);
		loginPage = frontPage.clickSignInBtn();
		loginPage=loginPage.typeUsername(userName);
		loginPage=loginPage.typePassword(password);
		 return feed= loginPage.submitSignIn();
	}
	
	public void shutDown() {
		eDriver.close();
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
		}finally {
			eDriver.quit();
		}
	}

}
