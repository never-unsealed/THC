package toxic;

import logger.ConsoleLogger;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

public class Main {

    public static String webHookURL = "";

    public static String[] eachAcc;

    public static long timeadjust = 0;
    public static long ping = 0;
    public static long failedLogin = 0;
    public static long minviews = 0;

    public static boolean isActive = false;



    public static void main(String[] args) throws IOException, InterruptedException, ParseException {

        if (args.length > 0) {

            double version = 5.1;

            if(System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "title THC v" + version).inheritIO().start().waitFor();
            }
            TimeZone.setDefault(TimeZone.getTimeZone("GMT"));

            ConsoleLogger.fancy();


            File accounts = new File("accounts.txt");
            accounts.createNewFile();

            File configfile = new File("config.txt");
            configfile.createNewFile();


            StringBuilder accs = new StringBuilder();
            Scanner accReader = new Scanner(accounts);
            while (accReader.hasNextLine()) {
                accs.append(accReader.nextLine()).append("\r\n");

            }

            String configfull = "";
            Scanner ckReader = new Scanner(configfile);
            while (ckReader.hasNextLine()) {
                configfull = ckReader.nextLine();
            }

            if (configfull.isEmpty()) {

                BufferedWriter writer = new BufferedWriter(new FileWriter(configfile));
                writer.write("TimeDifference='0' \r\n AutoModeMinViews='100' \r\n WebhookURL='NONE'".replaceAll(" ", ""));
                writer.close();
                ConsoleLogger.logInfo("Successfully generated config file, please enter required information and restart the bot.");
                System.exit(1);


            }



            StringBuilder config = new StringBuilder();
            Scanner confReader = new Scanner(configfile);
            while (confReader.hasNextLine()) {
                config.append(confReader.nextLine());

            }

            try{




                String timedifference = config.toString().split("TimeDifference='")[1].split("'")[0];

                try{

                    timeadjust = Long.parseLong(timedifference);

                }catch(NumberFormatException e){

                    ConsoleLogger.logError("Invalid value for time difference (check config file)");
                    System.exit(1);
                }

                String minviewsst = config.toString().split("AutoModeMinViews='")[1].split("'")[0];

                try{

                    minviews = Long.parseLong(minviewsst);


                }catch(NumberFormatException e){

                    ConsoleLogger.logError("Invalid value for min. namemc views (check config file)");
                    System.exit(1);

                }


                webHookURL = config.toString().split("WebhookURL='")[1].split("'")[0];



            }catch(ArrayIndexOutOfBoundsException e){

                ConsoleLogger.logError("Config file is corrupted, please delete it.");
                System.exit(1);

            }


            if(accs.length() == 0){

                ConsoleLogger.logError("No MC accounts found, please add them to accounts.txt in email:password format.");
                System.exit(1);


            }




            eachAcc = accs.toString().split("\r\n");
            ConsoleLogger.logInfo("Loaded " + eachAcc.length + " account(s)!");
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
            ConsoleLogger.logInput("3: Auto mode (>" + minviews + " views)" + "\n");
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

            if(System.getProperty("os.name").contains("Windows")) {

                String command = "cmd /c start cmd.exe /K \"@echo off && java -jar THC.jar run\"";
                Runtime.getRuntime().exec(command);

            }else{

                System.out.println("Please start program with command: java -jar THC.jar run");

            }
            System.exit(0);


        }


    }

}