package toxic;

import logger.ConsoleLogger;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

public class Main {
    
    public static String captchakeyst = "";
    public static String webHookURL = "";

    public static String[] eachAcc;

    public static long timeadjust = 0;
    public static long ping = 0;
    public static long failedLogin = 0;

    public static boolean isActive = false;



    public static void main(String[] args) throws IOException, InterruptedException, ParseException {

        if (args.length > 0) {

            double version = 4.5;

            new ProcessBuilder("cmd", "/c", "title THC v" + version).inheritIO().start().waitFor();
            TimeZone.setDefault(TimeZone.getTimeZone("GMT"));

            ConsoleLogger.fancy();


            File accounts = new File("accounts.txt");
            accounts.createNewFile();

            File configfile = new File("config.txt");
            configfile.createNewFile();


            String accs = "";
            Scanner accReader = new Scanner(accounts);
            while (accReader.hasNextLine()) {
                accs = accs + accReader.nextLine() + "\r\n";

            }

            String configfull = "";
            Scanner ckReader = new Scanner(configfile);
            while (ckReader.hasNextLine()) {
                configfull = ckReader.nextLine();
            }

            if (configfull.isEmpty()) {

                BufferedWriter writer = new BufferedWriter(new FileWriter(configfile));
                writer.write("CaptchaKey='NONE' \r\n TimeDifference='0' \r\n WebhookURL='NONE'".replaceAll(" ", ""));
                writer.close();
                ConsoleLogger.logInfo("Successfully generated config file, please enter required information and restart the bot.");
                System.exit(1);


            }



            String config = "";
            Scanner confReader = new Scanner(configfile);
            while (confReader.hasNextLine()) {
                config = config + confReader.nextLine();

            }

            try{

                String[] captchakey = config.split("CaptchaKey='");
                captchakeyst = captchakey[1].split("'")[0];

                String[] timedifference = config.split("TimeDifference='");
                try{

                    timeadjust = Long.parseLong(timedifference[1].split("'")[0]);

                }catch(NumberFormatException e){

                    ConsoleLogger.logError("Invalid value for time difference (check config file)");
                    System.exit(1);
                }

                String[] webhook = config.split("WebhookURL='");
                webHookURL = webhook[1].split("'")[0];


            }catch(ArrayIndexOutOfBoundsException e){

                ConsoleLogger.logError("Config file is corrupted, please delete it.");
                System.exit(1);

            }

            if(captchakeyst.equals("NONE") || captchakeyst.length() != 32){

                ConsoleLogger.logError("No captcha key or invalid key detected, please paste it into config.txt (Parameter: CaptchaKey)");
                System.exit(1);

            }

            if(accs.isEmpty()){

                ConsoleLogger.logError("No MC accounts found, please add them to accounts.txt in email:password format.");
                System.exit(1);


            }




            eachAcc = accs.split("\r\n");
            ConsoleLogger.logInfo("Loaded " + eachAcc.length + " account(s)!");
            ConsoleLogger.logInfo("Loaded captcha key!");
            ConsoleLogger.logInfo("Adjusted time by " + timeadjust + "ms");
            if(webHookURL.equals("NONE")){

                ConsoleLogger.logInfo("Not using a webhook");

            }else{

                ConsoleLogger.logInfo("Using a custom webhook");

            }

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date date = new Date(System.currentTimeMillis());
            ConsoleLogger.logInfo("Current time:" + formatter.format(date) + "\n");

            ConsoleLogger.logInput("Select mode:" + "\n");
            ConsoleLogger.logInput("1: Manual sniper" + "\n");
            ConsoleLogger.logInput("2: Auto mode (3-char)" + "\n");
            ConsoleLogger.logInput("3: Auto mode (>100 views)" + "\n");
            ConsoleLogger.logInput("4: Turbo mode" + "\n");
            ConsoleLogger.logInput("Mode: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String got = br.readLine();

            if (got.equals("1") || got.equals("4")) {



                new InitManual(got);




            } else if (got.equals("2") || got.equals("3")) {


                new InitAuto(got);


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