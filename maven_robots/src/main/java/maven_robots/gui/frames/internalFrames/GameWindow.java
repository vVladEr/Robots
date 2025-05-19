package maven_robots.gui.frames.internalFrames;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import maven_robots.data.profiler.IProfiler;
import maven_robots.data.profiler.enums.FrameName;
import maven_robots.gui.frames.baseFrames.BaseJInternalFrame;
import maven_robots.data.parameters.Parameters;
import maven_robots.gui.mainFrame.GameField;
import maven_robots.localization.ILocalizable;
import maven_robots.localization.LocalizationManager;
import maven_robots.logic.Fields.Field;

public class GameWindow extends BaseJInternalFrame implements ILocalizable
{

    public GameWindow(IProfiler profiler, Parameters parameters) {
        super(
            profiler,
            FrameName.GAME_WINDOW,
            LocalizationManager.getStringByName("game.title"),
            true,
            true,
            true,
            true
        );

        this.setBounds(
            parameters.getX(),
            parameters.getY(),
            parameters.getWidth(),
            parameters.getHeight()
        );

    }

    public void setField(Field field) {
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
