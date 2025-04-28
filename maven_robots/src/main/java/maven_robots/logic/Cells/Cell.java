package maven_robots.logic.Cells;

import maven_robots.logic.ChargeColor;

public class Cell implements ICell {

    private final CellType cellType;

    private ChargeColor color;

    private final ChargeColor baseColor;

    public Cell(CellType cellType, ChargeColor color)
    {
        this.cellType = cellType;
        this.color = color;
        baseColor = color;
    }

    @Override
    public CellType getType() {
        return cellType;
    }

    @Override
    public ChargeColor getColor() {
        return color;
    }

    @Override
    public void setColor(ChargeColor color) {
        if (CellType.isColorChangable(cellType))
        {
            this.color = color;
            return;
        }
    }

    public void reset() {
        color = baseColor;
    }

}
