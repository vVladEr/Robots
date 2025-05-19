package maven_robots.data.profiler.enums;

import java.util.Arrays;

public enum JFramePropertyNames {
    PARAMETERS("parameters"),
    IS_MAXIMIZED("isMaximized");

    private final String propertyName;

    JFramePropertyNames(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public static String[] getPropertyNames() {
        return Arrays.stream(values())
                .map(JFramePropertyNames::getPropertyName)
                .toArray(String[]::new);
    }
}
