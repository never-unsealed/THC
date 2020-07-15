package snipe;

import logger.ConsoleLogger;
import toxic.Main;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArrayRemove {

    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    public ArrayRemove(String email, String password, String curname) throws IOException {

        List< String > list = new ArrayList< >();
        Collections.addAll(list, Main.eachAcc);
        list.removeAll(Arrays.asList(email + ":" + password));
        Main.eachAcc = list.toArray(EMPTY_STRING_ARRAY);

        String uno = Arrays.toString(Main.eachAcc).replace(", ", "\n");
        String dos = uno.replaceAll("[\\[\\]]", "");
        String tres = dos.replace(email + ":" +  password, "");
        String cuatro = tres.replaceAll("(?m)^\\s*$[\n\r]{1,}", "");
        File newac = new File("accounts.txt");
        newac.createNewFile();

        BufferedWriter writer1 = new BufferedWriter(new FileWriter(newac));
        writer1.write(cuatro);
        writer1.close();

        if(Main.failedLogin > 2){

            ConsoleLogger.logNameError("Reached a critical level: Too many invalid accounts will lead to an IP ban by Mojang!", curname);

        }

        if (Main.eachAcc.length == 0) {

            ConsoleLogger.logInfo("All accounts used. Quitting...");;
            System.exit(0);


        }


    }

}
