package core.cmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class CommandLine {

    public static void executeCommand(String command) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
        builder.redirectErrorStream(true); // gộp stderr vào stdout
        Process process = builder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // in ra console
            }
        }

        process.waitFor(30, TimeUnit.SECONDS);
    }
    public static void executeCommand(String command, String cdFolder) throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec(command, null , new File(cdFolder));
        p.waitFor();
    }

    public static void main(String[] args) throws Exception {
        String command = "javac D:\\Haivt\\gen-test\\JGT-workspace\\instrument\\findGCD\\Solution.java";
        Process p = Runtime.getRuntime().exec(command);
        p.waitFor();
    }
    
}
