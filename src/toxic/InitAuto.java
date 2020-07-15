package toxic;

import logger.ConsoleLogger;
import snipe.ActivityCheck;
import snipe.PingCheck;
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

public class InitAuto {

    public InitAuto() throws ParseException, IOException {


        ConsoleLogger.logInfo("Retrieving names..." + "\n");

        URLConnection connection = new URL("https://namemc.com/minecraft-names?length_op=eq&length=3").openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        connection.connect();

        BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            sb.append(line);
        }

        String[] removeh = sb.toString().split("<div class=\"card-body p-0\">");

        String[] removef = removeh[1].split("<div class=\"col-lg-7 order-lg-4\">");

        String[] usernames = removef[0].split("<div class=\"row no-gutters py-1 px-3 border-top\">");


        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        String currentime = format.format(new Date(System.currentTimeMillis()));

        Date d1 = null;
        Date d2 = null;

        for (String namepart: usernames) {



            String[] name = namepart.split("translate=\"no\">|\\</a></div>");

            String[] time = namepart.split("time datetime=\"|\\\">");


            String[] releasetime1 = time[6].split("T");

            if (releasetime1[1].contains(">")) {


                releasetime1 = time[5].split("T");
            }

            String releasetime2 = releasetime1[1].replace(".000Z", "");



            String[] releasetime3 = releasetime1[0].split("-");


            String releasetime = releasetime3[1] + "/" + releasetime3[2] + "/" + releasetime3[0] + " " + releasetime2;

            d1 = format.parse(currentime);
            d2 = format.parse(releasetime);


            long diff = d2.getTime() - d1.getTime();

            long totalsecond = diff / 1000;


            if (totalsecond < 0) {

                continue;
            }




            Thread t = new Thread(new Runnable() {
                public void run() {


                    ConsoleLogger.logInfo("Thread for username '" + name[1] + "' started. " + totalsecond + "s remaining");



                    if (((totalsecond * 1000) - 300000) > 0) {
                        try {
                            Thread.sleep((totalsecond * 1000) - 300000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    if (!Main.isActive) {


                        System.out.println("\n");
                        ConsoleLogger.logName("Starting snipe...", name[1]);

                        Thread activity = new Thread(new Runnable() {
                            public void run() {

                                new ActivityCheck();

                            }
                        });
                        activity.start();

                        Main.failedLogin = 0;
                        Main.ping = 0;


                        for (int i = 0; i < 5; i++) {

                            try {
                                new PingCheck(name[1]);
                                Thread.sleep(1500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }

                        long average = Main.ping / 5;

                        ConsoleLogger.logName("Average ping to Mojang API:" + average + "ms" + "\n", name[1]);

                        long before = average;



                        for (String comb: Main.eachAcc) {
                            try {
                                long finalBefore = before;
                                Thread t = new Thread(new Runnable() {
                                    public void run() {

                                        String[] combination = comb.split(":");
                                        String email = combination[0];
                                        String password = combination[1];
                                        int retries = 0;

                                        try {
                                            new Snipe(email, password, name[1], releasetime, retries, finalBefore);
                                        } catch (IOException | InterruptedException | ParseException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
                                t.start();

                            } catch (ArrayIndexOutOfBoundsException e) {

                                continue;

                            }


                        }

                    } else {

                        ConsoleLogger.logName("Another snipe is active already", name[1]);

                    }

                }

            });
            t.start();

        }


    }

}