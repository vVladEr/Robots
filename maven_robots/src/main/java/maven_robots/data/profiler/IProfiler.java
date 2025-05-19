package maven_robots.data.profiler;

import java.awt.Component;

public interface IProfiler {
    void saveComponentToProfile(String profileFrameName, Component comp);
    void loadComponentFromProfile(String profileFrameName, Component comp);
    void setProfileName(String profileName);
}
