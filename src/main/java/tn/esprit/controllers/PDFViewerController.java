package tn.esprit.controllers;

import com.google.gson.Gson;
import okhttp3.*;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Base64;

public class PDFViewerController {

    @FXML
    private ImageView pdfImageView;

    @FXML
    private Canvas radarCanvas;

    private List<Image> pdfImages;
    private int currentPage = 0;
    private List<File> tempImageFiles = new ArrayList<>(); // Ajout de la liste pour stocker les fichiers temporaires

    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent";
    private static final String API_KEY = "AIzaSyCfRB6rJaFKMJDDDUXyOWeLi3XIYma8m1Y"; // Remplacer par votre clé API

    @FXML
    public void initialize() {
    }

    @FXML
    public void importPdf() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File selectedFile = fileChooser.showOpenDialog(pdfImageView.getScene().getWindow());

        if (selectedFile != null) {
            loadPdf(selectedFile.getAbsolutePath());
        }
    }

    public void loadPdf(String pdfFilePath) {
        try {
            PDDocument document = PDDocument.load(new File(pdfFilePath));
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            pdfImages = new ArrayList<>();

            for (int page = 0; page < document.getNumberOfPages(); page++) {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300);
                File imageFile = new File("page_" + page + ".png");
                ImageIO.write(bim, "png", imageFile);
                pdfImages.add(new Image(imageFile.toURI().toString()));
                tempImageFiles.add(imageFile); // Stocker le fichier temporaire
            }
            document.close();
            showPage(0);
            if (!pdfImages.isEmpty()) {
                analyzeImageWithGemini(tempImageFiles.get(0)); // Utiliser le fichier temporaire stocké
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showPage(int pageNumber) {
        if (pdfImages != null && !pdfImages.isEmpty() && pageNumber >= 0 && pageNumber < pdfImages.size()) {
            pdfImageView.setImage(pdfImages.get(pageNumber));
            currentPage = pageNumber;
        }
    }

    @FXML
    public void nextPage() throws IOException {
        if (pdfImages != null && !pdfImages.isEmpty() && currentPage < pdfImages.size() - 1) {
            showPage(currentPage + 1);
            analyzeImageWithGemini(tempImageFiles.get(currentPage));
        }
    }

    @FXML
    public void previousPage() throws IOException {
        if (pdfImages != null && !pdfImages.isEmpty() && currentPage > 0) {
            showPage(currentPage - 1);
            analyzeImageWithGemini(tempImageFiles.get(currentPage));
        }
    }

    private void analyzeImageWithGemini(File imageFile) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n" +
                "  \"contents\": [{\n" +
                "    \"parts\": [{\n" +
                "      \"inlineData\": {\n" +
                "        \"mimeType\": \"image/png\",\n" +
                "        \"data\": \"" + encodeImageToBase64(imageFile) + "\"\n" +
                "      }\n" +
                "    }, {\n" +
                "      \"text\": \"Extract age, number of diplomas, contact information (email, phone, address), hobbies, and presentation quality from the image. Return the result as a JSON object.\"\n" +
                "    }]\n" +
                "  }]\n" +
                "}");
        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("x-goog-api-key", API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            String responseBody = response.body().string();
            Gson gson = new Gson();
            GeminiResponse geminiResponse = gson.fromJson(responseBody, GeminiResponse.class);

            String extractedData = geminiResponse.candidates[0].content.parts[0].text;

            // Extract data from JSON
            DataExtractionResult result = parseExtractedData(extractedData);

            double ageScore = 1.0 - Math.abs(result.age - 25) / 5.0;
            double competenceScore = Math.min(1.0, result.numDiplomas / 5.0);
            double contactScore = (result.hasEmail ? 0.25 : 0) + (result.hasPhone ? 0.25 : 0) + (result.hasAddress ? 0.5 : 0);
            double hobbiesScore = result.hasHobbies ? 1.0 : 0.0;
            double presentationNote = result.presentationScore;

            double[] scores = {ageScore, competenceScore, contactScore, hobbiesScore, presentationNote};

            double averageScore = 0;
            for (double score : scores) {
                averageScore += score;
            }
            averageScore /= scores.length;

            String status;
            if (averageScore <= 0.4) {
                status = "Refusé";
            } else if (averageScore <= 0.8) {
                status = "En cours";
            } else {
                status = "Accepté";
            }

            Radarchart radarchart = new Radarchart();
            radarchart.setRadarCanvas(radarCanvas);
            radarchart.setData(scores);
            radarchart.initialize();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Statut de l'analyse");
            alert.setHeaderText(null);
            alert.setContentText("Statut : " + status + "\nNote moyenne : " + averageScore);
            alert.showAndWait();

        }
    }

    private String encodeImageToBase64(File imageFile) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(imageFile);
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    static class GeminiResponse {
        Candidate[] candidates;
    }

    static class Candidate {
        Content content;
    }

    static class Content {
        Part[] parts;
    }

    static class Part {
        String text;
    }

    static class DataExtractionResult {
        int age;
        int numDiplomas;
        boolean hasEmail;
        boolean hasPhone;
        boolean hasAddress;
        boolean hasHobbies;
        double presentationScore;
    }

    private DataExtractionResult parseExtractedData(String extractedData) {

        //This is where the parsing logic of the JSON response should be written.
        //For now, dummy data is returned.
        DataExtractionResult result = new DataExtractionResult();
        result.age = 25;
        result.numDiplomas = 3;
        result.hasEmail = true;
        result.hasPhone = true;
        result.hasAddress = true;
        result.hasHobbies = true;
        result.presentationScore = 0.9;

        return result;
    }
}