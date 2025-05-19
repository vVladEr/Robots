package maven_robots.gui.frames.baseFrames;

import javax.swing.JInternalFrame;

import maven_robots.gui.mainFrame.ClosingListeners;
import maven_robots.data.profiler.IProfiler;
import maven_robots.data.profiler.enums.FrameName;

public class BaseJInternalFrame extends JInternalFrame implements IProfileProcessor {
    private final IProfiler profiler;
    private final FrameName frameName;

    public BaseJInternalFrame(
            IProfiler profiler,
            FrameName frameName,
            final String title,
            final boolean resizable,
            final boolean closable,
            final boolean maximizable,
            final boolean iconable) {
        super(title, resizable, closable, maximizable, iconable);

        this.profiler = profiler;
        this.frameName = frameName;

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addInternalFrameListener(ClosingListeners.getFrameClosingListener());
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
