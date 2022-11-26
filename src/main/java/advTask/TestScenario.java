package advTask;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestScenario {

	private RemoteWebDriver driver;
	private String Status = "passed";

	@BeforeMethod
	public void setup(Method m, ITestContext ctx) throws MalformedURLException {
		String username = System.getenv("LT_USERNAME") == null ? "thumarbhavin.786" : System.getenv("LT_USERNAME");
		String authkey = System.getenv("LT_ACCESS_KEY") == null ? "TBcrP05QkFLjiomKmAgX9y4fRdgikSsx7CsAFN5pRFoy8Iu2fj"
				: System.getenv("LT_ACCESS_KEY");
		String hub = "@hub.lambdatest.com/wd/hub";

		DesiredCapabilities caps = new DesiredCapabilities();
		// Configure your capabilities here
		caps.setCapability("platform", "Windows 10");
		caps.setCapability("browserName", "Chrome");
		caps.setCapability("version", "86.0");
		caps.setCapability("resolution", "1024x768");
		caps.setCapability("build", "TestNG With Java");
		caps.setCapability("name", m.getName() + this.getClass().getName());
		caps.setCapability("plugin", "git-testng");

		String[] Tags = new String[] { "Feature", "Magicleap", "Severe" };
		caps.setCapability("tags", Tags);

		driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps);
	}

	@Test
	public void TestScenario1() throws InterruptedException {
		driver.get("https://www.lambdatest.com/");
		driver.manage().window().maximize();
		Thread.sleep(2000);

		WebElement firstresult = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.xpath(
						"//div[@class='text-center mt-25']/a[@href='https://www.lambdatest.com/integrations']")));

		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement integrations = driver.findElement(
				By.xpath("//div[@class='text-center mt-25']/a[@href='https://www.lambdatest.com/integrations']"));
		js.executeScript("arguments[0].scrollIntoView(true);", integrations);
		js.executeScript("arguments[0].style.border='2px solid red'", integrations);

		String clicklnk = Keys.chord(Keys.CONTROL, Keys.ENTER);
		integrations.sendKeys(clicklnk);

		Set<String> allwindows = driver.getWindowHandles();
		System.out.println("All window handles : " + allwindows);
		String current = driver.getWindowHandle();
		System.out.println("Current window handle : " + current);

		ArrayList<String> window1 = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(window1.get(1));

		String Actual_url = driver.getCurrentUrl();
		String Expected_url = "https://www.lambdatest.com/integrations";

		if (Actual_url.equals(Expected_url)) {
			System.out.println("url is matched");
		} else {
			System.out.println("url is not matched");
		}

		WebElement codeless = driver.findElement(By.xpath(
				"//li[4]/a[@class='block px-14 py-9 font-normal text-size-14 hover:bg-black hover:text-white ']"));

		js.executeScript("arguments[0].scrollIntoView(true);", codeless);
		js.executeScript("arguments[0].style.border='2px solid red'", codeless);

		WebElement learn_more = driver
				.findElement(By.xpath("//a[@href='https://www.lambdatest.com/support/docs/testingwhiz-integration/']"));
		learn_more.click();
		String Expected_title = "TestingWhiz Integration | LambdaTest";
		String Actual_title = driver.getCurrentUrl();

		if (Expected_title.equals(Actual_title)) {
			System.out.println("title is matched");
		} else {
			System.out.println("Title is not matched!");
		}

		driver.switchTo().window(window1.get(1)).close();
		driver.switchTo().window(window1.get(0));

		String url = "https://www.lambdatest.com/";
		String url1 = url.replaceAll("https://www.lambdatest.com/", "https://www.lambdatest.com/blog");
		driver.navigate().to(url1);

		WebElement community = driver
				.findElement(By.xpath("//li[@id='menu-item-10121']/a[@href='https://community.lambdatest.com/']"));
		js.executeScript("arguments[0].style.border='2px solid red'", community);
		community.click();

		String Exp_community_url = "https://community.lambdatest.com/";
		String Act_community_url = driver.getCurrentUrl();

		if (Exp_community_url.equals(Act_community_url)) {
			System.out.println("Community url is matched");
		} else
			System.out.println("Community url is not matched!");

	}

	@AfterMethod

	public void tearDown() {
		driver.executeScript("lambda-status=" + Status);
		driver.quit();
	}

}
