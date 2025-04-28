package maven_robots.logic.Cells;

import maven_robots.logic.ChargeColor;

public interface ICell {

    public CellType getType();

    public ChargeColor getColor();

    public void setColor(ChargeColor color);

    public void reset();
}
