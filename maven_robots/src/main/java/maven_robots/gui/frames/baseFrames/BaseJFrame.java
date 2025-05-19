package maven_robots.gui.frames.baseFrames;

import javax.swing.JFrame;

import maven_robots.gui.mainFrame.ClosingListeners;
import maven_robots.data.profiler.IProfiler;
import maven_robots.data.profiler.enums.FrameName;

public class BaseJFrame extends JFrame implements IProfileProcessor {
    private final IProfiler profiler;
    private final FrameName frameName;

    public BaseJFrame(IProfiler profiler, FrameName frameName) {
        this.profiler = profiler;
        this.frameName = frameName;

        addWindowListener(ClosingListeners.getFramewindowClosingAdapter());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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
