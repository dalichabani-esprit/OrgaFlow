package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



public class ShowQuestionnaire implements Initializable {

    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent";
    private static final String API_KEY = "AIzaSyCfRB6rJaFKMJDDDUXyOWeLi3XIYma8m1Y"; // Remplacer par une variable d’environnement

    @FXML
    private TextArea lreponse;

    @FXML
    private TextArea lquestion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateQuestionnaire();
    }

    private void generateQuestionnaire() {
        String prompt = "Génère un questionnaire de 10 questions essentielles pour un entretien d'embauche.";
        String response = sendQuestionToGemini(prompt);
        response = retrieveDataFromGemini(response);
        lreponse.setText(response);
    }

    public static String sendQuestionToGemini(String question) {
        try {
            String url = API_URL + "?key=" + API_KEY;
            String jsonPayload = "{ \"contents\": [{ \"parts\": [{ \"text\": \"" + question + "\" }]}]}";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();

        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur de connexion : " + e.getMessage();
        }
    }

    private String retrieveDataFromGemini(String response) {
        try {
            JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();

            JsonArray candidatesArray = jsonObject.getAsJsonArray("candidates");
            if (candidatesArray != null && candidatesArray.size() > 0) {
                JsonObject contentObject = candidatesArray.get(0).getAsJsonObject().getAsJsonObject("content");
                JsonArray partsArray = contentObject.getAsJsonArray("parts");
                if (partsArray != null && partsArray.size() > 0) {
                    JsonElement textElement = partsArray.get(0).getAsJsonObject().get("text");
                    return textElement != null ? textElement.getAsString() : "Aucune réponse reçue.";
                }
            }

            return "Aucune réponse valide trouvée.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur lors du traitement de la réponse.";
        }
    }
}
