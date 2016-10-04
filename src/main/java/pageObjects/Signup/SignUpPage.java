package pageObjects.Signup;

import helpers.SQL.GetRegistrationCode;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.Select;
import pageObjects.Common.Base;
import pageObjects.Common.Browser;

public class SignUpPage extends Base {
//	WebDriverWait wait;

	@FindBy(how=How.XPATH,using="//label[@for='agreementCb']")

	private By firstName_field =By.cssSelector("[name='fname']");

	private By lastName_field=By.cssSelector("[name='lname']");

	private By phone_field=By.cssSelector("[name='phone']");

	private By email_field=By.cssSelector("[name='email']");

	private By password_field=By.xpath("//register-password/div/article[1]/div/input");

	private By confirmPassword_field=By.xpath("//register-password/div/article[2]/div/input");

	private By address_field=By.id("pac-input");
	private By confChenkbox=By.xpath("//section/div/div/div/div/label");

	private By nextBtn =By.xpath("//div[@class='btn-container right-btn']/button");

	public SignUpPage(EventFiringWebDriver eDriver) {
		super(eDriver);
		isDisplayed(firstName_field,10);
	}


	public SignUpPage setFirstName_field(String firstName){
			type(firstName, firstName_field);
	return this;
	}

	public SignUpPage setLastName_field(String lastName){
		type(lastName,lastName_field);
	return this;
	}

	public SignUpPage setBirth(String birth){
		String[] dobSeparate = birth.split("[-]");
		if (dobSeparate[2].startsWith("0")){
			dobSeparate[2]= String.valueOf(dobSeparate[2].charAt(1));
		}
		Select monthDrodown = new Select(
				Browser.driver().findElement(By.xpath("//register-birth/div/select[starts-with(@class,'form-control')][1]")));
		monthDrodown.selectByVisibleText(dobSeparate[1]);
		Select dateDrodown = new Select(
				Browser.driver().findElement(By.xpath("//register-birth/div/select[starts-with(@class,'form-control')][2]")));
		dateDrodown.selectByVisibleText(dobSeparate[2]);
		Select yearDrodown = new Select(
				Browser.driver().findElement(By.xpath("//register-birth/div/select[starts-with(@class,'form-control')][3]")));
		yearDrodown.selectByVisibleText(dobSeparate[0]);
		return this;
	}


	public SignUpPage setAddress_field(String address) throws InterruptedException{
		clear(address_field);
		type(address,address_field);
		Thread.sleep(2000);
		find(address_field).sendKeys(Keys.ARROW_DOWN);
		Thread.sleep(1000);
		find(address_field).sendKeys(Keys.TAB);
		return this;
	}

	public SignUpPage setEmail_field(String email){
		clear(email_field);
		type(email,email_field);
		return this;
	}

	public SignUpPage setPhone_field(String phone){
		clear(phone_field);
		type(phone,phone_field);
		return this;
	}

	public SignUpPage setPassword_field(String password){
		clear(password_field);
		type(password,password_field);
		return this;
	}

	public SignUpPage setConfirmPassword_field(String confirmPassword){
		clear(confirmPassword_field);
		type(confirmPassword,confirmPassword_field);
		return this;
	}


	public SignUpPage setGender(String gender) {
		if(gender.equals("Male")){
			click(By.xpath("//div[@class='btn-gender-tbl']/button[1]"));
		}else{
			click(By.xpath("//div[@class='btn-gender-tbl']/button[2]"));
		}
		return this;

	}

	public SignUpPage acceptTermsAndConditions() {
		click(confChenkbox);
		return this;
	}

	public boolean isTermsAndConditionsAccepted() {
		return find(confChenkbox).isSelected();
	}
	public SignUpPage clickNextBtn(){
		click(nextBtn);
		return this;
	}

	private String getConfirmationCode(String email){
//		GetRegistrationCode.setEmail(email);
		return GetRegistrationCode.getRequestCode(email);
	}

	public SignUpStep2Page applyConfirmationCode(String email){
		isDisplayed(By.xpath("//input[@placeholder='Enter code..']"));
		String code=GetRegistrationCode.getRequestCode(email);
		type(code,By.xpath("//input[@placeholder='Enter code..']"));
		click(By.xpath("//button[text()='Proceed']"));
		return new SignUpStep2Page(eDriver);
//		return this;
	}

}
