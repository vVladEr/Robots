package maven_robots.gui.mainFrame;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

import maven_robots.localization.ILocalizable;
import maven_robots.localization.Languages;

import maven_robots.localization.LocalizationManager;
import maven_robots.log.Logger;

public class MenuBarFrame extends JMenuBar implements ILocalizable {
    private final MainApplicationListeners languageChangeListener;

    private final JMenu languageMenu = new JMenu(LocalizationManager.getStringByName("menu.language.title"));
    private final JMenuItem russian = new JMenuItem(LocalizationManager.getStringByName("menu.language.russian"), KeyEvent.VK_S);
    private final JMenuItem english = new JMenuItem(LocalizationManager.getStringByName("menu.language.english"), KeyEvent.VK_S);

    private final JMenu testMenu = new JMenu(LocalizationManager.getStringByName("menu.test.title"));
    private final JMenuItem addLogMessageItem = new JMenuItem(LocalizationManager.getStringByName("menu.test.addlogmessage"), KeyEvent.VK_S);

    private final JMenu lookAndFeelMenu = new JMenu(LocalizationManager.getStringByName("menu.lookandfeel.title"));
    private final JMenuItem systemLookAndFeel = new JMenuItem(LocalizationManager.getStringByName("menu.lookandfeel.system"), KeyEvent.VK_S);
    private final JMenuItem crossplatformLookAndFeel = new JMenuItem(LocalizationManager.getStringByName("menu.lookandfeel.crossplatform"), KeyEvent.VK_S);

    private final JMenuItem exitOption = new JMenuItem(LocalizationManager.getStringByName("menu.closing.title"));

    public MenuBarFrame(final MainApplicationListeners languageChangeListener) {
        this.languageChangeListener = languageChangeListener;
        fillMenuBar();
    }

    private void fillMenuBar() {
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext()
                .setAccessibleDescription(LocalizationManager.getStringByName("menu.lookandfeel.description"));

        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext()
                .setAccessibleDescription(LocalizationManager.getStringByName("menu.test.description"));

        languageMenu.setMnemonic(KeyEvent.VK_L);
        languageMenu.getAccessibleContext()
                .setAccessibleDescription(LocalizationManager.getStringByName("menu.language.description"));

        initMenuBarListeners();

        lookAndFeelMenu.add(systemLookAndFeel);
        lookAndFeelMenu.add(crossplatformLookAndFeel);

        testMenu.add(addLogMessageItem);

        languageMenu.add(russian);
        languageMenu.add(english);

        add(lookAndFeelMenu);
        add(testMenu);
        add(languageMenu);
        add(exitOption);
    }

    private void initMenuBarListeners() {
        russian.addActionListener((event) -> {
            LocalizationManager.setLocale(Languages.RU);
            System.out.println(LocalizationManager.getStringByName("menu.language.title"));
            languageChangeListener.onLanguageChange();
            changeLanguage();
        });

        english.addActionListener((event) -> {
            LocalizationManager.setLocale(Languages.EN);
            System.out.println(LocalizationManager.getStringByName("menu.language.title"));
            languageChangeListener.onLanguageChange();
            changeLanguage();
        });

        systemLookAndFeel.addActionListener((event) -> {
            languageChangeListener.onSetLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            this.invalidate();
        });

        crossplatformLookAndFeel.addActionListener((event) -> {
            languageChangeListener.onSetLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            this.invalidate();
        });

        addLogMessageItem.addActionListener((event) -> {
            Logger.debug(LocalizationManager.getStringByName("log.debug.message"));
        });

        exitOption.addActionListener((event) -> {
            languageChangeListener.onDispatch();
        });
    }

    @Override
    public final void changeLanguage() {
        languageMenu.setText(
            LocalizationManager.getStringByName("menu.language.title"));
        russian.setText(
            LocalizationManager.getStringByName("menu.language.russian"));
        english.setText(
            LocalizationManager.getStringByName("menu.language.english"));
        testMenu.setText(
            LocalizationManager.getStringByName("menu.test.title"));
        addLogMessageItem.setText(
            LocalizationManager.getStringByName("menu.test.addlogmessage"));
    
        lookAndFeelMenu.setText(LocalizationManager.getStringByName("menu.lookandfeel.title"));
        systemLookAndFeel.setText(LocalizationManager.getStringByName("menu.lookandfeel.system"));
        crossplatformLookAndFeel.setText(LocalizationManager.getStringByName("menu.lookandfeel.crossplatform"));

        exitOption.setText(LocalizationManager.getStringByName("menu.closing.title"));

        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(LocalizationManager.getStringByName("menu.lookandfeel.description"));
        testMenu.getAccessibleContext().setAccessibleDescription(LocalizationManager.getStringByName("menu.test.description"));
        languageMenu.getAccessibleContext().setAccessibleDescription(LocalizationManager.getStringByName("menu.language.description"));
        
    this.invalidate();
}
}
