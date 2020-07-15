package webhook;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendMessage {

    public SendMessage(String url, String message) {

        try {

            URL hook = new URL(url);

            HttpURLConnection conn = (HttpURLConnection) hook.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:68.0) Gecko/20100101 Firefox/68.0");

            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            String content = "username=THC Sniper&avatar_url=https://64.media.tumblr.com/c1a79db29d7af5772c9a0d135162ed2d/tumblr_mteceqBOTn1qe26n9o1_500.jpg&content=" + message;
            os.write(content.getBytes());

            os.flush();
            os.close();

            conn.getResponseCode();



        } catch(Exception ex) {

        }

    }

}