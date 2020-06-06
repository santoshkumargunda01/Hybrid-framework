package com.automation.core;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.automation.report.Report;
import com.automation.report.ReporterConstants;
import com.automation.support.CustomListner;
import com.automation.support.ExcelReader;

 
public class TestBase {
	public static final Logger LOG = Logger.getLogger(TestBase.class);
	protected WebDriver WebDriver = null;
	public EventFiringWebDriver driver=null;
	protected Report reporter = null;

	/*cloud platform*/
	public String browser = null;
	public String version = null;
	public String platform = null;
	public String environment = null;
	public String localBaseUrl = null;
	public String cloudBaseUrl = null;
	public String userName = null;
	public String accessKey = null;
	public String cloudImplicitWait = null;
	public String cloudPageLoadTimeOut = null;
	public String updateJira = null;
	public String buildNumber = "";
	public String jobName = "";
	public String executedFrom = null; 
	public String executionType = null;
	public String suiteExecution = null;
	public String suiteStartTime = null;

	public static ExcelReader xlsrdr = new ExcelReader(System.getProperty("user.dir")+"\\TestData\\TestData.xls","Test_Data");

	@Parameters({"executionType","suiteExecuted"})
	@BeforeSuite(alwaysRun=true)
	public void beforeSuite(ITestContext ctx,String type,String suite) throws Throwable{
		executionType=type;
		suiteExecution=suite;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd_MMM_yyyy hh mm ss SSS");
		String formattedDate = sdf.format(date);
		suiteStartTime = formattedDate.replace(":","_").replace(" ","_");
		System.out.println("Suite time ==============>"+suiteStartTime);

	}
	@BeforeClass(alwaysRun=true)
	@Parameters({"automationName","browser","browserVersion","environment","platformName"})
	public void beforeTest(String automationName, String browser, String browserVersion,String environment,String platformName) throws IOException, InterruptedException
	{
		
		this.browser = browser;
		this.version = browserVersion;
		this.platform = platformName;
		this.environment = environment;
		this.localBaseUrl = ReporterConstants.APP_BASE_URL;

		this.userName = ReporterConstants.SAUCELAB_USERNAME;
		this.accessKey = ReporterConstants.SAUCELAB_ACCESSKEY;
		this.executedFrom = System.getenv("COMPUTERNAME");
		this.cloudImplicitWait = ReporterConstants.CLOUD_IMPLICIT_WAIT;
		this.cloudPageLoadTimeOut = ReporterConstants.CLOUD_PAGELOAD_TIMEOUT;        


		if(environment.equalsIgnoreCase("local"))
		{
			this.setWebDriverForLocal(browser);
		}
		if(environment.equalsIgnoreCase("cloudSauceLabs"))
		{

			this.setRemoteWebDriverForCloudSauceLabs();	           
		}
		
		reporter = Report.getCReporter(browser, platformName , environment, true);
		this.driver = new EventFiringWebDriver(this.WebDriver);
		CustomListner myListener = new CustomListner();
		this.driver.register(myListener);
		driver.get(ReporterConstants.APP_BASE_URL);
		//Driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		reporter.calculateSuiteStartTime();
	}


	@Parameters({"browser"})
	@AfterClass(alwaysRun=true)
	public void afterTest(String browser) throws Exception
	{
		if (browser.equalsIgnoreCase("firefox")) {
			driver.quit();			
		}
		else{
			driver.quit();
		}

		reporter.calculateSuiteExecutionTime();
		reporter.createHtmlSummaryReport(ReporterConstants.APP_BASE_URL,true);
		reporter.closeSummaryReport();


	}

	@BeforeMethod

	public void beforeMethod(Method method)
	{		
		reporter.initTestCase(this.getClass().getName().substring(0,this.getClass().getName().lastIndexOf(".")), method.getName(), null, true);
	}

	@AfterMethod
	public void afterMethod() throws IOException
	{					
		reporter.calculateTestCaseExecutionTime();		
		reporter.closeDetailedReport();		
		reporter.updateTestCaseStatus();
	}

	public void setWebDriverForLocal(String browser) throws IOException, InterruptedException
	{
		switch(browser)
		{		
		case "ie":
			System.out.println("iam in case IE");
			DesiredCapabilities capab = DesiredCapabilities.internetExplorer();
			capab.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			capab.internetExplorer().setCapability("ignoreProtectedModeSettings", true);
			File file = new File("Drivers\\IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver",file.getAbsolutePath());
			capab.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capab.setJavascriptEnabled(true);
			capab.setCapability("requireWindowFocus", true);
			capab.setCapability("enablePersistentHover", false);
			this.WebDriver = new InternetExplorerDriver(capab);			
			Thread.sleep(1000);
			break;
		case "chrome":
			System.out.println("iam in case Chrome");
			System.setProperty("webdriver.chrome.driver","Drivers\\chromedriver.exe");
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("test-type");
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			this.WebDriver = new ChromeDriver(capabilities);
			break;
		}

	}

	private void setRemoteWebDriverForCloudSauceLabs() throws IOException, InterruptedException
	{
		if (this.browser.equalsIgnoreCase("Safari")) {
			DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
			desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME, this.browser);
			desiredCapabilities.setCapability(CapabilityType.VERSION, this.version);
			desiredCapabilities.setCapability(CapabilityType.PLATFORM, this.platform);
			desiredCapabilities.setCapability("username", this.userName);
			desiredCapabilities.setCapability("accessKey", this.accessKey);
			desiredCapabilities.setCapability("accessKey", this.accessKey);
			desiredCapabilities.setCapability("name", this.executedFrom + " - " /*+ this.jobName + " - " + this.buildNumber*/+this.platform +" - " +this.browser);
			URL commandExecutorUri = new URL("http://ondemand.saucelabs.com/wd/hub");
			for(int i=1;i<=10;i++){

				try{
					this.WebDriver = new RemoteWebDriver(commandExecutorUri, desiredCapabilities);

					break;
				}catch(Exception e1){
					Runtime.getRuntime().exec("taskkill /F /IM Safari.exe");
					Thread.sleep(3000);
					Runtime.getRuntime().exec("taskkill /F /IM plugin-container.exe");
					Runtime.getRuntime().exec("taskkill /F /IM WerFault.exe"); 

					continue;   

				}
			}
		}
		else{

			DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
			desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME, this.browser);
			desiredCapabilities.setCapability(CapabilityType.VERSION, this.version);
			desiredCapabilities.setCapability(CapabilityType.PLATFORM, this.platform);
			desiredCapabilities.setCapability("username", this.userName);
			desiredCapabilities.setCapability("accessKey", this.accessKey);
			desiredCapabilities.setCapability("accessKey", this.accessKey);
			desiredCapabilities.setCapability("name", this.executedFrom + " - " /*+ this.jobName + " - " + this.buildNumber*/+this.platform +" - " +this.browser);
			URL commandExecutorUri = new URL("http://ondemand.saucelabs.com/wd/hub");
			this.WebDriver = new RemoteWebDriver(commandExecutorUri, desiredCapabilities);
		}
	}

	
}
