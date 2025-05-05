package maven_robots.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import maven_robots.gui.BaseClasses.BaseInternalJFrame;
import maven_robots.log.LogChangeListener;
import maven_robots.log.LogEntry;
import maven_robots.log.LogWindowSource;

public class LogWindow extends BaseInternalJFrame implements LogChangeListener {
    private final LogWindowSource m_logSource;
    private final TextArea m_logContent;
    private final InternalFrameAdapter logRemoveFrameAdapter = new InternalFrameAdapter() {
        @Override
        public void internalFrameClosed(InternalFrameEvent e) {
            m_logSource.unregisterListener((LogChangeListener) e.getInternalFrame());
        }
    };

    public LogWindow(LogWindowSource logSource) {
        super("log.title", true, true, true, true);
        m_logSource = logSource;
        m_logSource.registerListener(this);
        m_logContent = new TextArea("");
        m_logContent.setSize(200, 500);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        addInternalFrameListener(logRemoveFrameAdapter);
        pack();
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
}
