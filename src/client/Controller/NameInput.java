package client.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;


public class NameInput {
    private static Stage nameInput=new Stage();
    static String name;
    public Rectangle mainRectangle;
    public Rectangle subRectangle;
    public TextField nameIn;

    public void run(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/resources/FXML/EnterName.fxml"));
            Parent root=loader.load();
            Scene nameScene=new Scene(root);
            nameInput.setScene(nameScene);
            nameInput.initStyle(StageStyle.TRANSPARENT);
            nameInput.showAndWait();

        }catch (IOException error){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("FXML file not found.\n Contact: Natthapol3011@gmail.com");
            alert.showAndWait();
        }
    }

    static public String getName(){
        return name;
    }

    @FXML

    public void initialize(){
        nameIn.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                name = nameIn.getText();
                nameInput.close();
            }
        });
    }
}
