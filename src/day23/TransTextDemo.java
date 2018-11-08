package day23;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 需求：文本转换服务器
 * <p>
 * 客户端给服务器发送文本，服务器转换成大写，再发送回给客户端
 * <p>
 * 客户端和服务端都在莫名的等待？
 * 这是因为客户端和服务端都有阻塞式方法，这些方法没有读到结束标记就会一直等
 * 而导致两端都在等待
 */

class TransClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("192.168.2.220", 10005);

        //键盘读入流，从键盘读入数据
        BufferedReader br =
                new BufferedReader(new InputStreamReader(System.in));
        //客户端读入流，从服务器读发回的数据
        BufferedReader bufIn =
                new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //客户端写出流，将数据发送到服务器
        //BufferedWriter bufOut =
        //      new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        //自带缓冲区的写出流
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        String line;

        while ((line = br.readLine()) != null) {
            if ("over".equals(line)) {
                break;
            }

            //bufOut.write(line);
            //bufOut.newLine();
            //bufOut.flush();
            out.println(line);

            System.out.println("Server:" + bufIn.readLine());
        }

        br.close();
        socket.close();
    }
}

class TransServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(10005);

        Socket socket = serverSocket.accept();

        String ip = socket.getInetAddress().getHostAddress();
        int port = socket.getPort();
        System.out.println(ip + ":" + port + "...connect");

        //服务端读取流，从客户端读取数据
        BufferedReader bufIn =
                new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //服务端写出流，向客户端写出数据
        //BufferedWriter bufOut =
        //      new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        String line;
        while ((line = bufIn.readLine()) != null) {
            System.out.println("Client:" + line);

            //bufOut.write(line.toUpperCase());
            //bufOut.newLine();
            //bufOut.flush();
            out.println(line.toUpperCase());
        }

        socket.close();
        serverSocket.close();
    }
}
