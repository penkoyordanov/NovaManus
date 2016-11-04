package Pages.Common;

import Pages.Browser.Browser;
import Pages.Profile.EditProfile;
import Pages.Profile.FollowSettingsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.events.EventFiringWebDriver;

/**
 * Created by penko.yordanov on 28-Oct-16.
 */
public class TopMenu extends Base {
    private EventFiringWebDriver eDriver;
    private By gearDropdown = By.cssSelector("[title='User Menu']");
    private By followSettingsOption = By.xpath("//div[@class='dropdown user-dropdown open']//*[text()='Follow settings']");
    private By editProfileOption = By.xpath("//div[@id='gear-menu']//*[text()='Edit profile']");

    public TopMenu() {

        super(Browser.eDriver());
        eDriver = Browser.eDriver();
    }

    public FollowSettingsPage selectFollowSettings() {
        click(gearDropdown);
        click(followSettingsOption);
        return new FollowSettingsPage(eDriver);
    }

    /*public EditProfile selectEditProfile() {
        click(gearDropdown);
        click(followSettingsOption);
        return new EditProfile(eDriver);
    }
*/

}
