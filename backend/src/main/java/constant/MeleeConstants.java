package constant;

import java.util.regex.Pattern;

public class MeleeConstants {
    public MeleeConstants(){}
    public static final Integer BATCH_SIZE = 100;
    public static final Pattern HTML_TAG_PATTERN = Pattern.compile("<[^>]*>");
    public static final Pattern URL_PATTERN = Pattern.compile("https?://\\S+");
    public static final Pattern IMAGE_TAG_PATTERN = Pattern.compile("<img[^>]*>");
}
