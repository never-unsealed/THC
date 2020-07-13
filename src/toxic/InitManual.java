package toxic;

import logger.ConsoleLogger;
import snipe.Calculator;
import snipe.Snipe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InitManual {

    public InitManual() throws IOException {

        ConsoleLogger.logInput("Enter name: ");
        BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
        String got1 = br1.readLine();

        ConsoleLogger.logInput("Turbo mode? (y/n) ");
        BufferedReader br3 = new BufferedReader(new InputStreamReader(System.in));
        String got3 = br3.readLine();

        if (got3.equals("y")) {

            ConsoleLogger.logInput("Enter exact date (GMT)(mm/dd/yyyy hh:mm:ss): ");
            BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
            String got2 = br2.readLine();

            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            try {

                Date d1 = format.parse(got2);

                Date date1 = new Date(System.currentTimeMillis());


                Date d2 = format.parse(format.format(date1));


                long diff = d1.getTime() - d2.getTime();


                if (diff < 0) {

                    ConsoleLogger.logError("Invalid date \n");
                    System.exit(1);

                }

                ConsoleLogger.logInfo("Task for username '" + got1 + "' started. " + diff / 1000 + "s remaining");

                if ((diff - 300000) > 0) {
                    Thread.sleep(diff - 300000);
                }


                System.out.println("\n");
                ConsoleLogger.logName("Starting snipe...", got1);

                Main.failedLogin = 0;
                Main.avgRunTime = 0;

                ConsoleLogger.logName("Starting network and performance test!", got1);

                for (int i = 0; i < 5; i++) {

                    new Calculator(got1);
                    Thread.sleep(4000);

                }

                ConsoleLogger.logName("Total test time:" + Main.avgRunTime + "ms", got1);

                long average = Main.avgRunTime / 5;

                ConsoleLogger.logName("Average time for Ion Cannon completion:" + average + "ms", got1);

                long before = average * 42 / 100;
                if (before < 50 || before > 1000) {

                    ConsoleLogger.logName("Results might be corrupted. Correcting...", got1);
                    before = 350;

                }

                ConsoleLogger.logName("Will launch Ion Cannon " + before + "ms before release time." + "\n", got1);




                for (String comb: Main.eachAcc) {



                    long finalBefore = before;
                    Thread t = new Thread(new Runnable() {
                        public void run() {

                            String[] combination = comb.split(":");
                            String email = combination[0];
                            String password = combination[1];
                            int retries = 0;

                            try {
                                new Snipe(email, password, got1, got2, retries, finalBefore);
                            } catch (IOException | InterruptedException | ParseException e) {
                                e.printStackTrace();
                            }

                        }
                    });

                    t.start();


                }





            } catch (ParseException | InterruptedException e) {

                ConsoleLogger.logError("Invalid format \n");
                System.exit(1);

            }



        } else if (got3.equals("n")) {

            URLConnection connection = new URL("https://namemc.com/name/" + got1).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.connect();

            BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                sb.append(line);

            }

            String[] removeh = sb.toString().split("\"countdown-timer\" data-datetime=\"");
            try {
                String[] datenr = removeh[1].split(".000Z\"");


                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                String[] releasetime2 = datenr[0].split("T");

                String[] releasetime3 = releasetime2[0].split("-");


                String releasetime = releasetime3[1] + "/" + releasetime3[2] + "/" + releasetime3[0] + " " + releasetime2[1];

                String currentime = format.format(new Date(System.currentTimeMillis()));

                Date d1 = format.parse(currentime);
                Date d2 = format.parse(releasetime);


                long diff = d2.getTime() - d1.getTime();


                ConsoleLogger.logInfo("Task for username '" + got1 + "' started. " + diff / 1000 + "s remaining");

                if ((diff - 300000) > 0) {
                    Thread.sleep(diff - 300000);
                }

                System.out.println("\n");
                ConsoleLogger.logName("Starting snipe...", got1);

                Main.failedLogin = 0;
                Main.avgRunTime = 0;

                ConsoleLogger.logName("Starting network and performance test!", got1);

                for (int i = 0; i < 5; i++) {

                    new Calculator(got1);
                    Thread.sleep(4000);

                }

                ConsoleLogger.logName("Total test time:" + Main.avgRunTime + "ms", got1);

                long average = Main.avgRunTime / 5;

                ConsoleLogger.logName("Average time for Ion Cannon completion:" + average + "ms", got1);

                long before = average * 42 / 100;
                if (before < 50 || before > 1000) {

                    ConsoleLogger.logName("Results might be corrupted. Correcting...", got1);
                    before = 350;

                }

                ConsoleLogger.logName("Will launch Ion Cannon " + before + "ms before release time." + "\n", got1);

                for (String comb: Main.eachAcc) {


                    long finalBefore = before;
                    Thread t = new Thread(new Runnable() {
                        public void run() {

                            String[] combination = comb.split(":");
                            String email = combination[0];
                            String password = combination[1];
                            int retries = 0;

                            try {
                                new Snipe(email, password, got1, releasetime, retries, finalBefore);
                            } catch (IOException | InterruptedException | ParseException e) {
                                e.printStackTrace();
                            }

                        }});
                    t.start();



                }





            } catch (ArrayIndexOutOfBoundsException | ParseException | InterruptedException e) {

                ConsoleLogger.logError("Name not available!");
                System.exit(1);

            }





        } else {

            ConsoleLogger.logError("Invalid action");
            System.exit(1);



        }


    }
}