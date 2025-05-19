package profileTests;

import javax.swing.JFrame;

import maven_robots.data.profiler.IProfiler;
import maven_robots.data.profiler.enums.FrameName;
import maven_robots.gui.frames.baseFrames.IProfileProcessor;

public class TestFrame extends JFrame implements IProfileProcessor {

    private final IProfiler profiler;
    private final FrameName frameName;

    public TestFrame(
            IProfiler profiler,
            FrameName frameName) {

        this.profiler = profiler;
        this.frameName = frameName;
    }

    @Override
    public void loadProfile() {
        profiler.loadComponentToProfile(frameName.getFrameName(), this);
    }

    @Override
    public void saveProfile() {
        profiler.saveComponentToProfile(frameName.getFrameName(), this);
    }
}
