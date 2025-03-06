package tn.esprit.sms;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import tn.esprit.utils.MyDatabase;


import java.sql.Connection;

public class SmsSender {

    Connection cnx;

    public SmsSender() {
        cnx = MyDatabase.getInstance().getConnection();
    }

    // twilio.com/console
    public static final String ACCOUNT_SID = "AC1db899b2ba5f4ad8ff8f31e004e33b1d";
    public static final String AUTH_TOKEN = "0d3d5121ac1b0f9a5ed4661938aeff6e";

    public static void main(String[] args) {
        // Sending SMS to the specified number
        String clientPhoneNumber = "25472331"; // The recipient's phone number
        String message = "Check ur email"; // Customize your message
        sendSMS(clientPhoneNumber, message);
    }

    public static void sendSMS(String clientPhoneNumber, String messageBody) {
        String accountSid = ACCOUNT_SID; // Use the class constant
        String authToken = AUTH_TOKEN; // Use the class constant

        try {
            Twilio.init(accountSid, authToken);
            Message message = Message.creator(
                    new PhoneNumber("+216" + clientPhoneNumber),
                    new PhoneNumber("+16622005023"), // Your Twilio number
                    messageBody
            ).create();

            System.out.println("SID du message : " + message.getSid());
        } catch (Exception ex) {
            System.out.println("Erreur : " + ex.getMessage());
        }
    }
}