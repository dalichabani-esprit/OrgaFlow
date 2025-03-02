package tn.esprit.services;

import javafx.stage.Stage;
import org.json.JSONObject;
import tn.esprit.Controllers.logginUser;

import java.awt.Desktop;
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GoogleAuthService {

    private static final String CLIENT_ID = "159031307891-u3e1dg91p9jhbieqbgblo7g2q4cmrmmk.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "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";
    private static final String TOKEN_ENDPOINT = "https://oauth2.googleapis.com/token";
    private static final String AUTH_URL = "https://accounts.google.com/o/oauth2/auth";
    private static final String SCOPE = "email profile openid";

    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void authenticateUser(Stage primaryStage) {
        try {
            String url = AUTH_URL +
                    "?client_id=" + CLIENT_ID +
                    "&response_type=code" +
                    "&scope=" + URLEncoder.encode(SCOPE, "UTF-8") +
                    "&redirect_uri=http://localhost:5000";
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
            } else {
                System.out.println("Ouvrez ce lien dans un navigateur : " + url);
            }
            executor.submit(() -> startHttpServer(primaryStage));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void startHttpServer(Stage primaryStage) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("En attente de l'autorisation Google...");
            Socket clientSocket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String line;
            String authCode = null;
            while ((line = in.readLine()) != null) {
                if (line.contains("code=")) {
                    authCode = line.split("code=")[1].split(" ")[0];
                    break;
                }
            }

            if (authCode != null) {
                System.out.println("Code reçu : " + authCode);
                String accessToken = getAccessToken(authCode);
                if (accessToken != null) {
                    JSONObject userInfo = getUserInfo(accessToken);
                    // Utiliser une instance de logginUser pour appeler processGoogleLogin
                    logginUser logginUserInstance = new logginUser();
                    logginUserInstance.processGoogleLogin(userInfo, primaryStage);  // Passer primaryStage ici
                }
            }
            OutputStream outputStream = clientSocket.getOutputStream();
            String response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n" +
                    "<html><body><h1>Connexion réussie ! Vous pouvez fermer cette fenêtre.</h1></body></html>";
            outputStream.write(response.getBytes());
            outputStream.flush();
            clientSocket.close();
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getAccessToken(String authCode) {
        try {
            URL url = new URL(TOKEN_ENDPOINT);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            String params = "code=" + authCode +
                    "&client_id=" + CLIENT_ID +
                    "&client_secret=" + CLIENT_SECRET +
                    "&redirect_uri=http://localhost:5000" +
                    "&grant_type=authorization_code";

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = params.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            JSONObject json = new JSONObject(response.toString());
            return json.getString("access_token");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getUserInfo(String accessToken) {
        try {
            URL url = new URL("https://www.googleapis.com/oauth2/v3/userinfo");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            return new JSONObject(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
