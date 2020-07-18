package snipe;

import logger.ConsoleLogger;
import toxic.Main;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PingCheck {

    public PingCheck() throws InterruptedException {



        long currentTime = System.currentTimeMillis();
        boolean isPinged = false;
        try {
            isPinged = InetAddress.getByName("api.mojang.com").isReachable(2000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentTime = System.currentTimeMillis() - currentTime;
        if(isPinged) {

            Main.ping = Main.ping + currentTime;

        } else {
            ConsoleLogger.logError("Ping to Mojang failed.");
            System.exit(1);
        }


    }



}