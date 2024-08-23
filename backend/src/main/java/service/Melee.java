package service;

import config.PropertiesConfig;
import utils.MeleeUtils;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Logger;

public class Melee {
    private final Logger log = Logger.getLogger(Melee.class.getName());

    public void run(){
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

    private Properties setUpProperties(String host, String protocol){
        Properties properties = new Properties();
        properties.put("mail.imap.host", host);
        properties.put("mail.imap.port", "995");
        properties.put("mail.store.protocol", protocol);
        return properties;
    }

    private void createEmailFetchSession(Map<String, String> environmentVariables){
        System.out.println(environmentVariables);
        try {
            Session emailSession = Session.getDefaultInstance(
                setUpProperties(
                    environmentVariables.get("host"),
                    environmentVariables.get("storeType"))
            );

            Store store = emailSession.getStore(environmentVariables.get("storeType"));
            store.connect(
                environmentVariables.get("host"),
                environmentVariables.get("address"),
                environmentVariables.get("password")
            );

            //create the folder object and open it
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            int totalMessages = inbox.getMessageCount();

            Message[] messages = inbox.getMessages(totalMessages - 10, totalMessages);

            for (Message message: messages){
                System.out.println(message.getAllHeaders());
                String contentType = inbox.getMessage(totalMessages).getContentType();
                System.out.println(contentType);

                MeleeUtils.HandleMultipartContent(message);
            }

            inbox.close(false);
            store.close();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
