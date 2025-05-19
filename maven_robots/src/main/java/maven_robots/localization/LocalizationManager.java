package maven_robots.localization;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;


public class LocalizationManager {
    private static ResourceBundle currentMessages;
    private static Language language;

    static {
        setLanguage(Language.RU);
    }

    public static void setLanguage(Language language) {
        LocalizationManager.language = language;
        currentMessages = ResourceBundle.getBundle("maven_robots.localization.messages", language.getLocale());
    }

    public static void setLocal(Locale locale) {
        LocalizationManager.language = Language.getLanguageFromLocal(locale);
        currentMessages = ResourceBundle.getBundle("maven_robots.localization.messages", locale);
    }

    public static Language getLanguage() {
        return language;
    }

    public static String getStringByName(String name) {
        return new String(currentMessages.getString(name).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }
}
