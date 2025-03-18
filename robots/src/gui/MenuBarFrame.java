package gui;

import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import localization.Languages;
import localization.LocalizationManager;
import log.Logger;

public class MenuBarFrame extends JFrame implements ILocalizable {
    private final LanguageChangeListener languageChangeListener;

    private final JMenuBar menuBar = new JMenuBar();

    private final JMenu languageMenu = new JMenu(LocalizationManager.getStringByName("menu.language.title"));
    private final JMenuItem russian = new JMenuItem(LocalizationManager.getStringByName("menu.language.russian"), KeyEvent.VK_S);
    private final JMenuItem english = new JMenuItem(LocalizationManager.getStringByName("menu.language.english"), KeyEvent.VK_S);

    private final JMenu testMenu = new JMenu(LocalizationManager.getStringByName("menu.test.title"));
    private final JMenuItem addLogMessageItem = new JMenuItem(LocalizationManager.getStringByName("menu.test.addlogmessage"), KeyEvent.VK_S);

    private final JMenu lookAndFeelMenu = new JMenu(LocalizationManager.getStringByName("menu.lookandfeel.title"));
    private final JMenuItem systemLookAndFeel = new JMenuItem(LocalizationManager.getStringByName("menu.lookandfeel.system"), KeyEvent.VK_S);
    private final JMenuItem crossplatformLookAndFeel = new JMenuItem(LocalizationManager.getStringByName("menu.lookandfeel.crossplatform"), KeyEvent.VK_S);


    public MenuBarFrame(LanguageChangeListener languageChangeListener) {
        this.languageChangeListener = languageChangeListener;
        fillMenuBar();
    }

    private void fillMenuBar() {
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(LocalizationManager.getStringByName("menu.lookandfeel.description"));

        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(LocalizationManager.getStringByName("menu.test.description"));

        languageMenu.setMnemonic(KeyEvent.VK_L);
        languageMenu.getAccessibleContext().setAccessibleDescription(LocalizationManager.getStringByName("menu.language.description"));

        initMenuBarListeners();
        
        lookAndFeelMenu.add(systemLookAndFeel);
        lookAndFeelMenu.add(crossplatformLookAndFeel);

        testMenu.add(addLogMessageItem);

        languageMenu.add(russian);
        languageMenu.add(english);

        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
        menuBar.add(languageMenu);
    }

    private void initMenuBarListeners() {
        russian.addActionListener((_) -> {
            LocalizationManager.setLocale(Languages.RU);
            System.out.println(LocalizationManager.getStringByName("menu.language.title"));
            languageChangeListener.onLanguageChange();
            changeLanguage();
        });

        english.addActionListener((_) -> {
            LocalizationManager.setLocale(Languages.EN);
            System.out.println(LocalizationManager.getStringByName("menu.language.title"));
            languageChangeListener.onLanguageChange();
            changeLanguage();
        });

        systemLookAndFeel.addActionListener((_) -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            this.invalidate();
        });

        crossplatformLookAndFeel.addActionListener((_) -> {
            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            this.invalidate();
        });

        addLogMessageItem.addActionListener((_) -> {
            Logger.debug(LocalizationManager.getStringByName("log.debug.message"));
        });
    }

    private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (ClassNotFoundException | InstantiationException
            | IllegalAccessException | UnsupportedLookAndFeelException e)
        { }
    }

    @Override
    public JMenuBar getJMenuBar() {
        return menuBar;
    }

    @Override
    public void changeLanguage() {
        languageMenu.setText(LocalizationManager.getStringByName("menu.language.title"));
        russian.setText(LocalizationManager.getStringByName("menu.language.russian"));
        english.setText(LocalizationManager.getStringByName("menu.language.english"));
    
        testMenu.setText(LocalizationManager.getStringByName("menu.test.title"));
        addLogMessageItem.setText(LocalizationManager.getStringByName("menu.test.addlogmessage"));
    
        lookAndFeelMenu.setText(LocalizationManager.getStringByName("menu.lookandfeel.title"));
        systemLookAndFeel.setText(LocalizationManager.getStringByName("menu.lookandfeel.system"));
        crossplatformLookAndFeel.setText(LocalizationManager.getStringByName("menu.lookandfeel.crossplatform"));

        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(LocalizationManager.getStringByName("menu.lookandfeel.description"));
        testMenu.getAccessibleContext().setAccessibleDescription(LocalizationManager.getStringByName("menu.test.description"));
        languageMenu.getAccessibleContext().setAccessibleDescription(LocalizationManager.getStringByName("menu.language.description"));
        
        this.invalidate();
    }
}
