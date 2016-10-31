package Pages.FullAd;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class CommentsContainer extends ViewAdPage {
    private static Faker faker;
    private By commentsStatus=By.xpath("//comments-list/div[1]");

    private By commentInputField=By.xpath("//comment-input/textarea");

    private By sendCommentBtn=By.className("btn-send-message");

    public CommentsContainer(EventFiringWebDriver eDriver){
        super(eDriver);
        faker = new Faker();
    }

    public int getCommentsStatus(){
        String statusCount=find(commentsStatus).getText();
        String[] s=statusCount.split(" ");
        return Integer.parseInt(s[0]);
    }

    public CommentsContainer postComment(String comment){
        eDriver.navigate().refresh();
        isDisplayed(commentInputField,10);
        eDriver.executeScript("scroll(0,600)");
        type(comment,commentInputField);

        click(sendCommentBtn);
        return this;
    }
    public CommentsContainer deleteComment(String comment){
        click(By.xpath("//div/p[text()='"+comment+"']/../../div/button[@title='Remove']"));
        return this;
    }

    public String generateComment(){
        return faker.lorem().sentence();

    }

    public void assertCommentIsPosted(String comment){
        assertTrue(isDisplayed(By.xpath("//ad-comment/div/div/p[text()='"+comment+"']"),10),"Comment is not posted");
        assertTrue(isDisplayed(By.xpath("//ad-comment/div/div/p[text()='"+comment+"']/../child::div/a/strong[text()='Lori Scott']"),10),"Comment publisher not displayed");
    }

    public void assertCommentIsDeleted(String comment){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertFalse(isDisplayed(By.xpath("//ad-comment/div/div/p[text()='"+comment+"']"),2),"Comment is not deleted");
    }
}
