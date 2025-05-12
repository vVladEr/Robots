package maven_robots.logic.Cells;

import maven_robots.logic.ChargeColor;

public class Cell implements ICell {

    private final CellType cellType;

    private ChargeColor color;

    private final ChargeColor baseColor;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cellType == null) ? 0 : cellType.hashCode());
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cell other = (Cell) obj;
        if (cellType != other.cellType)
            return false;
        if (color != other.color)
            return false;
        return true;
    }

    public Cell(CellType cellType, ChargeColor color) {
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
        if (CellType.isColorChangable(cellType)) {
            this.color = color;
        }
    }

    public void reset() {
        color = baseColor;
    }

}
