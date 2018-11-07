package day23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

class UDPSend2 {
    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;

        String localHostAddress = "192.168.238.1";
        int port = 10001;//端口号
        InetAddress receiverAddress = InetAddress.getByName(localHostAddress);

        while ((line = br.readLine()) != null) {
            if ("886".equals(line)) {
                break;
            }

            byte buf[] = line.getBytes();
            DatagramPacket dp = new DatagramPacket(buf, buf.length, receiverAddress, port);
            ds.send(dp);
        }

        ds.close();
    }
}

class UDPReceive2 {
    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket(10001);

        byte buf[] = new byte[1024];

        while (true) {
            DatagramPacket dp = new DatagramPacket(buf, buf.length);
            ds.receive(dp);

            String ip = dp.getAddress().getHostAddress();
            int port = dp.getPort();
            String data = new String(dp.getData(), 0, dp.getLength());

            System.out.println(ip + ":" + port);
            System.out.println(data);
        }
    }
}


