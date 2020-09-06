package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NetController {

	/**
	 * Shows IPv4 addresses
	 *
	 * @param osName Operating system name
	 * @return A StringBuffer object with the IPv4 address for each Ethernet adapter
	 * */
	public StringBuffer ip(String osName) {
		boolean testWindows = osName.toLowerCase().startsWith("w");
		StringBuffer result = new StringBuffer();

		String command = "ifconfig";
		if (testWindows) {
			command = "ipconfig";
		}

		try {
			Process outputRun = Runtime.getRuntime().exec(command);
			BufferedReader br = new BufferedReader(new InputStreamReader(outputRun.getInputStream()));

			result = linuxIfConfig(br);

			if (testWindows) {
				result = windowsIpConfig(br);
			}

			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Shows the average time of ping address
	 *
	 * @param osName Operating system name
	 * @param address Destination address
	 * @return A formatted String which contains the average time of ping address
	 * */
	public String ping(String osName, String address) {
		boolean testWindows = osName.toLowerCase().startsWith("w");

		String command = "ping -c 10 " + address;
		if (testWindows) {
			command = "ping -n 10 " + address;
		}

		double result = 0.;
		try {
			Process pr = Runtime.getRuntime().exec(command);
			BufferedReader br = new BufferedReader(new InputStreamReader(pr.getInputStream()));

			result = pingAvgLinux(br);
			if (testWindows) {
				result = pingAvgWindows(br);
			}

			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		String resultOutput = String.format("PING %s \nAverage time (10 packets): %f ms.", address, result);

		return resultOutput;
	}

	/**
	 * Reads ping command output for Linux OS
	 *
	 * @param br A BufferedReader object
	 * @return The average time of ping address (10 iterations)
	 * */
	public double pingAvgLinux(BufferedReader br) {
		double result = 0.;
		try {
			String lineOutput = br.readLine();
			while (lineOutput != null) {
				if (lineOutput.contains("time=")) {
					result += Double
							.parseDouble(lineOutput
							.substring(lineOutput.indexOf("time=") + 5, lineOutput.indexOf("ms")));
				}
				lineOutput = br.readLine();
			}
		} catch (IOException e){
			e.printStackTrace();
		}
		return (result/10);
	}

	/**
	 * Reads ping command output for Windows OS
	 *
	 * @param br A BufferedReader object
	 * @return The average time of ping address (10 iterations)
	 * */
	public double pingAvgWindows(BufferedReader br) {
		return 0.;
	}

	/**
	 * Reads ifconfig command output for Linux OS
	 *
	 * @param br A BufferedReader object
	 * @return A StringBuffer object with the IPv4 address for each Ethernet adapter
	 * */
	public StringBuffer linuxIfConfig(BufferedReader br) {
		StringBuffer resultOutput = new StringBuffer();

		try {
			String lineOutput = br.readLine();
			resultOutput.append(lineOutput);

			while (lineOutput != null) {
				lineOutput = br.readLine();

				if (lineOutput.contains("inet ")) {
					String[] arrOutput = lineOutput
							.substring(lineOutput.indexOf("inet"), lineOutput.length()-1)
							.split(" ");

					resultOutput.append(" " + arrOutput[0] + ": " + arrOutput[1] + "\n");
				}

				if (lineOutput.equals("")) {
					lineOutput = br.readLine();
					if (lineOutput != null) resultOutput.append(lineOutput);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultOutput;
	}

	/**
	 * Reads ifconfig command output for Windows OS
	 *
	 * @param br A BufferedReader object
	 * @return A StringBuffer object with the IPv4 address for each Ethernet adapter
	 * */
	public StringBuffer windowsIpConfig(BufferedReader br) {
		StringBuffer resultOutput = new StringBuffer();

		try {
			String lineOutput = br.readLine();
			resultOutput.append(lineOutput);

			while (lineOutput != null) {
				lineOutput = br.readLine();

				if (lineOutput.contains("inet ")) {
					String[] arrOutput = lineOutput
							.substring(lineOutput.indexOf("inet"), lineOutput.length()-1)
							.split(" ");

					resultOutput.append(" " + arrOutput[0] + ": " + arrOutput[1] + "\n");
				}

				if (lineOutput.equals("")) {
					lineOutput = br.readLine();
					if (lineOutput != null) resultOutput.append(lineOutput);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultOutput;
	}
}
