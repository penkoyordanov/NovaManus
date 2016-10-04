package pageObjects.Common;

import listeners.EventHandler;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import static java.util.logging.Level.INFO;

public class Browser {

	private static WebDriver wDriver ;

	private static EventFiringWebDriver edriver;
	private static EventHandler oc;

	public static EventFiringWebDriver driver() {
		return edriver;
	}

	public static void initChrome() {
		ChromeOptions options=new ChromeOptions();
		options.addArguments("chrome.switches","--disable-extensions");
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
		wDriver = new ChromeDriver(options);
		edriver = new EventFiringWebDriver(wDriver);
		oc=new EventHandler();
		edriver.register(oc);
		edriver.manage().deleteAllCookies();
		edriver.manage().window().maximize();
	}

	public static void initFF(){
		System.setProperty("webdriver.gecko.driver", "D:\\webdriver\\drivers\\geckodriver.exe");
		wDriver = new FirefoxDriver();
		edriver = new EventFiringWebDriver(wDriver);
		oc=new EventHandler();
		edriver.register(oc);
		edriver.manage().deleteAllCookies();
		edriver	.manage().window().maximize();
	}
	public static void initEdge(){
		System.setProperty("webdriver.edge.driver", "C:/Program Files (x86)/Microsoft Web Driver/MicrosoftWebDriver.exe");
        EdgeOptions options=new EdgeOptions();
        options.setPageLoadStrategy("eager");
		wDriver = new EdgeDriver();
		edriver = new EventFiringWebDriver(wDriver);
		oc=new EventHandler();
		edriver.register(oc);
		edriver	.manage().window().maximize();
		/*System.setProperty("webdriver.edge.driver","C:/Program Files (x86)/Microsoft Web Driver/MicrosoftWebDriver.exe");
		options=new EdgeOptions();
		options.setPageLoadStrategy("eager");
		wDriver = new EdgeDriver();*/
	}

	/*public static void initPhantomJs(){
		Capabilities caps = new DesiredCapabilities();
		((DesiredCapabilities) caps).setJavascriptEnabled(false);
//		((DesiredCapabilities) caps).setCapability("takesScreenshot", true);
		((DesiredCapabilities) caps).setCapability("load-images", false);
		((DesiredCapabilities) caps).setCapability("webdriver-loglevel", INFO);
		((DesiredCapabilities) caps).setCapability(CapabilityType.SUPPORTS_FINDING_BY_CSS,true);
		((DesiredCapabilities) caps).setCapability(
				PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
				"D:/webdriver/phantomjs-2.1.1-windows/bin/phantomjs.exe"
		);
		wDriver = new PhantomJSDriver(caps);
		edriver = new EventFiringWebDriver(wDriver);
		EventHandler oc=new EventHandler();
		edriver.register(oc);
	}*/


	public static void open(String url) {
		edriver.get(url);
	}

	public static void quit() {
		edriver.quit();
	}


	public static void close() {
		edriver.close();
	}

}
