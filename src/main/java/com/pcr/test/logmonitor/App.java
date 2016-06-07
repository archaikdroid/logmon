package com.pcr.test.logmonitor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class App {

	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private static Runnable command;

	public static void main(String[] args) {
		System.out.println("Start scheduling");
		Properties prop = readProps();
		command = new FolderMonitor(prop);
		scheduler.scheduleAtFixedRate(command, 0, 1, TimeUnit.HOURS);

	}

	public static Properties readProps() {

		Properties prop = new Properties();
		
		try (InputStream input = App.class.getResourceAsStream("/hosts.properties");) {

			prop.load(input);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return prop;

	}
}
