package tn.esprit.utils;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsApi {
    public static final String ACCOUNT_SID = "AC17eac3a0151d7805c5cb6a409a37d9b9";
    public static final String AUTH_TOKEN = "8d68c7b085f79f459712ff51c8740ea5";
    public static void sendSMS() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                        new PhoneNumber("+21693296498"),
                        new PhoneNumber("+17756289108"),
                        "Bonjour,verifier votre email svp !  ")
                .create();

        System.out.println(message.getSid());
    }
}
