package profileTests;

import javax.swing.JInternalFrame;

import maven_robots.data.profiler.IProfiler;
import maven_robots.data.profiler.enums.FrameName;
import maven_robots.gui.frames.baseFrames.IProfileProcessor;

public class TestFrame extends JInternalFrame  implements IProfileProcessor {

    private final IProfiler profiler;
    private final FrameName frameName;

    public TestFrame(
            IProfiler profiler,
            FrameName frameName,
            final String title) {
        super(title, true, true, true, true);

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


