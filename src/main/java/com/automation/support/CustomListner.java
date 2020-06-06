package com.automation.support;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

public class CustomListner extends AbstractWebDriverEventListener {

	
	public void beforeFindBy(By by, WebElement element, WebDriver driver){
		
		WebElement elem = driver.findElement(by);
		if (driver instanceof JavascriptExecutor) {
		   ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='4px solid yellow'", elem);
		}
	}
}

