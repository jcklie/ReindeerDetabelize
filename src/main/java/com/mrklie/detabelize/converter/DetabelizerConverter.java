package com.mrklie.detabelize.converter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.beust.jcommander.IStringConverter;
import com.mrklie.detabelize.components.detabelizer.NumberDetabelizer;
import com.mrklie.detabelize.components.detabelizer.SentenceDetabelizer;
import com.mrklie.detabelize.components.interfaces.IDetabelizer;

/**
 * 
 * @author Jan-Christoph Klie
 *
 */
public class DetabelizerConverter implements IStringConverter<IDetabelizer> {
	
	private static final Pattern p = Pattern.compile("\\d+$");
	private static final String numConverterPattern = "^num\\d+$";
	private static final String sentenceConverter = "sentence";

	@Override
	public IDetabelizer convert(String value) {
		IDetabelizer detabelizer = new SentenceDetabelizer();
		
		if( value.equalsIgnoreCase(sentenceConverter) ) {
			detabelizer = new SentenceDetabelizer();
		} else if( value.matches( numConverterPattern )) {
			Matcher m = p.matcher(value);
			int nums = m.find() ? Integer.parseInt(m.group() ) : 1;
			if ( nums >= 0) {
				detabelizer = new NumberDetabelizer(nums);
			}
				
		} 
		
		return detabelizer;
	}
	
	

}
