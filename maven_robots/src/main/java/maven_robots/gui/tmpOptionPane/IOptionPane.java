package maven_robots.gui.tmpOptionPane;

import java.awt.Component;

import javax.swing.Icon;

public interface IOptionPane {

    public int showOptionDialog(Component parentComponent,
                                   Object message,
                                   String title,
                                   int optionType,
                                   int messageType,
                                   Icon icon,
                                   Object[] options,
                                   Object initialValue);
}
