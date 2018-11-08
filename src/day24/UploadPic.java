package day24;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 上传图片
 */
class PicClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("192.168.2.220", 10007);

        String fileName = "pic.png";
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("文件不存在");
            return;
        }

        FileInputStream fis = new FileInputStream(file);
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        int len;
        byte buf[] = new byte[1024];
        while ((len = fis.read(buf)) != -1) {
            os.write(buf, 0, len);
        }
        //关闭输出流，告诉服务器端文件传送结束
        socket.shutdownOutput();

        len = is.read(buf);
        String feedback = new String(buf, 0, len);
        System.out.println(feedback);

        fis.close();
        socket.close();
    }
}

class PicServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(10007);

        Socket socket = serverSocket.accept();

        String ip = socket.getInetAddress().getHostAddress();
        int port = socket.getPort();
        System.out.println(ip + ":" + port + "...connect");

        String fileName = "pic_server.png";

        FileOutputStream fos = new FileOutputStream(fileName);
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        int len;
        byte buf[] = new byte[1024];
        while ((len = is.read(buf)) != -1) {
            fos.write(buf, 0, len);
        }

        os.write("上传成功".getBytes());

        fos.close();
        socket.close();
        serverSocket.close();
    }
}
