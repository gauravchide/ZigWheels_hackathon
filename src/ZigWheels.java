import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
//import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
//import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.annotations.Parameters;

public class ZigWheels {
	WebDriver driver;
	String winHandleBefore = null;
	public static int price_value = 0;

	public void wait_sec() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@BeforeClass
	@Parameters("browser")
	public void openBrowser(String browser) {
		// Check if parameter passed from TestNG is 'firefox'
		if (browser.equalsIgnoreCase("firefox")) {
			// set path for firefoxdriver
			System.setProperty("webdriver.gecko.driver",
					"C:\\Users\\HP\\eclipse-workspace\\ZigWheels_hackathon\\driver\\geckodriver-v0.29.0-win64\\geckodriver.exe");
			FirefoxOptions options = new FirefoxOptions();

			// disable notification in fireox
			options.addArguments("--disable-notifications");
			driver = new FirefoxDriver(options);
			
			// maximize window
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			System.out.println("\n\n");

		}

		else if (browser.equalsIgnoreCase("Chrome")) {

			// set path for chrome driver
			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\HP\\eclipse-workspace\\ZigWheels_hackathon\\driver\\chromedriver_win32 (1)\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();

			// disable notification in chrome
			options.addArguments("--disable-notifications");

			// create chrome instance
			driver = new ChromeDriver(options);

			// maximize window
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

		}

		// set URL of the website
		String URL = "https://www.zigwheels.com/";

		// launch the website
		driver.get(URL);

		// get the handle instance of parent window
		winHandleBefore = driver.getWindowHandle();

	}

	@Test(priority = 1)
	public void verifyLoginErrorMessage() {

		wait_sec();

		// Find login button and click
		driver.findElement(By.id("forum_login_wrap_lg")).click();
		wait_sec();

		WebDriverWait wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("googleSignIn"))));

		// Find login with google button and click
		driver.findElement(By.id("googleSignIn")).click();
		wait_sec();

		// Switch access to popup window
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}

		// Find Email text box and enter the mailId 
		
		
		  WebDriverWait wait2 = new WebDriverWait(driver, 100);
		  wait2.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id=\"identifierId\"]"))));
		  driver.findElement(By.xpath("//*[@id=\"identifierId\"]")).sendKeys("nalslqwle@gmial.com");
		 

		// Find next button and click
		driver.findElement(By.xpath(
				"/html/body/div[1]/div[1]/div[2]/div/div[2]/div/div/div[2]/div/div[2]/div/div[1]/div/div/button"))
				.click();

		// Capture Error message
		String msg = driver.findElement(By.xpath(
				"//*[@id=\"view_container\"]/div/div/div[2]/div/div[1]/div/form/span/section/div/div/div[1]/div/div[2]/div[2]/div"))
				.getText();
		System.out.println("********************************************************");
		System.out.println("CAPTURED ERROR MESSAGE-----" + msg);
		System.out.println("********************************************************");
		wait_sec();

		// Close the popup window
		driver.close();

	}

	@Test(priority = 2)
	public void extractPopularBike() {
		// switch to home page
		driver.switchTo().window(winHandleBefore);
		wait_sec();

		// close the login popup
		driver.findElement(By.id("report_submit_close_login")).click();
		wait_sec();

		// Click on Used cars
		driver.findElement(By.xpath("//*[@id=\"headerNewNavWrap\"]/div[2]/ul/li[5]/a")).click();
		//*[@id="headerNewNavWrap"]/div[2]/ul/li[5]/ul/li/div[2]/ul/li[5]/span
		wait_sec();

		// Click on Chennai
		driver.findElement(By.xpath("//*[@id=\"popularCityList\"]/li[8]/a")).click();
		wait_sec();

		System.out.println("\n\n");

		// Print popular models in a list
		
		System.out.println("POPULAR MODELS OF USED CARS IN CHENNAI");
		System.out.println("********************************************************");
		List<WebElement> models = driver
				.findElements(By.xpath("/html/body/div[11]/div/div[1]/div[1]/div[1]/div[2]/ul/li[2]/div[2]/div[4]/ul"));
		for (WebElement webElement : models) {
			String name = webElement.getText();
			System.out.println(name);
		}
		System.out.println("********************************************************");

		// Switch back to Homepage using logo
		//driver.findElement(By.xpath("//*[@class='zw i-b mt-10']")).click();

	}

	@Test(priority = 3)
	public void upcomingHondaBikes() {

		String temp;
		int count = 1;
		wait_sec();

		// click NewBike menu on home page
		Actions actions = new Actions(driver);
		WebElement newBikeElement = driver.findElement(By.xpath("//*[@id=\"headerNewNavWrap\"]/div[2]/ul/li[3]/a"));
		actions.moveToElement(newBikeElement).build().perform();

		// click on Upcoming Bike in submenu
		WebElement upComingBikeElement = driver
				.findElement(By.xpath("//*[@id=\"headerNewNavWrap\"]/div[2]/ul/li[3]/ul/li/div[1]/ul/li[3]/span"));
		actions.moveToElement(upComingBikeElement);
		actions.click().build().perform();
		wait_sec();

		// Select Honda in manufacturer dropdown
		Select dropH = new Select(driver.findElement(By.xpath("//*[@id=\"makeId\"]")));
		dropH.selectByVisibleText("Honda");
		wait_sec();

		// click on view more bikes
		WebElement viewMore = driver.findElement(
				By.xpath("//*[@id='carModels']/ul/li[18]/span[@data-track-label='view-more-models-button']"));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", viewMore);
		wait_sec();

		// print upcoming honda bikes less than 4lakh
		System.out.println("\n\n");
		System.out.println("LIST OF UPCOMING BIKE IN HONDA UNDER 4 LAKHS");
		System.out.println("********************************************************");

		List<WebElement> price = driver.findElements(By.xpath("//*[@id=\"carModels\"]/ul/li[@data-price]"));
		//System.out.println("size of price" + price.size());
 
		for (WebElement ele : price) {
			temp = ele.getAttribute("data-price");

			price_value = Integer.parseInt(temp.trim());

			if (price_value < 400000) {
				//System.out.println("********************************************************");
				System.out.println("BIKE " + count + " DETAILS AS FOLLOWS");
				System.out.println("\n");
				System.out.println(ele.getText());
				count++;
				System.out.println("********************************************************");
			}

		}
		
	}

	@AfterClass
	public void closeBrowser() {
		driver.quit();
	}
}
