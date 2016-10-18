package tests.TestOnLiveFeed;

import org.testng.Assert;
import org.testng.annotations.*;
import Pages.Common.Browser;
import Pages.Feed.FilterContainer;
import tests.BaseTest;

/**
 * Tests to verify filter feed by categories
 */
@Listeners(listeners.TestListener.class)
public class FeedFilterByCategory extends BaseTest {
    private FilterContainer filterContainer;

    @BeforeClass
    public void setUp() {
        super.setUpBrowser();
        String userName = "lscott1k@ftc.gov";
        String password = "$aLamura234";
        feed = super.signIn(userName, password);
        filterContainer = new FilterContainer(Browser.eDriver());
    }

    @Test(dataProvider = "getData", description = "Filter Feed by category")
    public void FilterAdsByCategoryInfoBoard(String infoCategory, String category) {
        filterContainer.openFilterMenu();
        filterContainer.selectFilterOption(infoCategory);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        filterContainer.selectFilterOption(category);

        filterContainer.closeFilterMenu(category);
        Assert.assertTrue(feed.assertFeedIsFilteredByCategory(category));
    }

    @AfterClass
    public void tearDown() {
        shutDown();
    }

    @DataProvider
    public Object[][] getData() {
        //Rows - Number of times your test has to be repeated.
        //Columns - Number of parameters in test data.
        Object[][] data = new Object[4][2];

        // 1st row
        data[0][0] = "Info board";
        data[0][1] = "Info board";

        // 2nd row
        data[1][0] = "Info board";
        data[1][1] = "Wanted";

        // 3nd row
        data[2][0] = "Info board";
        data[2][1] = "Free";

        // 2nd row
        data[3][0] = "Info board";
        data[3][1] = "Info board";


        return data;
    }
}
