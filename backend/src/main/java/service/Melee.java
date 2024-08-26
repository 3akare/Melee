package service;

import config.PropertiesConfig;
import utils.MeleeUtils;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;

// todo: Database READ and WRITE, no more logs
// todo: API Calls to python-server based on environment variables'
// todo: env file

public class Melee {
    private static final Logger log = Logger.getLogger(Melee.class.getName());

    public void run(){
        Map<String, String> environmentVariables = Objects.requireNonNull(MeleeUtils.fetchEnvironmentVariables()).orElseThrow(
            () -> new RuntimeException("Unable to fetch environment variables")
        );
        log.info("env: " + environmentVariables.toString());
    }

    private void apiCall(){
        try{
            System.out.println("API Call made");
        }catch (Exception exception){
            System.err.println("Error: " + exception.getLocalizedMessage());
        }
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
                MeleeUtils.HandleMultipartContent(message);
            }

            inbox.close(false);
            store.close();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    private Properties setUpProperties(String host, String protocol){
        Properties properties = new Properties();
        properties.put("mail.imap.host", host);
        properties.put("mail.imap.port", "995");
        properties.put("mail.store.protocol", protocol);
        return properties;
    }
}
