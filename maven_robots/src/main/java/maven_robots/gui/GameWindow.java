package maven_robots.gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import maven_robots.gui.BaseClasses.BaseInternalJFrame;
import maven_robots.localization.LocalizationManager;
import maven_robots.logic.Fields.Field;

public class GameWindow extends BaseInternalJFrame implements ILocalizable
{

    public GameWindow(Field field, String profileName) {
        super(LocalizationManager.getStringByName("game.title"), true, true, true, true, "gameWindow");

        GameField gameField = new GameField(field);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(gameField);
        getContentPane().add(panel);
        //pack();
        
        if (!profileName.equals("default")) {
            loadFromProfile(profileName, panel);
        }
        else {

        }  
    }

    @Override
    public void changeLanguage() {
        this.setTitle(LocalizationManager.getStringByName("game.title"));

        this.invalidate();
    }
}
