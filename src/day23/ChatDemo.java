package day23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 * 编写一个聊天程序
 */
public class ChatDemo {
    public static void main(String[] args) throws SocketException {
        DatagramSocket sendSocket = new DatagramSocket();
        String receiverAddressHostName = "192.168.238.1";//广播地址
        int port = 10002;

        new Thread(new Send(sendSocket, receiverAddressHostName, port)).start();
        new Thread(new Receive(port)).start();
    }
}

/**
 * 发送
 */
class Send implements Runnable {

    private DatagramSocket ds;
    private BufferedReader br;
    private String receiverAddressHostName;
    private int port;

    public Send(DatagramSocket ds, String receiverAddressHostName, int port) {
        this.ds = ds;
        this.br = new BufferedReader(new InputStreamReader(System.in));
        this.receiverAddressHostName = receiverAddressHostName;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            InetAddress receiverAddress = InetAddress.getByName(receiverAddressHostName);
            String line;

            while ((line = br.readLine()) != null) {
                byte buf[] = line.getBytes();
                DatagramPacket dp = new DatagramPacket(buf, buf.length, receiverAddress, port);
                ds.send(dp);
            }
        } catch (IOException e) {
            throw new RuntimeException("发送失败");
        } finally {
            ds.close();
        }
    }
}

/**
 * 接收
 */
class Receive implements Runnable {

    private DatagramSocket ds;
    private int port;

    public Receive(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            ds = new DatagramSocket(port);
            byte buf[] = new byte[1024];
            DatagramPacket dp = new DatagramPacket(buf, buf.length);
            while (true) {
                ds.receive(dp);
                String ip = dp.getAddress().getHostAddress();
                int port = dp.getPort();
                String data = new String(dp.getData(), 0, dp.getLength());
                System.out.println(ip + ":" + port);
                System.out.println(data);
            }
        } catch (IOException e) {
            throw new RuntimeException("接收失败");
        } finally {
            ds.close();
        }
    }
}