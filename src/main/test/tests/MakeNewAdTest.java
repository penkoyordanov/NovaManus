package tests;

import helpers.TestDataFaker;
import listeners.Utility;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import helpers.GetRandomFile;
import Pages.NewAdPage;
import Pages.Profile.ProfilePage;
import Pages.FullAd.ViewAdPage;

import java.util.Map;

import static org.testng.Assert.*;

@Listeners(listeners.TestListener.class)
public class MakeNewAdTest extends BaseTest {
    private String fullName = "Lori Scott";

	private String title=null;
	private String description=null;
	private String category=null;
	private String condition=null;
	private String price=null;
	private String imageUrl=null;

	private ProfilePage profile=null;
	private ViewAdPage ad;
	private NewAdPage makeAd =null;
	private String currencyDefault = "€";
	// Next method generates test data for each test method
	@BeforeMethod
	public void setUp() {
		super.setUpBrowser();
		final Map<String, String> reviewFormInfo= TestDataFaker.getAdValues();
		this.title=reviewFormInfo.get("title");
		this.description=reviewFormInfo.get("description");
		this.category = reviewFormInfo.get("category");
		this.condition=reviewFormInfo.get("condition");
		this.price = reviewFormInfo.get("price");

		// Navigate to Sign In page of NovaManus and sign in with username and
		// password
        String password = "$aLamura234";
        String userName = "lscott1k@ftc.gov";
        feed = super.signIn(userName, password);
    }

	/*@Test
	public void publishAdMakeAnOffer() throws InterruptedException {
		this.category="Make an offer";
		makeAd = feed.clickMakeNewAdButton();

		// Enter generated test data in Make new ad form
		makeAd =makeAd.selectCategory(category);
		makeAd =makeAd.typeTitle(title);
		makeAd =makeAd.typeDescription(description);
		makeAd =makeAd.typeKeywords(keywords);
		makeAd =makeAd.clickAddKeywordBtn();


		// Assert that keyword is added to the advertisement
		makeAd.assertKeywordsDisplayed(keywords);
		makeAd.typePrice(price,category);
		makeAd.uploadPhoto(GetRandomFile.getRandomFileToUpload());
		profile = makeAd.clickPublishBtn();

		// Assert that tile on the first advertisement on the profile list is
		// the one created.
		assertEquals( profile.getTitleLastAdvertise(),title,"Title of last advertisement in the list is not as expected");

		// Assert that price is displayed for advertisement with category Sell
		// or Rent and category name is displayed for other categories
		assertTrue(profile.isCategoryDisplayed(category, currencyDefault, price),"Category is not displayed");
		ad=profile.clickAdImage(title);

	}*/

	// Next method is to test that user can publish advertisement
    @Test(description = "Publish new advertisement. New advertisemetn should appear on the feed and on profile as live ad")
    public void publishNewAd(){



		makeAd = feed.clickMakeNewAdButton();

		// Enter generated test data in Make new ad form
		makeAd =makeAd.selectCategory(category);
		makeAd =makeAd.selectCondition(condition,category);
		makeAd =makeAd.typeTitle(title);
		makeAd =makeAd.typeDescription(description);
		makeAd.uploadPhoto(GetRandomFile.getRandomFileToUpload());
        String keywords = "turpis,sed ante,vivamus,tortor";
        makeAd =makeAd.typeKeywords(keywords);
		makeAd =makeAd.clickAddKeywordBtn();

		// Assert that keyword is added to the advertisement
//		makeAd.assertKeywordsDisplayed(keyword);
		makeAd.typePrice(price,category);

		profile = makeAd.clickPublishBtn();

		// Assert that tile on the first advertisement on the profile list is
		// the one created.
		assertEquals( profile.getTitleLastAdvertise(),title,"Title of last advertisement in the list is not as expected");

		// Assert that price is displayed for advertisement with category Sell
		// or Rent and category name is displayed for other categories
		assertTrue(profile.isCategoryDisplayed(category, currencyDefault, price), "Category is not displayed. Actual: " + profile.getPriceOfLastAdvertise());
		feed = profile.clickToLiveFeedLink();

        //When new ad is published it should be on the top of the live feed page. Next block is to assert its details
        assertEquals(feed.getValueOfTopAdByAttributeAd("title"),title,"Title of first ad is not as expected");
//        assertEquals(feed.getPublisherOfTopAd(),fullName,"Names of the publisher of the top ad are not as expected");
        assertTrue(feed.assertTopAdImage(),"Image of the top ad is not displayed");
        assertEquals(feed.getValueOfTopAdByAttributeAd("distance"),"0.00 km","Distance of the top ad is not as expected");
        assertEquals(feed.getValueOfTopAdByAttributeAd("time"),"Just now","Time of publish of the first ad is not as expected");

	}

	// Next method is to test that user can save advertisement as Draft
    @Test(description = "Save advertisement as draft. New advertisemetn should appear in the Draft list under profile. Should not be available on Feed", alwaysRun = true)
    public void saveAdDraft() throws InterruptedException {
		makeAd = feed.clickMakeNewAdButton();
		
		makeAd = makeAd.selectCategory(category);
		makeAd = makeAd.selectCondition(condition,category);
		makeAd = makeAd.typeTitle(title);
		makeAd = makeAd.typeDescription(description);
		makeAd = makeAd.clickAddKeywordBtn();
		makeAd = makeAd.typePrice(price,category);
		profile = makeAd.clickSaveDraftBtn();
		

		// Verify draft advertisement is not displayed in Live ads list
//		assertFalse("Advertisment is published (not saved as draft)", profile.getTitleLastAdvertise().equals(title));
		assertNotEquals(profile.getTitleLastAdvertise(),title,"Advertisment is published (not saved as draft)");

		// Select Draft ads from Ads menu
		profile = profile.selectFromAdsMenu("Drafts");

		// Verify advertisement details on profile page (Title, description,
		// category/price)
		assertEquals(profile.getTitleLastAdvertise(),title,"Either advertisement is not saved as draft or not diplayed on the profile page");

		// Assert that price is displayed for advertisement with category Sell
		// or Rent and category name is displayed for other categories
		assertTrue(profile.isCategoryDisplayed(category, currencyDefault, price), "Category is not displayed" + profile.getPriceOfLastAdvertise());
	}

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            Utility.captureScreenshot(eDriver, result.getName());

        }

		shutDown();
	}

}
