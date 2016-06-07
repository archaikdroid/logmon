package com.pcr.test.logmonitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;
import java.util.stream.Stream;

import com.pcr.test.logmonitor.parser.IParser;
import com.pcr.test.logmonitor.parser.LogParser;

/***
 * Monitor a folder, and perform a parsing on each file found, if file.lastmodified < INTERVAL_SECONDS
 * 
 * @author pece
 *
 */
public class FolderMonitor implements Runnable {

	/**interval in seconds to identify the files we are processing */
	private static final int INTERVAL_SECONDS = 3600;
	public static final String RECEIVED = "received";
	public static final String CONNECTED = "connected";
	// defaults to current folder
	private Path folder = Paths.get(".");
	
	private IParser parser = new LogParser();

	public FolderMonitor(Properties prop) {

		((LogParser)parser).setCONNECTED_HOST(prop.getProperty(CONNECTED));
		((LogParser)parser).setRECEIVED_HOST(prop.getProperty(RECEIVED));

	}

	public void setFolder(Path folder) {
		this.folder = folder;
		if (!folder.toFile().isDirectory()) {

			throw new IllegalArgumentException("Path: " + folder + " is not a folder");
		}
	}

	@Override
	public void run() {

		LocalDateTime now = LocalDateTime.now();
		System.out.println("now" + now);
		try (Stream<Path> paths = Files.walk(folder)) {
			paths.map(e -> e.toFile())
					.filter(e -> !e
							.isDirectory())
					.filter(e -> Duration
							.between(now,
									(new Date(e.lastModified()).toInstant().atZone(ZoneId.systemDefault())
											.toLocalDateTime()))
							.getSeconds() <= INTERVAL_SECONDS)
					.forEach(e -> parser.parse(e));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
