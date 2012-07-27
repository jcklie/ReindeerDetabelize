package com.mrklie.detabelize.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * @author Jan-Christoph Klie
 *
 */
public class SystemCall {
	
	private String stdOut;
	private String stdErr;
	
	/**
	 * @return the stdOut
	 */
	public String getStdOut() {
		return stdOut;
	}
	
	/**
	 * @return the stdErr
	 */
	public String getStdErr() {
		return stdErr;
	}
	
	public void execute(String command)  {
		Process p;
		BufferedReader stdError;
		BufferedReader stdInput;
		StringBuffer sb = new StringBuffer();
		StringBuffer errorMsg = new StringBuffer();
		String s;		
		
		try {
			p = Runtime.getRuntime().exec(command);
			stdInput = new BufferedReader(new InputStreamReader( p.getInputStream()));
			stdError = new BufferedReader(new InputStreamReader( p.getErrorStream()));	
			
			while ((s = stdInput.readLine()) != null) {				
				sb.append(s).append("\n");
			}
			
			if ( !stdError.ready() ) {				
				while ((s = stdInput.readLine()) != null) {
					errorMsg.append(s).append("\n");
				}				
			}
			
		} catch (IOException e) {			
			e.printStackTrace();
		} finally {
			stdOut = sb.toString();
			stdErr = errorMsg.toString();
		}		
	}

	
	

}
