package maven_robots.gui.BaseClasses;

import javax.swing.JInternalFrame;

import maven_robots.gui.ClosingListeners;

public class BaseInternalJFrame extends JInternalFrame {

    public BaseInternalJFrame(final String title, final boolean resizable,
            final boolean closable,
            final boolean maximizable, final boolean iconable) {
            super(title, resizable, closable, maximizable, iconable);
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            addInternalFrameListener(ClosingListeners.getFrameClosingListener());
    }
}
