package profileTests;

import java.beans.PropertyVetoException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import maven_robots.data.profiler.Profiler;
import maven_robots.data.profiler.enums.FrameName;
import maven_robots.data.profiler.strategies.JInternalFrameStrategy;

public class InternalFrameProfilerTests {

    private static Profiler profiler;
    
    @BeforeClass
    public static void setup() {
        profiler = new Profiler();
        profiler.addStrategy(TestInternalFrame.class, new JInternalFrameStrategy());
        profiler.setProfileName("test");
    }

    @Test
    public void savingAndLoadPositionWorksCorrectly() {
        TestInternalFrame testFrame = new TestInternalFrame(profiler, FrameName.TEST_FRAME, "test");
        int expectedX = 20;
        int expectedY = 30;
        int expectedWidth = 100;
        int expectedHeight = 100;
        testFrame.setBounds(expectedX, expectedY, expectedWidth, expectedHeight);
        testFrame.saveProfile();

        TestInternalFrame loadedTestFrame = new TestInternalFrame(profiler,
            FrameName.TEST_FRAME, "newTest");
        loadedTestFrame.loadProfile();

        Assert.assertEquals(expectedX, loadedTestFrame.getX());
        Assert.assertEquals(expectedY, loadedTestFrame.getY());
        Assert.assertEquals(expectedWidth, loadedTestFrame.getWidth());
        Assert.assertEquals(expectedHeight, loadedTestFrame.getHeight());
    }

    @Test
    public void savingAndLoadIconofiedWorksCorrectly() {
        TestInternalFrame testFrame = new TestInternalFrame(profiler, FrameName.TEST_FRAME, "test");
        int expectedX = 20;
        int expectedY = 30;
        int expectedWidth = 100;
        int expectedHeight = 100;
        testFrame.setBounds(expectedX, expectedY, expectedWidth, expectedHeight);
        try {
            testFrame.setIcon(true);
        } catch (PropertyVetoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        testFrame.saveProfile();

        TestInternalFrame loadedTestFrame = new TestInternalFrame(profiler,
            FrameName.TEST_FRAME, "newTest");
        loadedTestFrame.loadProfile();

        Assert.assertEquals(expectedX, loadedTestFrame.getX());
        Assert.assertEquals(expectedY, loadedTestFrame.getY());
        Assert.assertEquals(expectedWidth, loadedTestFrame.getWidth());
        Assert.assertEquals(expectedHeight, loadedTestFrame.getHeight());
        Assert.assertTrue(loadedTestFrame.isIcon());
    }

    @Test
    public void savingAndLoadClosedWorksCorrectly() {
        TestInternalFrame testFrame = new TestInternalFrame(profiler, FrameName.TEST_FRAME, "test");
        try {
            testFrame.setClosed(true);
        } catch (PropertyVetoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        testFrame.saveProfile();

        TestInternalFrame loadedTestFrame = new TestInternalFrame(profiler,
            FrameName.TEST_FRAME, "newTest");
        loadedTestFrame.loadProfile();
        Assert.assertTrue(loadedTestFrame.isClosed());
    }
}
