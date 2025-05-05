package maven_robots.gui;

import java.awt.Component;

public interface ISaveable {

    public void saveToProfile(String profileName);

    public void loadFromProfile(String profileName, Component comp);
}
