package com.mrklie.detabelize.validation;

import java.io.File;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

/**
 * 
 * @author Jan-Christoph Klie
 *
 */
public class DirectoryValidator implements IParameterValidator {

	@Override
	public void validate(String name, String value) throws ParameterException {
		File f = new File(value);
		if( !f.exists()) {
			throw new ParameterException("Parameter " + name + " should be an existing direcory! Found: " + value);
		}
	}

}
