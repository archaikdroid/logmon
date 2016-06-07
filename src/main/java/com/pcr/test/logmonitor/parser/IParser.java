package com.pcr.test.logmonitor.parser;

import java.io.File;
/**
 * 
 * @author pece
 *
 */
public interface IParser {

	/***
	 * Parses @file
	 * @param file
	 */
	public void parse(File file);
	/***
	 * Outputs the results of parsing
	 */
	public void printResults();
}
