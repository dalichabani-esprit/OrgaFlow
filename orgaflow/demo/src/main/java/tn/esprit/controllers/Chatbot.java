package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.nio.charset.StandardCharsets;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;




public class Chatbot {
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent";
    private static final String API_KEY = "AIzaSyChUmJB9Kp2aqRgLWfpprMpTWPy62FvV8A";


    @FXML
    private TextArea taQuestion;

    @FXML
    private TextArea taAnswer;


    @FXML
    private Label labelError;



    @FXML
    public void sendQuestion(ActionEvent event) {
        String response = sendQuestionToGemini(taQuestion.getText());
        response = retrieveDataFromGemini(response);
        taAnswer.setText(response);
    }

    @FXML
    public void clearText(ActionEvent event) {
        taQuestion.clear();
        taAnswer.clear();
    }


    public static String sendQuestionToGemini(String question) {
        try {
            String GEMINI_API_KEY = "AIzaSyChUmJB9Kp2aqRgLWfpprMpTWPy62FvV8A";
            // Your API URL and request body
            String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + GEMINI_API_KEY;
            String jsonPayload = "{" +
                    "\"contents\": [{" +
                    "\"parts\": [{" +
                    "\"text\": \"" + question +"\"" +
                    "}]" +
                    "}]" +
                    "}";

            // Create HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Build the HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload, StandardCharsets.UTF_8))
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();


        } catch (Exception e) {
            e.printStackTrace();

            return "Error" + e.getMessage();
        }
    }

    private String retrieveDataFromGemini(String response) {
        try {
            // Parse the JSON response string into a JsonObject
            JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();

            // Navigate through the JSON structure to get the text
            JsonArray candidatesArray = jsonObject.getAsJsonArray("candidates");
            if (candidatesArray != null && candidatesArray.size() > 0) {
                JsonObject contentObject = candidatesArray.get(0).getAsJsonObject().getAsJsonObject("content");
                JsonArray partsArray = contentObject.getAsJsonArray("parts");
                if (partsArray != null && partsArray.size() > 0) {
                    // Extract the text value
                    JsonElement textElement = partsArray.get(0).getAsJsonObject().get("text");
                    return textElement != null ? textElement.getAsString() : "Text not found";
                }
            }

            // Return a message if no text is found
            return "Text not found";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error parsing JSON";
        }

    }

}
