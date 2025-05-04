package maven_robots.localization;

import java.util.Locale;

public enum Languages {
    RU(Locale.forLanguageTag("ru-RU")),
    EN(Locale.forLanguageTag("en-EN"));

    private final Locale locale;

    Languages(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getDisplayName() {
        return locale.getDisplayName();
    }
}
