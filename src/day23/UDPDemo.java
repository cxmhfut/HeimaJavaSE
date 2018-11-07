package day23;

import java.io.IOException;
import java.net.*;

/**
 * 需求：通过UDP传输方式将一段文字传输出去
 * <p>
 * 步骤：
 * 1 建立UDP Socket服务
 * 2 提供数据，并将数据封装到数据包中
 * 3 通过Socket服务的发送功能，将数据包发出去
 * 4 关闭资源
 */
class UDPSend {
    public static void main(String[] args) throws IOException {

        String localHostAddress = "192.168.238.1";
        int port = 10000;//端口号

        //1 创建UDP服务，通过DatagramSocket对象
        DatagramSocket ds = new DatagramSocket();

        //2 确定数据，并封装成数据包
        byte data[] = "UDP data".getBytes();
        DatagramPacket dp = new DatagramPacket(data, data.length, InetAddress.getByName(localHostAddress), port);

        //3 通过Socket服务，将已有的数据包发送出去
        ds.send(dp);

        //4 关闭资源
        ds.close();
    }
}

/**
 * 需求：定义一个应用程序，用于接收UDP传输的数据并进行处理
 * <p>
 * 步骤
 * 1 建立UDP Socket服务
 * 2 定义一个数据包，用于接收数据
 * 3 通过Socket服务的receive方法将接收到的数据存入已定义的数据包中
 * 4 通过数据包的相关功能将数据取出
 * 5 关闭资源
 */
class UDPReceive {
    public static void main(String[] args) throws IOException {
        //1 建立UDP Socket服务，建立端点
        DatagramSocket ds = new DatagramSocket(10000);

        //2 定义数据包，用于接收数据
        byte buf[] = new byte[1024];
        DatagramPacket dp = new DatagramPacket(buf, buf.length);

        //3 通过receive方法将接收到的数据存入已定义的数据包中
        ds.receive(dp);//阻塞式方法

        //4 通过数据包的方法获取其中的数据
        String ip = dp.getAddress().getHostAddress();
        String data = new String(dp.getData(), 0, dp.getLength());
        int port = dp.getPort();
        System.out.println(ip + ":" + port + ":" + data);

        //5 关闭资源
        ds.close();
    }
}
