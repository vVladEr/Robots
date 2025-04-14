package maven_robots.logic;

public enum ChargeColor {
    EMPTY ("null"),
    RED ("red"),
    GREEN ("green"),
    BLUE ("blue"),
    WHITE ("white");

    private String colorCode;

    private ChargeColor(String code)
    {
        colorCode = code;
    }

    public String getColorCode()
    {
        return this.colorCode;
    }
}
