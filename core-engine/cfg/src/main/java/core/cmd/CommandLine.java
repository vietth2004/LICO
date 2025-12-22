package core.cmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommandLine {

    public static void executeCommand(String command) throws IOException, InterruptedException {
        String os = System.getProperty("os.name").toLowerCase();
        String javaHome = System.getProperty("java.home");
        String binDir = javaHome + File.separator + "bin";

        // Tách command ra thành mảng để ProcessBuilder hiểu từng tham số
        List<String> parts = new ArrayList<>(Arrays.asList(command.split(" ")));

        // Nếu command là "javac" hoặc "java" → thay bằng đường dẫn đầy đủ
        if (parts.get(0).equals("javac") || parts.get(0).equals("java")) {
            parts.set(0, "\"" + binDir + File.separator + parts.get(0) + "\"");
        }
        ProcessBuilder builder;
        if (os.contains("win")) {
            // Windows dùng cmd.exe
            builder = new ProcessBuilder("cmd.exe", "/c", String.join(" ", parts));
        } else {
            // Linux/Mac dùng sh
            builder = new ProcessBuilder("sh", "-c", String.join(" ", parts));
        }

        builder.redirectErrorStream(true);
        Process process = builder.start();

        try (BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = r.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //process.waitFor(2, TimeUnit.SECONDS);

        //process.destroyForcibly();
        try {
            int exitCode = process.waitFor();
            System.out.println("Process exited with code = " + exitCode);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // khôi phục cờ interrupt
            if (process.isAlive()) {
                process.destroyForcibly();
            }
            throw e;
        }

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