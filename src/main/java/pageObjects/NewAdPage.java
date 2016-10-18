package pageObjects;

import java.io.IOException;
import java.util.List;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.Common.Base;
import pageObjects.Profile.ProfilePage;

import static org.testng.Assert.*;

public class NewAdPage extends Base {

	private By titleField=By.cssSelector("[ngcontrol='title']");

	private By descriptionField=By.cssSelector("[ngcontrol='description']");

	private By priceField=By.cssSelector("[ngcontrol='price']");

	private By keywordField=By.cssSelector("[ngcontrol='keyword']");

	private By addKeywordBtn=By.xpath("//span[text()='Add keyword'][1]");

	private By publishBtn = By.cssSelector("button.btn-publish-add");

	private By saveDraftBtn=By.xpath("//button[text()='Save as draft']");

	private By photoUploadBtn=By.xpath("//button[@class='btn-upload fa fa-plus']");

	private By categoryDropdown=By.cssSelector("[ngcontrol='category']");

	private By conditionDropdown=By.cssSelector("[ngcontrol='condition']");
    private By defaultAddress = By.xpath("//input[@ngcontrol='address']/../ul/li");

	private Faker faker;



	public NewAdPage(EventFiringWebDriver eDriver) {
		super(eDriver);
        new WebDriverWait(eDriver, 10).until(ExpectedConditions.presenceOfElementLocated(defaultAddress));
//		isDisplayed(defaultAddress,10);
		faker=new Faker();
	}

	public NewAdPage typeTitle(String title) {
		type(title,titleField);
		return this;
	}

	public NewAdPage typeDescription(String description) {
		type(description,descriptionField);
		return this;
	}

	public NewAdPage selectCategory(String category) {
		Select categoryDdown = new Select(find(categoryDropdown));
		categoryDdown.selectByVisibleText(category);
		return this;
	}

	public NewAdPage selectCondition(String condition,String category) {
		if (!(category.equals("Rent"))||(category.equals("Info board"))) {
			Select conditionDrodown = new Select(find(conditionDropdown));
			conditionDrodown.selectByVisibleText(condition);
		}
		return this;
	}

	public NewAdPage typePrice(String price, String category) {
		if (category.equals("Sell")||category.equals("Rent")) {
			clear(priceField);
			type(price,priceField);
		}
		return this;
	}

	public NewAdPage typeKeywords(String keywords) {
		type(keywords,keywordField);
		return this;
	}
	
	public void uploadPhoto(String fileToUpload){
		/*WebElement fileInput=find(By.xpath("//button[@title='Upload photo or video file']/following::input"));

		int count=0;
		while (!isDisplayed(By.xpath("//ad-multimadia-tile/descendant::img[contains(@src,'.jpg')]"))&count==10){
			fileInput.sendKeys(fileToUpload);
			count++;
		}*/
		click(photoUploadBtn);
		eDriver.switchTo().activeElement();

		try {
			Thread.sleep(2000);
			Runtime.getRuntime().exec("D:/AutoIT/FileUploadNM.exe "+fileToUpload);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		new WebDriverWait(eDriver,10).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//ad-multimadia-tile/descendant::img[contains(@src,'.jpg')]")));
		assertTrue(isDisplayed(By.xpath("//ad-multimadia-tile/descendant::img[contains(@src,'https')]"),10),"Image is not uploaded");
	}

	public String getBlobSourceUrl(){
		return find(By.xpath("//ad-multimadia-tile/descendant::img")).getAttribute("src");
	}
	
	public void assertKeywordsDisplayed(String keyword){
		String[] key = keyword.split("[,]");
		List<WebElement> tags = findElements(By.xpath("//ul[@class='keywords-tags']/li"));
		for (int i = 0; i < key.length; i++) {
			assertEquals(key[i]+"\nX", tags.get(i).getText().trim());

		}
	}
	
	public String getLastKeyword(){
		return find(By.xpath("//ul[@class='keywords-tags']/li")).getText();
		
	}

	public NewAdPage clickAddKeywordBtn() {
		click(addKeywordBtn);
		return this;
	}

	public ProfilePage clickPublishBtn() {
		eDriver.executeScript("scroll(0,1400)");
		click(publishBtn);
		return new ProfilePage(eDriver);
	}
	
	public ProfilePage clickSaveDraftBtn() {
		eDriver.executeScript("scroll(0,1400)");
		click(saveDraftBtn);
		return new ProfilePage(eDriver);
	}

	private String generateTitle(){
		return faker.lorem().sentence();
	}

}
