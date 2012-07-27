package com.mrklie.detabelize.components.extractor;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mrklie.detabelize.components.interfaces.IPDFExtractor;
import com.mrklie.detabelize.exceptions.PDFParseException;
import com.mrklie.detabelize.util.SystemCall;

/**
 * Wraps the system calls to pdf2text
 * 
 * @author Jan-Christoph Klie
 * 
 */
public class PDF2TextExtractor implements IPDFExtractor {
	
	private final Log log = LogFactory.getLog( getClass());
	
	private final static String command = "pdftotext -raw -enc UTF-8 -nopgbrk ";
	
	public  static final String OS;

	static {
		OS = System.getProperty("os.name").toLowerCase();
	}

	@Override
	public String extractText(File f) throws PDFParseException {
		
		log.debug("######### PDFExtracting #########");
		
		String path = f.getAbsolutePath();
		String parseCommand;
		
		if ( OS.indexOf("win") >= 0 ) {
			parseCommand = (command + "\"" + path + "\" -");
		} else {
			parseCommand = command + path.replaceAll("\\s", "\\\\ ") + " -";
		}
		
		log.debug("Extracting pdf with command: " + parseCommand);
		
		SystemCall call = new SystemCall();
		call.execute(parseCommand);
		
		if( call.getStdErr().trim().length() > 0 ) {
			log.debug("Errors:" + call.getStdOut());
			throw new PDFParseException( call.getStdErr());
		}

		log.debug("Extracting finished!");
		return call.getStdOut();
	}
	
	public String toString() {
		return "I extract the text from PDF files by using pdf2text. But only if you specfified file ending to PDF";
	}

}
