package maven_robots.data.profiler.strategies;

import java.awt.Component;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;

import maven_robots.data.parameters.Parameters;
import maven_robots.data.profiler.ProfileData;
import maven_robots.data.profiler.enums.JInternalFramePropertyNames;
import maven_robots.data.profiler.strategies.interfaces.IComponentStrategy;
import maven_robots.gui.frames.baseFrames.BaseJInternalFrame;

public class JInternalFrameStrategy implements IComponentStrategy {
    @Override
    public ProfileData prepareDataToSave(Component comp) {
        JInternalFrame jInternalFrame = (JInternalFrame) comp;
        ProfileData profileData = new ProfileData();

        profileData.setProperty(
            JInternalFramePropertyNames.PARAMETERS.getPropertyName(),
            String.format(
                "%d;%d;%d;%d",
                comp.getX(),
                comp.getY(),
                comp.getWidth(),
                comp.getHeight()
            )
        );

        profileData.setProperty(
                JInternalFramePropertyNames.IS_CLOSED.getPropertyName(),
                Boolean.toString(jInternalFrame.isClosed())
        );
        profileData.setProperty(
                JInternalFramePropertyNames.IS_ICON.getPropertyName(),
                Boolean.toString(jInternalFrame.isIcon())
        );
        profileData.setProperty(
                JInternalFramePropertyNames.IS_MAXIMIZED.getPropertyName(),
                Boolean.toString(jInternalFrame.isMaximum())
        );

        return profileData;
    }

    @Override
    public String[] getPropertyNames(Component comp) {
        return JInternalFramePropertyNames.getPropertyNames();
    }

    @Override
    public void applyProperties(ProfileData profileData, Component comp) {
        JInternalFrame jInternalFrame = (JInternalFrame) comp;

        try {
            boolean isMaximized = Boolean.parseBoolean(profileData.getProperty(
                    JInternalFramePropertyNames.IS_MAXIMIZED.getPropertyName()
            ));

            if (isMaximized) {
                jInternalFrame.setMaximum(true);
            }

            if (Boolean.parseBoolean(
                profileData.getProperty(JInternalFramePropertyNames.IS_CLOSED.getPropertyName()))
            ) {
                jInternalFrame.dispose();
            }
            jInternalFrame.setIcon(Boolean.parseBoolean(profileData.getProperty(JInternalFramePropertyNames.IS_ICON.getPropertyName())));
        } catch (PropertyVetoException | NullPointerException e) {
            System.err.println("Ошибка при применении свойств: " + e.getMessage());
        }

        Parameters parameters = Parameters.parseParameters(profileData.getProperty(JInternalFramePropertyNames.PARAMETERS.getPropertyName()));

        jInternalFrame.setBounds(parameters.getX(), parameters.getY(), parameters.getWidth(), parameters.getHeight());
    }
}