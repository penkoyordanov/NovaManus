package tests;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjects.Profile.FollowPage;
import pageObjects.Profile.ProfilePage;

import static org.testng.Assert.assertTrue;

/**
 * Created by penko.yordanov on 03-Jun-16.
 */
public class FollowTests extends BaseTest{
    private String userName = "millie.murphy@yahoo.com";
    private String password = "$aLamura234";
    private String areaToFollow="Sant Julià de Lòria";

    @BeforeMethod
    public void setUp() {
        super.setUpBrowser();
        feed = super.signIn(userName,password);

    }

    @AfterMethod
    public void tearDown(ITestResult result) {

        shutDown();
    }

    @Test
    public void FollowAreaTest(){
        ProfilePage profile=feed.clickProfileImage();
        FollowPage follow=profile.selectFollowFromProfileMenu();
        if(follow.assertIsFollowed(areaToFollow)){
            follow.unfollowArea(areaToFollow);
        }
        follow=follow.searchToFollow(areaToFollow);
        follow.assertSuggestionAppear(areaToFollow);
        follow.assertStatisticsAboutArea(areaToFollow);
        follow=follow.followFromSuggestion(areaToFollow);
        assertTrue(follow.assertIsFollowed(areaToFollow),"Area is not in the Area list");
    }
}
