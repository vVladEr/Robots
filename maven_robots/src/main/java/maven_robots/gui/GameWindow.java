package maven_robots.gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import maven_robots.gui.BaseClasses.BaseInternalJFrame;
import maven_robots.localization.LocalizationManager;
import maven_robots.logic.Fields.Field;

public class GameWindow extends BaseInternalJFrame implements ILocalizable
{
    private final GameVisualizer m_visualizer;

    public GameWindow(Field field)
    {
        super(LocalizationManager.getStringByName("game.title"), true, true, true, true);
        m_visualizer = new GameVisualizer();
        GameField gameField = new GameField(field);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(gameField, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    @Override
    public void changeLanguage() {
        this.setTitle(LocalizationManager.getStringByName("game.title"));
        m_visualizer.changeLanguage();

        this.invalidate();
    }
}
