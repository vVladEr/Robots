package maven_robots.gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import maven_robots.gui.BaseClasses.BaseInternalJFrame;

public class GameWindow extends BaseInternalJFrame {
    private final GameVisualizer m_visualizer;
    public GameWindow() 
    {
        super("game.title", true, true, true, true);
        m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }
}
