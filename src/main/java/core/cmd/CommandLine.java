package core.cmd;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;

public class CommandLine {

    public static void executeCommand(String command) throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec(command);
        p.waitFor();
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
