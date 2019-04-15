package client.Controller;

import javafx.scene.shape.Rectangle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectHub {
    public TextField portFill;
    public TextField ipFill;
    public Button exitBut;
    public Button connectBut;
    public Rectangle bar;
    public static Stage connectHub=new Stage();
    public static BufferedWriter out;
    static BufferedReader in;
    static Socket client;
    double xOffset=0;
    double yOffset=0;


    public void run(){
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/resources/FXML/ConnectionGUI.fxml"));
        Parent root=loader.load();
        Scene connectScene=new Scene(root);
        connectHub.setScene(connectScene);
        connectHub.setTitle("client");
        connectHub.initStyle(StageStyle.TRANSPARENT);
        connectHub.showAndWait();
        }catch (IOException fxml){
            System.out.println("fxml file not found.");
        }

    }
    static public BufferedReader getIn(){
        return in;
    }
    static public BufferedWriter getOut(){
        return out;
    }

    public  Socket getClient(){
        return client;
    }

    @FXML
    public void initialize(){
        bar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        bar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                connectHub.setX(event.getScreenX() - xOffset);
                connectHub.setY(event.getScreenY() - yOffset);
            }
        });
    }
    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void  Connect(ActionEvent e){
        try {
         int portNum=Integer.parseInt(portFill.getText());
         String ip=ipFill.getText();
         client=new Socket(ip, portNum);
         InputStreamReader dataIn = new InputStreamReader(ConnectHub.client.getInputStream());
         OutputStreamWriter dataOut=new OutputStreamWriter(ConnectHub.client.getOutputStream());
         out=new BufferedWriter(dataOut);
         in=new BufferedReader(dataIn);
         connectHub.close();
        }catch (NumberFormatException error){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Port number should be numbers in range 0-65535");
            alert.showAndWait();
        }catch (UnknownHostException error){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Server not found.\n Please check triple check your ip and portNum");
            alert.showAndWait();
        }catch (IOException error){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("The server which you are connecting to is down.");
            alert.showAndWait();
        }
    }
}
