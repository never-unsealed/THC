package toxic;

import logger.ConsoleLogger;
import snipe.PingCheck;
import snipe.Snipe;

import java.io.IOException;
import java.text.ParseException;

public class InitSnipe {

    public InitSnipe(String curname, String fdate) throws InterruptedException {

        System.out.println("\n");
        ConsoleLogger.logName("Starting snipe...", curname);

        Main.failedLogin = 0;
        Main.ping = 0;


        for (int i = 0; i < 5; i++) {

            new PingCheck();
            Thread.sleep(1500);

        }

        long average = Main.ping / 5;

        ConsoleLogger.logName("Average ping to Mojang API:" + average + "ms" + "\n", curname);

        long before = average;


        for (String comb: Main.eachAcc) {



            long finalBefore = before;
            Thread t = new Thread(() -> {

                String[] combination = comb.split(":");
                String email = combination[0];
                String password = combination[1];
                int retries = 0;

                try {
                    new Snipe(email, password, curname, fdate, retries, finalBefore);
                } catch (IOException | InterruptedException | ParseException e) {
                    e.printStackTrace();
                }

            });

            t.start();


        }


    }

}
