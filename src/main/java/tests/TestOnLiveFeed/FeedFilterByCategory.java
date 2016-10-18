package tests.TestOnLiveFeed;

import listeners.Utility;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pageObjects.Common.Browser;
import pageObjects.Feed.FilterContainer;
import tests.BaseTest;

/**
 * Created by penko.yordanov on 03-Oct-16.
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
        filterContainer = new FilterContainer(Browser.driver());
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
        Assert.assertTrue(feed.assertCategory(category));
    }

    @AfterClass
    public void tearDown(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            Utility.captureScreenshot(eDriver, result.getName());

        }
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
