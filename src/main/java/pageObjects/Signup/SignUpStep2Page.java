package pageObjects.Signup;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;
import pageObjects.Common.Base;
import pageObjects.Feed.FeedPage;

/**
 * Created by penko.yordanov on 01-Jun-16.
 */
public class SignUpStep2Page extends Base {
    private By goBtn=By.xpath("//a[text()='Go and axplore!']");
    private By addBtn=By.xpath("//span[text()='Add']");
    SignUpStep2Page(EventFiringWebDriver eDriver){
        super(eDriver);
        isDisplayed(addBtn,10);
    }

    public void assertNamesDisplayed(String firstName, String lastName){
        String fullName=firstName+" "+lastName;
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            find(By.xpath("//div[@class='singup-profile-info']/h1[starts-with(.,'"+fullName+"')]"));
        }catch (NoSuchElementException nse){
            nse.printStackTrace();
        }

        Assert.assertTrue(isDisplayed(By.xpath("//div[@class='singup-profile-info']/h1[starts-with(.,'"+fullName+"')]")),"User full name is not displayed on step2");
    }

    public FeedPage clickLetsGoBtn() {
        eDriver.executeScript("arguments[0].scrollIntoView();", find(goBtn));
        isDisplayed(goBtn,10);
        click(goBtn);
        return new FeedPage(eDriver);
    }
////strong[text()='Manchester']/following::span/span
    public void assertCityToFollowIsThere(String city){
        Assert.assertTrue(isDisplayed(By.xpath("//div[@class='follow-body-item']/div/div/a/strong[starts-with(.,'"+city+"')]"),10),"Area "+city+" is not displayed");
    }

    public void assertStateOfFollowButton(String followed){
        Assert.assertTrue(isDisplayed(By.xpath("//strong[text()='"+followed+"']/following::span/span"),10),"State of button is not changed");
    }

    public SignUpStep2Page followSuggested(String suggestionToFollow){
        click(By.xpath("//strong[starts-with(.,'"+suggestionToFollow+"')]/following::span/span"));
        return this;
    }
}
