package pageObjects.Feed;

import org.openqa.selenium.By;
import org.openqa.selenium.support.events.EventFiringWebDriver;

/**
 * Created by penko.yordanov on 10-Aug-16.
 */
public class FilterContainer extends FeedPage {
    private By filterMenuArrow = By.xpath("//i[@title='Filter feed by']");

    public FilterContainer(EventFiringWebDriver eDriver){
        super(eDriver);
    }
    public void openFilterMenu() {
        isDisplayed(filterMenuArrow,5);
        click(filterMenuArrow);
        isDisplayed(By.xpath("//nav[@class=\"left-nav\"]//*[@class='fa fa-angle-left']"), 10);
    }

    public FeedPage selectFilterOption(String filterOption) {

        isDisplayed(By.xpath("//li//span[text()='Info board']"));
        click(By.xpath("//li//span[text()='" + filterOption + "']"));

        return this;
    }

    public void closeFilterMenu(String category) {
        click(filterMenuArrow);
        isDisplayed(By.xpath("//nav[@class=\"left-nav\"]//*[@class='fa fa-angle-right']"), 10);
    }
}
