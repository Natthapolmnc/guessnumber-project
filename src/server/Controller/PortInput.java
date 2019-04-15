package server.Controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.ServerSocket;

public class PortInput{
    static public Stage Port;
    static ServerSocket Server;
    static int NumPlayer;
    private double xOffset;
    private double yOffset;
    public TextField PortInput;
    public ComboBox PlayerNum;
    public Button CreateButton;
    public Rectangle bar;


    public void run(){
        try {
            Parent root =  FXMLLoader.load(getClass().getResource("/server/resources/FXML/PortInputVbox.fxml"));
            Scene portScene=new Scene(root);
            Port=new Stage();
            Port.setTitle("server");
            Port.initStyle(StageStyle.TRANSPARENT);
            Port.setScene(portScene);
            Port.showAndWait();
        }catch (IOException e){

            System.out.println("FXML File not found");
        }
    }
    public int getNumPlayer(){
        return NumPlayer;
    }
    public ServerSocket getServer(){
        return Server;
    }

    @FXML
    public void initialize(){
        PlayerNum.getItems().addAll(2,3,4,5);
        PlayerNum.getSelectionModel().selectFirst();
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
                Port.setX(event.getScreenX() - xOffset);
                Port.setY(event.getScreenY() - yOffset);
            }
        });
    }

    @FXML
    private void ClickedCreate(Event e){
        try {
            if (!(Integer.parseInt(PortInput.getText())>=0 && Integer.parseInt(PortInput.getText())<=65535)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Port number should be numbers in range 0-65535");
                alert.showAndWait();
            }
            NumPlayer=(int) PlayerNum.getValue();
            Server=new ServerSocket(Integer.parseInt(PortInput.getText()));
            PortInput.setText("");
            Stage stage=(Stage) CreateButton.getScene().getWindow();
            stage.close();

        } catch (Exception k){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Port number should be integer");
            alert.showAndWait();
        }

        }

        @FXML

        public void exit(Event l){
        System.exit(0);
        }


}
