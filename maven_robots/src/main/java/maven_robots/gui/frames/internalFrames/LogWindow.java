package maven_robots.gui.frames.internalFrames;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import maven_robots.data.profiler.IProfiler;
import maven_robots.data.profiler.enums.FrameName;
import maven_robots.gui.frames.baseFrames.BaseJInternalFrame;
import maven_robots.data.parameters.Parameters;
import maven_robots.localization.ILocalizable;
import maven_robots.localization.LocalizationManager;
import maven_robots.log.LogChangeListener;
import maven_robots.log.LogEntry;
import maven_robots.log.LogWindowSource;

public class LogWindow extends BaseJInternalFrame implements LogChangeListener, ILocalizable {
    private final LogWindowSource m_logSource;
    private final JTextArea m_logContent;

    private final InternalFrameAdapter logRemoveFrameAdapter = new InternalFrameAdapter() {
        @Override
        public void internalFrameClosed(InternalFrameEvent e) {
            m_logSource.unregisterListener((LogChangeListener) e.getInternalFrame());
        }
    };

    public LogWindow(
            IProfiler profiler,
            JTextArea textArea,
            LogWindowSource logSource,
            Parameters parameters
    ) {
        super(
            profiler,
            FrameName.LOG_WINDOW,
            LocalizationManager.getStringByName("log.title"),
            true,
            true,
            true,
            true
        );

        m_logSource = logSource;
        m_logSource.registerListener(this);
        m_logContent = textArea;

        setBounds(
            parameters.getX(),
            parameters.getY(),
            parameters.getWidth(),
            parameters.getHeight()
        );
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        addInternalFrameListener(logRemoveFrameAdapter);

        updateLogContent();
    }

    private void updateLogContent() {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : m_logSource.all()) {
            content.append(entry.getMessage()).append("\n");
        }
        m_logContent.setText(content.toString());
        m_logContent.invalidate();
    }
    
    @Override
    public void onLogChanged() {
        EventQueue.invokeLater(this::updateLogContent);
    }

    @Override
    public void changeLanguage() {
        this.setTitle(LocalizationManager.getStringByName("log.title"));
        this.invalidate();
    }
}
