package server.Controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ServerLog {
    static private Stage ServerLog=new Stage();
    public MenuItem ClearButton;
    public MenuItem CloseButton;
    public TextArea LogText;
    public MenuBar menu;
    @FXML
    private double xOffset;
    @FXML
    private double yOffset;

    public void run(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/server/resources/FXML/serverlog.fxml"));
            Parent root = loader.load();
            Scene logScene = new Scene(root);
            ServerLog.setScene(logScene);
            ServerLog.setTitle("server Log");
            ServerLog.initStyle(StageStyle.TRANSPARENT);
            ServerLog.show();
        }catch (IOException out){
            System.out.println("FXML file not found");
        }
    }
    public void setText(String text){
        ((TextArea) ServerLog.getScene().getRoot().getChildrenUnmodifiable().get(1)).setText(text);
    }



    @FXML
    public void initialize(){
        xOffset=0;
        yOffset=0;
        LogText.setText("");
        menu.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        menu.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ServerLog.setX(event.getScreenX() - xOffset);
                ServerLog.setY(event.getScreenY() - yOffset);
            }
        });
    }
    public void ClearLog(Event e){
        LogText.setText("");
    }

    public void  CloseSever(Event e){
        System.exit(0);
    }

}
