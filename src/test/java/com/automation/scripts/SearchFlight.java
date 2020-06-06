package com.automation.scripts;

import org.testng.annotations.Test;

import com.automation.businesslogic.CommonBusinessLibrary;

public class SearchFlight extends CommonBusinessLibrary{

	@Test
	public void searchFlight() throws Throwable {
		
		//Click flights link in home page 
		clickFlights();
		
		//Search a flight
		searchFlights();
		
	}

}
