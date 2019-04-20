package server;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import server.Controller.PortInput;
import server.Controller.ServerLog;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;


public class Server extends Application {
    private ServerLog serverLog;
    private ServerSocket server;
    static BufferedWriter[] out;
    static BufferedReader[] in;
    private ZonedDateTime time=ZonedDateTime.now();
    private String log="";

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage){
        //try {
        PortInput port=new PortInput();
        serverLog=new ServerLog();
        port.run();
        serverLog.run();
        server=port.getServer();
        int numPlayer=port.getNumPlayer();
        Socket[] playerSocket=new Socket[numPlayer];
        addText("Server started");

        ServerThread server =new ServerThread(numPlayer);
        server.start();
        /*}catch (IOException error){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Server down.");
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.showAndWait();
            alert.close();
        }*/
    }

    class ServerThread extends Thread{
        private int numPlayer;
        private boolean win;
        InputStreamReader[] dataIn;
        OutputStreamWriter[] dataOut;
        String[] name;
        Socket[] playerSocket;
        char[] clue={'_','_','_','_'};
        String clueHint;
        String password;


        ServerThread(int numPlayer){
            this.numPlayer=numPlayer;
            dataIn=new InputStreamReader[numPlayer];
            dataOut=new OutputStreamWriter[numPlayer];
            in=new BufferedReader[numPlayer];
            out=new BufferedWriter[numPlayer];
            playerSocket=new Socket[numPlayer];
            name=new String[numPlayer];
            Random rand=new Random();
            String Storage="";
            for (int i=0;i<4;i++){
                int l = rand.nextInt(10);
                Storage += l; }
            password="1234";
            clueHint="";
            for (char k:clue){
                clueHint+=k;
            }
        }

        @Override
        public void run() {
            try {
                addText("Waiting for client on port " + server.getLocalPort() + "...");
            for (int i=0;i<numPlayer;i++) {
                addText("Waiting for Player " + (i + 1) + " connection....");
                playerSocket[i] = server.accept();
                dataIn[i] = new InputStreamReader(playerSocket[i].getInputStream());
                dataOut[i] = new OutputStreamWriter(playerSocket[i].getOutputStream());
                in[i] = new BufferedReader(dataIn[i]);
                out[i] = new BufferedWriter(dataOut[i]);
                out[i].write("Connect");
                out[i].newLine();
                out[i].flush();
                name[i]=in[i].readLine();
                addText(name[i] + " is connected as Player " + (i + 1));
                if (i<numPlayer-1) {
                    out[i].write("Waiting for Player" + (i + 2));
                    out[i].newLine();
                    out[i].flush();
                }else {
                    out[i].write("Waiting for Player"+1);
                    out[i].newLine();
                    out[i].flush();
                }
            }
            while (!(win)) {
            for (int i=0;i<numPlayer;i++){
                System.out.println(i);
                if(win){
                    break;
                }
                out[i].write("Ready");
                out[i].newLine();
                out[i].flush();
                out[i].write("Your turn: Now clue: "+ clueHint );
                out[i].newLine();
                out[i].flush();
                String answer=in[i].readLine();
                System.out.println(answer);
                boolean correct=false;
                for (int j = 0; j < answer.length(); j++) {
                    if (answer.charAt(j) == password.charAt(j)) {
                        correct = true;
                        clue[j] = answer.charAt(j);
                    }
                }
                    clueHint = "";
                    for (char z : clue) {
                        clueHint += z;
                    }
                    if (answer.equals(password)){
                        win=true;
                        out[i].write("You Win");
                        out[i].newLine();
                        out[i].flush();
                        out[i].write("WIN");
                        out[i].newLine();
                        out[i].flush();
                    }if (correct) {
                        addText(name[i]+" correct some now clue "+clueHint);
                        out[i].write("Wait");
                        out[i].newLine();
                        out[i].flush();
                        out[i].write("Some correct:Now clue:" + clueHint+" Wait for other");
                        out[i].newLine();
                        out[i].flush();
                    } else {
                        addText(name[i]+" All wrong "+clueHint);
                        out[i].write("Wait");
                        out[i].newLine();
                        out[i].flush();
                        out[i].write("All wrong:Now clue:" + clueHint+" Wait for other");
                        out[i].newLine();
                        out[i].flush();
                    }

                }
            }
                for(BufferedWriter to:out){
                    System.out.println();
                    to.write("END");
                    to.newLine();
                    to.flush();
                }
            JOptionPane.showMessageDialog(null,"END","SERVER",JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);

            }catch(IOException error){
                JOptionPane.showMessageDialog(null,"Client down.","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }



    private void addText(String text){
        log+=time.format(DateTimeFormatter.RFC_1123_DATE_TIME)+" :  "+text+"\n";
        serverLog.setText(log);
    }
}
