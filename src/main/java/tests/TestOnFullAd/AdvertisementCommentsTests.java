package tests.TestOnFullAd;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjects.Common.Browser;
import pageObjects.FullAd.CommentsContainer;
import pageObjects.FullAd.ViewAdPage;
import tests.BaseTest;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by penko.yordanov on 12-May-16.
 */
public class AdvertisementCommentsTests  extends BaseTest {
    private ViewAdPage adPage;
    private int initialCommentsCount;
    private String userName = "lscott1k@ftc.gov";
    private String password = "$aLamura234";
    private CommentsContainer commentsContainer;
    @BeforeMethod
    public void setUp() {
        super.setUpBrowser();
        feed = super.signIn(userName,password);
        initialCommentsCount = feed.getCommentsCountFirstAd();
        adPage=feed.openFirstAd();
        commentsContainer=new CommentsContainer(Browser.driver());

    }

    @AfterMethod
    public void tearDown() {

        shutDown();
    }

    @Test
    public void postComment(){


        String comment=commentsContainer.generateComment();
        commentsContainer.postComment(comment);

        commentsContainer.assertCommentIsPosted(comment);

        assertEquals("Initial comments count:" + initialCommentsCount + ". Actual: " + commentsContainer.getCommentsStatus(), initialCommentsCount + 1, commentsContainer.getCommentsStatus());

    }

   /* @Test
    public void deleteComment(){
        initialCommentsCount=commentsContainer.getCommentsStatus();

        String comment=commentsContainer.generateComment();
        commentsContainer.postComment(comment);

        commentsContainer.assertCommentIsPosted(comment);
        commentsContainer.deleteComment(comment);
        commentsContainer.assertCommentIsDeleted(comment);

    }*/

}
