package snipe;

import logger.ConsoleLogger;
import toxic.Main;

import java.io.IOException;
import java.net.InetAddress;

public class PingCheck {

    public PingCheck() {



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