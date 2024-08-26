package utils;

import config.PropertiesConfig;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

public class MeleeUtils {
    private static final Logger log = Logger.getLogger(MeleeUtils.class.getName());

    public static Optional<Map<String, String>> fetchEnvironmentVariables(){
        try{
            PropertiesConfig propertiesConfig = new PropertiesConfig(
                    System.getenv("EMAIL_PASSWORD"),
                    System.getenv("EMAIL_HOST"),
                    System.getenv("EMAIL_STORE_TYPE"),
                    System.getenv("EMAIL_ADDRESS")
            );
            return Optional.of(
                    Map.of(
                            "host", propertiesConfig.getHost(),
                            "storeType", propertiesConfig.getStoreType(),
                            "address", propertiesConfig.getAddress(),
                            "password", propertiesConfig.getPassword()
                    )
            );
        }catch (Exception error){
            System.err.println("Error fetching environment variables: " + error.getLocalizedMessage());
        }
        return Optional.empty();
    }

    public static void HandleMultipartContent(Message message){
        try{
            Object content = message.getContent();
            Multipart multipart = (Multipart) content;
            for(int i = 0; i < multipart.getCount(); i++){
                BodyPart bodyPart = multipart.getBodyPart(i);
                if (bodyPart.getContent() instanceof String){
                    String bodyContent = (String) bodyPart.getContent();
                    log.info(bodyContent);
                }
            }
        } catch (Exception exception){
            System.err.println("Handling Multipart Content: " + exception.getLocalizedMessage());
        }
    }

    public static void HandleHTMLContent(Message message){
        try{
            Object content = message.getContent();
            log.info((String) content);
        } catch(Exception exception){
            System.err.println("Handling HTML Content: " + exception.getLocalizedMessage());
        }
    }
}
