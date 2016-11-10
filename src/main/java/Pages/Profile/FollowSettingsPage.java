package Pages.Profile;

import Pages.Common.Base;
import helpers.SQL.GetDataFromDB;
import org.openqa.selenium.By;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import static org.testng.Assert.assertTrue;

public class FollowSettingsPage extends Base {
    private By searchField = By.xpath("//input[@placeholder='Search cities, users and keywords to follow..']");

    public FollowSettingsPage(EventFiringWebDriver eDriver) {
        super(eDriver);
        assertTrue(isDisplayed(By.xpath("//div[@class='follow-search']/div"),10),"Follow page is not loaded");
    }

    public FollowSettingsPage searchToFollow(String searchCriteria){
        type(searchCriteria,searchField);
        return this;
    }

    public void assertSuggestionAppear(String searchCriteria){
        assertTrue(isDisplayed(By.xpath("//suggestion-item/li/strong[contains(.,'"+searchCriteria+"')]"),10),""+searchCriteria+" didn't appear as suggestion");
    }

    public FollowSettingsPage unfollow(String areaName) {
        click(By.xpath("//followed-item/descendant::strong[starts-with(.,'" + areaName + "')]/../../span"));
        assertItemIsUnfollowed(areaName);
        return this;
    }

    public FollowSettingsPage followFromSuggestion(String follow){
        click(By.xpath("//suggestion-item/li/strong[contains(.,'" + follow + "')]/..//button"));
        return this;
    }

    public int getStatisticsFromSuggestion(String followedItem, String statisticName) {
        int statisticCount = 0;
        switch (statisticName) {
            case "followers":
                statisticCount = Integer.parseInt(getTextOfElement((By.xpath("//suggestion-Item/li/strong[contains(.,'" + followedItem + "')]/../span[1]"))));//followers
                break;
            case "ads":
                statisticCount = Integer.parseInt(getTextOfElement((By.xpath("//suggestion-Item/li/strong[contains(.,'" + followedItem + "')]/../span[2]")))); //related ads
                break;
        }
        return statisticCount;
    }

    public int getStatisticsFromDB(String followedItem, String FollowedItemValue, String statisticName) {
        int statisticsCount = 0;
        switch (followedItem) {
            case "area":
                if (statisticName.equals("ads")) {
                    statisticsCount = GetDataFromDB.getAdsInArea(FollowedItemValue);
                    break;
                } else {
                    statisticsCount = GetDataFromDB.getFollowersInArea(FollowedItemValue);
                    break;
                }
            case "keyword":
                if (statisticName.equals("ads")) {
                    statisticsCount = GetDataFromDB.getAdsWithKeyword(FollowedItemValue);
                    break;
                } else {
                    statisticsCount = GetDataFromDB.getFollowersByKeyword(FollowedItemValue);
                    break;
                }
        }
        return statisticsCount;
    }

    public boolean assertIsFollowed(String follow){
        return isDisplayed(By.xpath("//div[@class='follow-container']//strong[contains(.,'"+follow+"')]"),5);
    }

    private Boolean assertItemIsUnfollowed(String itemToCheck) {
        boolean isDisplayed=false;
        try {
            isDisplayed=isDisplayed(By.xpath("//followed-item/descendant::strong[starts-with(.,'" + itemToCheck + "')]"), 5);
        }catch (org.openqa.selenium.StaleElementReferenceException sere){

        }
        return isDisplayed;
    }

}
