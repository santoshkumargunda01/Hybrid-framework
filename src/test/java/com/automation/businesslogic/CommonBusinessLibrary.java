package com.automation.businesslogic;

import com.automation.core.CoreActions;
import com.automation.page.FlightFinderPage;
import com.automation.page.HomePage;
import com.automation.page.RegisterPage;

public class CommonBusinessLibrary extends CoreActions{
	

	public void enterUserName() throws Throwable {

		type(HomePage.txtUserName, "demo", "UserName");
	}

	public void enterPassword() throws Throwable {
		type(HomePage.txtPassword, "demo", "Password");

	}

	public void clickSubmit() throws Throwable {
		click(HomePage.btnSubmit, "Submit");

	}

	public void clickRegister() throws Throwable {
		click(HomePage.lnkRegister, "Register link home page");

	}
	
	public void clickFlights() throws Throwable {
		click(HomePage.lnkFlights, "Flights link home page");

	}
	
	
	


	public void register() {

		try {
			type(RegisterPage.txtFirstName,xlsrdr.getData("Register", "Firstname"), "First name");
			type(RegisterPage.txtLastName, xlsrdr.getData("Register", "Lastname"), "Last name");
			type(RegisterPage.txtPhone, xlsrdr.getData("Register", "Phone"), "Phone");
			type(RegisterPage.txtEmail, xlsrdr.getData("Register", "Email"), "Email");
			type(RegisterPage.txtAddress, xlsrdr.getData("Register", "Address1"), "Address");
			type(RegisterPage.txtCity, xlsrdr.getData("Register", "City"), "City");
			type(RegisterPage.txtState, xlsrdr.getData("Register", "State"), "State");
			type(RegisterPage.txtUserNameInfo, xlsrdr.getData("Register", "Username"), "User Name");			
			type(RegisterPage.txtPasswordInfo, xlsrdr.getData("Register", "Password"), "Password");
			type(RegisterPage.btnSubmit, xlsrdr.getData("Register", "Confirmpassword"), "Confirm password");			
			Thread.sleep(5000);

		} catch (Throwable e) {
			e.printStackTrace();
		}


 
	}

	
	
	public void searchFlights() throws Throwable {
		
		click(FlightFinderPage.btnContinue, "Continue button");
		click(FlightFinderPage.btnDepartContinue, "Continue in flight selection page");
		

	}

}


