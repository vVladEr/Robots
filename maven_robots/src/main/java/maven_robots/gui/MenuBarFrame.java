package maven_robots.gui;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Window;

import java.awt.event.KeyEvent;

import maven_robots.log.Logger;

public class MenuBarFrame extends JFrame {
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu testMenu = new JMenu("Тесты");
    private final JMenuItem addLogMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);
    private final JMenu lookAndFeelMenu = new JMenu("Режим отображения");
    private final JMenuItem systemLookAndFeel = new JMenuItem("Системная схема", KeyEvent.VK_S);
    private final JMenuItem crossplatformLookAndFeel = new JMenuItem("Универсальная схема", KeyEvent.VK_S);
    private final Window mainApplicationFrame;

    public MenuBarFrame(Window mainApplicationFrame) {
        fillMenuBar();
        this.mainApplicationFrame = mainApplicationFrame;
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

    private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(mainApplicationFrame);
        }
        catch (ClassNotFoundException | InstantiationException
            | IllegalAccessException | UnsupportedLookAndFeelException e)
        { }
    }

    public JMenuBar getJMenuBar() {
        return menuBar;
    }
}
