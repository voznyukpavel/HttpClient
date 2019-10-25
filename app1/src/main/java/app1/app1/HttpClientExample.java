package app1.app1;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Properties;

public class HttpClientExample {

    public static void main(String[] args) throws Exception {
        String url = "https://repo.openearth.community/artifactory/dsgbase-p2-snapshots/installables/master/20191024082943-4cfdf4bdc4/plugins/",
                // proxy settings
                proxy = "np1prxy801.corp.halliburton.com", port = "80", proxyuser = "H239267",
                proxyPassword = "Ufoper660",
                // credentials
                user = "pavel.vozniuk", password = "opcl542GKQM!(^";

        setProxy(proxy, port, proxyuser, proxyPassword);
        InputStream in = setConnection(url, user, password);
        char c;
        String s;
        ArrayList<String> strings = new ArrayList<>();
        boolean flag=false;
        while (true) {
            s = "";
            do {
                c = (char) in.read();
                s += c + "";
            } while (c != '\n');
            if (s.contains(".jar")) {
                strings.add(s);
                flag=true;
            }
            if (flag&&!s.contains(".jar")) {
                break;
            }
        }
        for (String string : strings) {
            System.out.print(string);
        }
        in.close();
    }

    private static InputStream setConnection(String url, String user, String password)
            throws MalformedURLException, IOException {
        URL server = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) server.openConnection();
        String userpass = user + ":" + password;
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));
        connection.setRequestProperty("Authorization", basicAuth);
        connection.connect();
        InputStream in = connection.getInputStream();
        return in;
    }

    private static void setProxy(String proxy, String port, String proxyuser, String proxyPassword) {
        Properties systemProperties = System.getProperties();
        systemProperties.setProperty("http.proxyHost", proxy);
        systemProperties.setProperty("http.proxyPort", port);
        systemProperties.setProperty("http.proxyUser", proxyuser);
        systemProperties.setProperty("http.proxyPassword", proxyPassword);

        systemProperties.setProperty("https.proxyHost", proxy);
        systemProperties.setProperty("https.proxyPort", port);
        systemProperties.setProperty("https.proxyUser", proxyuser);
        systemProperties.setProperty("https.proxyPassword", proxyPassword);
    }
}
