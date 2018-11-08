package day24;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class PicThread implements Runnable {

    private Socket socket;

    public PicThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        String ip = socket.getInetAddress().getHostAddress();
        int port = socket.getPort();
        System.out.println(ip + ":" + port + "...uploading");
        File file = new File(ip + "/" + "server.png");
        if (file.exists()) {
            int count = 1;
            file = new File(ip + "/" + "server(" + count + ").png");
            while (file.exists()) {
                count++;
                file = new File(ip + "/" + "server(" + count + ".png");
            }
        } else {
            File parent = file.getParentFile();
            if (!parent.exists()) {
                if (parent.mkdirs()) {
                    System.out.println("create dir:" + ip);
                } else {
                    System.out.println("create dir fail");
                }
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(file);
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            byte buf[] = new byte[1024];
            int len;
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }

            System.out.println(ip + ":" + port + "...upload success");

            os.write("上传成功".getBytes());

            fos.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(ip + ":" + port + "...upload fail");
        }
    }
}

class PicClient2 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("192.168.2.220", 10008);

        String fileName = "pic.png";
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("文件不存在");
            return;
        }

        if (!file.getName().endsWith("png")) {
            System.out.println("上传图片格式必须为png格式");
            return;
        }

        if (file.length() > 1024 * 1024 * 5) {
            System.out.println("图片过大，图片大小最大为5M");
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

class PicServer2 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(10008);

        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(new PicThread(socket)).start();
        }
    }
}
