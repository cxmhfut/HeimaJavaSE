package day24;

import java.net.MalformedURLException;
import java.net.URL;

public class URLDemo {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://localhost:8080/myweb/1.html?name=A&age=12");

        System.out.println("Protocol:" + url.getProtocol());
        System.out.println("Host:" + url.getHost());
        System.out.println("Port:" + url.getPort());
        System.out.println("Path:" + url.getPath());
        System.out.println("File:" + url.getFile());
        System.out.println("Query:" + url.getQuery());
    }
}
