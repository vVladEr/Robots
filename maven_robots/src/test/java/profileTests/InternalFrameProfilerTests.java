package profileTests;

import static maven_robots.gui.RobotsProgram.getPath;

import java.beans.PropertyVetoException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import helpers.mocks.mockOptionPanes.YesMockOptionPane;
import helpers.testWindows.TestJInternalFrame;
import maven_robots.data.profiler.Profiler;
import maven_robots.gui.mainFrame.ClosingListeners;
import maven_robots.gui.optionPane.IOptionPane;

public class InternalFrameProfilerTests {

    private static Profiler profiler;
    private final IOptionPane yesMockOptionPane = new YesMockOptionPane();
    
    @BeforeClass
    public static void setup() {
        String path = getPath();
        profiler = new Profiler(path);
        profiler.setProfileName("test");
    }

    @Test
    public void savingAndLoadPositionWorksCorrectly() {
        TestJInternalFrame testFrame = new TestJInternalFrame(profiler,
            "test", true, true, true, true);
        int expectedX = 20;
        int expectedY = 30;
        int expectedWidth = 100;
        int expectedHeight = 100;
        testFrame.setBounds(expectedX, expectedY, expectedWidth, expectedHeight);
        testFrame.saveFrameState();

        TestJInternalFrame loadedTestFrame = new TestJInternalFrame(profiler,
            "testLoad", true, true, true, true);
        loadedTestFrame.loadFrameState();

        Assert.assertEquals(expectedX, loadedTestFrame.getX());
        Assert.assertEquals(expectedY, loadedTestFrame.getY());
        Assert.assertEquals(expectedWidth, loadedTestFrame.getWidth());
        Assert.assertEquals(expectedHeight, loadedTestFrame.getHeight());
    }

    @Test
    public void savingAndLoadIconofiedWorksCorrectly() {
        TestJInternalFrame testFrame = new TestJInternalFrame(profiler,
            "test", true, true, true, true);
        int expectedX = 20;
        int expectedY = 30;
        int expectedWidth = 200;
        int expectedHeight = 100;
        testFrame.setBounds(expectedX, expectedY, expectedWidth, expectedHeight);
        try {
            testFrame.setIcon(true);
        } catch (PropertyVetoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        testFrame.saveFrameState();

        TestJInternalFrame loadedTestFrame = new TestJInternalFrame(profiler,
            "testLoad", true, true, true, true);
        loadedTestFrame.loadFrameState();

        Assert.assertTrue(loadedTestFrame.isIcon());

        try {
            loadedTestFrame.setIcon(false);
        } catch (PropertyVetoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        Assert.assertEquals(expectedX, loadedTestFrame.getX());
        Assert.assertEquals(expectedY, loadedTestFrame.getY());
        Assert.assertEquals(expectedWidth, loadedTestFrame.getWidth());
        Assert.assertEquals(expectedHeight, loadedTestFrame.getHeight());
    }

    @Test
    public void savingAndLoadClosedWorksCorrectly() {
        ClosingListeners.setOptionPane(yesMockOptionPane);
        TestJInternalFrame testFrame = new TestJInternalFrame(profiler,
            "test", true, true, true, true);
        try {
            testFrame.setClosed(true);
        } catch (PropertyVetoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        testFrame.saveFrameState();

        TestJInternalFrame loadedTestFrame = new TestJInternalFrame(profiler,
            "testLoaded", true, true, true, true);
        loadedTestFrame.loadFrameState();
        Assert.assertTrue(loadedTestFrame.isClosed());
    }
}
