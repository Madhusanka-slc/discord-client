package util;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class DiscordServerApp {

    public static void main(String[] args) {
        List<Socket> userList = new ArrayList<>();
        StringBuilder chatHistory = new StringBuilder();

        try (ServerSocket serverSocket = new ServerSocket(6060)) {
            System.out.println("Server started");
            while (true){
                System.out.println("Waiting for an incoming connection");
                Socket localSocket = serverSocket.accept();
                userList.add(localSocket);
                System.out.println("Incoming connection: " + localSocket.getRemoteSocketAddress());
                new Thread(()->{
                    try {
                        BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(localSocket.getOutputStream()));
                        bw1.write(chatHistory.toString() + "\n");
                        bw1.flush();

                        BufferedReader br = new BufferedReader(new InputStreamReader(localSocket.getInputStream()));
                        while (true){
                            String message = br.readLine();
                            chatHistory.append(message + "\n");

                            for (Socket socket : userList) {
                                if (socket.isConnected()){
                                    var bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                                    bw.write(message + "\n");
                                    bw.flush();
                                }
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
        } catch (IOException e) {
            System.err.println("Failed to create the discord server");
            e.printStackTrace();
        }
    }
}
