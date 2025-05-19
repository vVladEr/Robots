package maven_robots.data.profiler.enums;

import java.util.Arrays;

public enum JTextAreaPropertyNames {
    PARAMETERS("parameters");

    private final String propertyName;

    JTextAreaPropertyNames(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public static String[] getPropertyNames() {
        return Arrays.stream(values())
                .map(JTextAreaPropertyNames::getPropertyName)
                .toArray(String[]::new);
    }
}