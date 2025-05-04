package maven_robots.gui.BaseClasses;

import javax.swing.JFrame;

import maven_robots.gui.ClosingListeners;
import maven_robots.gui.ILocalizable;

public abstract class BaseJFrame extends JFrame implements ILocalizable {

    public BaseJFrame() {
        addWindowListener(ClosingListeners.getFramewindowClosingAdapter());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    @Override
    public abstract void changeLanguage();
}
