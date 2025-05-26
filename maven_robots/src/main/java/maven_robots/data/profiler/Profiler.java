package maven_robots.data.profiler;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Locale;
import java.util.Properties;

import maven_robots.data.parameters.Parameters;
import maven_robots.gui.frames.baseFrames.FrameState;
import maven_robots.localization.LocalizationManager;

public class Profiler implements IProfiler{
    private final String path;
    private String folderPath;
    private String currentPath;
    private File profile;

    public Profiler(String path) {
        this.path = path + "/profiles";
    }

    @Override
    public void setProfileName(String profileName) {
        folderPath = path + "/" + profileName;
        currentPath = folderPath + "/frames.properties";
        profile = new File(currentPath);
    }

    @Override
    public boolean isProfileExists() {
        return profile.exists();
    }

    @Override
    public void saveFrameState(FrameState frameState) {
        createFramePropertyFile(folderPath, profile);

        String frameName = frameState.getFrameName();

        Properties properties = new Properties();

        try {
            properties.load(new FileReader(profile));

            properties.setProperty(frameName + "." + "parameters", frameState.getParameters().toString());

            properties.setProperty(frameName + "." + "isIcon", frameState.getIsIcon().toString());
            properties.setProperty(frameName + "." + "isMaximized", frameState.getIsMaximized().toString());
            properties.setProperty(frameName + "." + "isClosed", frameState.getIsClosed().toString());

            properties.store(Files.newOutputStream(new File(currentPath).toPath()), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadFrameState(FrameState frameState) {
        Properties properties = new Properties();
        String frameName = frameState.getFrameName();

        try {
            properties.load(new FileReader(profile));

            String parameters = properties.getProperty(frameName + "." + "parameters");
            String isIcon = properties.getProperty(frameName + "." + "isIcon");
            String isMaximized = properties.getProperty(frameName + "." + "isMaximized");
            String isClosed = properties.getProperty(frameName + "." + "isClosed");

            if (parameters != null) {
                frameState.setParameters(
                    Parameters.parseParameters(parameters)
                );
            }

            if (isIcon != null) {
                frameState.setIsIcon(
                    Boolean.parseBoolean(isIcon)
                );
            }

            if (isMaximized != null) {
                frameState.setIsMaximized(
                    Boolean.parseBoolean(isMaximized)
                );
            }

            if (isClosed != null) {
                frameState.setIsClosed(
                    Boolean.parseBoolean(isClosed)
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
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

    @Override
    public void loadLanguage() {
        Properties properties = new Properties();

        try {
            properties.load(new FileReader(profile));

            String locale = properties.getProperty("locale");
            if (locale != null) {
                LocalizationManager.setLocal(Locale.forLanguageTag(locale));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
