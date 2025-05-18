package maven_robots.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import maven_robots.gui.optionPane.DefaultOptionPane;
import maven_robots.gui.optionPane.IOptionPane;
import maven_robots.localization.LocalizationManager;


public final class ClosingListeners {

    private static String[] getYesNoOptions() {
        return new String[] {
                LocalizationManager.getStringByName("option.yes"),
                LocalizationManager.getStringByName("option.no") };
    }

    private static IOptionPane optionPane = new DefaultOptionPane();

    public static void setOptionPane(IOptionPane pane) {
        optionPane = pane;
    }

    private static InternalFrameListener frameClosingListener =
        new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(final InternalFrameEvent e) {
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
        public void windowClosing(final WindowEvent e) {
            String[] yesNoOptions = getYesNoOptions();
            int op = optionPane.showOptionDialog(
                e.getWindow(),
                LocalizationManager.getStringByName("app.closing.text"),
                LocalizationManager.getStringByName("app.closing.title"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, yesNoOptions, yesNoOptions[0]);

            if (op == JOptionPane.YES_OPTION) {
                e.getWindow().dispose();
            }
        }
        
        @Override
        public void windowClosed(final WindowEvent e) {
            System.exit(0);
        }
    };

    private ClosingListeners() {
    }

    public static InternalFrameListener getFrameClosingListener() {
        return frameClosingListener;
    }

    public static WindowAdapter getFramewindowClosingAdapter() {
        return windowClosingAdapter;
    }
}
