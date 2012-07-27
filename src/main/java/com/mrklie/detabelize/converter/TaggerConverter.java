package com.mrklie.detabelize.converter;

import com.beust.jcommander.IStringConverter;
import com.mrklie.detabelize.components.interfaces.ITagger;
import com.mrklie.detabelize.components.tagger.SimpleTagger;

/**
 * 
 * @author Jan-Christoph Klie
 *
 */
public class TaggerConverter implements IStringConverter<ITagger> {

	@Override
	public ITagger convert(String value) {
		ITagger tagger;
		
		if ( value.trim().equalsIgnoreCase( "german" )  ) {
			tagger = new SimpleTagger( SimpleTagger.GERMAN );
		} else if ( value.trim().equalsIgnoreCase( "english" )) {
			tagger = new SimpleTagger( SimpleTagger.ENGLISH );
		} else {
			tagger = new SimpleTagger( SimpleTagger.ENGLISH );
		}
		
		return tagger;
	}

}
