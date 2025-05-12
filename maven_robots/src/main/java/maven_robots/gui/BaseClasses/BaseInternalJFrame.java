package maven_robots.gui.BaseClasses;

import java.awt.Component;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.swing.JInternalFrame;

import maven_robots.gui.ClosingListeners;
import maven_robots.gui.ISaveable;
import maven_robots.gui.MainApplicationFrame;

public class BaseInternalJFrame extends JInternalFrame implements ISaveable {

    private final String frameName;

    public BaseInternalJFrame(final String title, final boolean resizable,
            final boolean closable,
            final boolean maximizable, final boolean iconable, final String frameName) {
        super(title, resizable, closable, maximizable, iconable);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addInternalFrameListener(ClosingListeners.getFrameClosingListener());
        this.frameName = frameName;
    }

    @Override
    public void saveToProfile(String profileName) {
        String path = "";
        try {
            String classPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            path = classPath.substring(1) + "maven_robots/data/profiles";
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String currentPath = String.format("%s/%s/frames.properties", path, profileName);
        File file = new File(currentPath);
        
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(file));
            properties.setProperty(frameName + ".isClosed", Boolean.toString(this.isClosed()));
            properties.setProperty(frameName + ".isIcon", Boolean.toString(this.isIcon()));
            properties.setProperty(frameName + ".x", Integer.toString(this.getX()));
            properties.setProperty(frameName + ".y", Integer.toString(this.getY()));
            properties.setProperty(frameName + ".width", Integer.toString(this.getWidth()));
            properties.setProperty(frameName + ".height", Integer.toString(this.getHeight()));
            properties.store(new FileOutputStream(currentPath), currentPath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    @Override
    public void loadFromProfile(String profileName, Component comp) {
        String path = "";
        try {
            String classPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            path = classPath.substring(1) + "maven_robots/data/profiles";
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String currentPath = String.format("%s/%s/frames.properties", path, profileName);
        File file = new File(currentPath);
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(file));
            int x = Integer.parseInt(properties.getProperty(frameName + ".x"));
            int y = Integer.parseInt(properties.getProperty(frameName + ".y"));
            int width = Integer.parseInt(properties.getProperty(frameName + ".width"));
            int height = Integer.parseInt(properties.getProperty(frameName + ".height"));
            boolean isClosed = Boolean.parseBoolean(properties.getProperty(frameName + ".isClosed"));
            boolean isIcon = Boolean.parseBoolean(properties.getProperty(frameName + ".isIcon"));
            this.setBounds(x, y, width, height);
            comp.setBounds(x, y, width, height);
            this.setClosed(isClosed);
            this.setIcon(isIcon);
        } catch (IOException | PropertyVetoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
