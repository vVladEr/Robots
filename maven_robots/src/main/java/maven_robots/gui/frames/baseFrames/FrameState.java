package maven_robots.gui.frames.baseFrames;

import maven_robots.data.parameters.Parameters;

public class FrameState {
    private final String frameName;

    private Parameters parameters;

    private Boolean isMaximized;

    private Boolean isClosed;

    private Boolean isIcon;

    public FrameState(String frameName) {
        this.frameName = frameName;

        parameters = new Parameters(0, 0, 0, 0);
        isMaximized = false;
        isIcon = false;
        isClosed = false;
    }

    public String getFrameName() {
        return frameName;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public Boolean getIsIcon() {
        return isIcon;
    }

    public Boolean getIsMaximized() {
        return isMaximized;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    public void setIsIcon(Boolean isIcon) {
        this.isIcon = isIcon;
    }

    public void setIsMaximized(Boolean isMaximized) {
        this.isMaximized = isMaximized;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }
}
