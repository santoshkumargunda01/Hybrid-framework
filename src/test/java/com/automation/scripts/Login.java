package com.automation.scripts;

import org.testng.annotations.Test;

import com.automation.businesslogic.CommonBusinessLibrary;

public class Login extends CommonBusinessLibrary{
		
	@Test
	public void login() throws Throwable {	
		//Login in to the application
		enterUserName();
		enterPassword();
		clickSubmit();
	}
	
}
