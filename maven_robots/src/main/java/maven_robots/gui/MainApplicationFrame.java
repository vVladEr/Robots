package maven_robots.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import maven_robots.gui.BaseClasses.BaseJFrame;
import maven_robots.gui.parameters.DefaultParameters;
import maven_robots.gui.parameters.Parameters;
import maven_robots.gui.profileSaver.Profiler;
import maven_robots.gui.profileSaver.ProfileName;
import maven_robots.localization.LocalizationManager;
import maven_robots.log.Logger;
import maven_robots.logic.Fields.Field;

public final class MainApplicationFrame extends BaseJFrame implements ILocalizable {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final MenuBarFrame menuBarFrame;
    private final LogWindow logWindow;
    private final GameWindow gameWindow;
    
    public MainApplicationFrame(Field field) {
        Parameters initialGameWindowParameters = Parameters.parseParameters(
            DefaultParameters.GameWindowDefaultParameters.getParameters()
        );
        Parameters initialLogWindowParameters = Parameters.parseParameters(
            DefaultParameters.LogWindowDefaultParameters.getParameters()
        );

        gameWindow = new GameWindow(
            field,
            initialGameWindowParameters
        );
        logWindow = new LogWindow(
            Logger.getDefaultLogSource(),
            initialLogWindowParameters
        );

        Profiler.GetInstance().loadJFrameFromProfile(ProfileName.MainFrame.getProfileName(), this);

        MainApplicationListeners listeners = new MainApplicationListeners() {
            @Override
            public void onLanguageChange() {
                changeLanguage();
            }
        
            @Override
            public void onSetLookAndFeel(String className) {
                setLookAndFeel(className);
            }
        
            @Override
            public void onDispatch() {
                dispatch();
            }
        };

        menuBarFrame = new MenuBarFrame(listeners);

        setContentPane(desktopPane);
        
        fillLogWindow();
        addWindow(logWindow);
        addWindow(gameWindow);

        setJMenuBar(menuBarFrame.getJMenuBar());

        WindowAdapter windowClosingAdapter = new WindowAdapter() {
            @Override
            public void windowClosed(final WindowEvent e) {
                saveToProfile();
                System.exit(0);
            }
        };
        this.addWindowListener(windowClosingAdapter);

        Profiler.GetInstance().loadJInternalFrameFromProfile(
                ProfileName.GameWindow.getProfileName(), gameWindow
        );
        Profiler.GetInstance().loadJInternalFrameFromProfile(
                ProfileName.LogWindow.getProfileName(), logWindow
        );
    }
    
    protected void fillLogWindow()
    {
        Logger.debug(LocalizationManager.getStringByName("log.debug.title"));
    }
    
    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    @Override
    public void changeLanguage() {
        logWindow.changeLanguage();
        gameWindow.changeLanguage();

        this.invalidate();
    }

    public void dispatch() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException
            | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
    }

    public void saveToProfile() {
        Profiler.GetInstance().saveJFrameToProfile(ProfileName.MainFrame.getProfileName(), this);
        Profiler.GetInstance().saveJInternalFrameToProfile(ProfileName.GameWindow.getProfileName(), gameWindow);
        Profiler.GetInstance().saveJInternalFrameToProfile(ProfileName.LogWindow.getProfileName(), logWindow);
        logWindow.saveTextArea();
    }
}