package day23;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPDemo {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress i = InetAddress.getLocalHost();//获取本地主机

        System.out.println(i.toString());//DESKTOP-3K4ONBO/192.168.238.1
        System.out.println("HostName:" + i.getHostName());
        System.out.println("HostAddress:" + i.getHostAddress());

        //InetAddress ia = InetAddress.getByName("192.168.238.1");
        InetAddress ia = InetAddress.getByName("www.baidu.com");

        System.out.println(ia.toString());
        System.out.println("HostName:" + ia.getHostName());
        System.out.println("HostAddress:" + ia.getHostAddress());
    }
}
