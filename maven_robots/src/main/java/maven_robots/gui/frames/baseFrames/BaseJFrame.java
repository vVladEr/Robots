package maven_robots.gui.frames.baseFrames;

import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedHashMap;

import javax.swing.JFrame;

import maven_robots.data.parameters.Parameters;
import maven_robots.data.profiler.enums.FrameName;
import maven_robots.gui.frames.internalFrames.GameWindow;
import maven_robots.gui.frames.internalFrames.LogWindow;
import maven_robots.gui.mainFrame.ClosingListeners;
import maven_robots.data.profiler.IProfiler;
import maven_robots.gui.mainFrame.MenuBarFrame;
import maven_robots.localization.ILocalizable;

public class BaseJFrame extends JFrame implements IProfileProcessor, ILocalizable {
    private final IProfiler profiler;
    private final FrameState frameState;
    protected final LinkedHashMap<FrameName, Component> components;
    private boolean isApplyingState = false;

    public BaseJFrame(IProfiler profiler, String frameName) {
        this.profiler = profiler;
        frameState = new FrameState(frameName);
        components = new LinkedHashMap<>();

        addWindowListener(ClosingListeners.getFramewindowClosingAdapter());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (isApplyingState) return;

                int state = getExtendedState();
                if (state == JFrame.NORMAL) {
                    frameState.setIsMaximized(false);
                    frameState.setIsIcon(false);
                    updateFrameParameters();
                } else if (state == JFrame.MAXIMIZED_BOTH) {
                    frameState.setIsMaximized(true);
                    frameState.setIsIcon(false);
                } else if (state == JFrame.ICONIFIED) {
                    frameState.setIsMaximized(false);
                    frameState.setIsIcon(true);
                }
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                if (isApplyingState) return;

                int state = getExtendedState();
                if (state == JFrame.NORMAL) {
                    frameState.setIsMaximized(false);
                    frameState.setIsIcon(false);
                    updateFrameParameters();
                } else if (state == JFrame.MAXIMIZED_BOTH) {
                    frameState.setIsMaximized(true);
                    frameState.setIsIcon(false);
                } else if (state == JFrame.ICONIFIED) {
                    frameState.setIsMaximized(false);
                    frameState.setIsIcon(true);
                }
            }

            @Override
            public void componentShown(ComponentEvent e) {
                if (profiler.isProfileExists()) {
                    for (Component comp : components.values()) {
                        if (comp instanceof BaseJInternalFrame) {
                            ((BaseJInternalFrame) comp).loadFrameState();
                        }
                    }
                    profiler.loadLanguage();
                    changeLanguage();
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowIconified(WindowEvent e) {
                if (isApplyingState) return;
                frameState.setIsIcon(true);
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                if (isApplyingState) return;
                frameState.setIsIcon(false);
            }

            @Override
            public void windowClosing(WindowEvent e) {
                profiler.saveLanguage();
                for (Component component : components.values()) {
                    if (component instanceof BaseJFrame) {
                        ((BaseJFrame) component).saveFrameState();
                    } else if (component instanceof BaseJInternalFrame) {
                        ((BaseJInternalFrame) component).saveFrameState();
                    }
                }
            }
        });
    }

    @Override
    public void changeLanguage() {
        ((LogWindow) components.get(FrameName.LOG_WINDOW)).changeLanguage();
        ((GameWindow) components.get(FrameName.GAME_WINDOW)).changeLanguage();
        ((MenuBarFrame) components.get(FrameName.MENU_BAR_FRAME)).changeLanguage();

        this.invalidate();
    }

    @Override
    public void loadFrameState() {
        profiler.loadFrameState(frameState);
        isApplyingState = true;
        boolean isMaximized = frameState.getIsMaximized();
        boolean isIcon = frameState.getIsIcon();

        Parameters parameters = frameState.getParameters();

        setBounds(
                parameters.getX(),
                parameters.getY(),
                parameters.getWidth(),
                parameters.getHeight()
        );

        if (isMaximized) {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        if (isIcon) {
            setExtendedState(JFrame.ICONIFIED);
        }

        isApplyingState = false;
    }

    @Override
    public void saveFrameState() {
        profiler.saveFrameState(frameState);
    }

    private void updateFrameParameters() {
        frameState.setParameters(new Parameters(getX(), getY(), getWidth(), getHeight()));
    }
}
