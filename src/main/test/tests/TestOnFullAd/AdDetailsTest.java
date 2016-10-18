package tests.TestOnFullAd;

import helpers.GetRandomFile;
import helpers.TestDataFaker;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;


import Pages.NewAdPage;
import Pages.Profile.ProfilePage;
import Pages.FullAd.ViewAdPage;
import tests.BaseTest;

import java.util.Map;

public class AdDetailsTest extends BaseTest {

    private static final String pointOfSaleDefault = "Camí Pui d'Olivesa, AD600 Sant Julià de Lòria, Andorra";
    private static final String fullName = "Lori\nScott";
    private static final String userName = "lscott1k@ftc.gov";
    private static final String password = "$aLamura234";
    private static final String currencyDefault = "€";
    private static final String keyword = "nulla ut";
    private static final Map<String, String> NewAdValues = TestDataFaker.getAdValues();

    @BeforeMethod
    public void setUp() {
        super.setUpBrowser();
        feed = super.signIn(userName, password);
        // Click to "Make New ad" button
    }

    // Next test method verifies entered test data matches information displayed
    // on View ad page
    @Test(description = "Verify details of advertisement", enabled = false)
    public void VerifyViewAdDetailsAreAsEntered() {
        NewAdPage makeAd = feed.clickMakeNewAdButton();
        makeAd = makeAd.selectCategory(NewAdValues.get("category"));
        makeAd = makeAd.selectCondition(NewAdValues.get("condition"), NewAdValues.get("category"));
        makeAd = makeAd.typeTitle(NewAdValues.get("title"));
        makeAd = makeAd.typeDescription(NewAdValues.get("description"));
        makeAd.uploadPhoto(GetRandomFile.getRandomFileToUpload());

        //Storing source of uploaded image
        String uploadedImageSrc = makeAd.getBlobSourceUrl();

        makeAd = makeAd.typeKeywords(keyword);
        makeAd = makeAd.clickAddKeywordBtn();
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

        assertEquals(ad.getEmail(), "mailto:backupemail@novamanus.com", "Email is not as exected");

        //assertEquals(ad.getPhone(), "tel:+359123456781", "Phone is not as exected");

        ad.assertKeywordsDisplayed(keyword);

        //Open Image Dialog
        ad = ad.clickAdImage();
        assertEquals(ad.getImgSrcFullAddDlg(), uploadedImageSrc, "Uploaded image is not the same as displayed in the full ad dialog");

    }


    @AfterMethod
    public void tearDown(ITestResult result) {

        shutDown();
    }

}
