package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import localization.LocalizationManager;
import log.Logger;

/**
 * Что требуется сделать:
 * 1. Метод создания меню перегружен функционалом и трудно читается. 
 * Следует разделить его на серию более простых методов (или вообще выделить отдельный класс).
 *
 */
public final class MainApplicationFrame extends JFrame implements ILocalizable
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final MenuBarFrame menuBarFrame;
    private final LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
    private final GameWindow gameWindow = new GameWindow();
    
    public MainApplicationFrame() {
        menuBarFrame = new MenuBarFrame(this::changeLanguage);
        
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setContentPane(desktopPane);
        
        fillLogWindow();
        addWindow(logWindow);
        
        gameWindow.setSize(400,  400);
        addWindow(gameWindow);

        setJMenuBar(menuBarFrame.getJMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    protected void fillLogWindow()
    {
        logWindow.setLocation(10,10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug(LocalizationManager.getStringByName("log.debug.title"));
    }
    
    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    @Override
    public void changeLanguage() {
        logWindow.changeLanguage();
        gameWindow.changeLanguage();

        this.invalidate();
    }
}
