package toxic;

import logger.ConsoleLogger;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

public class Main {
    
    public static String captchakeyst = "";

    public static String[] eachAcc;

    public static long timeadjust = 0;
    public static long avgRunTime = 0;
    public static long failedLogin = 0;

    public static boolean isActive = false;



    public static void main(String[] args) throws IOException, InterruptedException, ParseException {



        if (args.length > 0) {

            double version = 4.1;

            new ProcessBuilder("cmd", "/c", "title THC v" + version).inheritIO().start().waitFor();
            TimeZone.setDefault(TimeZone.getTimeZone("GMT"));

            ConsoleLogger.fancy();


            File accounts = new File("accounts.txt");
            accounts.createNewFile();

            File captchakey = new File("captchakey.txt");
            captchakey.createNewFile();


            String accs = "";
            Scanner accReader = new Scanner(accounts);
            while (accReader.hasNextLine()) {
                accs = accs + accReader.nextLine() + "\n";

            }

            Scanner ckReader = new Scanner(captchakey);
            while (ckReader.hasNextLine()) {
                captchakeyst = ckReader.nextLine();
            }

            if (captchakeyst.isEmpty()) {

                ConsoleLogger.logError("No captcha key found - please get an api token from 2captcha.com and paste it into captchakey.txt");
                System.exit(1);

            }

            if (accs.isEmpty()) {

                ConsoleLogger.logError("No MC accounts found - please add accounts to accounts.txt in email:password format");
                System.exit(1);

            }


            eachAcc = accs.split("\n");
            ConsoleLogger.logInfo("Loaded " + eachAcc.length + " account(s)!");
            ConsoleLogger.logInfo("Loaded captcha key!");


            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date date = new Date(System.currentTimeMillis());
            ConsoleLogger.logInfo("Current time:" + formatter.format(date) + "\n");

            ConsoleLogger.logInput("Please check if your local time is accurate on https://time.is" + "\n");
            ConsoleLogger.logInput("Do you want to adjust your time? (y/n) ");
            BufferedReader bryt = new BufferedReader(new InputStreamReader(System.in));
            String gotyt = bryt.readLine();

            if (gotyt.equals("y")) {

                ConsoleLogger.logInput("Please enter time difference in milliseconds (i.e. '-400' if you're 400ms ahead) ");
                BufferedReader brta = new BufferedReader(new InputStreamReader(System.in));
                String gotta = brta.readLine();
                try {
                    timeadjust = Long.parseLong(gotta);
                } catch (NumberFormatException e) {

                    ConsoleLogger.logError("Invalid value!");
                    System.exit(1);

                }

            }

            System.out.print("\n");
            ConsoleLogger.logInput("Select mode:" + "\n");
            ConsoleLogger.logInput("1: Manual sniper" + "\n");
            ConsoleLogger.logInput("2: Auto mode (3-char)" + "\n");
            ConsoleLogger.logInput("Mode: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String got = br.readLine();

            if (got.equals("1")) {



                new InitManual();




            } else if (got.equals("2")) {


                new InitAuto();


            } else {


                ConsoleLogger.logError("Unknown action!");
                System.exit(1);


            }




        } else {

            String command = "cmd /c start cmd.exe /K \"@echo off && java -jar THC.jar run\"";
            Runtime.getRuntime().exec(command);
            System.exit(0);


        }


    }

}