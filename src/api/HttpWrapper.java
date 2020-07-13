package api;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

public class HttpWrapper {
    private boolean printHeaders = false;
    private String html = "";
    private int responseCode = 0;

    public void get(String url) {
        try {
            URL url_ = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)url_.openConnection();
            conn.setRequestMethod("GET");
            conn.setAllowUserInteraction(false);
            conn.setDoOutput(false);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0");
            conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
            conn.setRequestProperty("Connection", "keep-alive");
            String headers = "";
            String key;
            if (this.printHeaders) {
                for(@SuppressWarnings("rawtypes")
                    Iterator var6 = conn.getHeaderFields().keySet().iterator(); var6.hasNext(); headers = headers + (key != null ? key + ": " : "") + conn.getHeaderField(key) + "\n") {
                    key = (String)var6.next();
                }
            }

            this.responseCode = conn.getResponseCode();
            BufferedReader d = new BufferedReader(new InputStreamReader(new DataInputStream(conn.getInputStream())));
            String result = "";

            for(String line = null; (line = d.readLine()) != null; result = result + line + "\n") {
                line = new String(line.getBytes(), "UTF-8");
            }

            d.close();
            if (this.printHeaders) {
                this.setHtml(headers + "\n" + result);
            } else {
                this.setHtml(result);
            }

        } catch (IOException var8) {
            throw new IllegalStateException("An IOException occurred:\n" + var8.getMessage());
        }
    }

    public String getHtml() {
        return this.html;
    }

    private void setHtml(String html) {
        this.html = html;
    }

    public void setPrintHeaders(boolean trueOrFalse) {
        this.printHeaders = trueOrFalse;
    }

    public int getResponseCode() {
        return this.responseCode;
    }
}