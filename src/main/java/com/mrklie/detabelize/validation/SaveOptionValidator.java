package com.mrklie.detabelize.validation;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

/**
 * 
 * @author Jan-Christoph Klie
 *
 */
public class SaveOptionValidator implements IParameterValidator {
	
	private static List<String> options;
	
	static {
		options = new ArrayList<String>();
		options.add("none");
		options.add("detabelized");
		options.add("tagged");
	}

	@Override
	public void validate(String name, String value) throws ParameterException {
		if( !options.contains(value.toLowerCase().trim())) {
			throw new ParameterException(name + " has to be one of this: " + options + " ! Found: " + value.toLowerCase().trim());
		}
		
	}
	
	

}
