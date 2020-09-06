package view;

import controller.NetController;

public class Main {
	public static void main(String[] args) {
		NetController nc = new NetController();

		String osName = System.getProperty("os.name");

		String arrString = nc.ping(osName, "www.google.com");

		System.out.println(arrString);
	}
}
