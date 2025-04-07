package maven_robots.gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import maven_robots.gui.BaseClasses.BaseInternalJFrame;
import maven_robots.localization.LocalizationManager;

public class GameWindow extends BaseInternalJFrame implements ILocalizable
{
    private final GameVisualizer m_visualizer;
    public GameWindow() 
    {
        super(LocalizationManager.getStringByName("game.title"), true, true, true, true);
        m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
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
