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
 * This is an implementation of the IDetabelizer interface. This detabelizer
 * splits the text given to it in sentences and counts the occurences of numbers
 * in each sentence. If it exceeds the amount given by a constant, it will
 * delete it.
 * 
 * @author Jan-Christoph Klie
 * 
 */
public class NumberDetabelizer implements IDetabelizer {

	private final Log log = LogFactory.getLog(getClass());
	private final int NUM_ALLOWED;
	private static StanfordCoreNLP pipeline;
	private static Properties props;

	public NumberDetabelizer() {
		NUM_ALLOWED = 1;
	}

	public NumberDetabelizer(int nums) {
		this.NUM_ALLOWED = nums;
	}

	public int getNumsAllowed() {
		return NUM_ALLOWED;
	}

	@Override
	public String detabelize(String text) {
		log.debug("######### Detabelizing #########");
		log.debug("Detabelizing started!");

		log.debug("Detabelizing finished!");
		return alignSentences(text);
	}

	static {
		props = new Properties();
		props.put("annotators", "tokenize, ssplit");
		pipeline = new StanfordCoreNLP(props);
	}

	private String alignSentences(String text) {
		int count = 0;

		StringBuilder result = new StringBuilder();

		text = text.replaceAll("\n", " ");

		Annotation document = new Annotation(text);
		pipeline.annotate(document);

		List<CoreMap> sentences = document.get(SentencesAnnotation.class);

		for (CoreMap sentence : sentences) {
			String[] words = sentence.toString().split("[ \t]{1,}");
			for (String s : words) {
				if (s.matches("^\\d+(\\.\\d+)?(\\s*%)?$")) {
					count++;
				}
			}

			if (count <= NUM_ALLOWED) {
				result.append(sentence).append("\n");
			}
			count = 0;
		}

		return result.toString();
	}

	@Override
	public String toString() {
		String s = "I am a NumberDetabelizer. ";
		s += "Max # of numbers allowed in each sentence is " + NUM_ALLOWED
				+ ". ";
		s += "Otherwise, the sentence will be deleted!";
		return s;
	}

}
