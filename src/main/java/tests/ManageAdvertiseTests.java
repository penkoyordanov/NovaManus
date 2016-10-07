package tests;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertNotEquals;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjects.NewAdPage;
import pageObjects.Profile.ProfilePage;
import pageObjects.FullAd.ViewAdPage;

public class ManageAdvertiseTests extends BaseTest{
	private String adTitle = null;
	private String userName = "lscott1k@ftc.gov";
	private String password = "$aLamura234";
	private ProfilePage profile=null;
	private ViewAdPage ad;
	private NewAdPage makeAd =null;

	@BeforeMethod
	public void SetUp()  {
		super.setUpBrowser();
		feed = super.signIn(userName,password);
	}

	@Test
	public void MarkAdvertiseAsSold(){
		profile = feed.clickProfileImage();

		// Store title of last advertise in title field
		adTitle = profile.getTitleLastAdvertise();

		// Store price of last advertise in price field
		profile = profile.soldFirstAdvertise();

		assertTrue(profile.isSold(),"Sold overlay not displayed");


		// Verify that Sold advertise is shown with Sold overlay on Feed page

		feed = profile.clickToLiveFeedLink();
		feed.scrollWhileFindAd(adTitle);
		assertTrue(feed.isAdvertiseSold(adTitle),"Sold ad is not shown on the Live Feed page");

	}

	@Test
	public void RemoveLiveAd() {
		profile = feed.clickProfileImage();
		adTitle = profile.getTitleLastAdvertise();
		
		System.out.println("Ad wich is going to be deleted: "+profile.getTitleLastAdvertise());
		profile.clickdeleteForTopAdvertise();
		profile.assertConfirmationOnRemove("You can find it in your previous advertisements.");
		profile.confirmDelete();

		assertNotEquals(adTitle,profile.getTitleLastAdvertise(),"Advertisement is not deleted from Live ads");

		// Select "Previous ads" from "Ads" menu and verify removed
		// advertisement is available in Previos ads
		// Thread.sleep(2000);
		
		profile.selectFromAdsMenu("History");
		assertTrue(profile.assertRemovedAdisInOldAdsList(adTitle), "Deleted advertisement is not in previous ads");
	}

	/*@Test
	public void DeleteDraftAd() throws InterruptedException {
		profile = feed.clickProfileImage();
		profile = profile.selectSavedDraftsFromAdsMenu();
		title = profile.getTitleLastAdvertise();
		profile.clickdeleteForTopAdvertise();
		assertTrue(driver.findElement(By.xpath("//div[@id='saveDraftDlg']")).getAttribute("style")
				.equals("display: block; padding-right: 17px;"));
		profile.confirmDelete();
		assertNotEquals(title,
				driver.findElement(By.xpath("//simple-ad[1]/article/div[@class='mp-item-body']/a/h2")).getText(),
				"Advertisement is not deleted from Drafts ads");

	}

	@Test
	public void DeletePreviousAd() throws InterruptedException {
		profile = feed.clickProfileImage();
		profile = profile.selectPreviousFromAdsMenu();
		title = profile.getTitleLastAdvertise();
		profile.clickdeleteForTopAdvertise();
		assertTrue(driver.findElement(By.xpath("//div[@id='saveDraftDlg']")).getAttribute("style")
				.equals("display: block; padding-right: 17px;"), "Confirmation message is not displayed");
		profile.confirmDelete();
		assertNotEquals(title,
				driver.findElement(By.xpath("//simple-ad[1]/article/div[@class='mp-item-body']/a/h2")).getText(),
				"Advertisement is not deleted from Previous ads");

	}*/

	@AfterMethod
	public void tearDown(ITestResult result) {

		super.shutDown();
	}
}
