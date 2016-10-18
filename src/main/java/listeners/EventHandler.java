package listeners;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.events.WebDriverEventListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventHandler implements WebDriverEventListener {
	private By finalFindBy;

	public void beforeNavigateTo(String url, WebDriver driver) {
		System.out.println("Before Navigating To : " + url + ", my url was: "
				+ driver.getCurrentUrl());

	}

	public void afterNavigateTo(String url, WebDriver driver) {
		System.out.println("After Navigating To: " + url + ", my url is: "
				+ driver.getCurrentUrl());
	}

	public void beforeNavigateBack(WebDriver driver) {
		System.out.println("Just before beforeNavigateBack I was at " + driver.getCurrentUrl());
	}

	public void afterNavigateBack(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	public void beforeNavigateForward(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	public void afterNavigateForward(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeNavigateRefresh(WebDriver webDriver) {

	}

	@Override
	public void afterNavigateRefresh(WebDriver webDriver) {

	}

	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		finalFindBy = by;
		System.out.println("Trying to find: '" + finalFindBy + "'.");
//		System.out.println("Trying to find: " + by.toString()); // This is optional and an alternate way

	}

	public void afterFindBy(By by, WebElement element, WebDriver driver) {
		finalFindBy = by;
		System.out.println("Found: '" + finalFindBy + "'.");
//		System.out.println("Found: " + by.toString() + "'."); // This is optional and an alternate way
	}

	public void beforeClickOn(WebElement element, WebDriver driver) {
		System.out.println("Trying to click: '" + element + "'");
		// Highlight Elements before clicking
		for (int i = 0; i < 1; i++) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
					"arguments[0].setAttribute('style', arguments[1]);",
					element, "color: black; border: 3px solid black;");
		}
	}

	public void afterClickOn(WebElement element, WebDriver driver) {
		System.out.println("Clicked Element with: '" + element + "'");
	}

	@Override
	public void beforeChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {

	}

	@Override
	public void afterChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {

	}

	public void beforeChangeValueOf(WebElement element, WebDriver driver) {
        String actualValue = element.getText();

		// What if the element is not visible anymore?
		if (actualValue.isEmpty()) {
			actualValue = element.getAttribute("value");
		}
	}

/*	public void afterChangeValueOf(WebElement element, WebDriver driver) {
		element = element;
		String changedValue = "";
		try {
			changedValue = element.getText();
		} catch (StaleElementReferenceException e) {
			System.out
					.println("Could not log change of element, because of a stale"
							+ " element reference exception.");
			return;
		}
		// What if the element is not visible anymore?
		if (changedValue.isEmpty()) {
			changedValue = element.getAttribute("value");
		}

		System.out.println("Changing value in element found " + element
				+ " from '" + actualValue + "' to '" + changedValue + "'");
	}*/

	public void beforeScript(String script, WebDriver driver) {
		System.out.println("Just before beforeScript " + script);
	}

	public void afterScript(String script, WebDriver driver) {
		System.out.println("Inside the afterScript to, Script is " + script);
	}

	public void onException(Throwable throwable, WebDriver driver) {
		System.out.println("Caught Exception");
		try {
			String fn = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString(); //Or the name you want. I suggest don't use static name.
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File("screenshots/" + fn.toString() + ".jpg"));

		} catch (Exception e) {
			System.out.println("Unable to Save");
		}
	}

}
