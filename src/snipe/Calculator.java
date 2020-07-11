package snipe;

import logger.ConsoleLogger;
import toxic.Main;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Calculator {

    public Calculator(String curname) throws InterruptedException {

        long now = System.currentTimeMillis();
        int tnow = Thread.activeCount();




        for (int i = 0; i < 30; i++) {


            Thread t = new Thread(new Runnable() {
                @SuppressWarnings("unused")
                public void run() {





                    try {
                        URL url = new URL("https://api.mojang.com/user/profile/" + "b2762a8581f343a18dab2a16d2ad4ead" + "/name");

                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("POST");
                        con.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:68.0) Gecko/20100101 Firefox/68.0");
                        con.setRequestProperty("Accept", "*/*");
                        con.setRequestProperty("Accept-Encoding", "gzip, deflate");
                        con.setRequestProperty("Referer", "https://my.minecraft.net/en-us/profile/");
                        con.setRequestProperty("Authorization", "Bearer ");
                        con.setRequestProperty("Content-Type", "application/json; utf-8");
                        con.setDoOutput(true);

                        String jsonInputString = "{\"name\":\"" + "4345" + "\",\"password\":\"" + "54345" + "\"}";

                        try (OutputStream os = con.getOutputStream()) {
                            byte[] input = jsonInputString.getBytes("utf-8");
                            os.write(input, 0, input.length);
                        }

                        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
                        Date date = new Date(System.currentTimeMillis());
                        long no1w = System.currentTimeMillis();

                        if (con.getResponseCode() == 204) {

                            SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm:ss.SSS");
                            Date date1 = new Date(System.currentTimeMillis());




                        } else if (con.getResponseCode() == 400) {

                            SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm:ss.SSS");
                            Date date1 = new Date(System.currentTimeMillis());



                        } else if (con.getResponseCode() == 401) {

                            SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm:ss.SSS");
                            Date date1 = new Date(System.currentTimeMillis());



                        } else {



                        }

                    } catch (IOException e) {

                        e.printStackTrace();
                    }


                }

            });
            t.start();
            Thread.sleep(20);
        }



        while (Thread.activeCount() != tnow);

        long later = (System.currentTimeMillis() - now);
        ConsoleLogger.logName("It took " + later + "ms", curname);
        Main.avgRunTime = Main.avgRunTime + later;




    }



}
