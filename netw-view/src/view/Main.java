package view;

import controller.NetController;

public class Main {
	public static void main(String[] args) {
		NetController nc = new NetController();

		String osName = "Linux";

		nc.ip(osName);
	}
}
