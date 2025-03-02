package tn.esprit.services;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RecaptchaAPI {
    public static boolean verifyCaptcha(String recaptchaResponse) throws IOException {
        String secretKey = "6Lc3ueYqAAAAAPIRce3d-Efx0Ho3TaFjoNpMvYmR";
        String url = "https://www.google.com/recaptcha/api/siteverify?secret=" + secretKey + "&response=" + recaptchaResponse;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        JSONObject myResponse = new JSONObject(response.toString());
        return myResponse.getBoolean("success");
    }

}
