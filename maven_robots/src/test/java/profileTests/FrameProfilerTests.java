package profileTests;

import static maven_robots.gui.RobotsProgram.getPath;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import helpers.testWindows.TestJFrame;
import maven_robots.data.profiler.Profiler;

public class FrameProfilerTests {

    private static Profiler profiler;
    
    @BeforeClass
    public static void setup() {
        String path = getPath();
        profiler = new Profiler(path);
        profiler.setProfileName("test");
    }

    @Test
    public void savingAndLoadPositionWorksCorrectly() {
        TestJFrame testFrame = new TestJFrame(profiler);
        int expectedX = 20;
        int expectedY = 30;
        int expectedWidth = 100;
        int expectedHeight = 100;
        testFrame.setBounds(expectedX, expectedY, expectedWidth, expectedHeight);
        testFrame.saveFrameState();

        TestJFrame loadedTestFrame = new TestJFrame(profiler);
        loadedTestFrame.loadFrameState();

        Assert.assertEquals(expectedX, loadedTestFrame.getX());
        Assert.assertEquals(expectedY, loadedTestFrame.getY());
        Assert.assertEquals(expectedWidth, loadedTestFrame.getWidth());
        Assert.assertEquals(expectedHeight, loadedTestFrame.getHeight());
    }
}
