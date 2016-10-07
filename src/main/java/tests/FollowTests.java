package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjects.Profile.FollowPage;
import pageObjects.Profile.ProfilePage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by penko.yordanov on 03-Jun-16.
 */
public class FollowTests extends BaseTest{
    private String userName = "millie.murphy@yahoo.com";
    private String password = "$aLamura234";
    private String areaToFollow="Sant Julià de Lòria";
    private String userToFollow = "Gregory, Wagner";
    private String keywordToFollow = "iPhone 6s";

    @BeforeMethod
    public void setUp() {
        super.setUpBrowser();
        feed = super.signIn(userName,password);

    }

    @AfterMethod
    public void tearDown() {

        shutDown();
    }

    @Test
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

   /* @Test
    public void FollowUser(){
        ProfilePage profile=feed.clickProfileImage();
        FollowPage follow=profile.selectFollowFromProfileMenu();
        if(follow.assertIsFollowed(areaToFollow)){
            follow.unfollow(areaToFollow);
        }
        follow=follow.searchToFollow(areaToFollow);
        follow.assertSuggestionAppear(areaToFollow);
        follow.assertStatisticsAboutItem(areaToFollow);
        follow=follow.followFromSuggestion(areaToFollow);
        assertTrue(follow.assertIsFollowed(areaToFollow),"Area is not in the Area list");
    }*/

    @Test
    public void FollowKeyword() {
        ProfilePage profile = feed.clickProfileImage();
        FollowPage follow = profile.selectFollowFromProfileMenu();
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
