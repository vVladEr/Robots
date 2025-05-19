package maven_robots.data.profiler.strategies.interfaces;

import java.awt.Component;

import maven_robots.data.profiler.ProfileData;

public interface IComponentSaveStrategy {
    ProfileData prepareDataToSave(Component comp);
}
