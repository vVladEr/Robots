import static maven_robots.gui.RobotsProgram.getPath;

import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import helpers.mockOptionPanes.NoMockOptionPane;
import helpers.mockOptionPanes.YesMockOptionPane;
import helpers.testWindows.TestJFrame;
import maven_robots.data.profiler.IProfiler;
import maven_robots.data.profiler.Profiler;
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
    public void frameShouldClose_WhenYes() throws PropertyVetoException {
        ClosingListeners.setOptionPane(yesMockOptionPane);
        String path = getPath();
        IProfiler profiler = new Profiler(path);
        TestJFrame frame = new TestJFrame(profiler);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        Assert.assertFalse(frame.isVisible());
    }

    @Test
    public void frameShouldNotClose_WhenNo() throws PropertyVetoException {
        ClosingListeners.setOptionPane(noMockOptionPane);
        String path = getPath();
        IProfiler profiler = new Profiler(path);
        TestJFrame frame = new TestJFrame(profiler);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        Assert.assertTrue(frame.isVisible());
    }
}
