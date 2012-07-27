package com.mrklie.detabelize.test;

import junit.framework.TestCase;

import org.junit.Test;

import com.beust.jcommander.IStringConverter;
import com.mrklie.detabelize.components.detabelizer.NumberDetabelizer;
import com.mrklie.detabelize.components.detabelizer.SentenceDetabelizer;
import com.mrklie.detabelize.components.interfaces.IDetabelizer;
import com.mrklie.detabelize.converter.DetabelizerConverter;

/**
 * 
 * @author Jan-Christoph Klie
 *
 */
public class DetabelizerConverterTest extends TestCase {
	
	IStringConverter<IDetabelizer> c = new DetabelizerConverter();
	
	public DetabelizerConverterTest(String testName) {
		super(testName);
	}

	@Test
	public void test() {
		IDetabelizer d;
		NumberDetabelizer n;
		
		/*
		 * Test sentence detabelizer
		 */
		
		d = c.convert("sentence");
		
		assertTrue( SentenceDetabelizer.class.isInstance(d) );		
		assertFalse( NumberDetabelizer.class.isInstance(d) );
		
		/*
		 * Test number detabelizer
		 */
		
		d = c.convert("num42");
		assertFalse( SentenceDetabelizer.class.isInstance(d) );		
		assertTrue( NumberDetabelizer.class.isInstance(d) );
		
		n =  (NumberDetabelizer) c.convert("num42");
		
		assertEquals(42, n.getNumsAllowed());
		
		/*
		 * Test wrong input
		 */
		
		d = c.convert("num-42");
		assertTrue( SentenceDetabelizer.class.isInstance(d) );		
		assertFalse( NumberDetabelizer.class.isInstance(d) );
		
		d = c.convert("foo");
		assertTrue( SentenceDetabelizer.class.isInstance(d) );		
		assertFalse( NumberDetabelizer.class.isInstance(d) );
	}

}
