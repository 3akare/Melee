package utils;

import javax.mail.Message;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtils {
    private static final Logger log = Logger.getLogger(EmailUtils.class.getName());

    public static String extractEmail(Message message){
        try{
            String regex = "<(.+?)>";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(Arrays.toString(message.getFrom()));

            if (matcher.find()) {
                return matcher.group(1);
            }
        }catch (Exception exception){
            log.warning("email: extraction error: " + exception.getLocalizedMessage());
        }
        return null;
    }

    public static boolean validEmailAddress(Message message){
        try{
            List<String> validList = List.of(
                "crew@morningbrew.com",
                "newsletters@medium.com",
                "news@thehustle.co",
                "noreply@medium.com",
                "jakub@mail.leadershipintech",
                "easlo@mail.beehive.com",
                "comic@mail.smartnonsense.com",
                "beatsnbusiness@substack.com"
            );

            return validList.contains(extractEmail(message));
        }
        catch (Exception exception){
            log.warning("email: validation error: " + exception.getLocalizedMessage());
        }
        return false;
    }
}
