package com.mrklie.detabelize.validation;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

/**
 * 
 * @author Jan-Christoph Klie
 *
 */
public class FileEndingValidator implements IParameterValidator {

	@Override
	public void validate(String name, String value) throws ParameterException {
		if( !value.matches("^[a-zA-Z0-9]+$")) {
			throw new ParameterException(name + " should be a valid file ending and only consist of characters and/or digits! Found: " + value);
		}
		
	}

}
