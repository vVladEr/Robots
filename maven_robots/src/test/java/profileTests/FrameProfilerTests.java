package profileTests;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import maven_robots.data.profiler.Profiler;
import maven_robots.data.profiler.enums.FrameName;
import maven_robots.data.profiler.strategies.JFrameStrategy;

public class FrameProfilerTests {

    private static Profiler profiler;
    
    @BeforeClass
    public static void setup() {
        profiler = new Profiler();
        profiler.addStrategy(TestFrame.class, new JFrameStrategy());
        profiler.setProfileName("test");
    }

    @Test
    public void savingAndLoadPositionWorksCorrectly() {
        TestFrame testFrame = new TestFrame(profiler, FrameName.TEST_FRAME);
        int expectedX = 20;
        int expectedY = 30;
        int expectedWidth = 100;
        int expectedHeight = 100;
        testFrame.setBounds(expectedX, expectedY, expectedWidth, expectedHeight);
        testFrame.saveProfile();

        TestFrame loadedTestFrame = new TestFrame(profiler, FrameName.TEST_FRAME);
        loadedTestFrame.loadProfile();

        Assert.assertEquals(expectedX, loadedTestFrame.getX());
        Assert.assertEquals(expectedY, loadedTestFrame.getY());
        Assert.assertEquals(expectedWidth, loadedTestFrame.getWidth());
        Assert.assertEquals(expectedHeight, loadedTestFrame.getHeight());
    }
}
