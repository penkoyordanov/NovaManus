package Pages.Feed;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import Pages.Common.Base;

import java.util.List;

import static org.testng.Assert.*;

public class ChatBar extends Base {
    private By activeChatHeader=By.xpath("//div[@class='chat-window active']/div/h5");
    private By chatInput=By.id("message-input");

    ChatBar(EventFiringWebDriver eDriver) {
        super(eDriver);
    }

    public void clickNewChatButton(){
        click(By.xpath("//button[@class='btn-chat-search fa fa-plus']"));
    }

    public boolean assertNewChatIsOpened(){
        return isDisplayed(activeChatHeader,10);
    }
    public void searchUser(String user){
        isDisplayed(By.xpath("//input[@placeholder='Add user to conversation']"),10);
        type(user,By.xpath("//input[@placeholder='Add user to conversation']"));
    }

    public void assertUserIsFound(String username){
        isDisplayed(By.xpath("//div[@class='chat-window-add']/div[1]/div"),10);

        try {
            assertTrue(getTextOfElement(By.xpath("//div[@class='chat-window-add']/div[1]/div/h4")).equals(username),"USer is not found");
        }catch(java.lang.AssertionError assertionError){
            System.out.println("Expected: "+username);
            System.out.println("Actual:\""+getTextOfElement(By.xpath("//div[@class='chat-window-add']/div[1]/div/h4"))+"\"");
        }
    }

    public void selectUserFromSuggestions(String username){
        List<WebElement> usersSuggestion=findElements(By.xpath("//div[@class='chat-title online active']"));
        for(WebElement user:usersSuggestion){
            if(user.getText().equals(username)){
                user.click();
                click(By.xpath("//button[@class='btn-done-message']"));

            }
        }
    }

    public void assertChatIsCreated(String username){
        try {
            assertTrue(getTextOfElement(activeChatHeader).equals(username));
            assertTrue(getTextOfElement(By.xpath("//div[@id='chat-window']/descendant::h4")).equals(username));
        }catch(java.lang.AssertionError assertionError){
            System.out.println("Expected: "+username);
            System.out.println("Actual:\""+getTextOfElement(activeChatHeader)+"\"");
        }
    }

    public void assertChatIsInChatBar(String userFirstName){
            assertTrue(isDisplayed(By.xpath("//div[@class='chat-users']/strong[contains(.,"+userFirstName+")]"),10),"Created chat is not in the chat overview");
    }

    public void sendMessage(String message){
        isDisplayed(chatInput,10);
        type(message,chatInput);
        click(By.xpath("//label[text()='Send']"));
    }

    public void assertLastMessageIsInConversationOverview(String userFirstName,String message){
        assertTrue(isDisplayed(By.xpath("//div[@class='chat-users']/strong[starts-with(.,'"+userFirstName+"')]/preceding::div[contains(.,'"+message+"')]"),10),"Message is not in the chat overview");

    }

    public void assertMessageContainer(String message){
        assertTrue(getTextOfElement(By.xpath("//message-item/descendant::p[contains(.,'"+message+"')]")).equals(message),"Message not in the message container");
        assertTrue(isDisplayed(By.xpath("//message-item/descendant::plane-text/p[contains(.,'"+message+"')]/following::div[@class='chat-time']/span[contains(.,'Just now')]")),"Time is wrong");
        assertTrue(isDisplayed(By.xpath("//message-item/descendant::plane-text/p[contains(.,'"+message+"')]/preceding::div[@class='chat-text-img']/app-image/img[starts-with(@src,'https://novamanus.blob.core.windows.net/pphoto')]")),"Profile photo not displayed");

    }
}
