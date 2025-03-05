package utils;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
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
