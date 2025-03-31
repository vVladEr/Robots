package gui;

import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import localization.LocalizationManager;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



public class Listeners{

    private static String[] getYesNoOptions() {
        return new String[]{
            LocalizationManager.getStringByName("option.yes"),
            LocalizationManager.getStringByName("option.no")};
    }

    private static InternalFrameListener frameClosingListener = new InternalFrameAdapter() {
        @Override
        public void internalFrameClosing(InternalFrameEvent e)
        {
            String[] yesNoOptions = getYesNoOptions();
            int op = JOptionPane.showOptionDialog(
                e.getInternalFrame(),
                LocalizationManager.getStringByName("window.closing.text"),
                LocalizationManager.getStringByName("window.closing.title"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                yesNoOptions, 
                yesNoOptions[0]);

        if (op == JOptionPane.YES_OPTION) {
            e.getInternalFrame().dispose();
            }
        }
    };

    private static WindowAdapter windowClosingAdapter = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e)
        {
            String[] yesNoOptions = getYesNoOptions();
            int op = JOptionPane.showOptionDialog(
                e.getWindow(),
                LocalizationManager.getStringByName("app.closing.text"),
                LocalizationManager.getStringByName("app.closing.title"),
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, yesNoOptions, yesNoOptions[0]);

            if (op == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    };

    private Listeners(){}

    public static InternalFrameListener getFrameClosingListener()
    {
        return frameClosingListener;
    }

    public static WindowAdapter getFramewindowClosingAdapter()
    {
        return windowClosingAdapter;
    }
}
