package day23;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 演示客户端和服务端的相互通信
 */

class TCPClient2 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("192.168.238.1", 10004);

        OutputStream os = socket.getOutputStream();
        os.write("服务端，你好".getBytes());

        InputStream is = socket.getInputStream();
        byte buffer[] = new byte[1024];
        int len = is.read(buffer);
        String data = new String(buffer, 0, len);
        System.out.println(data);
    }
}

class TCPServer2 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(10004);

        Socket socket = serverSocket.accept();
        InputStream is = socket.getInputStream();
        byte buffer[] = new byte[1024];
        int len = is.read(buffer);
        String data = new String(buffer,0,len);
        String ip = socket.getInetAddress().getHostAddress();
        int port = socket.getPort();
        System.out.println(ip+":"+port);
        System.out.println(data);

        OutputStream os = socket.getOutputStream();

        os.write("收到，你好".getBytes());

        socket.close();
        serverSocket.close();
    }
}
