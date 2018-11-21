package day24;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MyIE {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8080);

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        out.println("GET /myweb/1.html HTTP/1.1");
        out.println("Accept: */*");
        out.println("Accept-Language: zh-cn");
        out.println("Host: 192.168.238.1:8080");
        out.println("Connection: closed");

        out.println();

        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String line;

        while ((line=br.readLine())!=null){
            System.out.println(line);
        }

        socket.close();
    }
}
