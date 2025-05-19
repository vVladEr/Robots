package maven_robots.data.profiler.strategies;

import java.awt.Component;

import maven_robots.data.parameters.Parameters;
import maven_robots.data.profiler.ProfileData;
import maven_robots.data.profiler.enums.JTextAreaPropertyNames;
import maven_robots.data.profiler.strategies.interfaces.IComponentStrategy;

public class JTextAreaStrategy implements IComponentStrategy {
    @Override
    public ProfileData prepareDataToSave(Component comp) {
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

        return profileData;
    }

    @Override
    public String[] getPropertyNames(Component comp) {
        return JTextAreaPropertyNames.getPropertyNames();
    }

    @Override
    public void applyProperties(ProfileData profileData, Component comp) {
        Parameters parameters = Parameters.parseParameters(
                profileData.getProperty(JTextAreaPropertyNames.PARAMETERS.getPropertyName())
        );

        comp.setBounds(
                parameters.getX(),
                parameters.getY(),
                parameters.getWidth(),
                parameters.getHeight()
        );
    }
}