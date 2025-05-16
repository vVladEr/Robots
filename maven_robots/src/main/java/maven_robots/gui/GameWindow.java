package maven_robots.gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import maven_robots.gui.baseClasses.BaseInternalJFrame;
import maven_robots.logic.fields.Field;

public class GameWindow extends BaseInternalJFrame {
    public GameWindow(Field field)
    {
        super("game.title", true, true, true, true);
        GameField gameField = new GameField(field);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(gameField, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }
}
