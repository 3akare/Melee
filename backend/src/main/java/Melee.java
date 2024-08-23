import config.PropertiesConfig;
import log.LogService;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Logger;

import static constant.MeleeConstants.BATCH_SIZE;

public class Melee {
    private final Logger log = Logger.getLogger(Melee.class.getName());

    public void appStart(){
        Map<String, String> environmentVariables = Objects.requireNonNull(fetchEnvironmentVariables()).orElseThrow(
                () -> new RuntimeException("Unable to fetch environment variables")
        );
        log.info("env: " + environmentVariables.toString());
        createEmailFetchSession(environmentVariables);
    }

    private Optional<Map<String, String>> fetchEnvironmentVariables(){
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

    private Properties setUpProperties(String host){
        Properties properties = new Properties();
        properties.put("mail.pop3.host", host);
        properties.put("mail.pop3.port", "995");
        properties.put("mail.pop3.starttls.enable", "true");

        return properties;
    }

    private void createEmailFetchSession(Map<String, String> environmentVariables){
        System.out.println(environmentVariables);
        try {
            Session emailSession = Session.getDefaultInstance(setUpProperties(environmentVariables.get("host")));
            Store store = emailSession.getStore(environmentVariables.get("storeType") + "s");

            System.out.println(store);

            store.connect(
                    environmentVariables.get("host"),
                    environmentVariables.get("address"),
                    environmentVariables.get("password")
            );

            System.out.println(store);

            //create the folder object and open it
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            int totalMessages = inbox.getMessageCount();
            int start = 1;
            System.out.println(totalMessages);
            int end = 0;

//            while(end < totalMessages){
//                start = end + 1;
//                end += Math.min(start + BATCH_SIZE - 1, totalMessages);
//
//                Message[] messages = inbox.getMessages(start, end);
//                for (Message message: messages){
//                    LogService.logger(
//                            "Subject:" + message.getSubject() + '\n' +
//                            "Content: " + message.getContent().toString() + "\n" +
//                            "Date: " + message.getSentDate() +
//                                    "\n\n"
//                    );
//                    System.out.println(message.getContent().toString() + " " + message.getSubject() + " " + message.getSentDate());
//                }
//                if (end == totalMessages) break; /* when the end equals the total messages, then we've reached to the end*/
//            }

            inbox.close(false);
            store.close();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
