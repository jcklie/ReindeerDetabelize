package com.mrklie.detabelize.exceptions;

import java.io.IOException;

/**
 * 
 * @author Jan-Christoph Klie
 *
 */
public class PDFParseException extends IOException {
	
	private static final long serialVersionUID = 3416667900097587278L;

	public PDFParseException(String string) {
		super(string); 
	}
}
