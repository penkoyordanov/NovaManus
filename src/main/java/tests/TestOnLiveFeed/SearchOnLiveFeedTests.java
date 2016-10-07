package tests.TestOnLiveFeed;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.*;

/**
 * Created by penko.yordanov on 10-Aug-16.
 */
public class SearchOnLiveFeedTests extends BaseTest {
    private String userName = "lscott1k@ftc.gov";
    private String password = "$aLamura234";

    @BeforeClass
    public void setUp() {
        super.setUpBrowser();
        feed = super.signIn(userName,password);
        feed.clickSearchOptionsBtn();
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
