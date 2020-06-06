package com.automation.report;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

public class ConfigFileReadWrite {
	@SuppressWarnings("finally")
	public synchronized static <T> Boolean write(String fileName, String key, T value) {
		Boolean successStatus = false;
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		Properties prop = null;
		try {
			try {
				bufferedReader = new BufferedReader(new FileReader(fileName));
				/*
				 * If FileNotFoundException Not Encountered, Then Read
				 * Properties In File
				 */
				if (bufferedReader != null) {
					/* Instantiate Properties */
					prop = new Properties();
					try {
						prop.load(bufferedReader);
					} catch (IOException e) {						
						return successStatus;
					}
				}
				try {
					bufferedWriter = new BufferedWriter(new FileWriter(
							fileName, false));
				} catch (IOException e) {					
					return successStatus;

				}

			}
			/* if File Does Not Exist, Then Create It and Open It for Write */
			catch (FileNotFoundException e) {
				try {
					bufferedWriter = new BufferedWriter(new PrintWriter(
							FileUtils.openOutputStream(new File(fileName),
									false)));
				} catch (IOException e1) {					
					return successStatus;
				}
			} catch (NullPointerException e) {
				
				return successStatus;
			}

			/*
			 * Instantiate Properties In Case, File Did Not Exist Previously,
			 * And Update New Properties
			 */
			if (prop == null) {
				prop = new Properties();
			}
			try {

				prop.put(key, value);				
				prop.store(bufferedWriter, null);				
				successStatus = true;

			} catch (IOException e) {				
				return successStatus;
			} catch (Exception e) {				
				return successStatus;
			}
		}
		finally {
			return false;
		}
	}	

	
	public synchronized static String read(String fileName, String key) {
		String keyValue = null;

		BufferedReader bufferedReader = null;
		Properties prop = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(fileName));
			/* Instantiate Properties */
			prop = new Properties();
			prop.load(bufferedReader);
			keyValue = prop.getProperty(key);
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {
			
		} catch (NullPointerException e) {
			
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {

				
			} catch (NullPointerException e) {
				
			}
		}
		
		return keyValue;
	}
	
	
}
