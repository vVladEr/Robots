import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import Helpers.MockOptionPanes.NoMockOptionPane;
import Helpers.MockOptionPanes.YesMockOptionPane;
import Helpers.TestWindows.TestJFrame;
import maven_robots.gui.ClosingListeners;
import maven_robots.gui.OptionPane.DefaultOptionPane;
import maven_robots.gui.OptionPane.IOptionPane;

public class ConfirmationCloseTests {

    private final IOptionPane defaultPane = new DefaultOptionPane();
    private final IOptionPane yesMockOptionPane = new YesMockOptionPane();
    private final IOptionPane noMockOptionPane = new NoMockOptionPane();
    @After
    public void reset() {
        ClosingListeners.setOptionPane(defaultPane);
    }

    @Test
    public void internalFrameShouldClose_WhenYes() throws PropertyVetoException {
        ClosingListeners.setOptionPane(yesMockOptionPane);
        TestJFrame frame = new TestJFrame();
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        Assert.assertFalse(frame.isVisible());
    }

    @Test
    public void internalFrameShouldNotClose_WhenNo() throws PropertyVetoException {
        ClosingListeners.setOptionPane(noMockOptionPane);
        TestJFrame frame = new TestJFrame();
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        Assert.assertTrue(frame.isVisible());
    }
}
