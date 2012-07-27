package com.mrklie.detabelize.test;

import junit.framework.TestCase;

import org.junit.Test;

import com.beust.jcommander.IStringConverter;
import com.mrklie.detabelize.components.interfaces.ITagger;
import com.mrklie.detabelize.components.tagger.SimpleTagger;
import com.mrklie.detabelize.converter.TaggerConverter;

/**
 * 
 * @author Jan-Christoph Klie
 *
 */
public class TaggerConverterTest extends TestCase {
	
	IStringConverter<ITagger> c = new TaggerConverter();
	
	public TaggerConverterTest(String testName) {
		super(testName);
	}

	@Test
	public void test() {
		SimpleTagger t;
		
		/*
		 * Test german
		 */
		
		t = (SimpleTagger) c.convert("german");		
		assertEquals( t.getModel(), SimpleTagger.GERMAN);	
		
		t = (SimpleTagger) c.convert("GERMAN");		
		assertEquals( t.getModel(), SimpleTagger.GERMAN);
		
		t = (SimpleTagger) c.convert("geRMaN");		
		assertEquals( t.getModel(), SimpleTagger.GERMAN);
		
		/*
		 * Test english
		 */
		
		t = (SimpleTagger) c.convert("english");
		assertEquals( t.getModel(), SimpleTagger.ENGLISH);
		
		t = (SimpleTagger) c.convert("ENGLISH");
		assertEquals( t.getModel(), SimpleTagger.ENGLISH);
		
		t = (SimpleTagger) c.convert("ENglISh");
		assertEquals( t.getModel(), SimpleTagger.ENGLISH);
		
		/*
		 * Test misc
		 */
		
		t = (SimpleTagger) c.convert("foo");
		assertEquals( t.getModel(), SimpleTagger.ENGLISH);
		
		t = (SimpleTagger) c.convert("bar");
		assertEquals( t.getModel(), SimpleTagger.ENGLISH);
		
	}

}
