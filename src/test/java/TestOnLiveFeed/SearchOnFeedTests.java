package TestOnLiveFeed;

import base.BaseTest;
import org.testng.annotations.*;

import static org.testng.Assert.*;

/**
 * Search on Feed tests
 */
@Listeners(listeners.TestListener.class)
public class SearchOnFeedTests extends BaseTest {

    @BeforeClass
    public void setUp() {
        super.setUpBrowser();
        String password = "$aLamura234";
        String userName = "lscott1k@ftc.gov";
        feed = super.signIn(userName, password);
        feed.clickSearchOptionsBtn();
    }

    @AfterClass
    public void tearDown() {
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

        return new Object[][] { { "Norway", "Oslo" }, { "United Kingdom", "London" },{ "Belgium", "Leuven" }};

    }
}
