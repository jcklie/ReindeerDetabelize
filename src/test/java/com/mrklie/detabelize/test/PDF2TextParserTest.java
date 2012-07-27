package com.mrklie.detabelize.test;

import java.io.File;

import junit.framework.TestCase;

import org.junit.Test;

import com.mrklie.detabelize.components.extractor.PDF2TextExtractor;
import com.mrklie.detabelize.components.interfaces.IPDFExtractor;
import com.mrklie.detabelize.exceptions.PDFParseException;

/**
 * 
 * @author Jan-Christoph Klie
 *
 */
public class PDF2TextParserTest extends TestCase {
	
	public PDF2TextParserTest(String testName) {
		super(testName);
	}

	@Test
	public void test() {
		IPDFExtractor parser = new PDF2TextExtractor();
		String s = null;
		try {
			s = parser.extractText( new File("testdata/simple.pdf"));
		} catch (PDFParseException e) {
			// TODO Auto-generated catch block

		}
		assertTrue( s.contains("Simple section"));
		assertTrue( s.contains("This is a simple text."));
	}

}
