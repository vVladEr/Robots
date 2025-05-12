package maven_robots.gui.tmpOptionPane;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JOptionPane;

public class DefaultOptionPane implements IOptionPane {

    @Override
    public int showOptionDialog(Component parentComponent, Object message, String title, int optionType,
            int messageType, Icon icon, Object[] options, Object initialValue) {
        return JOptionPane.showOptionDialog(
            parentComponent,
            message,
            title,
            optionType,
            messageType,
            icon,
            options,
            initialValue
        );
    }

}
