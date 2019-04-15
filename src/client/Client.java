package client;

import client.Controller.ConnectHub;
import client.Controller.Game;
import client.Controller.NameInput;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;


public class Client extends Application {
    private String playerName;
    private String command;
    private boolean win;
    static BufferedWriter out;
    static BufferedReader in;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
            ConnectHub connectHub = new ConnectHub();
            NameInput name = new NameInput();
            Game game = new Game();
            connectHub.run();
            name.run();
            game.run();


    }
}