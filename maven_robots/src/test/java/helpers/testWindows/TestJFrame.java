package helpers.testWindows;

import maven_robots.data.profiler.IProfiler;
import maven_robots.data.profiler.enums.FrameName;
import maven_robots.gui.frames.baseFrames.BaseJFrame;
import maven_robots.localization.ILocalizable;

;

public class TestJFrame extends BaseJFrame implements ILocalizable {

    public TestJFrame(IProfiler profiler) {
        super(profiler, FrameName.DefaultWindow);
        setVisible(true);
    }
    @Override
    public void changeLanguage() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changeLanguage'");
    }
}
