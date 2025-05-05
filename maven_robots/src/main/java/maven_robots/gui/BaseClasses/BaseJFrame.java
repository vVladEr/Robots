package maven_robots.gui.BaseClasses;

import java.awt.Component;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;

import maven_robots.gui.ClosingListeners;
import maven_robots.gui.ISaveable;

public class BaseJFrame extends JFrame implements ISaveable{

    private final String frameName;

    public BaseJFrame(String frameName) {
        addWindowListener(ClosingListeners.getFramewindowClosingAdapter());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.frameName = frameName;
    }

    @Override
    public void saveToProfile(String profileName) {
        String basePath = "D:/codes/java/Robots/maven_robots/src/main/java/maven_robots/data/profiles";
        String currentPath = String.format("%s/%s/frames.properties", basePath, profileName);
        File file = new File(currentPath);
        
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(file));
            properties.setProperty(frameName + ".x", Integer.toString(this.getX()));
            properties.setProperty(frameName + ".y", Integer.toString(this.getY()));
            properties.setProperty(frameName + ".width", Integer.toString(this.getWidth()));
            properties.setProperty(frameName + ".height", Integer.toString(this.getHeight()));
            properties.store(new FileOutputStream(currentPath), null);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    @Override
    public void loadFromProfile(String profileName, Component comp) {
        String basePath = "D:/codes/java/Robots/maven_robots/src/main/java/maven_robots/data/profiles";
        String currentPath = String.format("%s/%s/frames.properties", basePath, profileName);
        File file = new File(currentPath);
        
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(file));
            int x = Integer.parseInt(properties.getProperty(frameName + ".x"));
            int y = Integer.parseInt(properties.getProperty(frameName + ".y"));
            int width = Integer.parseInt(properties.getProperty(frameName + ".width"));
            int height = Integer.parseInt(properties.getProperty(frameName + ".height"));
            this.setBounds(x, y, width, height);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
