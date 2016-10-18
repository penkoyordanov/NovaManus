package tests.TestOnFullAd;

import listeners.Utility;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import Pages.Common.Browser;
import Pages.FullAd.CommentsContainer;
import Pages.FullAd.ViewAdPage;
import tests.BaseTest;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Tests for CRUD comments on advertisement
 */
@Listeners(listeners.TestListener.class)
public class CRD_Comments extends BaseTest {
    private int initialCommentsCount;
    private CommentsContainer commentsContainer;
    @BeforeMethod
    public void setUp() {
        super.setUpBrowser();
        String userName = "lscott1k@ftc.gov";
        String password = "$aLamura234";
        feed = super.signIn(userName, password);
        initialCommentsCount = feed.getCommentsCountFirstAd();
        ViewAdPage adPage = feed.openFirstAd();
        commentsContainer = new CommentsContainer(Browser.eDriver());

    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            Utility.captureScreenshot(eDriver, result.getName());

        }
        shutDown();
    }

    @Test(description = "Create new comment for advertisement ")
    public void postComment(){


        String comment=commentsContainer.generateComment();
        commentsContainer.postComment(comment);

        commentsContainer.assertCommentIsPosted(comment);

        assertEquals("Initial comments count:" + initialCommentsCount + ". Actual: " + commentsContainer.getCommentsStatus(), initialCommentsCount + 1, commentsContainer.getCommentsStatus());

    }

    @Test(description = "Delete comment from advertisement ")
    public void deleteComment(){
        initialCommentsCount=commentsContainer.getCommentsStatus();

        String comment=commentsContainer.generateComment();
        commentsContainer.postComment(comment);

        commentsContainer.assertCommentIsPosted(comment);
        commentsContainer.deleteComment(comment);
        commentsContainer.assertCommentIsDeleted(comment);

    }

}
