package snipe;

import logger.ConsoleLogger;
import toxic.Main;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArrayRemove {

    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    public ArrayRemove(String email, String password){

        List< String > list = new ArrayList< >();
        Collections.addAll(list, Main.eachAcc);
        list.removeAll(Arrays.asList(email + ":" + password));
        Main.eachAcc = list.toArray(EMPTY_STRING_ARRAY);

        if(Main.failedLogin > 2){

            ConsoleLogger.logError("Reached a critical level: Too many invalid accounts will lead to an IP ban by Mojang!");

        }

        if (Main.eachAcc.length == 0) {

            ConsoleLogger.logInfo("All accounts used. Quitting...");;
            System.exit(0);


        }


    }

}
