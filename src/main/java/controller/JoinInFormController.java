package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import util.NetworkUtils;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class JoinInFormController {
    public TextField txtIP;
    public TextField txtName;
    public Button btnJoinServer;
    private Map<String,Short> localNetworksInfo=new HashMap<>();
    private static Socket socket;

    public void initialize() throws SocketException {
        /*Lets obtain the network interfaces asssociated with this device*/
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

        /*Lets iterate each network interface*/
        while (networkInterfaces.hasMoreElements()){
            NetworkInterface ni = networkInterfaces.nextElement();
            //System.out.println(ni);
            List<InterfaceAddress> interfaceAddresses = ni.getInterfaceAddresses();
            for (InterfaceAddress interfaceAddress : interfaceAddresses) {
        /*        System.out.println(interfaceAddress);*/
                if (interfaceAddress.getAddress() instanceof  Inet4Address) {
             /*       System.out.println(interfaceAddress.getAddress());
                    System.out.println(interfaceAddress.getNetworkPrefixLength());*/
                    localNetworksInfo.put(interfaceAddress.getAddress().getHostAddress(),interfaceAddress.getNetworkPrefixLength());
                    break;

                }
            }

        }



        InetAddress loopbackAddress = Inet4Address.getLoopbackAddress();
        System.out.println(loopbackAddress.getHostAddress());
        Platform.runLater(() -> {

            try {
                InetAddress localHost = InetAddress.getLocalHost();
                txtIP.setText(localHost.getHostAddress());
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }

        });
    }

    private boolean isWithinNetworks(String ipAddress){
        Set<String> networks = localNetworksInfo.keySet();
        for (String network : networks) {
            short subnetmask = localNetworksInfo.get(network);
            if (NetworkUtils.isWithinNetwork(network,subnetmask,ipAddress)) {
                return true;
            }
        }
        return false;
    }

    public void btnJoinServerOnAction(ActionEvent actionEvent) throws SocketException {

        if(txtIP.getText().isBlank()){
            new Alert(Alert.AlertType.ERROR,"Server IP cann't be empty.").showAndWait();
            txtIP.requestFocus();
            return;
        } else if (txtName.getText().isBlank()) {
            new Alert(Alert.AlertType.ERROR,"Name cann't be empty.").showAndWait();
            txtName.requestFocus();
            return;
            
        }  /* else if (!isWithinNetworks(txtIP.getText().trim())) {
            new Alert(Alert.AlertType.ERROR,"Server IP is not reachable").showAndWait();
            txtIP.requestFocus();
            return;
        }*/
        try {
            InetAddress serverIpAddress = Inet4Address.getByName(txtIP.getText());
            if (!serverIpAddress.isReachable(1000)) {
                throw new IOException();
            }
            Socket socket = new Socket(serverIpAddress, 6060);
           // System.out.println(socket.getRemoteSocketAddress());
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/MainForm.fxml"));
            Parent mainForm = fxmlLoader.load();
            Scene scene = new Scene(mainForm);
            MainFormController ctrl = fxmlLoader.getController();
            ctrl.initData(socket,txtName.getText());
            Stage stage = (Stage) btnJoinServer.getScene().getWindow();
            stage.setScene(scene);
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.show();
            if(socket.isConnected()){
                System.out.println("Reachable");
            }
        } catch (UnknownHostException e) {
            new Alert(Alert.AlertType.ERROR,"Invalid Server IP").show();
            txtIP.requestFocus();
            return;
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"Failed to reach this server");
        }

        System.out.println("Valid IP");

    }

    private boolean isInterface(String input) throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()){
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            while (inetAddresses.hasMoreElements()){
                InetAddress inetAddress = inetAddresses.nextElement();
           /*     System.out.println("-----------------------------");
                System.out.println(inetAddress);
                System.out.println(inetAddress.getHostName());
                System.out.println(inetAddress.getHostAddress());
                System.out.println("/"+input+"------------------------------");*/
                if(inetAddress.getHostAddress().equals(input)){
                    System.out.println(inetAddress.getHostAddress());
                    System.out.println("true");
                    return true;

                }
            }

        }
        return false;

    }



/*    private boolean isOctet(String input){
        if(input.length()==0||input.length()>3) return false;
        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            if(!Character.isDigit(aChar)) return false;
        }
        int number=Integer.parseInt(input);
        if(number>=0 && number<=255) return true;
        return false;

    }*/
}
