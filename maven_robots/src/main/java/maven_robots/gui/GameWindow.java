package maven_robots.gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import maven_robots.gui.BaseClasses.BaseInternalJFrame;
import maven_robots.gui.parameters.Parameters;
import maven_robots.gui.profileSaver.Profiler;
import maven_robots.gui.profileSaver.ProfileName;
import maven_robots.localization.LocalizationManager;
import maven_robots.logic.Fields.Field;

public class GameWindow extends BaseInternalJFrame implements ILocalizable
{

    public GameWindow(Field field, Parameters parameters) {
        super(LocalizationManager.getStringByName("game.title"), true, true, true, true);

        this.setBounds(
            parameters.getX(),
            parameters.getY(),
            parameters.getWidth(),
            parameters.getHeight()
        );
        GameField gameField = new GameField(field);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(gameField);
        getContentPane().add(panel);
    }

    @Override
    public void changeLanguage() {
        this.setTitle(LocalizationManager.getStringByName("game.title"));

        this.invalidate();
    }
}
