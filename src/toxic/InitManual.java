package toxic;

import logger.ConsoleLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InitManual {


    public InitManual(String submode) throws IOException {

        ConsoleLogger.logInput("Enter name: ");
        BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
        String got1 = br1.readLine();


        if (submode.equals("4")) {

            ConsoleLogger.logInput("Enter exact date (GMT)(mm/dd/yyyy hh:mm:ss): ");
            BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
            String got2 = br2.readLine();

            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            try {

                Date d1 = format.parse(got2);

                Date date1 = new Date(System.currentTimeMillis());


                Date d2 = format.parse(format.format(date1));


                long diff = d1.getTime() - d2.getTime();


                if (diff < 0) {

                    ConsoleLogger.logError("Invalid date \n");
                    System.exit(1);

                }

                ConsoleLogger.logInfo("Task for username '" + got1 + "' started. " + diff / 1000 + "s remaining");

                if ((diff - 30000) > 0) {
                    Thread.sleep(diff - 30000);
                }


                new InitSnipe(got1, got2);


            } catch (ParseException | InterruptedException e) {

                ConsoleLogger.logError("Invalid format \n");
                System.exit(1);

            }



        } else if (submode.equals("1")) {

            URLConnection connection = new URL("https://namemc.com/name/" + got1).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.connect();
            
            BufferedReader r = null;
            try {
                r = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            }catch(IOException e){
                
                ConsoleLogger.logError("Connection to namemc failed! (VPS IP detected?)");
                System.exit(1);
                
            }
                
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                sb.append(line);

            }

            String[] removeh = sb.toString().split("\"countdown-timer\" data-datetime=\"");
            try {
                String[] datenr = removeh[1].split(".000Z\"");


                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                String[] releasetime2 = datenr[0].split("T");

                String[] releasetime3 = releasetime2[0].split("-");


                String releasetime = releasetime3[1] + "/" + releasetime3[2] + "/" + releasetime3[0] + " " + releasetime2[1];

                String currentime = format.format(new Date(System.currentTimeMillis()));

                Date d1 = format.parse(currentime);
                Date d2 = format.parse(releasetime);


                long diff = d2.getTime() - d1.getTime();


                ConsoleLogger.logInfo("Task for username '" + got1 + "' started. " + diff / 1000 + "s remaining");

                if ((diff - 30000) > 0) {
                    Thread.sleep(diff - 30000);
                }

                new InitSnipe(got1, releasetime);





            } catch (ArrayIndexOutOfBoundsException | ParseException | InterruptedException e) {

                ConsoleLogger.logError("Name not available!");
                System.exit(1);

            }





        } else {

            ConsoleLogger.logError("Invalid action");
            System.exit(1);



        }


    }
}