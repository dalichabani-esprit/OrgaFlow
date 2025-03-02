package tn.esprit.Controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import tn.esprit.services.RecaptchaAPI;

@RestController
@RequestMapping("/api")
public class RecaptchaController {

    @PostMapping("/validateRecaptcha")
    public ResponseEntity<?> validateRecaptcha(@RequestBody RecaptchaRequest request) {
        try {
            String recaptchaResponse = request.getRecaptchaResponse();
            boolean isValid = RecaptchaAPI.verifyCaptcha(recaptchaResponse);
            if (isValid) {
                return ResponseEntity.ok("{\"success\": true}");
            } else {
                return ResponseEntity.status(400).body("{\"success\": false}");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"success\": false, \"error\": \"Internal server error\"}");
        }
    }
    static class RecaptchaRequest {
        private String recaptchaResponse;
        public String getRecaptchaResponse() {
            return recaptchaResponse;
        }

        public void setRecaptchaResponse(String recaptchaResponse) {
            this.recaptchaResponse = recaptchaResponse;
        }
    }
}
