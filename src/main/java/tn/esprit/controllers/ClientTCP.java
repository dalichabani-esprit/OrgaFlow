package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientTCP {

    @FXML
    private TextArea taDiscussion;
    @FXML
    private TextArea taMessage;
    @FXML
    private TextField tfNickname;
    @FXML
    private TextField tfIP;


    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    @FXML
    public void connect() {
        try {
            String serverIP = tfIP.getText().trim();
            clientSocket = new Socket(serverIP, 9999);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            new Thread(this::listenForMessages).start();
        } catch (Exception e) {
            taDiscussion.appendText("Error: Unable to connect to the server.\n");
        }
    }

    @FXML
    public void sendMessage() {
        String message = taMessage.getText().trim();
        String nickname = tfNickname.getText().trim();

        if (message.isEmpty()) {
            return;
        }

        if (nickname.isEmpty()) {
            nickname = "Anonyme";
        }


        out.println(nickname + ": " + message);
        taMessage.clear();
    }

    private void listenForMessages() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                taDiscussion.appendText(message + "\n");
            }
        } catch (Exception e) {
            taDiscussion.appendText("Error: Disconnected from the server.\n");
        }
    }

    public void closeConnection() {
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
        } catch (Exception e) {
            // Ignore errors during close
        }
    }
}
