package tests.TestOnLiveFeed;

import listeners.Utility;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import tests.BaseTest;

import static org.testng.Assert.*;

/**
 * Created by penko.yordanov on 10-Aug-16.
 */
@Listeners(listeners.TestListener.class)
public class SearchOnLiveFeedTests extends BaseTest {

    @BeforeClass
    public void setUp() {
        super.setUpBrowser();
        String password = "$aLamura234";
        String userName = "lscott1k@ftc.gov";
        feed = super.signIn(userName, password);
        feed.clickSearchOptionsBtn();
    }

    @AfterClass
    public void tearDown(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            Utility.captureScreenshot(eDriver, result.getName());

        }
        shutDown();
    }

    @Test(dataProvider = "SearchCriteria")
    public void advancedSearch(String country,String city){

        feed.selectCountry(country);
        feed.selectCity(city);
        assertTrue(feed.assertCityAppearedSearchField(city, country), "City is not as expected" + city);
        feed.clickSearchBtn();

//        Assert.assertEquals(feed.getValueOfTopAdByAttributeAd("distance"),city);

    }

    @DataProvider(name = "SearchCriteria")

    public static Object[][] searchCriterias() {

        return new Object[][] { { "Norway", "Oslo" }, { "Netherlands", "Strijensas" },{ "Belgium", "Langemark-Poelkapelle" }};

    }
}
