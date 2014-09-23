package mainpackage;

import java.io.File;
import java.io.IOException;


public class Directory {
	
	/**
	 * This method checks for the existence of log file if it does not exist,
	 * it creates a new file.
	 */
	public static void makeDir(String dir) {
		try {
			File directory = new File(dir);
			if (!directory.exists()) {
				new File(dir).mkdir();
				directory.createNewFile();
			}

		} catch (IOException e) {
			e.printStackTrace();

		}
	}
	/*
	public static void makeDir() throws Exception{
		Process process;
		StringBuilder sb = new StringBuilder();

		sb.append("mkdir "+dir);
		//sb.append();
		String command = sb.toString();

		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c",
				command);

		
		try {
			process = builder.start();
			process.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}*/
	
	public static void removeDir(String dir) throws Exception{
		Process process;
		StringBuilder sb = new StringBuilder();

		sb.append("rm -r "+dir);
	
		String command = sb.toString();

		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c",
				command);

		
		try {
			process = builder.start();
			process.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
