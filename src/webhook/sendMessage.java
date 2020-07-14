package webhook;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class sendMessage {

    public sendMessage(String url, String message) {

        try {

            URL hook = new URL(url);

            HttpURLConnection conn = (HttpURLConnection) hook.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:68.0) Gecko/20100101 Firefox/68.0");

            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            String content = "content=" + message;
            os.write(content.getBytes());

            os.flush();
            os.close();

            System.out.println(conn.getResponseCode());

        } catch(Exception ex) {

        }

    }

}
