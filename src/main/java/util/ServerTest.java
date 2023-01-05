package util;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

public class ServerTest {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("192.168.1.7", 6060);


        System.out.println(socket.getRemoteSocketAddress());
        System.out.println("------------------------");
        System.out.println(InetAddress.getLocalHost());
        System.out.println(InetAddress.getLocalHost().getAddress());
        System.out.println(InetAddress.getLocalHost().getHostAddress());
        System.out.println(InetAddress.getLocalHost().getHostName());

        System.out.println(1<<2);

        Enumeration<NetworkInterface> ni = NetworkInterface.getNetworkInterfaces();
        if (ni.hasMoreElements()) {
            System.out.println(ni.nextElement().getHardwareAddress());
            System.out.println(ni.nextElement().getInterfaceAddresses());
            System.out.println(ni.nextElement().getInetAddresses());

        }

    }
}
