package gui;

import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



public class Listeners {
    
    private static final String[] YesNoOptions = {"Да", "Нет"};

    private static InternalFrameListener frameClosingListener = new InternalFrameAdapter() {
        @Override
        public void internalFrameClosing(InternalFrameEvent e)
        {
            int op = JOptionPane.showOptionDialog(e.getInternalFrame(), "Вы точно хотите закрыть окно?", "Закрыть окно?",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, YesNoOptions, YesNoOptions[0]);

        if (op == JOptionPane.YES_OPTION) {
            e.getInternalFrame().dispose();
            }
        }
    };

    private static WindowAdapter windowClosingAdapter = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e)
        {
            int op = JOptionPane.showOptionDialog(e.getWindow(), "Вы точно хотите закрыть приложение?", "Закрыть приложение?",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, YesNoOptions, YesNoOptions[0]);

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
