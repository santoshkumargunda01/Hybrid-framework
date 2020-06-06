
package com.automation.core;
import org.openqa.selenium.By;

public class CoreActions extends TestBase {
	private final String msgClickSuccess = "Successfully Clicked On ";
	private final String msgClickFailure = "Unable To Click On ";
	private final String msgTypeSuccess = "Successfully Typed On ";
	private final String msgTypeFailure = "Unable To Type On ";
	private final String msgIsElementFoundFailure = "Unable To Found Element ";
	
	
	/**
	 * Wrapper method to Selenium method click
	 * @param locator
	 * @param locatorName
	 * @return
	 * @throws Throwable
	 */
	public boolean click(By locator, String locatorName) throws Throwable
	{
		boolean status = false;
		try
		{		
			this.driver.findElement(locator).click();			
			this.reporter.SuccessReport("Click on "+locatorName , this.msgClickSuccess + locatorName);
			status = true;
		}
		catch(Exception e)
		{
			status = false;
			LOG.info(e.getMessage());
			this.reporter.failureReport("Click on "+locatorName, this.msgClickFailure + locatorName, this.driver);

		}
		return status;

	}
	/**
	 * Method to verify given element present on the screen
	 * @param by
	 * @param locatorName
	 * @param expected
	 * @return
	 * @throws Throwable
	 */

	public boolean isElementPresent(By by, String locatorName,boolean expected) throws Throwable
	{
		boolean status = false;
		try
		{
			this.driver.findElement(by);
			status = true;
		}
		catch(Exception e)
		{
			status = false;
			LOG.info(e.getMessage());
			if(expected == status)
			{
				this.reporter.SuccessReport("isElementPresent", "isElementPresent");
			}
			else
			{
				this.reporter.failureReport("isElementPresent", this.msgIsElementFoundFailure + locatorName, this.driver);
			}
		}
		return status;
	}
	
	/**
	 * Method to type text in the given locator
	 * @param locator
	 * @param testdata
	 * @param locatorName
	 * @return
	 * @throws Throwable
	 */
	public boolean type(By locator, String testdata, String locatorName) throws Throwable
	{
		boolean status = false;
		try
		{
			this.driver.findElement(locator).sendKeys(testdata);
			this.reporter.SuccessReport("Enter text in "+locatorName , this.msgTypeSuccess + locatorName);
			status = true;
		}
		catch(Exception e)
		{
			status = false;
			LOG.info(e.getMessage());
			this.reporter.failureReport("Enter text in "+locatorName, this.msgTypeFailure + locatorName, this.driver);
		}

		return status;
	}
	
}
