package controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class NetController {
	
	public String ip(String osName) {
		String command = "ifconfig";
		if (osName.toLowerCase().startsWith("w")) {
			command = "ipconfig";
		}

		try {
			Process outputRun = Runtime.getRuntime().exec(command);
			BufferedReader br = new BufferedReader(new InputStreamReader(outputRun.getInputStream()));

			String lineOutput = br.readLine();

			while (lineOutput != null) {
				System.out.println(lineOutput);
				lineOutput = br.readLine();
			}

			br.close();

		} catch (Exception e) {
			e.getMessage();
		}

		return command;
	}
}
