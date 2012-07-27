package com.mrklie.detabelize.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 * 
 * @author Jan-Christoph Klie
 */
public class AppTest extends TestCase {
	
	/**
	 * 
	 * Create the test case
	 * 
	 * @param testName name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		TestSuite s = new TestSuite();
		s.addTest(new DetabelizerConverterTest("test"));
		s.addTest(new PDF2TextParserTest("test"));
		s.addTest(new TaggerConverterTest("test"));

		return s;
	}

}
