package tests;

import helpers.TestDataFaker;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pageObjects.Feed.ChatBar;

/**
 * Created by penko.yordanov on 07-Jun-16.
 */
public class StartNewChat extends BaseTest{
    private final String message=TestDataFaker.getChatMessage();
    EventFiringWebDriver eDriver;

  /*  @BeforeClass
    public static void clearChat(){
        ClearChatSQL.clearChat();
    }*/
  @AfterMethod
  public void tearDown(ITestResult result) {

      shutDown();
  }

    @BeforeMethod
    public void setUp() {
        super.setUpBrowser();

        // Navigate to Sign In page of NovaManus and sign in with username and
        // password
        String password = "$aLamura234";
        String userName = "mgarrett10@ca.gov";
        feed = super.signIn(userName, password);
    }

    @Test(enabled = false)
    public void regularChatWithUser(){
        ChatBar chat=feed.openChat();
        chat.clickNewChatButton();
        String chateeFirstName = "Gregory";
        chat.searchUser(chateeFirstName);
        String chateeFullName = "Gregory Wagner";
        chat.assertUserIsFound(chateeFullName);
        chat.selectUserFromSuggestions(chateeFullName);
        chat.assertChatIsCreated(chateeFullName);
        chat.assertChatIsInChatBar(chateeFirstName);
        chat.sendMessage(message);
        chat.assertLastMessageIsInConversationOverview(chateeFirstName,message);
        chat.assertMessageContainer(message);

        //Assert other user has received chat messages
        loginPage=feed.loggout();
        feed=signIn("test@novamanus.com","123456");
        chat=feed.openChat();
        String chateInitiatorName = "Martha";
        chat.assertLastMessageIsInConversationOverview(chateInitiatorName,message);

    }
}
