package tmpHelpers.tmpTestWindows;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.JInternalFrame;

import maven_robots.log.LogChangeListener;
import maven_robots.log.LogWindowSource;

public class TestLogWindow extends JInternalFrame implements LogChangeListener
{
    private final LogWindowSource m_logSource;
    private final InternalFrameAdapter logRemoveFrameAdapter = new InternalFrameAdapter() {
        @Override
        public void internalFrameClosed(InternalFrameEvent e)
        {
            m_logSource.unregisterListener((LogChangeListener)e.getInternalFrame());
        }
    };

    public TestLogWindow(LogWindowSource logSource) {
        super("test.title", true, true, true, true);
        m_logSource = logSource;
        m_logSource.registerListener(this);
        addInternalFrameListener(logRemoveFrameAdapter);
    }
    
    @Override
    public void onLogChanged()
    {
        
    }
}
