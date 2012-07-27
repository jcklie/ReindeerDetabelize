package com.mrklie.detabelize.components.interfaces;

/**
 * This interface describes the methods needed to work as a detabelizer in
 * reindeer-detabelize.
 * 
 * @author Jan-Christoph Klie
 * 
 */
public interface IDetabelizer {

	/**
	 * Detabelizes the text given and returns it
	 * @param text The text to detabelize
	 * @return The detabelized text.
	 */
	public String detabelize(String text);

}
