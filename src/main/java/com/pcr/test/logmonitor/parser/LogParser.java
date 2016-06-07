package com.pcr.test.logmonitor.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

/***
 * Parse logfiles 
 * outputs hosts connected to
 * CONNECTED_HOST or receiving from RECEIVED_HOST, and displays host with most
 * connections in console
 * 
 * @author pece
 *
 */
public class LogParser implements IParser {

	// defaults to current folder
	private HashSet<String> connectedTo = new HashSet<String>();
	private HashSet<String> receivedFrom = new HashSet<String>();
	private Map<String, Integer> connectionCount = new HashMap<String, Integer>();
	private String CONNECTED_HOST = "";
	private String RECEIVED_HOST = "";

	public LogParser() {

	}


	/***
	 * Split into according list if CONNECTED_HOST or RECEIVED_HOST. Compute
	 * host with most connections.
	 * 
	 * @param f
	 */
	@Override
	public void parse(File f) {

		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)))) {
			String[] tab;
			for (String line = null; (line = br.readLine()) != null;) {
				tab = line.split(" ");

				if (tab.length == 3) {

					if (CONNECTED_HOST.equals(tab[2])) {
						connectedTo.add(tab[1]);
					}
					if (RECEIVED_HOST.equals(tab[1])) {
						receivedFrom.add(tab[2]);
					}
					connectionCount.compute(tab[1], (k, v) -> (v == null) ? 1 : v + 1);
					connectionCount.compute(tab[2], (k, v) -> (v == null) ? 1 : v + 1);
				}
				// TODO: case where line is malformed

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setCONNECTED_HOST(String cONNECTED_HOST) {
		CONNECTED_HOST = cONNECTED_HOST;
	}

	public void setRECEIVED_HOST(String rECEIVED_HOST) {
		RECEIVED_HOST = rECEIVED_HOST;
	}

	public HashSet<String> getConnectedTo() {
		return connectedTo;
	}

	public void setConnectedTo(HashSet<String> connectedTo) {
		this.connectedTo = connectedTo;
	}

	public HashSet<String> getReceivedFrom() {
		return receivedFrom;
	}

	public void setReceivedFrom(HashSet<String> receivedFrom) {
		this.receivedFrom = receivedFrom;
	}

	public Map<String, Integer> getConnectionCount() {
		return connectionCount;
	}

	public void setConnectionCount(Map<String, Integer> mostConnections) {
		this.connectionCount = mostConnections;
	}

	/**
	 * Simple output to console
	 */
	@Override
	public void printResults() {

		System.out.println("results:");
		System.out.println("connected to " + CONNECTED_HOST + ":");
		connectedTo.forEach(System.out::println);
		System.out.println("received from " + RECEIVED_HOST + ":");
		receivedFrom.forEach(System.out::println);
		System.out.println("most connections:");
		System.out.println(getMostConnections());
		

	}
	
	/***
	 * get max value from connections list
	 * @return
	 */
	public Entry<String, Integer> getMostConnections(){
		//FIXME:returning first found max value in case of double, maybe return list ? 
		Entry<String, Integer> max = null ;
		if (!connectionCount.isEmpty()) {
			max = connectionCount.entrySet().stream()
					.max((a, b) -> a.getValue().compareTo(b.getValue())).get();
			
		}
		return max;
	}

}
