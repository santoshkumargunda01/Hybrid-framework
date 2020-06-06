package com.automation.scripts;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.automation.businesslogic.CommonBusinessLibrary;

public class Register extends CommonBusinessLibrary {


	@Test	
	public void registerNewUser(ITestContext testContext) throws Throwable {

		try{	

			//Click Register link home page
			clickRegister();
			//to Register a new user
			register();

		}
		catch(Exception e){			
			e.printStackTrace();
		}

	}

}
