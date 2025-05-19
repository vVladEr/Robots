package maven_robots.data.profiler.enums;

public enum FrameName {
    DefaultWindow("default"),
    GAME_WINDOW("gameWindow"),
    LOG_WINDOW("logWindow"),
    TEXT_AREA("textArea"),
    MAIN_FRAME("mainFrame"),
    MENU_BAR_FRAME("menuBarFrame"),
    PROFILE_PICKER_DIALOG("profilePickerDialog");

    private final String frameName;

    FrameName(String parameters) {
        this.frameName = parameters;
    }

    public String getFrameName() {
        return frameName;
    }
}
