package maven_robots.data.profiler.strategies;

import java.awt.Component;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;

import maven_robots.data.parameters.Parameters;
import maven_robots.data.profiler.ProfileData;
import maven_robots.data.profiler.enums.JInternalFramePropertyNames;
import maven_robots.data.profiler.strategies.interfaces.IComponentStrategy;

public class JInternalFrameStrategy implements IComponentStrategy {
    @Override
    public ProfileData prepareDataToSave(Component comp) {
        JInternalFrame jInternalFrame = (JInternalFrame) comp;
        ProfileData profileData = new ProfileData();

        profileData.setProperty(
            "parameters",
            String.format(
                    "%d;%d;%d;%d",
                    comp.getX(),
                    comp.getY(),
                    comp.getWidth(),
                    comp.getHeight()
            )
        );

        profileData.setProperty("isClosed", Boolean.toString(jInternalFrame.isClosed()));
        profileData.setProperty("isIcon", Boolean.toString(jInternalFrame.isIcon()));

        return profileData;
    }

    @Override
    public String[] getPropertyNames(Component comp) {
        return JInternalFramePropertyNames.getPropertyNames();
    }

    @Override
    public void applyProperties(ProfileData profileData, Component comp) {
        JInternalFrame jInternalFrame = (JInternalFrame) comp;
        Parameters parameters = Parameters.parseParameters(
                profileData.getProperty(JInternalFramePropertyNames.PARAMETERS.getPropertyName())
        );

        jInternalFrame.setBounds(
                parameters.getX(),
                parameters.getY(),
                parameters.getWidth(),
                parameters.getHeight()
        );

        try {
            boolean isClosed = Boolean.parseBoolean(
                    profileData.getProperty(JInternalFramePropertyNames.IS_CLOSED.getPropertyName())
            );
            boolean isIcon = Boolean.parseBoolean(
                    profileData.getProperty(JInternalFramePropertyNames.IS_ICON.getPropertyName())
            );

            jInternalFrame.setClosed(isClosed);
            jInternalFrame.setIcon(isIcon);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }

    }
}