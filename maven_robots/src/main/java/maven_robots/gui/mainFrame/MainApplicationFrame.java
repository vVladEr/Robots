package maven_robots.gui.mainFrame;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedHashMap;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import maven_robots.data.parser.IParser;
import maven_robots.data.parser.LevelParser;
import maven_robots.data.profiler.Profiler;
import maven_robots.gui.frames.baseFrames.BaseJInternalFrame;
import maven_robots.gui.frames.baseFrames.BaseJFrame;
import maven_robots.data.parameters.DefaultParameters;
import maven_robots.data.parameters.Parameters;
import maven_robots.data.profiler.IProfiler;
import maven_robots.data.profiler.enums.FrameName;
import maven_robots.gui.frames.internalFrames.GameWindow;
import maven_robots.localization.ILocalizable;
import maven_robots.gui.frames.internalFrames.LogWindow;
import maven_robots.localization.LocalizationManager;
import maven_robots.log.Logger;
import maven_robots.logic.fields.Field;

public final class MainApplicationFrame extends BaseJFrame implements ILocalizable {
    private final LinkedHashMap<FrameName, Component> components;
    private final String path;

    private final IProfiler profiler;
    private final IParser levelParser;

    public MainApplicationFrame(IProfiler profiler, String path) {
        super(profiler, FrameName.MAIN_FRAME);

        this.path = path;
        this.profiler = profiler;
        this.levelParser = new LevelParser(path);

        components = new LinkedHashMap<>();
        initComponents();

        Logger.debug(LocalizationManager.getStringByName("log.debug.title"));

        for (Component comp : components.values()) {
            if (comp instanceof JInternalFrame) {
                addWindow((JInternalFrame) comp);
            } else if (comp instanceof JMenuBar) {
                setJMenuBar((JMenuBar) comp);
            }
        }
    }

    @Override
    public void changeLanguage() {
        ((LogWindow) components.get(FrameName.LOG_WINDOW)).changeLanguage();
        ((GameWindow) components.get(FrameName.GAME_WINDOW)).changeLanguage();
        ((MenuBarFrame) components.get(FrameName.MENU_BAR_FRAME)).changeLanguage();

        this.invalidate();
    }

    private void loadProfiles() {
        for (Component comp : components.values()) {
            if (comp instanceof BaseJFrame) {
                ((BaseJFrame) comp).loadProfile();
            } else if (comp instanceof BaseJInternalFrame) {
                ((BaseJInternalFrame) comp).loadProfile();
            }
        }
    }

    private void saveProfiles() {
        for (Component comp : components.values()) {
            if (comp instanceof BaseJFrame) {
                ((BaseJFrame) comp).saveProfile();
            } else if (comp instanceof BaseJInternalFrame) {
                ((BaseJInternalFrame) comp).saveProfile();
            }
        }
    }

    public void showProfilePickerDialog() {
        ProfilePickerDialog dialog =
                (ProfilePickerDialog) components.get(FrameName.PROFILE_PICKER_DIALOG);

        dialog.setVisible(true);

        if (dialog.getSelectedProfileName() != null) {
            String profileName = dialog.getSelectedProfileName();
            profiler.setProfileName(profileName);

            Field field = levelParser.parseLevel(1);
            ((GameWindow) components.get(FrameName.GAME_WINDOW)).setField(field);

            JOptionPane.showMessageDialog(
                this,
                "Загрузка/инициализация профиля: " + profileName
            );
            pack();
            setVisible(true);

            Profiler profiler = (Profiler) this.profiler;
            if (profiler.isProfileExists()) {
                loadProfiles();
                profiler.loadLanguage();
                changeLanguage();
            }
        } else {
            System.exit(0);
        }
    }

    private void addWindow(JInternalFrame frame) {
        this.getContentPane().add(frame);
        frame.setVisible(true);
    }

    private void initComponents() {
        initMainApplicationFrame();
        initMenuBarFrame();
        initGameWindow();
        initTextArea();
        initLogWindow();
        initProfilePickerDialog();
    }

    private void initMainApplicationFrame() {
        Parameters initialMainFrameParameters = Parameters.parseParameters(
                DefaultParameters.MainFrameDefaultParameters.getParameters()
        );

        WindowAdapter windowClosingAdapter = new WindowAdapter() {
            @Override
            public void windowClosed(final WindowEvent e) {
                saveProfiles();
                ((Profiler) profiler).saveLanguage();
                System.exit(0);
            }
        };

        addWindowListener(windowClosingAdapter);

        setContentPane(new JDesktopPane());

        setBounds(
            initialMainFrameParameters.getX(),
            initialMainFrameParameters.getY(),
            initialMainFrameParameters.getWidth(),
            initialMainFrameParameters.getHeight()
        );

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        components.put(FrameName.MAIN_FRAME, this);
    }

    private void initMenuBarFrame() {
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

        components.put(FrameName.MENU_BAR_FRAME, new MenuBarFrame(listeners));
    }

    public void dispatch() {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }


    private void initGameWindow() {
        Parameters initialGameWindowParameters = Parameters.parseParameters(
                DefaultParameters.GameWindowDefaultParameters.getParameters()
        );

        components.put(
            FrameName.GAME_WINDOW,
            new GameWindow(
                profiler,
                initialGameWindowParameters
            )
        );
    }

    private void initTextArea() {
        components.put(FrameName.TEXT_AREA, new JTextArea(""));
    }

    private void initLogWindow() {
        Parameters initialLogWindowParameters = Parameters.parseParameters(
                DefaultParameters.LogWindowDefaultParameters.getParameters()
        );

        components.put(FrameName.LOG_WINDOW, new LogWindow(
                profiler,
                (JTextArea) components.get(FrameName.TEXT_AREA),
                Logger.getDefaultLogSource(),
                initialLogWindowParameters
        ));
    }

    private void initProfilePickerDialog() {
        components.put(FrameName.PROFILE_PICKER_DIALOG, new ProfilePickerDialog(this, path));
    }

    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException
            | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
    }
}