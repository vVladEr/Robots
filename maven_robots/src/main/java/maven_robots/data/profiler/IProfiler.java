package maven_robots.data.profiler;

import java.awt.Component;

import maven_robots.gui.frames.baseFrames.FrameState;

public interface IProfiler {
    void saveFrameState(FrameState frameState);
    void loadFrameState(FrameState frameState);
    void setProfileName(String profileName);
    boolean isProfileExists();
    void saveLanguage();
    void loadLanguage();
}
