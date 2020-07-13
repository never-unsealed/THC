package snipe;

import api.TwoCaptchaService;
import logger.ConsoleLogger;
import toxic.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Snipe {


    public Snipe(String email, String password, String curname, String fdate, int retries, long before) throws IOException, InterruptedException, ParseException {


        String masked = "";

        for (@SuppressWarnings("unused") char chars: password.toCharArray()) {

            masked = masked + "*";

        }

        ConsoleLogger.logName("Trying to authenticate account: " + email + ":" + masked, curname);


        URL urlh = new URL("https://authserver.mojang.com/authenticate");

        HttpURLConnection conh = (HttpURLConnection) urlh.openConnection();
        conh.setRequestMethod("POST");
        conh.setRequestProperty("Content-Type", "application/json; utf-8");
        conh.setDoOutput(true);

        String jsonHash = "{\"agent\":{\"name\":\"Minecraft\",\"version\":1},\"username\":\"" + email + "\",\"password\":\"" + password + "\"}";


        try (OutputStream oso = conh.getOutputStream()) {
            byte[] input1 = jsonHash.getBytes("utf-8");
            oso.write(input1, 0, input1.length);
        }
        

        if (conh.getResponseCode() == 200) {
            String jsonResponse1;

            try (BufferedReader br1 = new BufferedReader(new InputStreamReader(conh.getInputStream(), "utf-8"))) {
                StringBuilder response1 = new StringBuilder();
                String responseLine1 = null;
                while ((responseLine1 = br1.readLine()) != null) {
                    response1.append(responseLine1.trim());
                }

                jsonResponse1 = response1.toString();
            }



            String[] hash1 = jsonResponse1.split("\"id\":\"");
            String[] hash2 = hash1[1].split("\"");
            String uhash = hash2[0];

            String responseToken = null;



            String apiKey = Main.captchakeyst;
            String googleKey = "6LfbsiMUAAAAAOu1nGK8InBaFrIk17dcbI0sqvzj";
            String pageUrl = "https://www.minecraft.net/en-us/login";

            TwoCaptchaService service = new TwoCaptchaService(apiKey, googleKey, pageUrl);

            responseToken = service.solveCaptcha();



            URL url = new URL("https://authserver.mojang.com/authenticate");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setDoOutput(true);

            String jsonInputString = "{\"username\":\"" + email + "\",\"password\":\"" + password + "\",\"captcha\":\"" + responseToken + "\",\"captchaSupported\":\"invisibleReCaptcha\",\"requestUser\":true}";



            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            String jsonResponse;

            try {

                try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }

                    jsonResponse = response.toString();
                }


                String[] bearer1 = jsonResponse.split("\"accessToken\":\"");
                String[] bearer2 = bearer1[1].split("\"");
                String oauth = bearer2[0];


                ConsoleLogger.logName("Successfully authenticated account: " + email + ":" + masked, curname);

                Date d1 = null;
                Date d2 = null;

                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");
                Date date = new Date(System.currentTimeMillis());
                String currentime = format.format(date);


                d1 = format.parse(currentime);
                d2 = format.parse(fdate + ".000");


                long diff = d2.getTime() - d1.getTime();


                if ((diff - before + Main.timeadjust) > 0) {
                    Thread.sleep(diff - before + Main.timeadjust);

                    int tnow = Thread.activeCount();



                    ConsoleLogger.logName("Starting Ion Cannon for " + email, curname);

                    for (int i = 0; i < 25; i++) {


                        String finalMasked = masked;
                        Thread t = new Thread(new Runnable() {
                            public void run() {

                                new Request(uhash, oauth, curname, password, email, finalMasked);


                            }

                        });

                        t.start();
                        Thread.sleep(20);


                    }

                    while (Thread.activeCount() != tnow);


                    ConsoleLogger.logName("Finished Ion Cannon for " + email, curname);

                }else{

                    ConsoleLogger.logFailed(email + " couldn't prepare in time.", curname);

                }






            } catch (IOException e) {



                if (retries < 2) {

                    ConsoleLogger.logNameError("Invalid captcha key for account: " + email + ":" + masked + " - Retrying...", curname);
                    new Snipe(email, password, curname, fdate, retries + 1, before);

                } else {

                    ConsoleLogger.logFailed("Account seems to be invalid: " + email + ":" + masked, curname);

                    new ArrayRemove(email, password);



                }

            }

        } else {

            Main.failedLogin++;
            ConsoleLogger.logFailed("Account seems to be invalid: " + email + ":" + masked, curname);

            new ArrayRemove(email, password);

        }



    }

}
