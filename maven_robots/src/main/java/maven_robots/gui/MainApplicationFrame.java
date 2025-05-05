package maven_robots.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import maven_robots.gui.BaseClasses.BaseJFrame;
import maven_robots.localization.LocalizationManager;
import maven_robots.log.Logger;
import maven_robots.logic.Fields.Field;

public final class MainApplicationFrame extends BaseJFrame implements ILocalizable {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final MenuBarFrame menuBarFrame;
    private final LogWindow logWindow;
    private final GameWindow gameWindow;
    
    public MainApplicationFrame(Field field, String profileName) {
        super("MainFrame");
        gameWindow = new GameWindow(field, profileName);
        logWindow = new LogWindow(Logger.getDefaultLogSource(), profileName);

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
        
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        if (profileName.equals("default")) {
            int inset = 50;        
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            setBounds(inset, inset,
                screenSize.width  - inset * 2,
                screenSize.height - inset * 2);
        } else {
            loadFromProfile(profileName, this);
        }


        setContentPane(desktopPane);
        
        fillLogWindow();
        addWindow(logWindow);
        
        gameWindow.setSize(400,  400);
        addWindow(gameWindow);

        setJMenuBar(menuBarFrame.getJMenuBar());

        WindowAdapter windowClosingAdapter = new WindowAdapter() {
            @Override
            public void windowClosed(final WindowEvent e) {
                File profilePath = new File("D:/codes/java/Robots/maven_robots/src/main/java/maven_robots/data/profiles/test");
                try {
                    profilePath.mkdir();
                    File profile = new File("D:/codes/java/Robots/maven_robots/src/main/java/maven_robots/data/profiles/test/frames.properties");
                    profile.createNewFile();
                    saveToProfile("test");
                    
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                System.exit(0);
            }
        };
        this.addWindowListener(windowClosingAdapter);
    }
    
    protected void fillLogWindow()
    {
        logWindow.setLocation(10, 10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
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

    @Override
    public void saveToProfile(String profileName) {
        super.saveToProfile(profileName);
        logWindow.saveToProfile(profileName);
        gameWindow.saveToProfile(profileName);
    }
}