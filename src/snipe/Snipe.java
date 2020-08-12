package snipe;

import logger.ConsoleLogger;
import toxic.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Snipe {


    public Snipe(String email, String password, String curname, String fdate, long before) throws IOException, InterruptedException, ParseException {


        StringBuilder masked = new StringBuilder();

        for (char ignored: password.toCharArray()) {

            masked.append("*");

        }

        ConsoleLogger.logName("Trying to authenticate account: " + email + ":" + masked, curname);


        URL urlh = new URL("https://authserver.mojang.com/authenticate");

        HttpURLConnection conh = (HttpURLConnection) urlh.openConnection();
        conh.setRequestMethod("POST");
        conh.setRequestProperty("Content-Type", "application/json; utf-8");
        conh.setDoOutput(true);
        conh.setDoInput(true);

        String jsonInputString = "{\"agent\":{\"name\":\"Minecraft\",\"version\": 1},\"username\":\"" + email + "\",\"password\":\"" + password + "\",\"requestUser\":true}";


        try (OutputStream oso = conh.getOutputStream()) {
            byte[] input1 = jsonInputString.getBytes(StandardCharsets.UTF_8);
            oso.write(input1, 0, input1.length);
        }


        if (conh.getResponseCode() == 200) {
            String jsonResponse1;

            try (BufferedReader br1 = new BufferedReader(new InputStreamReader(conh.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response1 = new StringBuilder();
                String responseLine1;
                while ((responseLine1 = br1.readLine()) != null) {
                    response1.append(responseLine1.trim());
                }

                jsonResponse1 = response1.toString();
            }



            String uhash = jsonResponse1.split("selectedProfile")[1].split("\"id\":\"")[1].split("\"")[0];


            String oauth = jsonResponse1.split("\"accessToken\":\"")[1].split("\"")[0];

            URL sqserver = new URL("https://api.mojang.com/user/security/challenges");

            HttpURLConnection sq = (HttpURLConnection) sqserver.openConnection();

            sq.setRequestMethod("GET");
            sq.setRequestProperty("Authorization", "Bearer " + oauth);

            sq.setDoOutput(true);
            sq.connect();

            sq.getResponseCode();


            ConsoleLogger.logName("Successfully authenticated account: " + email + ":" + masked, curname);


            String finalMasked = masked.toString();
            Runnable request = () -> new Request(uhash, oauth, curname, password, email, finalMasked);
            ExecutorService ic = Executors.newFixedThreadPool(25);

            long wait;

            if (Main.eachAcc.length > 5) {

                Random r = new Random();
                int low = 5;
                int high = 15;

                wait = r.nextInt(high - low) + low;

            } else {

                wait = 20;


            }




            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");
            SimpleDateFormat formatic = new SimpleDateFormat("HH:mm:ss.SSS");
            Date date = new Date(System.currentTimeMillis());
            String currentime = format.format(date);


            Date d1 = format.parse(currentime);
            Date d2 = format.parse(fdate + ".000");


            long diff = d2.getTime() - d1.getTime();


            if ((diff - 500 + before + Main.timeadjust) > 0) {


                Thread.sleep(diff - 500 - before + Main.timeadjust);

                Date date1 = new Date(System.currentTimeMillis());
                ConsoleLogger.logName("Starting Ion Cannon for " + email + ". Timing: " + formatic.format(date1), curname);

                for (int i = 0; i < 25; i++) {

                    ic.execute(request);
                    Thread.sleep(wait);

                }

                Thread.sleep(2000);
                ic.shutdown();
                while (!ic.isTerminated());


                Date date2 = new Date(System.currentTimeMillis());
                ConsoleLogger.logName("Finished Ion Cannon for " + email + ". Timing: " + formatic.format(date2), curname);

            } else {

                ConsoleLogger.logFailed(email + " couldn't prepare in time.", curname);

            }

        } else {

            Main.failedLogin++;
            ConsoleLogger.logFailed("Account seems to be invalid: " + email + ":" + masked, curname);

            new ArrayRemove(email, password, curname);

        }



    }

}