package logger;

import org.fusesource.jansi.AnsiConsole;

public class ConsoleLogger {

    public static void logError(String message) {
        AnsiConsole.out.println(RED + "[ERROR] " + WHITE + message);
    }

    public static void logInfo(String message) {
        AnsiConsole.out.println(BLUE + "[INFO] " + WHITE + message );
    }

    public static void logInput(String message) {
        AnsiConsole.out.print(YELLOW + "[INPUT] " + WHITE + message);
    }

    public static void logFailed(String message, String name) {
        AnsiConsole.out.println(CYAN + "[" + name + "] " + RED + "[FAILED] " + WHITE + message);
    }

    public static void logNameError(String message, String name) {
        AnsiConsole.out.println(CYAN + "[" + name + "] " + RED + "[ERROR] " + WHITE + message);
    }

    public static void logSuccess(String message, String name) {
        AnsiConsole.out.println(CYAN + "[" + name + "] " + GREEN + "[SUCCESS] " + WHITE + message);
    }

    public static void logName(String message, String name) {
        AnsiConsole.out.println(CYAN + "[" + name + "] " + WHITE + message);
    }

    public static void fancy() {

        String[] fancy = {" _______ _    _  _____    _____       _                 ","|__   __| |  | |/ ____|  / ____|     (_)                ","   | |  | |__| | |      | (___  _ __  _ _ __   ___ _ __ ","   | |  |  __  | |       \\___ \\| '_ \\| | '_ \\ / _ \\ '__|","   | |  | |  | | |____   ____) | | | | | |_) |  __/ |   ","   |_|  |_|  |_|\\_____| |_____/|_| |_|_| .__/ \\___|_|   ","   by smoke#1337                       | |              ","                                       |_|              "};

        for(String line:fancy){

            for(char c:line.toCharArray()){
                AnsiConsole.out.print(PURPLE + c);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            AnsiConsole.out.print("\n");

        }
        AnsiConsole.out.print(WHITE + "\n");

    }



    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String PURPLE = "\u001B[35m";

}
