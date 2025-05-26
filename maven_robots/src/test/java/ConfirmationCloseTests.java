
import java.awt.event.WindowEvent;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import helpers.mocks.MockProfiler;
import helpers.mocks.mockOptionPanes.NoMockOptionPane;
import helpers.mocks.mockOptionPanes.YesMockOptionPane;
import helpers.testWindows.TestJFrame;
import maven_robots.gui.mainFrame.ClosingListeners;
import maven_robots.gui.optionPane.DefaultOptionPane;
import maven_robots.gui.optionPane.IOptionPane;

public class ConfirmationCloseTests {

    private final IOptionPane defaultPane = new DefaultOptionPane();
    private final IOptionPane yesMockOptionPane = new YesMockOptionPane();
    private final IOptionPane noMockOptionPane = new NoMockOptionPane();
    @After
    public void reset() {
        ClosingListeners.setOptionPane(defaultPane);
    }

    @Test
    public void frameShouldClose_WhenYes() {
        ClosingListeners.setOptionPane(yesMockOptionPane);
        TestJFrame frame = new TestJFrame(new MockProfiler());
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        Assert.assertFalse(frame.isVisible());
    }

    @Test
    public void frameShouldNotClose_WhenNo() {
        ClosingListeners.setOptionPane(noMockOptionPane);
        TestJFrame frame = new TestJFrame(new MockProfiler());
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        Assert.assertTrue(frame.isVisible());
    }
}
