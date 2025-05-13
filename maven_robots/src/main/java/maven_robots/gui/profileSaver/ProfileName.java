package maven_robots.gui.profileSaver;

public enum ProfileName {
    DefaultWindow("default"),
    GameWindow("gameWindow"),
    LogWindow("logWindow"),
    TextArea("textArea"),
    MainFrame("mainFrame");

    private final String profileName;

    ProfileName(String parameters) {
        this.profileName = parameters;
    }

    public String getProfileName() {
        return profileName;
    }
}
