package Helpers.MockOptionPanes;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import maven_robots.gui.OptionPane.IOptionPane;

public class YesMockOptionPane implements IOptionPane{

    @Override
    public int showOptionDialog(Component parentComponent, Object message, String title, int optionType,
                                int messageType, Icon icon, Object[] options, Object initialValue) {
        return JOptionPane.YES_OPTION;
    }

}
