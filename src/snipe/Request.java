package snipe;

import logger.ConsoleLogger;
import toxic.Main;
import webhook.SendMessage;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class Request {


    public Request(String hash, String bearer, String curname, String curpass, String email, String masked) {




            try {
                URL url = new URL("https://api.mojang.com/user/profile/" + hash + "/name");

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Authorization", "Bearer " + bearer);
                con.setRequestProperty("Content-Type", "application/json; utf-8");
                con.setDoOutput(true);

                String jsonInputString = "{\"name\":\"" + curname + "\",\"password\":\"" + curpass + "\"}";

                try (OutputStream os = con.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
                Date date = new Date(System.currentTimeMillis());


                if (con.getResponseCode() == 204) {

                    Date date1 = new Date(System.currentTimeMillis());

                    ConsoleLogger.logName("Response 204: Username changed. Timing: " + formatter.format(date) + "/" + formatter.format(date1), curname);
                    ConsoleLogger.logSuccess("Username sniped on account: " + email + ":" + masked, curname);

                    if (!Main.webHookURL.equals("NONE") && Main.webHookURL.startsWith("https://discordapp.com/api/webhooks/") || Main.webHookURL.startsWith("http://discordapp.com/api/webhooks/")) {
                        new SendMessage(Main.webHookURL, ":white_check_mark: | Successfully sniped username **'" + curname + "'** onto **" + email + "**.");
                    }


                    File suc = new File("success.txt");
                    suc.createNewFile();
                    StringBuilder prev = new StringBuilder();
                    Scanner accReader = new Scanner(suc);
                    while (accReader.hasNextLine()) {
                        prev.append(accReader.nextLine());

                    }

                    BufferedWriter writer = new BufferedWriter(new FileWriter(suc));
                    if(prev.length() == 0){

                        writer.write(email + ":" + curpass + " - " + curname);

                    }else{

                        writer.write(prev + "\r\n" + email + ":" + curpass + " - " + curname);

                    }
                    writer.close();


                    new ArrayRemove(email, curpass, curname);


                }

            } catch (IOException e) {

                e.printStackTrace();
            }


    }



}