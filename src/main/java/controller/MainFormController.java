package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;

import java.io.*;
import java.net.Socket;


public class MainFormController {
    public JFXListView lstOnline;
    public JFXTextArea txtMsg;
    public JFXButton btnSend;
    public TextArea txaView;
    private Socket socket;
    private String name;
    public void initData(Socket socket,String name) {
        this.socket=socket;
        this.name=name;
        new Thread(() -> {
            try {
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                while(true) {
                    String message = br.readLine();
                    System.out.println(message);
                    Platform.runLater(() -> {
                        txaView.appendText(message + "\n\r");
                    });
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }).start();





    }

    public void btnSendOnAction(ActionEvent actionEvent) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bw.write(name+": "+txtMsg.getText()+"\n");
        bw.flush();
        txtMsg.clear();
        txtMsg.requestFocus();
    }
}
