package com.mrklie.detabelize.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.eval.NotImplementedException;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.mrklie.detabelize.components.extractor.PDF2TextExtractor;
import com.mrklie.detabelize.components.interfaces.IDetabelizer;
import com.mrklie.detabelize.components.interfaces.IPDFExtractor;
import com.mrklie.detabelize.components.interfaces.ITagger;
import com.mrklie.detabelize.converter.DetabelizerConverter;
import com.mrklie.detabelize.converter.TaggerConverter;
import com.mrklie.detabelize.validation.DetabelizeValidator;
import com.mrklie.detabelize.validation.DirectoryValidator;
import com.mrklie.detabelize.validation.FileEndingValidator;
import com.mrklie.detabelize.validation.SaveOptionValidator;

/**
 * 
 * @author Jan-Christoph Klie
 *
 */
public class Client {
	
	private IPDFExtractor extractor;

	public Client( ) {
		this.extractor = new PDF2TextExtractor();
	}

	private final Log log = LogFactory.getLog( getClass());
	
	@Parameter(names = {"-v", "--verbose"}, description = "Verbose mode. Prints out some more information about every the processing steps.")
	private boolean verbose = false;

	@Parameter(names = {"-i", "--input"}, description = "Specifies the path to input folder (there lay the files to treat).", validateWith = DirectoryValidator.class, required = true)
	private String inputFolder;

	@Parameter(names = {"-o", "--output"}, description = "Specifies the path to the output folder (if a save option has been chosen, files will be saved there) .", validateWith = DirectoryValidator.class, required = true)
	private String outputFolder;

	@Parameter(names = {"-f", "--files"}, description = "File ending. Only files with this ending will be processed. Mind: no leading point!", validateWith = FileEndingValidator.class,required = true)
	private String fileEnding;
	
	@Parameter(names = {"-s", "--save"}, description = "Saving options. Valid are: none, detabelized, tagged. Just valid if the associated option was chosen, too.", validateWith = SaveOptionValidator.class)
	private String saveOption = "detabelized";

	@Parameter(names = {"-t", "--tag"}, converter=TaggerConverter.class, description = "The files will be tagged with the given tagger. Allowed: german, english")
	private ITagger tagger;
	
	@Parameter(names ={ "-d", "--detabelize"}, validateWith = DetabelizeValidator.class, converter=DetabelizerConverter.class, description = "The files will be detabelized with the given detabelizer.")
	private IDetabelizer detabelizer;
	
	@Parameter(names = {"-e", "--excel"}, description = "If tagging, an excel file containing some statistic will be created.", validateWith = DirectoryValidator.class)
	private String excelPath = null;
	
	@Parameter(names = {"-b", "-beautify"}, description = "Beautifies the saved data. Multiple whitespaces will be reduced to one.")
	private boolean beautify = true;
	
	@Parameter(names = "--help", help = true)
	private boolean help;
	
	/**
	 * Regular expression matching file names: Dot, sequence of words/digits followed by the end of the string.
	 */
	private final String FILE_ENDING_PATTERN = "\\.[a-zA-Z0-9]+$";
	
	// TODO: Check for if file
	/**
	 * Filters files according to a file ending
	 */
	private FilenameFilter filter = new FilenameFilter() {
		public boolean accept(File dir, String name) {
			return name.endsWith(fileEnding);
		}
	};
	
	/**
	 * Sets the level of all used loggers to OFF
	 */
	@SuppressWarnings("unchecked")
	private void disableLogging() {
		List<Logger> loggers = Collections.<Logger>list(LogManager.getCurrentLoggers());
		loggers.add(LogManager.getRootLogger());
		for ( Logger logger : loggers ) {
		    logger.setLevel(Level.OFF);
		}
	}

