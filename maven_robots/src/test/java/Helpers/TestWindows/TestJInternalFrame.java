package Helpers.TestWindows;

import maven_robots.gui.BaseClasses.BaseInternalJFrame;

public class TestJInternalFrame extends BaseInternalJFrame {

    public TestJInternalFrame(String titleBandleName, boolean resizable, boolean closable, boolean maximizable,
            boolean iconable) {
        super(titleBandleName, resizable, closable, maximizable, iconable);
        setVisible(true);
    }

}
