package utils;

import log.LogService;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;

public class MeleeUtils {
    public static void HandleMultipartContent(Message message){
        try{
            Object content = message.getContent();
            Multipart multipart = (Multipart) content;
            LogService.logger("Subject: " + message.getSubject());
            LogService.logger("Content: ");
            for(int i = 0; i < multipart.getCount(); i++){
                BodyPart bodyPart = multipart.getBodyPart(i);
                if (bodyPart.getContent() instanceof String){
                    String bodyContent = (String) bodyPart.getContent();
                    LogService.logger(bodyContent + "\n");
                }
            }
            LogService.logger("\n\n");
        } catch (Exception exception){
            System.err.println("Handling Multipart Content: " + exception.getLocalizedMessage());
        }
    }

    public static void HandleHTMLContent(Message message){
        try{
            Object content = message.getContent();
            LogService.logger("Subject: " + message.getSubject());
            LogService.logger("Content: " + content.toString());
        } catch(Exception exception){
            System.err.println("Handling HTML Content: " + exception.getLocalizedMessage());
        }
    }
}
