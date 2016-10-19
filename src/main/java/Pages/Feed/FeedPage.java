package Pages.Feed;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.Select;
import Pages.*;
import Pages.Common.Base;
import Pages.FullAd.ViewAdPage;
import Pages.Profile.ProfilePage;

import java.util.List;

import static org.testng.Assert.*;

public class FeedPage extends Base {
    private String filterOption;

    private By newAdButton = By.xpath("//div/a[@class='fa fa-plus header-icon']");

    private By profilePicture = By.xpath("//a[@class='user-photo']/*[1]");

    private By logo = By.xpath("//img[1][@alt='NOVAMANUS logo']");

    private By firstAd = By.xpath("//ad-tile[1]/article/figure/div");

    private By searchOptionsBtn = By.xpath("//button[@title='Search options']");

    private By searchBtn = By.xpath("//*[@class='fa fa-search']");


    public FeedPage(EventFiringWebDriver edriver) {
        super(edriver);
        assertTrue(isDisplayed(newAdButton, 10), "Make ad button is not displayed");
        isDisplayed(profilePicture);
    }

    public ProfilePage clickProfileImage() {
        isDisplayed(profilePicture, 10);
        click(profilePicture);
        return new ProfilePage(eDriver);
    }

    public ChatBar openChat() {
        return new ChatBar(eDriver);
    }

    public FeedPage clickSearchOptionsBtn() {
        click(searchOptionsBtn);
        isDisplayed(By.xpath("//advanced-search/div/form"));
        return this;
    }

    public FeedPage selectCountry(String countryToSelect) {
        isDisplayed(By.xpath("//advanced-search/div/form/div[1]/select/option[text()='" + countryToSelect + "']"), 10);
        Select cSelect = new Select(find(By.xpath("//advanced-search/div/form/div[1]/select")));
        cSelect.selectByVisibleText(countryToSelect);
        return this;
    }

    public FeedPage selectCity(String cityToSelect) {
        isDisplayed(By.xpath("//advanced-search/div/form/div[2]/select/option[text()='" + cityToSelect + "']"), 10);
        Select cSelect = new Select(find(By.xpath("//advanced-search/div/form/div[2]/select")));
        cSelect.selectByVisibleText(cityToSelect);
        return this;
    }

    public FeedPage clickSearchBtn() {
        click(searchBtn);
        return this;
    }

    public ViewAdPage openFirstAd() {
        isDisplayed(By.xpath("//ad-tile[1]//div[@class='lf-item-title']"), 10);
        click(By.xpath("//ad-tile[1]//div[@class='sold-price']"));
        return new ViewAdPage(eDriver);
    }

    public Boolean isAdvertiseSold(String adTitle) {
        return (isDisplayed(By.xpath("//ad-tile/descendant::h3[contains(.,'" + adTitle + "')]/../preceding::figure/div/div[text()='SOLD']"), 10));

    }

    public int getCommentsCountFirstAd() {
        isDisplayed(By.xpath("//ad-tile[1]//div[@class='lf-item-title']"), 10);
        String commentsFullString = getTextOfElement(By.xpath("//ad-tile[1]//div[@class='lf-statistic']//span[2]"));
        String[] commentssplit = commentsFullString.split(" ");

        return Integer.parseInt(commentssplit[0]);
    }

    public void scrollWhileFindAd(String adTitle) {
        boolean isAdAvailable = false;
        while (!isAdAvailable) {
            List<WebElement> adTitles = findElements(By.xpath("//ad-tile/article/div/div/h3"));
            for (WebElement title : adTitles) {
                if (adTitle.equals(title.getText().trim())) {
                    isAdAvailable = true;
                    break;
                }
                String js = "setTimeout(function(){window.scrollTo(0,document.body.scrollHeight)}, 2000);";
                eDriver.executeScript(js);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }


        }

    }

    public NewAdPage clickMakeNewAdButton() {
        click(newAdButton);
        return new NewAdPage(eDriver);
    }

    public String getValueOfTopAdByAttributeAd(String attribute) {
        /*if (attribute.equals("title")){
            return getTextOfElement(By.xpath("//ad-tile[1]/descendant::h3"));
		}else if (attribute.equals("time")){
			return getTextOfElement(By.xpath("//ad-tile[1]/descendant::div[@class='time']"));
		}else if (attribute.equals("distance")){
			return getTextOfElement(By.xpath("//ad-tile[1]/descendant::ad-location/span/span"));
		}else (attribute.equals("publisher")){
			return eDriver.findElement(By.xpath("//ad-tile[1]/descendant::div[@class='sold-username']")).getText();
		}*/
        String value = "";
        switch (attribute) {
            case "title":
                value = getTextOfElement(By.xpath("//ad-tile[1]/descendant::h3"));
                break;
            case "time":
                value = getTextOfElement(By.xpath("//ad-tile[1]/descendant::div[@class='time']"));
                break;
            case "distance":
                isDisplayed(By.xpath("//ad-tile[1]/descendant::ad-location/span/span"));
                value = getTextOfElement(By.xpath("//ad-tile[1]/descendant::ad-location/span/span"));
                break;
            case "publisher":
                value = eDriver.findElement(By.xpath("//ad-tile[1]/descendant::div[@class='sold-username']")).getText();
                break;

        }
        return value;
    }

	/*public String getTitleTopAdvertise(){
        isDisplayed(By.xpath("//ad-tile[1]/descendant::h3"),10);
		return getTextOfElement(By.xpath("//ad-tile[1]/descendant::h3"));
	}*/
	/*public String getTimeTopAd(){
		return getTextOfElement(By.xpath("//ad-tile[1]/descendant::div[@class='time']"));
	}*/

   /* public String getDistanceTopAd(){
        return getTextOfElement(By.xpath("//ad-tile[1]/descendant::ad-location/span/span"));
    }*/

   /* public String getPublisherOfTopAd(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return eDriver.findElement(By.xpath("//ad-tile[1]/descendant::div[@class='sold-username']")).getText();
    }*/

    public boolean assertTopAdImage() {
        return isDisplayed(By.xpath("//ad-tile[1]/descendant::app-image/img[contains(@src,'https')][@alt='Live Feed Image']"));
    }


    public boolean assertFeedIsFilteredByCategory(String expectedCategory) {
        boolean isAllAdsSameCategory = true;
        List<WebElement> categories = findElements(By.xpath("//ad-tile/article/div/h4/span"));
        for (WebElement category : categories) {
            //Check if actual category of each ad in the feed is the same of expected category
            if (!(category.getText().trim() != expectedCategory || category.getText().trim() == "SOLD")) {
                isAllAdsSameCategory = false;
            }

        }
        return isAllAdsSameCategory;
    }

    public boolean assertCityAppearedSearchField(String city, String country) {
        ////div[@class='live-feed-search']//div[@class='lfsk-item']//text()
        isDisplayed(By.xpath("//div[@class='live-feed-search']//div[@class='lfsk-item']"));
        System.out.println(getTextOfElement(By.xpath("//div[@class='live-feed-search']//div[@class='lfsk-item']")));
        return getTextOfElement(By.xpath("//div[@class='live-feed-search']//div[@class='lfsk-item']")).equals(city + " " + country);
    }

    public LoginPage loggout() {
        click(By.xpath("//div[@class='dropdown user-dropdown']"));
        WebElement select = find(By.xpath("//div[@class='dropdown user-dropdown open']"));
        List<WebElement> options = select.findElements(By.tagName("li"));
        for (WebElement option : options) {
            if ("Logout".equals(option.getText().trim()))
                option.click();
        }
        return new LoginPage(eDriver);
    }
}
