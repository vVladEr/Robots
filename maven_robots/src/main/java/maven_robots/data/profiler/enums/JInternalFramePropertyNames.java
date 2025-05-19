package maven_robots.data.profiler.enums;

import java.util.Arrays;

public enum JInternalFramePropertyNames {
    PARAMETERS("parameters"),
    IS_CLOSED("isClosed"),
    IS_ICON("isIcon"),
    IS_MAXIMIZED("isMaximized");

    private final String propertyName;

    JInternalFramePropertyNames(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public static String[] getPropertyNames() {
        return Arrays.stream(values())
                .map(JInternalFramePropertyNames::getPropertyName)
                .toArray(String[]::new);
    }
}
