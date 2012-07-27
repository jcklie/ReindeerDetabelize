package com.mrklie.detabelize.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This data structure stores statistics about the count of occurrences of
 * something in a List of Maps. These maps associate an identifier as a String
 * with an Integer which describes the amount of occurrences of the identifier.
 * This will be used to store statistics about the tagger.
 * 
 * @author Jan-Christoph Klie
 * 
 */
public class ResultTable {

	private List<Map<String, Integer>> data;

	public ResultTable() {
		data = new LinkedList<Map<String, Integer>>();
	}

	/**
	 * Adds a row to the result table
	 * 
	 * @param row
	 *            The row to add
	 */
	public void add(Map<String, Integer> row) {
		data.add(row);
	}

}
