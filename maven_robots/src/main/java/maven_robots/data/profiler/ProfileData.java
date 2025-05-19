package maven_robots.data.profiler;

import java.util.HashMap;

public class ProfileData {
    private final HashMap<String, String> data;

    public ProfileData() {
        data = new HashMap<>();
    }

    public void setProperty(String propertyName, String property) {
        data.put(propertyName, property);
    }

    public String getProperty(String propertyName) {
        return data.get(propertyName);
    }

    public HashMap<String, String> getData() {
        return data;
    }
}
