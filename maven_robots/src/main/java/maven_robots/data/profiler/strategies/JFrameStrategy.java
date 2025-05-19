package maven_robots.data.profiler.strategies;

import java.awt.Component;

import javax.swing.JFrame;

import maven_robots.data.parameters.Parameters;
import maven_robots.data.profiler.ProfileData;
import maven_robots.data.profiler.enums.JFramePropertyNames;
import maven_robots.data.profiler.strategies.interfaces.IComponentStrategy;

public class JFrameStrategy implements IComponentStrategy {
    @Override
    public ProfileData prepareDataToSave(Component comp) {
        ProfileData profileData = new ProfileData();
        JFrame frame = (JFrame) comp;

        profileData.setProperty(JFramePropertyNames.PARAMETERS.getPropertyName(),
                String.format("%d;%d;%d;%d", comp.getX(), comp.getY(), comp.getWidth(), comp.getHeight()));

        profileData.setProperty(
                JFramePropertyNames.IS_MAXIMIZED.getPropertyName(),
                String.valueOf(frame.getExtendedState() == JFrame.MAXIMIZED_BOTH)
        );

        return profileData;
    }

    @Override
    public String[] getPropertyNames(Component comp) {
        return JFramePropertyNames.getPropertyNames();
    }

    @Override
    public void applyProperties(ProfileData profileData, Component comp) {
        JFrame frame = (JFrame) comp;

        boolean isMaximized = Boolean.parseBoolean(
                profileData.getProperty(JFramePropertyNames.IS_MAXIMIZED.getPropertyName())
        );

        if (isMaximized) {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }

        Parameters parameters = Parameters.parseParameters(
                profileData.getProperty(JFramePropertyNames.PARAMETERS.getPropertyName())
        );

        frame.setBounds(
            parameters.getX(),
            parameters.getY(),
            parameters.getWidth(),
            parameters.getHeight()
        );
    }
}
