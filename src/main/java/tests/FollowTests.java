package tests;

import listeners.Utility;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageObjects.Profile.FollowPage;
import pageObjects.Profile.ProfilePage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


@Listeners(listeners.TestListener.class)
public class FollowTests extends BaseTest{
    private String areaToFollow="Sant Julià de Lòria";
//    private String userToFollow = "Gregory, Wagner";

    @BeforeMethod
    public void setUp() {
        super.setUpBrowser();
        String userName = "millie.murphy@yahoo.com";
        String password = "$aLamura234";
        feed = super.signIn(userName, password);

    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            Utility.captureScreenshot(eDriver, result.getName());

        }
        shutDown();
    }

    @Test(description = "Searh and follow area (City,Town) ")
    public void FollowAreaTest(){
        ProfilePage profile=feed.clickProfileImage();
        FollowPage follow=profile.selectFollowFromProfileMenu();
        if(follow.assertIsFollowed(areaToFollow)){
            follow.unfollow(areaToFollow);
        }
        follow=follow.searchToFollow(areaToFollow);
        follow.assertSuggestionAppear(areaToFollow);
        assertEquals(follow.getStatisticsFromSuggestion(areaToFollow, "ads"), follow.getStatisticsFromDB("area", areaToFollow, "ads"));
        follow=follow.followFromSuggestion(areaToFollow);
        assertTrue(follow.assertIsFollowed(areaToFollow), "Area is not in the list");
    }

    @Test(enabled = false)
    public void FollowUser(){
        ProfilePage profile=feed.clickProfileImage();
        FollowPage follow=profile.selectFollowFromProfileMenu();
        if(follow.assertIsFollowed(areaToFollow)){
            follow.unfollow(areaToFollow);
        }
        follow=follow.searchToFollow(areaToFollow);
        follow.assertSuggestionAppear(areaToFollow);
//        follow.assertStatisticsAboutItem(areaToFollow);
        follow=follow.followFromSuggestion(areaToFollow);
        assertTrue(follow.assertIsFollowed(areaToFollow),"Area is not in the Area list");
    }

    @Test(description = "Searh and follow another user ")
    public void FollowKeyword() {
        ProfilePage profile = feed.clickProfileImage();
        FollowPage follow = profile.selectFollowFromProfileMenu();
        String keywordToFollow = "iPhone 6s";
        if (follow.assertIsFollowed(keywordToFollow)) {
            follow.unfollow(keywordToFollow);
        }
        follow = follow.searchToFollow(keywordToFollow);
        follow.assertSuggestionAppear(keywordToFollow);
        assertEquals(follow.getStatisticsFromSuggestion(keywordToFollow, "ads"), follow.getStatisticsFromDB("keyword", keywordToFollow, "ads"));
        follow = follow.followFromSuggestion(keywordToFollow);
        assertTrue(follow.assertIsFollowed(keywordToFollow), "Keyword is not in the Followed list");
    }
}
