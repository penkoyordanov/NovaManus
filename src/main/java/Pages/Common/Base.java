package Pages.Common;

import org.openqa.selenium.*;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Base {
	protected WebDriverWait wait;

    protected EventFiringWebDriver eDriver;

	protected Base(EventFiringWebDriver eDriver){
		this.eDriver=eDriver;
	}

	protected final void visit (String url){
		eDriver.get(url);
	}

	protected final WebElement find (By locator){
		return eDriver.findElement(locator);
	}

	protected final List<WebElement> findElements (By locator){
		return eDriver.findElements(locator);
	}

	protected final void click(By locator){
		find(locator).click();
	}

	protected final void clear(By locator){
		find(locator).clear();
	}

	protected final void type(String inputText, By locator){
		for (char ch : inputText.toCharArray()) {
			find(locator).sendKeys(Character.toString(ch));
		}
	}

	protected final void typeJS(String inputText, By locator){
		find(locator).sendKeys(inputText);
	}

	protected final String getTextOfElement(By locator){
		return find(locator).getText();
	}

	protected final Boolean isDisplayed(By locator){
		try {
			return find(locator).isDisplayed();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	protected final Boolean isDisplayed(By locator, int MaxTime){
		try {
			waitFor(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator),MaxTime);
		} catch (org.openqa.selenium.TimeoutException e) {
			return false;
		}
		return true;
	}

	private void waitFor(ExpectedCondition<List<WebElement>> condition, Integer timeout){
		timeout=timeout !=null?timeout:5;
		WebDriverWait wait=new WebDriverWait(eDriver, timeout);
		wait.until(condition);
	}

	protected final void waitUntilElementDisappear(final By locator, Integer timeout){
		timeout=timeout !=null?timeout:5;
		new WebDriverWait(eDriver, timeout).until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}
}
