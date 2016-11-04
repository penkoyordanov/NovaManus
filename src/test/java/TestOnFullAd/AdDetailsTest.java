package TestOnFullAd;

import Pages.FullAd.ViewAdPage;
import Pages.NewAdPage;
import Pages.Profile.ProfilePage;
import base.BaseTest;
import helpers.GetRandomFile;
import helpers.SQL.GetDataFromDB;
import helpers.TestDataFaker;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.*;

public class AdDetailsTest extends BaseTest {

    private String pointOfSaleDefault;
    private String fullName;
    private String epxpectedPublisherPhone;
    private static final String userName = "lscott1k@ftc.gov";
    private static final String password = "$aLamura234";
    private static final String currencyDefault = "€";
    private static final String keyword = "nulla ut";
    private static final Map<String, String> NewAdValues = TestDataFaker.getAdValues();

    @BeforeMethod
    public void setUp() {
        Map<String,String> userDetails=GetDataFromDB.getUserDetails(userName);
        this.fullName=userDetails.get("firstName")+"\n"+userDetails.get("lastName");
        this.pointOfSaleDefault=userDetails.get("address");
        this.epxpectedPublisherPhone=userDetails.get("phone");
        super.setUpBrowser();
        feed = super.signIn(userName, password);
        // Click to "Make New ad" button
    }

    // Next test method verifies entered test data matches information displayed
    // on View ad page
    @Test(description = "Verify details of advertisement", enabled = true)
    public void VerifyViewAdDetailsAreAsEnteredTest() {
        NewAdPage makeAd = feed.clickMakeNewAdButton();
        makeAd.selectCategory(NewAdValues.get("category"));
        makeAd.selectCondition(NewAdValues.get("condition"), NewAdValues.get("category"));
        makeAd.typeTitle(NewAdValues.get("title"));
        makeAd.typeDescription(NewAdValues.get("description"));
        makeAd.uploadPhoto(GetRandomFile.getRandomFileToUpload());

        //Storing source of uploaded image
        String uploadedImageSrc = makeAd.getBlobSourceUrl();

        makeAd.typeKeywords(keyword);
        makeAd.clickAddKeywordBtn();
        makeAd.typePrice(NewAdValues.get("price"), NewAdValues.get("category"));

        ProfilePage profile = makeAd.clickPublishBtn();

        ViewAdPage ad = profile.clickAdImage(NewAdValues.get("title"));

        assertTrue(ad.assertTitleFullAd(NewAdValues.get("title")), "Actual title is not as exected");

        assertEquals(ad.getDescription(), NewAdValues.get("description"), "Actual description is not as exected");

        assertTrue(ad.isCategoryDisplayed(NewAdValues.get("category"), currencyDefault, NewAdValues.get("price")), "Category or price not displayed");

        assertTrue(ad.isConditionDisplayed(NewAdValues.get("category"), NewAdValues.get("condition")), "Condition is not displayed");

        assertEquals(ad.getOwnerNames(), fullName, "Full name is not as exected");

        assertEquals(ad.getPointOfSale(), pointOfSaleDefault, "Address is not as exected");
        assertTrue(ad.isImageAttachedToAdvertise(uploadedImageSrc), "Uploaded image is not attached to the advertise");

        assertEquals(ad.getPhone(), "tel:"+epxpectedPublisherPhone, "Phone is not as exected");
        assertTrue(ad.isMapDisplayed(), "Map is not displayed");

        ad.assertKeywordsDisplayed(keyword);

        //Open Image Dialog
        ad = ad.clickAdImage();
        assertNotEquals(ad.getImgSrcFullAddDlg(), uploadedImageSrc, "Uploaded image is not the same as displayed in the full ad dialog");

    }


    @AfterMethod
    public void tearDown(ITestResult result) {

        shutDown();
    }

}
