package maven_robots.data.profiler;

import java.awt.Component;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.swing.JTextArea;

import maven_robots.data.profiler.strategies.JInternalFrameStrategy;
import maven_robots.data.profiler.strategies.JTextAreaStrategy;
import maven_robots.data.profiler.strategies.JFrameStrategy;
import maven_robots.data.profiler.strategies.interfaces.IComponentLoadStrategy;
import maven_robots.data.profiler.strategies.interfaces.IComponentSaveStrategy;
import maven_robots.data.profiler.strategies.interfaces.IComponentStrategy;
import maven_robots.gui.frames.internalFrames.GameWindow;
import maven_robots.gui.frames.internalFrames.LogWindow;
import maven_robots.gui.mainFrame.MainApplicationFrame;
import maven_robots.localization.LocalizationManager;

public class Profiler implements IProfiler{
    private final String path;
    private String folderPath;
    private String currentPath;
    private File profile;

    private final Map<Class<? extends Component>, IComponentStrategy> strategies = new HashMap<>();

    public Profiler(String path) {
        this.path = path + "/profiles";

        JFrameStrategy jFrameStrategy = new JFrameStrategy();
        JInternalFrameStrategy jInternalFrameStrategy = new JInternalFrameStrategy();

        strategies.put(MainApplicationFrame.class, jFrameStrategy);
        strategies.put(LogWindow.class, jInternalFrameStrategy);
        strategies.put(GameWindow.class, jInternalFrameStrategy);
        strategies.put(JTextArea.class, new JTextAreaStrategy());
    }

    public boolean isProfileExists() {
        return profile.exists();
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

    public void loadComponentFromProfile(String frameName, Component comp) {
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
        folderPath = path + "/" + profileName;
        currentPath = folderPath + "/frames.properties";
        profile = new File(currentPath);
    }

    public void saveLanguage() {
        Properties properties = new Properties();

        try {
            properties.load(new FileReader(profile));

            properties.setProperty("locale", LocalizationManager.getLanguage().getLanguageName());

            properties.store(Files.newOutputStream(new File(currentPath).toPath()), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveProfileProperties(String frameName, ProfileData profileData) {
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

    public void loadLanguage() {
        Properties properties = new Properties();

        try {
            properties.load(new FileReader(profile));

            Locale locale =  Locale.forLanguageTag(properties.getProperty("locale"));

            LocalizationManager.setLocal(locale);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ProfileData loadProfileProperties(String frameName, String[] propertyNames) {
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
}
