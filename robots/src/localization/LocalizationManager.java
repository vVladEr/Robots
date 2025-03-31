package localization;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;


public class LocalizationManager {
    private static ResourceBundle currentMessages;

    static {
        setLocale(Languages.RU);
    }

    public static void setLocale(Languages language) {
        Locale locale = language.getLocale();
        currentMessages = ResourceBundle.getBundle("localization.messages", locale);
    }

    public static ResourceBundle getResourceBundle() {
        return currentMessages;
    }

    public static String getStringByName(String name) { 
        try {
            return new String(currentMessages.getString(name).getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "default";
        }
    }
}
