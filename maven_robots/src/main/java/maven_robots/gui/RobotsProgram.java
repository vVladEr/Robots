package maven_robots.gui;

import java.net.URISyntaxException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import maven_robots.data.profiler.IProfiler;
import maven_robots.data.profiler.Profiler;
import maven_robots.gui.mainFrame.MainApplicationFrame;

public class RobotsProgram {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
        }
        String path = getPath();

        SwingUtilities.invokeLater(() -> {
            IProfiler profiler = new Profiler(path);

            MainApplicationFrame mainFrame = new MainApplicationFrame(profiler, path);

            mainFrame.showProfilePickerDialog();
        });
    }

    private static String getPath() {
        String path;

        try {
            String classPath =
                    RobotsProgram.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI()
                    .getPath()
                    .replace("out/production", "src")
                    .replace("/main/", "/main/java/");
            path = classPath.substring(1) + "maven_robots/data";
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return path;
    }
}
