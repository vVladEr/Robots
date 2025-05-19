package maven_robots.data.profiler.strategies.interfaces;

import java.awt.Component;

import maven_robots.data.profiler.ProfileData;

public interface IComponentLoadStrategy {
    String[] getPropertyNames(Component comp);
    void applyProperties(ProfileData profileData, Component comp);
}
