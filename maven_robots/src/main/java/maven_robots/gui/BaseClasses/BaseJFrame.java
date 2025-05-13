package maven_robots.gui.BaseClasses;

import javax.swing.JFrame;

import maven_robots.gui.ClosingListeners;

public class BaseJFrame extends JFrame {
    public BaseJFrame() {
        addWindowListener(ClosingListeners.getFramewindowClosingAdapter());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }
}
