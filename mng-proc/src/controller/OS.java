package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OS {

    public String getName() {
        return System.getProperty("os.name");
    }

    public StringBuilder getProcesses(String osName) {
        boolean testWindows = osName.toLowerCase().startsWith("w");

        String command = "ps -e";
        if (testWindows) command = "TASKLIST /";

        StringBuilder result = new StringBuilder();
        try {
            Process ps = Runtime.getRuntime().exec(command);
            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));

            String lineOutput = br.readLine();
            while (lineOutput != null) {
                result.append(lineOutput + "\n");
                lineOutput = br.readLine();
            }

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }



    public void killProcessByPid(String osName, int pid) {
        boolean testWindows = osName.toLowerCase().startsWith("w");

        String command = "kill " + String.valueOf(pid);
        if (testWindows) command = "TASKLIST /";

        try {
            Process ps = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Success!");
    }

    public void killProcessByName(String osName, String name) {
        boolean testWindows = osName.toLowerCase().startsWith("w");

        String command = "pkill " + name;
        if (testWindows) command = "TASKLIST /";

        try {
            Process ps = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Success!");
    }
}
