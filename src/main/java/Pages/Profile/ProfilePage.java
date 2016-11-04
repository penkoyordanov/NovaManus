package Pages.Profile;

import java.util.List;

import org.openqa.selenium.By;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import Pages.Common.Base;
import Pages.FullAd.ViewAdPage;
import Pages.Feed.FeedPage;
import Pages.NewAdPage;

public class ProfilePage extends Base {

	private By newAdButton = By.xpath("//div/a[@class='fa fa-plus header-icon']");

	private By profilePicture = By.xpath("//a[@href='/l/en/profile']");

	private By liveFeedLnk = By.linkText("Feed");

	private By AdsMenu = By.xpath("//ads-profile/div/div");

	private By searchField=By.id("search");

	private By firstAdPrice = By.xpath("//ad-tile[1]/article/div/h4/span");

	public ProfilePage(EventFiringWebDriver eDriver) {
		super(eDriver);
		assertTrue(isDisplayed(By.xpath("//div[@class='mp-name']"), 10),"Profile page is not loaded");

	}

	public NewAdPage clickMakeNewAdButton() {
		click(newAdButton);
		return new NewAdPage(eDriver);
	}

	public ProfilePage clickProfileImage() {
		click(profilePicture);
		return this;
	}

	public ProfilePage soldFirstAdvertise() {
		try {
			if (isSold()) {
				selectFromAdvertisementContextMenu("Mark as unsold");

			}else {
				selectFromAdvertisementContextMenu("Mark as sold");

				assertTrue(isDisplayed(By.xpath("//ad-tile[1]/article/figure/div"), 10));
			}

		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println(e);
		}

		return this;
	}
	
	public Boolean isSold(){
		try {
			return isDisplayed(By.xpath("//ad-tile[1]/article/figure/div"),2);
		} catch (Exception e) {
			return false;
		}
		
	}
	

	private ProfilePage selectFromAdvertisementContextMenu(String item){
		click(By.xpath("//ad-tile[1]/article/div/div"));
		WebElement select = find(By.xpath("//ad-tile[1]/article/div/div"));
		List<WebElement> options = select.findElements(By.tagName("li"));
		for (WebElement option : options) {
			if (item.equals(option.getText().trim())){
				option.click();
				break;
			}


		}
		isDisplayed(By.xpath("//ad-tile[1]/article/div/div[@class='dropdown dropdown-blue']"),10);
		return this;
	}

	
	public ProfilePage clickdeleteForTopAdvertise() {
		selectFromAdvertisementContextMenu("Remove advertise");
		isDisplayed(By.xpath("//div[@id='saveDraftDlg']//h3[contains(.,'Sure you want to remove')]"), 10);
		return this;
	}

	public void assertConfirmationOnRemove(String confirmation){
		assertEquals(getTextOfElement(By.xpath("//div[@id='saveDraftDlg']/descendant::p")), confirmation, "Find removed ads in your history.");
	}

	public ProfilePage confirmDelete() {
		click(By.xpath("//div[@id='saveDraftDlg']/div/div[1]/div[2]/div/button[contains(.,'Yes')]"));
//		waitUntilElementDisappear();
		waitUntilElementDisappear(By.xpath("//div[@id='saveDraftDlg'][@class='modal fade in']/div/div[1]/div[2]/div/button[contains(.,'Yes')]"),10);
		return this;
	}


	public FeedPage clickToLiveFeedLink() {
		eDriver.executeScript("scroll(0,0)");
		click(liveFeedLnk);
		assertTrue(isDisplayed(By.xpath("//ad-tile/descendant::h3"), 10),"Ads not displayed on the Live feed page");
		return new FeedPage(eDriver);
	}

	public ProfilePage selectFromAdsMenu(String menuItem) {
		isDisplayed(By.xpath("//ads-profile//button[text()='My ads ']/parent::div"), 10);
		click(By.xpath("//ads-profile//button[text()='My ads ']/parent::div"));
		WebElement select = find(By.xpath("//ads-profile//button[text()='My ads ']/parent::div"));
		List<WebElement> options = select.findElements(By.tagName("li"));
		for (WebElement option : options) {
			if (menuItem.equals(option.getText().trim()))
				option.click();
		}
		isDisplayed(By.xpath("//ad-tile[1]/descendant::div[@class='lf-item-title']/h3"), 10);
		return this;
	}

	public FollowSettingsPage selectFollowFromProfileMenu() {
		isDisplayed(By.xpath("//div[@class='dropdown my-profile-menu active']/button/span[@class='fa fa-angle-down']"),10);
		click(By.xpath("//div[@class='dropdown my-profile-menu active']"));
		isDisplayed(By.xpath("//div[@class='dropdown my-profile-menu active open']"),10);
		WebElement select = find(By.xpath("//div[@class='dropdown my-profile-menu active open']"));
		List<WebElement> options = select.findElements(By.tagName("li"));
		for (WebElement option : options) {
			if ("Follow settings".equals(option.getText().trim()))
				option.click();
			break;
		}
		return new FollowSettingsPage(eDriver);
	}

	public ViewAdPage clickAdImage(String title) {
		isDisplayed(By.xpath("//ad-tile[1]/descendant::img"),10);
		click(By.xpath("//ad-tile[1]//app-image"));
		return new ViewAdPage(eDriver);
	}

	public String getPriceOfLastAdvertise() {
		return getTextOfElement(By.xpath("//ad-tile[1]/descendant::h4/span"));
	}

	public String getTitleLastAdvertise() {
		isDisplayed(By.xpath("//ad-tile[1]/descendant::*[@class='lf-item-title']"),10);
		return getTextOfElement(By.xpath("//ad-tile[1]/descendant::*[@class='lf-item-title']"));
	}
	
	public Boolean assertRemovedAdisInOldAdsList(String ad){
		Boolean isAdAvailable=false;
		while (!isAdAvailable){
			List<WebElement> adTitles=findElements(By.xpath("//ad-tile/article/div/div/h3"));
			for (WebElement title : adTitles) {
				if(ad.equals(title.getText().trim())){
					isAdAvailable=true;
					break;
				}

			}
			String js = "setTimeout(function(){window.scrollTo(0,document.body.scrollHeight)}, 2000);";
			eDriver.executeScript(js);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return isAdAvailable;
	}

	public void assertProfileNames(String firstName, String lastName){
		assertTrue(isDisplayed(By.xpath("//div[@class='mp-name']/h1[contains(.,'"+firstName+"')]/span[contains(.,'"+lastName+"')]"),10),"Profile names are not displayed");
	}


	public boolean isCategoryDisplayed(String category, String currency, String price) {
		boolean isCategoryDisplayed = false;
		System.out.println(getTextOfElement(firstAdPrice));
		if (category.trim().equals("Sell") || category.trim().equals("Rent")) {

			if (getTextOfElement(firstAdPrice).trim().replaceAll("(,)|( )", "").equals(currency + price)) {

				isCategoryDisplayed = true;
			}
		} else if (category.trim().equals("Give away")) {
			if (getTextOfElement(firstAdPrice).trim()
					.equals("Free")) {
				isCategoryDisplayed = true;
			}
		}else if (category.trim().equals("Make an offer")) {
			if (getTextOfElement(firstAdPrice).trim().equals("Offer")) {
				isCategoryDisplayed = true;
			}

		}else {
			if (getTextOfElement(firstAdPrice).trim()
					.equals(category)) {
				isCategoryDisplayed = true;
			}
		}
		return isCategoryDisplayed;
	}

}
