package service;

import com.mongodb.MongoClient;
import config.PropertiesConfig;
import entity.Data;
import service.impl.DataServiceDaoImpl;
import utils.MeleeUtils;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;
import java.util.logging.Logger;

// todo: Database READ and WRITE, no more logs

public class Melee {
    private static final Logger log = Logger.getLogger(Melee.class.getName());
    private static final  PropertiesConfig props = MeleeUtils.fetchEnvironmentVariables().orElseThrow(
            () -> new RuntimeException("Error fetching environment variables")
    );

    public void run(){
        try(MongoClient mongoClient = new MongoClient("localhost", 27017);
        ){
            iDataServiceDao dataServiceDao = new DataServiceDaoImpl(mongoClient, "MeleeDatabase");
            createEmailSession(dataServiceDao);
//            System.out.println(parserApiCall());
        }catch (Exception exception){
            System.err.println(exception.getLocalizedMessage());
        }
    }

    private Object parserApiCall(){
        try{
            HttpClient httpClient = HttpClient.newHttpClient();
            String url = props.getParserUrl() + "/process";
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("Hello"))
                .build();
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (Exception error){
            System.err.println(error.getLocalizedMessage());
        }
        return null;
    }

    private void createEmailSession(iDataServiceDao dataServiceDao){
        try {
            Session emailSession = Session.getDefaultInstance(
                setUpProperties(
                    props.getHost(),
                    props.getStoreType()
                )
            );
            Store store = emailSession.getStore(props.getStoreType());
            store.connect(
                props.getHost(),
                props.getAddress(),
                props.getPassword()
            );

            //create the folder object and open it
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            int totalMessages = inbox.getMessageCount();

            Message[] messages = inbox.getMessages(totalMessages, totalMessages);

            for (Message message: messages){
                dataServiceDao.save(new Data(
                        MeleeUtils.randomString(),
                        MeleeUtils.HandleMultipartContent(message),
                        false
                ));
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
