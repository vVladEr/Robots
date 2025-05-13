package maven_robots.gui.profileSaver;

import java.awt.TextArea;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;

import maven_robots.gui.parameters.Parameters;

public class Profiler implements ISavable {
    private String path;
    private static Profiler instance;

    private Profiler() {
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

    public static Profiler GetInstance() {
        if (instance == null) {
            instance = new Profiler();
        }

        return instance;
    }

    private void createFramePropertyFile(String folderPath, File profile) {
        try {
            File profileFolder = new File(folderPath);
            profileFolder.mkdir();
            profile.createNewFile();
        } catch (IOException e) {

        }
    }

    @Override
    public void saveJInternalFrameToProfile(String profileName, JInternalFrame comp) {
        String folderPath = path + "/" + profileName;
        String currentPath = folderPath + "/frame.properties";
        File profile = new File(currentPath);

        createFramePropertyFile(folderPath, profile);

        Properties properties = new Properties();
        try {
            properties.load(new FileReader(profile));
            properties.setProperty(
                    profileName + ".parameters",
                    String.format(
                            "%d;%d;%d;%d",
                            comp.getX(),
                            comp.getY(),
                            comp.getWidth(),
                            comp.getHeight()
                    )
            );
            properties.setProperty(profileName + ".isClosed", Boolean.toString(comp.isClosed()));
            properties.setProperty(profileName + ".isIcon", Boolean.toString(comp.isIcon()));
            properties.store(new FileOutputStream(currentPath), currentPath);
        } catch (IOException e) {

        }
    }

    @Override
    public void saveJFrameToProfile(String profileName, JFrame comp) {
        String folderPath = path + "/" + profileName;
        String currentPath = folderPath + "/frame.properties";
        File profile = new File(currentPath);

        createFramePropertyFile(folderPath, profile);

        Properties properties = new Properties();
        try {
            properties.load(new FileReader(profile));
            properties.setProperty(
                    profileName + ".parameters",
                    String.format(
                            "%d;%d;%d;%d",
                            comp.getX(),
                            comp.getY(),
                            comp.getWidth(),
                            comp.getHeight()
                    )
            );
            properties.store(new FileOutputStream(currentPath), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveTextAreaToProfile(String profileName, TextArea comp) {
        String folderPath = path + "/" + profileName;
        String currentPath = folderPath + "/frame.properties";
        File profile = new File(currentPath);

        createFramePropertyFile(folderPath, profile);

        Properties properties = new Properties();
        try {
            properties.load(new FileReader(profile));
            properties.setProperty(
                    profileName + ".parameters",
                    String.format(
                            "%d;%d;%d;%d",
                            comp.getX(),
                            comp.getY(),
                            comp.getWidth(),
                            comp.getHeight()
                    )
            );
            properties.store(new FileOutputStream(currentPath), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadJInternalFrameFromProfile(String profileName, JInternalFrame comp) {
        String folderPath = path + "/" + profileName;
        String currentPath = folderPath + "/frame.properties";
        File profile = new File(currentPath);

        Properties properties = new Properties();
        try {
            properties.load(new FileReader(profile));
            System.out.println(profileName);
            Parameters parameters = Parameters.parseParameters(
                    properties.getProperty(profileName + ".parameters")
            );
            boolean isClosed = Boolean.parseBoolean(properties.getProperty(profileName + ".isClosed"));
            boolean isIcon = Boolean.parseBoolean(properties.getProperty(profileName + ".isIcon"));

            comp.setBounds(
                    parameters.getX(),
                    parameters.getY(),
                    parameters.getWidth(),
                    parameters.getHeight()
            );
            comp.setClosed(isClosed);
            comp.setIcon(isIcon);
        } catch (IOException | PropertyVetoException e) {

        }
    }

    @Override
    public void loadJFrameFromProfile(String profileName, JFrame comp) {
        String folderPath = path + "/" + profileName;
        String currentPath = folderPath + "/frame.properties";
        File profile = new File(currentPath);

        Properties properties = new Properties();
        try {
            properties.load(new FileReader(profile));
            Parameters parameters = Parameters.parseParameters(
                properties.getProperty(profileName + ".parameters")
            );

            comp.setBounds(
                    parameters.getX(),
                    parameters.getY(),
                    parameters.getWidth(),
                    parameters.getHeight()
            );
        } catch (IOException e) {

        }
    }

    @Override
    public void loadTextAreaFromProfile(String profileName, TextArea comp) {
        String folderPath = path + "/" + profileName;
        String currentPath = folderPath + "/frame.properties";
        File profile = new File(currentPath);

        Properties properties = new Properties();
        try {
            properties.load(new FileReader(profile));
            Parameters parameters = Parameters.parseParameters(
                    properties.getProperty(profileName + ".parameters")
            );

            comp.setBounds(
                    parameters.getX(),
                    parameters.getY(),
                    parameters.getWidth(),
                    parameters.getHeight()
            );
        } catch (IOException e) {

        }
    }
}
