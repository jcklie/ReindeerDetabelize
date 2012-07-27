package com.mrklie.detabelize.components.interfaces;

/**
 * This interface describes the needed functions for a Tagger as used in
 * reindeer-detabelize
 * 
 * @author Jan-Christoph Klie
 * 
 */
public interface ITagger {

	/**
	 * Tags the given text and returns the result.
	 * 
	 * @param text The text to tag.
	 * @return The tagged text
	 */
	public String tag(String text);

}
