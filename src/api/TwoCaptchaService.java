package api;

import java.io.IOException;

public class TwoCaptchaService {
    private String apiKey;
    private String googleKey;
    private String pageUrl;
    private String proxyIp;
    private String proxyPort;
    private String proxyUser;
    private String proxyPw;
    private ProxyType proxyType;
    private HttpWrapper hw;

    public TwoCaptchaService(String apiKey, String googleKey, String pageUrl) {
        this.apiKey = apiKey;
        this.googleKey = googleKey;
        this.pageUrl = pageUrl;
        this.hw = new HttpWrapper();
    }

    public TwoCaptchaService(String apiKey, String googleKey, String pageUrl, String proxyIp, String proxyPort, ProxyType proxyType) {
        this(apiKey, googleKey, pageUrl);
        this.proxyIp = proxyIp;
        this.proxyPort = proxyPort;
        this.proxyType = proxyType;
    }

    public TwoCaptchaService(String apiKey, String googleKey, String pageUrl, String proxyIp, String proxyPort, String proxyUser, String proxyPw, ProxyType proxyType) {
        this(apiKey, googleKey, pageUrl);
        this.proxyIp = proxyIp;
        this.proxyPort = proxyPort;
        this.proxyUser = proxyUser;
        this.proxyPw = proxyPw;
        this.proxyType = proxyType;
    }

    public String solveCaptcha() throws InterruptedException, IOException {

        String parameters = "key=" + this.apiKey + "&method=userrecaptcha" + "&googlekey=" + this.googleKey + "&pageurl=" + this.pageUrl;
        if (this.proxyIp != null) {
            if (this.proxyUser != null) {
                parameters = parameters + "&proxy=" + this.proxyUser + ":" + this.proxyPw + "@" + this.proxyIp + ":" + this.proxyPort;
            } else {
                parameters = parameters + "&proxy=" + this.proxyIp + ":" + this.proxyPort;
            }

            parameters = parameters + "&proxytype=" + this.proxyType;
        }

        this.hw.get("http://2captcha.com/in.php?" + parameters);
        String captchaId = this.hw.getHtml().replaceAll("\\D", "");
        @SuppressWarnings("unused")
        int timeCounter = 0;

        do {
            this.hw.get("http://2captcha.com/res.php?key=" + this.apiKey + "&action=get" + "&id=" + captchaId);
            Thread.sleep(1000L);
            ++timeCounter;

        } while(this.hw.getHtml().contains("NOT_READY"));


        String gRecaptchaResponse = this.hw.getHtml().replaceAll("OK\\|", "").replaceAll("\\n", "");
        return gRecaptchaResponse;
    }

    public String getApiKey() {
        return this.apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getGoogleKey() {
        return this.googleKey;
    }

    public void setGoogleKey(String googleKey) {
        this.googleKey = googleKey;
    }

    public String getPageUrl() {
        return this.pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getProxyIp() {
        return this.proxyIp;
    }

    public void setProxyIp(String proxyIp) {
        this.proxyIp = proxyIp;
    }

    public String getProxyPort() {
        return this.proxyPort;
    }

    public void setProxyPort(String proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getProxyUser() {
        return this.proxyUser;
    }

    public void setProxyUser(String proxyUser) {
        this.proxyUser = proxyUser;
    }

    public String getProxyPw() {
        return this.proxyPw;
    }

    public void setProxyPw(String proxyPw) {
        this.proxyPw = proxyPw;
    }

    public ProxyType getProxyType() {
        return this.proxyType;
    }

    public void setProxyType(ProxyType proxyType) {
        this.proxyType = proxyType;
    }
}
