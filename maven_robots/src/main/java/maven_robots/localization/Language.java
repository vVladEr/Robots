package maven_robots.localization;

import java.util.Locale;

public enum Language {
    RU("ru-RU"),
    EN("en-EN");

    private final String languageName;

    Language(String languageName) {
        this.languageName = languageName;
    }

    public Locale getLocale() {
        return Locale.forLanguageTag(languageName);
    }

    public String getLanguageName() {
        return languageName;
    }

    public static Language getLanguageFromLocal(Locale locale) {
        if (locale.toString().equals("en-EN")) {
            return Language.EN;
        } else if (locale.toString().equals("ru-RU")) {
            return Language.RU;
        } else {
            return Language.RU;
        }
    }
}
