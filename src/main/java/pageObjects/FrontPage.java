package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import pageObjects.Common.Base;
import pageObjects.Signup.SignUpPage;

public class FrontPage extends Base {
//	static String url = "http://novamanus.azurewebsites.net";
//	static String url = "http://localhost:3000/";
private String url = "http://novamanus.icb.bg:3001/";
//	static String url = "http://novamanus-test.azurewebsites.net/";
	private final static String title = "NOVAMANUS";


	public FrontPage(EventFiringWebDriver edriver) {
		super(edriver);
		visit(url);
		isDisplayed(loginBtn,10);
	}

	private By registerLink = By.cssSelector("a.btn-fp-signup");

	private By loginBtn = By.cssSelector("a.btn-fp-login");


	public LoginPage clickSignInBtn() {
		click(loginBtn);
		return new LoginPage(eDriver);
	}
	
	public SignUpPage clickSignUpBtn() {
		click(registerLink);
		return new SignUpPage(eDriver);
	}



}