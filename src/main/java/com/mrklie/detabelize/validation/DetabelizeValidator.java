package com.mrklie.detabelize.validation;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

/**
 * 
 * @author Jan-Christoph Klie
 *
 */
public class DetabelizeValidator implements IParameterValidator {
	
	@Override
	public void validate(String name, String value) throws ParameterException {			
		if( !( value.equalsIgnoreCase("sentence") || value.matches("^num\\d+$")) ) {
			throw new ParameterException( name + " has to be either 'sentence' or num follwed by the # of allowed number per sentence, e.g. num42");
		}
		
	}

}
