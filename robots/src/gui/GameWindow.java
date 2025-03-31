package gui;

import java.awt.BorderLayout;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import localization.LocalizationManager;

public class GameWindow extends JInternalFrame implements ILocalizable
{
    private final GameVisualizer m_visualizer;
    public GameWindow() 
    {
        super(LocalizationManager.getStringByName("game.title"), true, true, true, true);
        m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addInternalFrameListener(Listeners.getFrameClosingListener());
        pack();
    }

    @Override
    public void changeLanguage() {
        this.setTitle(LocalizationManager.getStringByName("game.title"));
        m_visualizer.changeLanguage();

        this.invalidate();
    }
}
