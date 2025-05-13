package maven_robots.gui.profileSaver;

import java.awt.TextArea;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;

public interface ISavable {

    void saveJInternalFrameToProfile(String profileName, JInternalFrame comp);

    void saveJFrameToProfile(String profileName, JFrame comp);
    void saveTextAreaToProfile(String profileName, TextArea comp);

    void loadJInternalFrameFromProfile(String profileName, JInternalFrame comp);
    void loadJFrameFromProfile(String profileName, JFrame comp);
    void loadTextAreaFromProfile(String profileName, TextArea comp);
}
