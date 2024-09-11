package utils;

import config.PropertiesConfig;
import io.github.cdimascio.dotenv.Dotenv;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.*;
import java.nio.charset.*;

public class MeleeUtils {
    private static final Logger log = Logger.getLogger(MeleeUtils.class.getName());

    public static Optional<PropertiesConfig> fetchEnvironmentVariables(){
        try{
            Dotenv dotenv = Dotenv.load();
            return Optional.of(
                new PropertiesConfig(
                    dotenv.get("EMAIL_PASSWORD"),
                    dotenv.get("EMAIL_HOST"),
                    dotenv.get("EMAIL_STORE_TYPE"),
                    dotenv.get("EMAIL_ADDRESS"),
                    dotenv.get("GEMINI_AI_URL"),
                    dotenv.get("GEMINI_API_KEY")
                )
            );
        }catch (Exception error){
            log.warning("melee: error fetching environment variables: " + error.getLocalizedMessage());
        }
        return Optional.empty();
    }

    public static String HandleMultipartContent(Message message) {
        try {
            StringBuilder bodyContent = new StringBuilder();
            Object content = message.getContent();
            Multipart multipart = (Multipart) content;
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                if (bodyPart.getContent() instanceof String) {
                    assert false;
                    bodyContent.append((String) bodyPart.getContent());
                }
            }
            return bodyContent.toString();
        } catch (Exception exception) {
            log.warning("melee: error handling multipart content: " + exception.getLocalizedMessage());
        }
        return "";
    }

    public static String randomString() {
        int n = 20;
        byte[] array = new byte[256];
        new Random().nextBytes(array);
        String randomString
                = new String(array, StandardCharsets.UTF_8);
        StringBuilder r = new StringBuilder();

        for (int k = 0; k < randomString.length(); k++) {
            char ch = randomString.charAt(k);
            if (((ch >= 'a' && ch <= 'z')
                    || (ch >= 'A' && ch <= 'Z')
                    || (ch >= '0' && ch <= '9'))
                    && (n > 0)) {
                r.append(ch);
                n--;
            }
        }
        return r.toString();
    }
}
