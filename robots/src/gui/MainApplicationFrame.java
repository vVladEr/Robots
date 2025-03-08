package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import log.Logger;

/**
 * Что требуется сделать:
 * 1. Метод создания меню перегружен функционалом и трудно читается. 
 * Следует разделить его на серию более простых методов (или вообще выделить отдельный класс).
 *
 */
public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu testMenu = new JMenu("Тесты");
    private final JMenuItem addLogMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);
    private final JMenu lookAndFeelMenu = new JMenu("Режим отображения");
    private final JMenuItem systemLookAndFeel = new JMenuItem("Системная схема", KeyEvent.VK_S);
    private final JMenuItem crossplatformLookAndFeel = new JMenuItem("Универсальная схема", KeyEvent.VK_S);
    
    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setContentPane(desktopPane);
        
        
        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(400,  400);
        addWindow(gameWindow);

        fillMenuBar();
    
        setJMenuBar(menuBar);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    protected LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10,10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }
    
    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private void fillMenuBar() {
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription("Управление режимом отображения приложения");

        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription("Тестовые команды");

        initMenuBarListeners();
        
        lookAndFeelMenu.add(systemLookAndFeel);
        lookAndFeelMenu.add(crossplatformLookAndFeel);
        testMenu.add(addLogMessageItem);

        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
    }

    private void initMenuBarListeners() {
        systemLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            this.invalidate();
        });

        crossplatformLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            this.invalidate();
        });

        addLogMessageItem.addActionListener((event) -> {
            Logger.debug("Новая строка");
        });
    }
    
//    protected JMenuBar createMenuBar() {
//        JMenuBar menuBar = new JMenuBar();
// 
//        //Set up the lone menu.
//        JMenu menu = new JMenu("Document");
//        menu.setMnemonic(KeyEvent.VK_D);
//        menuBar.add(menu);
// 
//        //Set up the first menu item.
//        JMenuItem menuItem = new JMenuItem("New");
//        menuItem.setMnemonic(KeyEvent.VK_N);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_N, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("new");
////        menuItem.addActionListener(this);
//        menu.add(menuItem);
// 
//        //Set up the second menu item.
//        menuItem = new JMenuItem("Quit");
//        menuItem.setMnemonic(KeyEvent.VK_Q);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("quit");
////        menuItem.addActionListener(this);
//        menu.add(menuItem);
// 
//        return menuBar;
//    }
    
    // private JMenuBar generateMenuBar()
    // {
    //     JMenuBar menuBar = new JMenuBar();
        
    //     JMenu lookAndFeelMenu = new JMenu("Режим отображения");
    //     lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
    //     lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
    //             "Управление режимом отображения приложения");
        
    //     {
    //         JMenuItem systemLookAndFeel = new JMenuItem("Системная схема", KeyEvent.VK_S);
    //         systemLookAndFeel.addActionListener((event) -> {
    //             setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    //             this.invalidate();
    //         });
    //         lookAndFeelMenu.add(systemLookAndFeel);
    //     }

    //     {
    //         JMenuItem crossplatformLookAndFeel = new JMenuItem("Универсальная схема", KeyEvent.VK_S);
    //         crossplatformLookAndFeel.addActionListener((event) -> {
    //             setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    //             this.invalidate();
    //         });
    //         lookAndFeelMenu.add(crossplatformLookAndFeel);
    //     }

    //     JMenu testMenu = new JMenu("Тесты");
    //     testMenu.setMnemonic(KeyEvent.VK_T);
    //     testMenu.getAccessibleContext().setAccessibleDescription(
    //             "Тестовые команды");
        
    //     {
    //         JMenuItem addLogMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);
    //         addLogMessageItem.addActionListener((event) -> {
    //             Logger.debug("Новая строка");
    //         });
    //         testMenu.add(addLogMessageItem);
    //     }

    //     menuBar.add(lookAndFeelMenu);
    //     menuBar.add(testMenu);
    //     return menuBar;
    // }
    
    // private void setLookAndFeel(String className)
    // {
    //     try
    //     {
    //         UIManager.setLookAndFeel(className);
    //         SwingUtilities.updateComponentTreeUI(this);
    //     }
    //     catch (ClassNotFoundException | InstantiationException
    //         | IllegalAccessException | UnsupportedLookAndFeelException e)
    //     {
    //         // just ignore
    //     }
    // }
    private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (ClassNotFoundException | InstantiationException
            | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            // just ignore
        }
    }
}
