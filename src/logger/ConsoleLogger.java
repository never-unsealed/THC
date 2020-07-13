package logger;

public class ConsoleLogger {

    public static void logError(String message) { System.out.println(RED + "[ERROR] " + WHITE + message); }

    public static void logInfo(String message) {
        System.out.println(BLUE + "[INFO] " + WHITE + message );
    }

    public static void logInput(String message) {
        System.out.print(YELLOW + "[INPUT] " + WHITE + message);
    }

    public static void logFailed(String message, String name) {
        System.out.println(CYAN + "[" + name + "] " + RED + "[FAILED] " + WHITE + message);
    }

    public static void logNameError(String message, String name) {
        System.out.println(CYAN + "[" + name + "] " + RED + "[ERROR] " + WHITE + message);
    }

    public static void logSuccess(String message, String name) {
        System.out.println(CYAN + "[" + name + "] " + GREEN + "[SUCCESS] " + WHITE + message);
    }

    public static void logName(String message, String name) {
        System.out.println(CYAN + "[" + name + "] " + WHITE + message);
    }

    public static void fancy() {


        String[] fancy = {" _______ _    _  _____    _____       _                 ","|__   __| |  | |/ ____|  / ____|     (_)                ","   | |  | |__| | |      | (___  _ __  _ _ __   ___ _ __ ","   | |  |  __  | |       \\___ \\| '_ \\| | '_ \\ / _ \\ '__|","   | |  | |  | | |____   ____) | | | | | |_) |  __/ |   ","   |_|  |_|  |_|\\_____| |_____/|_| |_|_| .__/ \\___|_|   ","   by smoke#1337                       | |              ","                                       |_|              "};

        for(String line:fancy){

            for(char c:line.toCharArray()){

                if(!(c == '_' || c == ' ' || c == '|'|| c == '/'|| c == '\\'|| c == '(' || c == ')' || c == '\'' || c == '.') ){

                    System.out.print(GREEN + c);

                }else{
                    System.out.print(PURPLE + c);
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.print("\n");

        }
        System.out.print(WHITE + "\n");

    }



    public static final String RED = "\u001b[31;1m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001b[34;1m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String PURPLE = "\u001B[35m";

}
