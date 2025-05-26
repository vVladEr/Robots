package maven_robots.gui.frames.baseFrames;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import maven_robots.data.parameters.Parameters;
import maven_robots.gui.mainFrame.ClosingListeners;
import maven_robots.data.profiler.IProfiler;

public class BaseJInternalFrame extends JInternalFrame implements IProfileProcessor {
    private final IProfiler profiler;
    private final FrameState frameState;

    public BaseJInternalFrame(
            IProfiler profiler,
            String frameName,
            final String title,
            final boolean resizable,
            final boolean closable,
            final boolean maximizable,
            final boolean iconable) {
        super(title, resizable, closable, maximizable, iconable);

        this.profiler = profiler;
        frameState = new FrameState(frameName);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addInternalFrameListener(ClosingListeners.getFrameClosingListener());

        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameIconified(InternalFrameEvent e) {
                frameState.setIsIcon(true);
            }

            @Override
            public void internalFrameDeiconified(InternalFrameEvent e) {
                frameState.setIsIcon(false);
            }

            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                frameState.setIsClosed(true);
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (!isMaximum) {
                    frameState.setIsMaximized(false);
                    if (!isIcon) {
                        updateFrameParameters();
                    }
                } else {
                    frameState.setIsMaximized(true);
                }
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                if (!isMaximum) {
                    updateFrameParameters();
                }
            }
        });
    }

    @Override
    public void loadFrameState() {
        profiler.loadFrameState(frameState);

        try {
            boolean isMaximized = frameState.getIsMaximized();
            boolean isClosed = frameState.getIsClosed();
            boolean isIcon = frameState.getIsIcon();

            Parameters parameters = frameState.getParameters();

            if (isClosed) {
                setClosed(true);
                return;
            }

            setBounds(
                parameters.getX(),
                parameters.getY(),
                parameters.getWidth(),
                parameters.getHeight()
            );

            if (isMaximized) {
                setMaximum(true);
            }
            if (isIcon) {
                setIcon(true);
            }
        } catch (PropertyVetoException e) {
            System.err.println("Ошибка при применении свойств: " + e.getMessage());
        }
    }

    @Override
    public void saveFrameState() {
        profiler.saveFrameState(frameState);
    }

    private void updateFrameParameters() {
        frameState.setParameters(new Parameters(getX(), getY(), getWidth(), getHeight()));
    }
}