package listeners;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by penko.yordanov on 13-Oct-16.
 */
public class Utility {
    public static void captureScreenshot(WebDriver driver, String screenShotName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String fn = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString(); //Or the name you want. I suggest don't use static name.
            FileUtils.copyFile(source, new File("./Screenshots/" + screenShotName + fn.toString() + ".png"));
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot " + e.getMessage());
        }
    }
}
