package webhook;

import java.net.HttpURLConnection;
import java.net.URL;

public class checkHook {

    public static Boolean check(String HookUrl) {
        try {
            if(HookUrl.length() != 0) {

                URL check = new URL(HookUrl);

                HttpURLConnection conn = (HttpURLConnection) check.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:68.0) Gecko/20100101 Firefox/68.0");
                conn.setDoOutput(true);

                if(conn.getResponseCode() == 200) {
                    System.out.println(conn.getResponseCode());
                    return true;
                } else {
                    System.out.println(conn.getResponseCode());
                    return false;
                }

            } else {
                System.out.println("No Webhook-URL provided.");
                return false;
            }
        } catch (Exception ex) {
            if(HookUrl.startsWith("https://discordapp.com/api/webhooks/") || HookUrl.startsWith("http://discordapp.com/api/webhooks/")) {
                System.out.println("Invalid URL.");
                return false;
            }
        }
        return false;
    }

}