	/**
	 * Main function of this class. 
	 * 1) Reading files in input directory
	 * 2) Removes all files from the agenda which have not the specified file ending.
	 * Exectutes the following steps (if told so) in this order for every file:
	 * 3a) Extracts text from the pdf file if file ending is pdf
	 * 3b) Reads the text from any other file
	 * 4) Detabelizes if specified. Saves detabelized file if specified.
	 * 5) Tags if specified. Saves tagged file if specified. Writes excel statistics if specified.
	 */
	private void proceed() {
		if( excelPath != null ) {
			throw new NotImplementedException("Excel statistics is currently not implemented!");
		}
		
		if( !verbose) {
			disableLogging();
		}
		
		log.debug("######### Introduction of participants #########");
		log.debug("<Extractor>");
		log.debug( extractor);
		
		log.debug("<Detabelizer>");
		log.debug( detabelizer);
		
		log.debug("<Tagger>");
		log.debug( tagger);
		
		String text;
		int i = 0;
		
		File dir = new File(inputFolder);
		String[] fileList = dir.list(filter);
		
		log.info("OS: " + PDF2TextExtractor.OS );

		for (String s : fileList) {
			File f = new File( buildPath(dir,s) );
			i++;
			
			log.debug("######### " + "Reading file " + " #########");
			log.debug( i + "/" + fileList.length + " " +  "Currently proceeding: " + f.getAbsolutePath() );
			
			try {
				if (fileEnding.equalsIgnoreCase("pdf")) {					
					text = extractor.extractText(f);
				} else {
					text = readFromFile(f);
				}				
			} catch (IOException e) {				
				log.fatal("Aborting file proceeding! Path: " + f.getAbsolutePath(), e);
				continue;
			}			
			
			if(  detabelizer != null ) {				
				text = detabelizer.detabelize(text);
				
				if( saveOption.equals("detabelized")) {
					String fileName = "det_" + s.replaceFirst(FILE_ENDING_PATTERN, ".txt");
					File dest = new File( buildPath(outputFolder, fileName ));
					writeFile(dest, text);
				}
			}
			
			if( tagger != null ) {				
				if( excelPath != null ) {
					text = tagger.tag(text);
				} else {
					text = tagger.tag(text);
				}				
				
				if( saveOption.equals("tagged")) {
					String fileName = "tag_" + s.replaceFirst(FILE_ENDING_PATTERN, ".txt");
					File dest = new File( buildPath(outputFolder, fileName ));
					writeFile(dest, text);
				}			
			}
			
			log.debug("Finished!");			
		}

	}
	
	/**
	 * Reads in text from given file and returns it
	 * @param f The file one reads frim
	 * @return A string containing the text representation of the file
	 * @throws IOException If file cannot be read
	 */
	private String readFromFile(File f) throws IOException {
		log.debug("Reading file: " + f.getAbsolutePath());
		StringBuilder sb = new StringBuilder();
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		
		while ( br.ready()) {
			sb.append( br.readLine()).append("\n");
		}
		
		if ( br != null ) {
			br.close();
		}
		
		if( fr != null) {
			fr.close();
		}
			
		return sb.toString();
	}
	
	/**
	 * Writes content to file
	 * @param f The file one writes to
	 * @param content The content one writes to f
	 */
	private void writeFile( File f, String content)  {		
		FileWriter fw;
		if( beautify) {
			content = content.replaceAll("[ \t]{2,}", " ");
		}
		try {
			log.debug("Writing file: " + f.getAbsolutePath());
			fw = new FileWriter(f);
			fw.write(content);
			fw.close();
		} catch (IOException e) {
			log.fatal("Writing file has failed! Path: " + f.getAbsolutePath() , e);			
		}		
	}
	
	/**
	 * Creates a path from a directory and a child file.
	 * E.g. dir = C:, fileName is foo. OS is Windows, so the result is C:\foo
	 * @param dir Directory of the 
	 * @param fileName Name of the file
	 * @return A file path consisting of dir plus filename
	 */
	private String buildPath(File dir, String fileName) {
		if( dir.getAbsolutePath().endsWith( System.getProperty("file.separator") )) {
			return dir.getAbsolutePath() + fileName;
		} else {
			return dir.getAbsoluteFile() + System.getProperty("file.separator") + fileName;
		}		
	}
	
	/**
	 * Creates a path from a directory and a child file.
	 * E.g. dir = C:, fileName is foo. OS is Windows, so the result is C:\foo
	 * @param dir Directory of the 
	 * @param fileName Name of the file
	 * @return A file path consisting of dir plus filename
	 */
	private String buildPath(String dir, String fileName) {
		if( dir.endsWith( System.getProperty("file.separator") )) {
			return dir + fileName;
		} else {
			return dir + System.getProperty("file.separator") + fileName;
		}		
	}

	public static void main(String[] args) {	
		Client c = new Client();
		
//		String[] args = { "--verbose", "--input", "testdata/in", "--output", "testdata/out/xml/num", "--files", "xml",  "--detabelize", "num2", "--tag", "german", "--save", "detabelized" };
//		System.out.println( );
		
		try {
			JCommander jc = new JCommander(c, args);
			
			if( c.help ) {
				jc.usage();
			} else {
				c.proceed();
			}
		} catch( ParameterException e) {
			System.err.println(e.getMessage());
		}

	}
}
