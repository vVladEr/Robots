package maven_robots.logic;

import java.awt.Color;

public enum ChargeColor {
    EMPTY ("w", Color.WHITE),
    RED ("r", Color.RED),
    GREEN ("g", Color.GREEN),
    BLUE ("b", Color.BLUE),
    PURPLE ("p", new Color(128, 0, 128));

    private final String colorCode;
    private final Color awtColor;

    ChargeColor(String code, Color awtColor) {
        this.colorCode = code;
        this.awtColor = awtColor;
    }

    public String getColorCode() {
        return this.colorCode;
    }

    public Color getAwtColor() {
        return this.awtColor;
    }

    public static ChargeColor getChargeColorByCode(String colorCode) {
        for (ChargeColor color : ChargeColor.values()) {
            if (color.getColorCode().equals(colorCode)) {
                return color;
            }
        }
        return null;
    }

    public static Color getColorByChargeColor(ChargeColor chargeColor) {
        if (chargeColor != null) {
            return chargeColor.getAwtColor();
        }
        return null;
    }
}

