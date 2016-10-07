package pageObjects;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.openqa.selenium.By;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import pageObjects.Common.Base;
import pageObjects.Feed.FeedPage;

public class LoginPage extends Base {

	public LoginPage(EventFiringWebDriver edriver) {
		super(edriver);
		isDisplayed(emailField,10);
	}

	private By passwordField=By.cssSelector("[type='password']");

	private By signInBtn=By.cssSelector("[type='submit']");

	private By emailField = By.cssSelector("[placeholder='Email..']");

	public LoginPage typeUsername(String username){
		clear(emailField);
		type(username,emailField);
		return this;
	}
	
	public LoginPage typePassword(String password){
		clear(passwordField);
		type(password,passwordField);
		return this;
	}
	
	public LoginPage submitSignInExpectingFailure(){
		click(signInBtn);
		return this;
	}

	public boolean assertPresenceValidationMessege(){
		return isDisplayed(By.xpath("//*[@class='input-error-text shake'][text()='Username or password is incorrect.']"),10);
	}

	public FeedPage submitSignIn() {
		click(signInBtn);
		return new FeedPage(eDriver);
	}


	public String getAttributeValueJSON(String json,String attribute){
		JsonParser parser=new JsonParser();
		JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
		return jsonObject.get(attribute).getAsString();
	}
}
