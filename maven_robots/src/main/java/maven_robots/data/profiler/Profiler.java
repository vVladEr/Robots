package maven_robots.data.profiler;

import java.awt.Component;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JTextArea;

import maven_robots.data.profiler.strategies.JInternalFrameStrategy;
import maven_robots.data.profiler.strategies.JTextAreaStrategy;
import maven_robots.data.profiler.strategies.JFrameStrategy;
import maven_robots.data.profiler.strategies.interfaces.IComponentLoadStrategy;
import maven_robots.data.profiler.strategies.interfaces.IComponentSaveStrategy;
import maven_robots.data.profiler.strategies.interfaces.IComponentStrategy;
import maven_robots.gui.frames.baseFrames.BaseJFrame;
import maven_robots.gui.frames.baseFrames.BaseJInternalFrame;
import maven_robots.gui.frames.internalFrames.GameWindow;
import maven_robots.gui.frames.internalFrames.LogWindow;
import maven_robots.gui.mainFrame.MainApplicationFrame;

public class Profiler implements IProfiler{
    private String path;
    private String profileName;

    private final Map<Class<? extends Component>, IComponentStrategy> strategies = new HashMap<>();

    public Profiler() {
        JFrameStrategy jFrameStrategy = new JFrameStrategy();
        JInternalFrameStrategy jInternalFrameStrategy = new JInternalFrameStrategy();

        strategies.put(MainApplicationFrame.class, jFrameStrategy);
        strategies.put(LogWindow.class, jInternalFrameStrategy);
        strategies.put(GameWindow.class, jInternalFrameStrategy);
        strategies.put(JTextArea.class, new JTextAreaStrategy());

        try {
            String classPath = this
                    .getClass()
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI()
                    .getPath()
                    .replace("out/production", "src")
                    .replace("/main/", "/main/java/");
            path = classPath.substring(1) + "maven_robots/data/profiles";

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public boolean isProfileExists() {
        String folderPath = path + "/" + profileName;
        String currentPath = folderPath + "/frames.properties";
        return new File(currentPath).exists();
    }

    public void saveComponentToProfile(String frameName, Component comp) {
        IComponentSaveStrategy strategy = strategies.get(comp.getClass());
        if (strategy != null) {
            ProfileData profileData = strategy.prepareDataToSave(comp);

            saveProfileProperties(frameName, profileData);
        } else {
            System.err.println("Unknown component type: " + comp.getClass().getName());
        }
    }

    public void loadComponentToProfile(String frameName, Component comp) {
        IComponentLoadStrategy strategy = strategies.get(comp.getClass());
        if (strategy != null) {
            String[] propertyNames = strategy.getPropertyNames(comp);

            ProfileData profileData = loadProfileProperties(frameName, propertyNames);

            strategy.applyProperties(profileData, comp);
        } else {
            System.err.println("Unknown component type: " + comp.getClass().getName());
        }
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    private void saveProfileProperties(String frameName, ProfileData profileData) {
        String folderPath = path + "/" + profileName;
        String currentPath = folderPath + "/frames.properties";
        File profile = new File(currentPath);

        createFramePropertyFile(folderPath, profile);

        Properties properties = new Properties();

        try {
            properties.load(new FileReader(profile));
            HashMap<String, String> data = profileData.getData();

            for (String property : data.keySet()) {
                properties.setProperty(frameName + "." + property, data.get(property));
            }

            properties.store(Files.newOutputStream(new File(currentPath).toPath()), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ProfileData loadProfileProperties(String frameName, String[] propertyNames) {
        String folderPath = path + "/" + profileName;
        String currentPath = folderPath + "/frames.properties";
        File profile = new File(currentPath);

        Properties properties = new Properties();
        ProfileData profileData = new ProfileData();

        try {
            properties.load(new FileReader(profile));

            for (String propertyName : propertyNames) {
                profileData.setProperty(
                        propertyName,
                        properties.getProperty(frameName + "." + propertyName)
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return profileData;
    }

    private void createFramePropertyFile(String folderPath, File profile) {
        try {
            File profileFolder = new File(folderPath);
            profileFolder.mkdir();
            profile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addStrategy(Class<? extends Component> componentClass,
        IComponentStrategy strategy) {
        strategies.put(componentClass, strategy);
    }
}
