package day23;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class UploadClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("192.168.2.220", 10006);

        String fileName = "src/day23/IPDemo.java";
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("文件不存在");
            return;
        }

        BufferedReader br = new BufferedReader(new FileReader(file));
        BufferedReader bufIn =
                new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out =
                new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

        String line;
        while ((line = br.readLine()) != null) {
            out.println(line);
        }

        socket.shutdownOutput();//关闭客户端输出流，相当于加入结束标记-1

        System.out.println(bufIn.readLine());

        br.close();
        socket.close();
    }
}

class UploadServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(10006);

        Socket socket = serverSocket.accept();

        String ip = socket.getInetAddress().getHostAddress();
        int port = socket.getPort();
        System.out.println(ip + ":" + port + "...connect");

        String fileName = "server.txt";
        PrintWriter pr = new PrintWriter(new FileWriter(fileName), true);
        BufferedReader bufIn =
                new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out =
                new PrintWriter(socket.getOutputStream(), true);

        String line;
        while ((line = bufIn.readLine()) != null) {
            pr.println(line);
        }

        out.println("上传成功！");

        pr.close();
        socket.close();
        serverSocket.close();
    }
}