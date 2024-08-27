package service;

import com.mongodb.MongoClient;
import config.PropertiesConfig;
import entity.Data;
import service.impl.DataServiceDaoImpl;
import utils.MeleeUtils;

import javax.mail.*;
import java.util.Properties;
import java.util.logging.Logger;

import static constant.MeleeConstants.BATCH_SIZE;
import static utils.EmailUtils.*;

// todo AI model to parse data

public class Melee {
    private static final Logger log = Logger.getLogger(Melee.class.getName());

    private static final  PropertiesConfig props = MeleeUtils.fetchEnvironmentVariables().orElseThrow(
            () -> new RuntimeException("melee: error fetching environment variables")
    );

    public void run(){
        try(MongoClient mongoClient = new MongoClient("localhost", 27017);
        ){
            iDataServiceDao dataServiceDao = new DataServiceDaoImpl(mongoClient, "MeleeDatabase");
            createEmailSession(dataServiceDao);
        }catch (Exception exception){
            log.warning("melee: error starting melee :"+ exception.getLocalizedMessage());
        }
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
            fetchEmailData(store, dataServiceDao);
            store.close();
        } catch (Exception e) {
            log.warning("melee: error creating email session: "+ e.getLocalizedMessage());
        }
    }

    private void fetchEmailData(Store store, iDataServiceDao dataServiceDao) throws MessagingException {
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        int totalMessages = inbox.getMessageCount();
        Message[] messages = inbox.getMessages(totalMessages - BATCH_SIZE, totalMessages);

        for (Message message: messages){
            if(validEmailAddress(message)){
                dataServiceDao.save(new Data(
                    MeleeUtils.randomString(),
                    extractText(MeleeUtils.HandleMultipartContent(message)),
                    removeStyles(extractEmail(message)),
                    message.getSentDate(),
                    false
                ));
            }
        }

        inbox.close(false);
    }

    private Properties setUpProperties(String host, String protocol){
        Properties properties = new Properties();
        properties.put("mail.imap.host", host);
        properties.put("mail.imap.port", "995");
        properties.put("mail.store.protocol", protocol);
        return properties;
    }
}
