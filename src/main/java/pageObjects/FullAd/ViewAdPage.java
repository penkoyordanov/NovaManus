package pageObjects.FullAd;

import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.Common.Base;
import pageObjects.Feed.FeedPage;

import static org.testng.Assert.*;

public class ViewAdPage extends Base {


	private By adTitle=By.xpath("//article[@class='view-add-detail']/h1");

	private By adDescription =By.xpath("//div[@class='va-description']/trimmable/p");



	public ViewAdPage(EventFiringWebDriver eDriver) {
		super(eDriver);
		isDisplayed(adTitle,10);


	}

	public FeedPage clickToLiveFeedLink() {
		find(By.linkText("Live feed")).click();
		return new FeedPage(eDriver);
	}

	public boolean assertTitleFullAd(String title){
		new WebDriverWait(eDriver,10).until(ExpectedConditions.textToBePresentInElementLocated(adTitle,title));
		return getTextOfElement(adTitle).equals(title);

	}
	
	public String getDescription(){
		return getTextOfElement(adDescription).trim();
	}

	public ViewAdPage clickAdImage(){
		click(By.xpath("//article[@class='view-add-images']/descendant::img[@alt='Add Main Image']"));
		isDisplayed(By.xpath("//div[@id='fullAddDlg'][@class='modal fade in']"),10);
		return this;
	}

	public String getImgSrcFullAddDlg(){
		return find(By.xpath("//div[@id='fullAddDlg'][@class='modal fade in']/descendant::img[@alt='Full Add Image']")).getAttribute("src");
	}
	
	public String getOwnerNames(){
		return getTextOfElement(By.xpath("//div[@class='va-username']")).trim();
	}
	
	public String getPointOfSale(){
		return getTextOfElement(By.cssSelector(".va-address")).trim();
	}
	
	public String getEmail(){
		return find(By.xpath("//div[@class='va-user-info']/descendant::a[contains(@class,'btn-mp-mail')]")).getAttribute("href");
	}
	
	public String getPhone(){
		return find(By.xpath("//div[@class='va-user-info']/descendant::a[contains(@class,'btn-mp-phone')]")).getAttribute("href");
	}
	
	public boolean isConditionDisplayed(String category,String condition){
		boolean isConditonThere=false;
		if (category.equals("Wanted") || category.equals("Sell") || category.equals("Swap")
				|| category.equals("Give away")) {
					if(getTextOfElement(By.xpath("//div[@class='va-condition']/span")).trim().equals(condition)){
						isConditonThere=true;
					}
		}else if  (category.equals("Rent") || category.equals("Info board")) {
			isConditonThere = true;
		}
		return isConditonThere;
	}
	
	public boolean isCategoryDisplayed(String category, String currency, String price){
		boolean isCategoryDisplayed=false;
		switch (category) {
			case "Sell":
				if (getTextOfElement(By.xpath("//article[@class='view-add-detail']/descendant::h2[1]")).trim().equals(currency + " " + price)) {
					isCategoryDisplayed = true;
				}
			case "Rent":
				if (getTextOfElement(By.xpath("//article[@class='view-add-detail']/descendant::h2[1]")).trim().equals(currency + " " + price)) {
					isCategoryDisplayed = true;
				}
			case "Give away":
				if (getTextOfElement(By.xpath("//article[@class='view-add-detail']/descendant::h2[2]"))
						.trim().equals("Free")) {
					isCategoryDisplayed = true;
				}
			default:
				if (getTextOfElement(By.xpath("//article[@class='view-add-detail']/descendant::h2[2]"))
						.trim().equals(category)) {
					isCategoryDisplayed = true;
				}
		}
		/*if (category.equals("Sell") || category.equals("Rent")) {
			if (getTextOfElement(By.xpath("//article[@class='view-add-detail']/descendant::h2[1]")).trim().equals(currency + " " + price)){
						isCategoryDisplayed=true;
					}	
		} else if (category.equals("Give away")) {
			if(getTextOfElement(By.xpath("//article[@class='view-add-detail']/descendant::h2[2]"))
					.trim().equals("Free")){
				isCategoryDisplayed=true;
			}
					

		} else {
			if(getTextOfElement(By.xpath("//article[@class='view-add-detail']/descendant::h2[2]"))
					.trim().equals(category)){
				isCategoryDisplayed=true;
			}
		}*/
		return isCategoryDisplayed;
	}
	
	public void assertKeywordsDisplayed(String keyword){
		String[] key = keyword.split("[,]");
		List<WebElement> tags = findElements(By.xpath("//ul[@class='keywords-tags']/li"));
		for (int i = 0; i < key.length; i++) {
			assertEquals(key[i], tags.get(i).getText().trim());

		}
	}

	public Boolean isImageAttachedToAdvertise(String imageSource){
		return isDisplayed(By.xpath("//article[@class='view-add-images']/descendant::img[contains(@src,'"+imageSource+"')][@alt='Add Main Image']"),10);
	}

}
