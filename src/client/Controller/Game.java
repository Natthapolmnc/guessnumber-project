package client.Controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.*;

public class Game {
    static Stage game=new Stage();
    String command;
    static boolean win;
    static BufferedWriter out;
    static BufferedReader in;
    public Button checkButton;
    public Text nameText;
    public Text logText;
    public ComboBox box1;
    public ComboBox box2;
    public ComboBox box3;
    public ComboBox box4;

    public void run(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/resources/FXML/PlayerInterface.fxml"));
            Parent root = loader.load();
            Scene gameScene = new Scene(root);
            game.setTitle("Game");
            game.initStyle(StageStyle.DECORATED);

            game.setScene(gameScene);
            game.show();
            new Thread(() -> {
                try {
                    out.write(NameInput.getName());
                    out.newLine();
                    out.flush();
                    do {
                        command = in.readLine();
                        System.out.println(command);
                        if (command.equals("Connect")){
                            out.write(NameInput.getName());
                            out.newLine();
                            out.flush();
                            String wait=in.readLine();
                            System.out.println(wait);
                            setLogText(wait);
                        }else if (command.equals("Ready")) {
                            System.out.println("Ready");
                            ableCheck();
                            String text=in.readLine();
                            setLogText(text);
                        } else if (command.equals("Wait")) {
                            disableCheck();
                            String text=in.readLine();
                            setLogText(text);
                        } else if (command.equals("WIN")){
                            JOptionPane.showMessageDialog(null,"Congratulation!!, You WIN","YOU WIN",JOptionPane.INFORMATION_MESSAGE);
                            command="END";
                        }else if (command.equals("NOOB")){
                            JOptionPane.showMessageDialog(null,"YOU LOSE!","LOSE",JOptionPane.INFORMATION_MESSAGE);
                            command="END";
                        }

                    } while (command != "END");
                    System.exit(0);
                } catch (IOException error) {
                    JOptionPane.showMessageDialog(null,"Server down.","Error",JOptionPane.ERROR_MESSAGE);
                }
            }).start();}
        catch (IOException error){
            System.out.println("FXML FILE NOT FOUND.");
        }
    }

    public void ableCheck(){
        ((Button) game.getScene().getRoot().getChildrenUnmodifiable().get(4)).setDisable(false);
    }
    public void disableCheck(){
        ((Button) game.getScene().getRoot().getChildrenUnmodifiable().get(4)).setDisable(true);
    }

    public void trick() throws IOException{
        char q=(char) box1.getValue();
        char w=(char) box2.getValue();
        char e=(char) box3.getValue();
        char r=(char) box4.getValue();
        String sum=""+q+w+e+r;
        out.write(sum);
        out.newLine();
        out.flush();
    }

    @FXML
    private void  initialize(){
        checkButton.setDisable(true);
        in=ConnectHub.getIn();
        out=ConnectHub.getOut();
        nameText.setText(NameInput.name);
        box1.getItems().addAll('0','1','2','3','4','5','6','7','8','9');
        box2.getItems().addAll('0','1','2','3','4','5','6','7','8','9');
        box3.getItems().addAll('0','1','2','3','4','5','6','7','8','9');
        box4.getItems().addAll('0','1','2','3','4','5','6','7','8','9');
        box1.getSelectionModel().selectFirst();
        box2.getSelectionModel().selectFirst();
        box3.getSelectionModel().selectFirst();
        box4.getSelectionModel().selectFirst();
    }

    public void setLogText(String log){
        ((Text) game.getScene().getRoot().getChildrenUnmodifiable().get(8)).setText(log);
    }

    @FXML
    private void check(Event e){
        try {
            trick();
        }catch (IOException error){
            JOptionPane.showMessageDialog(null,"Error","Can't commute with server.",JOptionPane.ERROR_MESSAGE);
        }

    }

}
