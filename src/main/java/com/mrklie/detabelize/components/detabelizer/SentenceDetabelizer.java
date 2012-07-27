package com.mrklie.detabelize.components.detabelizer;

import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mrklie.detabelize.components.interfaces.IDetabelizer;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

/**
 * Splits file in sentences. Detabelizes by checking if sentences has structure
 * of a correct sentence (Starting with capital letter, endind with .?!.
 * 
 * @author Jan-Christoph Klie
 * 
 */
public class SentenceDetabelizer implements IDetabelizer {
	
	private final Log log = LogFactory.getLog( getClass());
	
	@Override
	public String detabelize(String text) {
		log.debug("######### Detabelizing #########");
		log.debug("Detabelizing started!");
		
		log.debug("Detabelizing finished!");
		return purge( alignSentences(text));
	}
	
	private static StanfordCoreNLP pipeline;
	private static Properties props;
	
	static {
		props = new Properties();
		props.put("annotators", "tokenize, ssplit");
		pipeline = new StanfordCoreNLP(props);
	}
	
	private String alignSentences(String text) {
		
		StringBuilder result = new StringBuilder();
		
		text = text.replaceAll("\n", " ");

		Annotation document = new Annotation(text);
		pipeline.annotate(document);
		
		List<CoreMap> sentences =  document.get(SentencesAnnotation.class);
		
		
		
		for(CoreMap sentence: sentences) {
			result.append(sentence).append("\n");
		}
		
		return result.toString();
	}
	
	private String purge(String text) {
		StringBuilder sb = new StringBuilder();
		String[] lines = text.split("\n");
		
		for(String line : lines) {
			if( line.length() > 0 && Character.isUpperCase(line.charAt(0)) && ( line.endsWith(".") || line.endsWith("?") || line.endsWith("!")) ) {
				sb.append(line + "\n");
			}
		}
		
		return sb.toString();		
	}
	
	public String toString() {
		String s = "I am a SentenceDetabelizer.\n";
		s += "If a sentence does not start with a capital letter and end with .!?, I delete it.";
		return s;
	}


}
