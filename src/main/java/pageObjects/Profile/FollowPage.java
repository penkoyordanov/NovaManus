package pageObjects.Profile;

import helpers.SQL.GetFollowStatisics;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;
import pageObjects.Common.Base;

import java.util.List;

import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

/**
 * Created by penko.yordanov on 03-Jun-16.
 */
public class FollowPage extends Base {
    private By searchField=By.xpath("//input[@placeholder='Type search..']");

    public FollowPage(EventFiringWebDriver eDriver) {
        super(eDriver);
        assertTrue(isDisplayed(By.xpath("//div[@class='follow-search']/div"),10),"Follow page is not loaded");
    }

    public FollowPage searchToFollow(String searchCriteria){
        type(searchCriteria,searchField);
        return this;
    }

    public void assertSuggestionAppear(String searchCriteria){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(isDisplayed(By.xpath("//suggestion-item/li/strong[contains(.,'"+searchCriteria+"')]"),10),""+searchCriteria+" didn't appear as suggestion");
    }

    public FollowPage unfollowArea(String areaName){
        click(By.xpath("//followed-item/descendant::strong[starts-with(.,'"+areaName+"')]/../../../span"));
        assertAreaIsUnfollowed(areaName);
        return this;
    }

    public FollowPage followFromSuggestion(String follow){
        click(By.xpath("//suggestion-item/li/strong[contains(.,'"+follow+"')]/../span/button[@class='fa fa-plus']"));
        return this;
    }


    public void assertStatisticsAboutArea(String area){
        String areaStatisticsString=getTextOfElement(By.xpath("//suggestion-item/li/strong[starts-with(.,'"+area+"')]/../span[2]"));
        String[] areaStatistics= areaStatisticsString.split(" ");
        Assert.assertEquals(areaStatistics[1], GetFollowStatisics.getAdsInArea(area),"Statistics about ads in area are wrong");
        Assert.assertEquals(areaStatistics[0], GetFollowStatisics.getFollowersInArea(area),"Statistics about area followers is wrong");
    }

    public boolean assertIsFollowed(String follow){
        return isDisplayed(By.xpath("//followed-item/descendant::strong[starts-with(.,'"+follow+"')]"),1);
    }

    private Boolean assertAreaIsUnfollowed(String area){
        boolean areaIsRemoved=true;
        List<WebElement> areas=findElements(By.xpath("//div[@class='follow-item areas']/descendant::followed-item/descendant::strong"));
        for(WebElement o:areas){
            if (o.getText().equals(area)){
                areaIsRemoved=false;
            }
        }
        return areaIsRemoved;
    }
}
