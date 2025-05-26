package helpers.testWindows;


import maven_robots.data.profiler.IProfiler;
import maven_robots.data.profiler.enums.FrameName;
import maven_robots.gui.frames.baseFrames.BaseJInternalFrame;

public class TestJInternalFrame extends BaseJInternalFrame {

    public TestJInternalFrame(IProfiler profiler, String titleBundleName, boolean resizable, boolean closable, boolean maximizable,
                              boolean iconable) {
        super(profiler,
            FrameName.TestWindow.getFrameName(),
            titleBundleName, resizable, closable, maximizable, iconable);
        setVisible(true);
    }

}
