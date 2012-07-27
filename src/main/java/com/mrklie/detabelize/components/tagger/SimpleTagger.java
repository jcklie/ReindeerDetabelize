package com.mrklie.detabelize.components.tagger;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mrklie.detabelize.components.interfaces.ITagger;
import com.mrklie.detabelize.util.ResultTable;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 * Simple implementation of the ITagger interface. Uses the Stanford NLP library
 * to tag the given text. Supports currently German and English texts
 * 
 * @author Jan-Christoph Klie
 * 
 */
public class SimpleTagger implements ITagger {

	private final static Log log = LogFactory.getLog(SimpleTagger.class);

	@SuppressWarnings("unused")
	private ResultTable stats;

	private static MaxentTagger tagger;
	private final String model;
	private static final String path;

	public static final String ENGLISH = "english-left3words-distsim.tagger";
	public static final String GERMAN = "german-fast.tagger";

	static {
		path = "models" + System.getProperty("file.separator");
	}

	public SimpleTagger() {
		this(ENGLISH);
	}

	public SimpleTagger(String model) {
		this.model = model;
		stats = new ResultTable();
		initTagger();
	}

	public String getModel() {
		return model;
	}

	private void initTagger() {
		try {
			tagger = new MaxentTagger(path + model);
		} catch (IOException e) {
			log.fatal("Could not get model! Path: " + path + model, e);

		} catch (ClassNotFoundException e) {
			log.fatal("Could not get model! Path: " + path + model, e);
		}
	}

	@Override
	public String tag(String text) {
		log.debug("######### Tagging #########");
		log.debug("Tagging started!");

		return tagger.tagString(text);
	}

	public String tagAndWriteStatistics(String text) {
		return null;
	}

	public String toString() {
		return "I tag texts by using the Stanford NLP library. I use the model "
				+ model + ". ";
	}

}
