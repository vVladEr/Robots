package maven_robots.data.parameters;

public enum DefaultParameters {
    GameWindowDefaultParameters("500;100;500;500"),
    LogWindowDefaultParameters("10;10;300;800"),
    MainFrameDefaultParameters("-9;-9;1938;1048");

    private final String parameters;

    DefaultParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getParameters() {
        return parameters;
    }
}