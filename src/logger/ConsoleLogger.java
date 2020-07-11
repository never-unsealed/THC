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

    public static void logRequestCount(String message, int count, String name) {
        AnsiConsole.out.println(CYAN + "[" + name + "] " + PURPLE + "[" + count +"] " + WHITE + message);
    }

    public static void logName(String message, String name) {
        AnsiConsole.out.println(CYAN + "[" + name + "] " + WHITE + message);
    }

    public static void fancy() {
        AnsiConsole.out.println(PURPLE + " _______ _    _  _____    _____       _                 ");
        AnsiConsole.out.println(PURPLE + "|__   __| |  | |/ ____|  / ____|     (_)                ");
        AnsiConsole.out.println(PURPLE + "   | |  | |__| | |      | (___  _ __  _ _ __   ___ _ __ ");
        AnsiConsole.out.println(PURPLE + "   | |  |  __  | |       \\___ \\| '_ \\| | '_ \\ / _ \\ '__|");
        AnsiConsole.out.println(PURPLE + "   | |  | |  | | |____   ____) | | | | | |_) |  __/ |   ");
        AnsiConsole.out.println(PURPLE + "   |_|  |_|  |_|\\_____| |_____/|_| |_|_| .__/ \\___|_|   ");
        AnsiConsole.out.println(PURPLE + "   by smoke#1337                       | |              ");
        AnsiConsole.out.println(PURPLE + "                                       |_|              " + WHITE);
        AnsiConsole.out.println("\n");
    }


    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String PURPLE = "\u001B[35m";

}
