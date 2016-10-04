package tests;

import helpers.TestDataFaker;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pageObjects.LiveFeed.ChatBar;

/**
 * Created by penko.yordanov on 07-Jun-16.
 */
public class StartNewChat extends BaseTest{
    private String chateInitiatorName="Martha";
    private String userName = "mgarrett10@ca.gov";
    private String password = "$aLamura234";
    private String chateeFirstName ="Gregory";
    private String chateeFullName ="Gregory Wagner";
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
        feed = super.signIn(userName,password);
    }

    @Test
    public void regularChatWithUser(){
        ChatBar chat=feed.openChat();
        chat.clickNewChatButton();
        chat.searchUser(chateeFirstName);
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
        chat.assertLastMessageIsInConversationOverview(chateInitiatorName,message);

    }
}
