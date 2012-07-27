package com.mrklie.detabelize.components.interfaces;

import java.io.File;

import com.mrklie.detabelize.exceptions.PDFParseException;

/**
 * Works as an adapter to PDF extraction tools
 * 
 * @author Jan-Christoph Klie
 * 
 */
public interface IPDFExtractor {

	/**
	 * Parses a given file identified by its path
	 * 
	 * @param f The file to parse
	 * @return the extracted text
	 * @throws PDFParseException
	 */
	String extractText(File f) throws PDFParseException;

}
