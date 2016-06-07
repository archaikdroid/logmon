package com.pcr.test.logmonitor.parser;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

//TODO: use fluent library like AssertJ, better to assert on collections, easier to read.
public class LogParserTest {

	private LogParser logparser = new LogParser();
	@Test
	public void test() {
		logparser.setCONNECTED_HOST("quark");
		logparser.setRECEIVED_HOST("lilac");
		logparser.parse(new File("src/test/resources/test"));
		
	
		assertEquals(1, logparser.getConnectedTo().size());
		assertEquals(2, logparser.getReceivedFrom().size());
		assertEquals("lilac", logparser.getMostConnections().getKey());
		assertEquals(2, logparser.getMostConnections().getValue().intValue());

	}
	
	@Test
	public void test2() {
		logparser.setCONNECTED_HOST("quark");
		logparser.setRECEIVED_HOST("lilac");
		logparser.parse(new File("src/test/resources/test2"));
		
		assertEquals(1, logparser.getConnectedTo().size());
		assertEquals(1, logparser.getReceivedFrom().size());
		assertEquals("quark", logparser.getMostConnections().getKey());
		assertEquals(2, logparser.getMostConnections().getValue().intValue());

	}

	
	
	@Test
	public void testCombined() {
		logparser.setCONNECTED_HOST("quark");
		logparser.setRECEIVED_HOST("lilac");
		logparser.parse(new File("src/test/resources/test"));
		
		
		assertEquals(1, logparser.getConnectedTo().size());
		assertEquals(2, logparser.getReceivedFrom().size());
		assertEquals("lilac", logparser.getMostConnections().getKey());
		assertEquals(2, logparser.getMostConnections().getValue().intValue());
		
		logparser.parse(new File("src/test/resources/test2"));
		
		assertEquals(1, logparser.getConnectedTo().size());
		assertEquals(2, logparser.getReceivedFrom().size());
		assertEquals("quark", logparser.getMostConnections().getKey());
		assertEquals(4, logparser.getMostConnections().getValue().intValue());
		

	}
	//FIXME: add test cases at the limits (no file, malformed file, )

	
}
