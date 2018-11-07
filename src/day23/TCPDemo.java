package day23;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 客户端
 */
class TCPClient {
    public static void main(String[] args) throws IOException {
        //1 创建客户端的Socket服务，指定目的主机和端口
        Socket socket = new Socket("192.168.238.1", 10003);
        //2 为了发送数据，应该获取Socket流中的输出流
        OutputStream os = socket.getOutputStream();
        //3 写数据
        os.write("I am coming...".getBytes());
        //4 关闭资源
        socket.close();
    }
}

/**
 * 服务端
 */
class TCPServer {
    public static void main(String[] args) throws IOException {
        //1 建立服务端的Socket服务，并监听一个端口
        ServerSocket serverSocket = new ServerSocket(10003);
        //2 获取链接到的客户端对象
        Socket socket = serverSocket.accept();
        //3 拿到客户端对象进行操作
        InputStream in = socket.getInputStream();
        byte buf[] = new byte[1024];
        int len = in.read(buf);
        String data = new String(buf, 0, len);
        String ip = socket.getInetAddress().getHostAddress();
        int port = socket.getPort();
        System.out.println(ip + ":" + port);
        System.out.println(data);
        //4 关闭资源
        socket.close();
        serverSocket.close();
    }
}