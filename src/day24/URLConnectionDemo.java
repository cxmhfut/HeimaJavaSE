package day24;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class URLConnectionDemo {
    public static void main(String[] args) throws IOException {
        URL url = new URL("http://localhost:8080/myweb/1.html");

        URLConnection conn = url.openConnection();

        System.out.println(conn);

        InputStream is = conn.getInputStream();

        byte buf[] = new byte[1024];

        int len = is.read(buf);
        System.out.println(new String(buf, 0, len));
    }
}
