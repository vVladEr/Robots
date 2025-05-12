package maven_robots.logic.tmpCells;

public enum CellType {
    CELL,
    POWER_POINT,
    WALL;

    public static Boolean isColorChangable(CellType cellType) {
        return cellType == CELL;
    }
}
