package localization;

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

    public static String getStringByName(String name) { 
        return currentMessages.getString(name);
    }
}
