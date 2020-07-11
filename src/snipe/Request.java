package snipe;

import logger.ConsoleLogger;
import toxic.Main;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;


public class Request {

    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    public Request(String hash, String bearer, String curname, String curpass, int requests, String email, String password, String masked) {




        try {
            URL url = new URL("https://api.mojang.com/user/profile/" + hash + "/name");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:68.0) Gecko/20100101 Firefox/68.0");
            con.setRequestProperty("Accept", "*/*");
            con.setRequestProperty("Accept-Encoding", "gzip, deflate");
            con.setRequestProperty("Referer", "https://my.minecraft.net/en-us/profile/");
            con.setRequestProperty("Authorization", "Bearer " + bearer);
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setDoOutput(true);

            String jsonInputString = "{\"name\":\"" + curname + "\",\"password\":\"" + curpass + "\"}";

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
            Date date = new Date(System.currentTimeMillis());

            if (con.getResponseCode() == 204) {

                Date date1 = new Date(System.currentTimeMillis());

                ConsoleLogger.logRequestCount("Response 204: Username changed. Timing: " + formatter.format(date) + "/" + formatter.format(date1), requests, curname);
                ConsoleLogger.logSuccess("Username sniped on account: " + email + ":" + masked, curname);


                File suc = new File("success.txt");

                suc.createNewFile();


                String prev = "";
                @SuppressWarnings("resource")
                Scanner myReader = new Scanner(suc);
                while (myReader.hasNextLine()) {

                    if (!prev.isEmpty()) {
                        prev = prev + "\n" + myReader.nextLine();
                    } else {
                        prev = myReader.nextLine();

                    }

                }

                BufferedWriter writer = new BufferedWriter(new FileWriter(suc));
                writer.write(prev + "\n" + email + ":" + password + " - " + curname + " > " + "\n");
                writer.close();



                List < String > list = new ArrayList < > ();
                Collections.addAll(list, Main.eachAcc);
                list.removeAll(Arrays.asList(email + ":" + password));
                Main.eachAcc = list.toArray(EMPTY_STRING_ARRAY);

                if (Main.eachAcc.length == 0) {

                    ConsoleLogger.logInfo("All accounts used. Quitting...");;
                    System.exit(0);


                }

            }

        } catch (IOException e) {

            e.printStackTrace();
        }

    }



}
