package day24;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 演示客户端和服务端
 */
public class ServerDemo {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(11000);

        Socket socket = serverSocket.accept();

        System.out.println(socket.getInetAddress().getHostAddress());

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        out.println("客户端您好");

        socket.close();
        serverSocket.close();
    }
}
