package snipe;

import logger.ConsoleLogger;
import toxic.Main;
import webhook.SendMessage;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class Request {


    public Request(String hash, String bearer, String curname, String curpass, String email, String masked) {




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

                ConsoleLogger.logName("Response 204: Username changed. Timing: " + formatter.format(date) + "/" + formatter.format(date1), curname);
                ConsoleLogger.logSuccess("Username sniped on account: " + email + ":" + masked, curname);

                if(!Main.webHookURL.equals("NONE") && Main.webHookURL.startsWith("https://discordapp.com/api/webhooks/") || Main.webHookURL.startsWith("http://discordapp.com/api/webhooks/")) {
                        new SendMessage(Main.webHookURL, ":white_check_mark: | Successfully sniped username **'" + curname + "'** onto **" + email + "**.");
                }



                File suc = new File("success.txt");
                suc.createNewFile();
                String prev = "";
                Scanner accReader = new Scanner(suc);
                while (accReader.hasNextLine()) {
                    prev = prev + accReader.nextLine() + "\n";

                }

                BufferedWriter writer = new BufferedWriter(new FileWriter(suc));
                writer.write(prev + "\n" + email + ":" + curpass + " - " + curname);
                writer.close();


                new ArrayRemove(email, curpass, curname);



            }

        } catch (IOException e) {

            e.printStackTrace();
        }

    }



}