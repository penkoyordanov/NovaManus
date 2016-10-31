package listeners;

import Pages.Browser.Browser;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestListener extends TestListenerAdapter {
    private EventFiringWebDriver eDriver;
    private static String fileSeperator = System.getProperty("file.separator");

    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println("Test case started and details are " + iTestResult.getName());
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("TestCase succeed and TestCase details are " + iTestResult.getName());

    }


    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("TestCase failed and TestCase details are " + result.getName());
        System.out.println("***** Error " + result.getName() + " test has failed *****");

        eDriver = Browser.eDriver();

        String testClassName = getTestClassName(result.getInstanceName()).trim();

        String testMethodName = result.getName().toString().trim();
        String fn = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString(); //Or the name you want. I suggest don't use static name.
        String screenShotName = testMethodName + fn.toString() + ".png";

        if (eDriver != null) {
            String imagePath = ".." + fileSeperator + "Screenshots"
                    + fileSeperator + "Results" + fileSeperator + testClassName
                    + fileSeperator
                    + takeScreenShot(eDriver, screenShotName, testClassName);
            System.out.println("Screenshot can be found : " + imagePath);
        }
    }

    private String getTestClassName(String testName) {
        String[] reqTestClassname = testName.split("\\.");
        int i = reqTestClassname.length - 1;
        System.out.println("Required Test Name : " + reqTestClassname[i]);
        return reqTestClassname[i];
    }

    private static String takeScreenShot(EventFiringWebDriver eDriver,
                                         String screenShotName, String testName) {
        try {
            File file = new File("Screenshots" + fileSeperator + "Results");
            if (!file.exists()) {
                System.out.println("File created " + file);
                file.mkdir();
            }

            File screenshotFile = eDriver.getScreenshotAs(OutputType.FILE);
            File targetFile = new File("Screenshots" + fileSeperator + "Results" + fileSeperator + testName, screenShotName);
            FileUtils.copyFile(screenshotFile, targetFile);

            return screenShotName;
        } catch (Exception e) {
            System.out.println("An exception occured while taking screenshot " + e.getCause());
            return null;
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("TestCase failed and TestCase details are " + iTestResult.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {
        System.out.println(iTestContext.getName() + " test case started");

    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        System.out.println(iTestContext.getName() + " test case finished");

    }
}
