package maven_robots.logic.Robots;

import maven_robots.logic.ChargeColor;
import maven_robots.logic.Coord;
import maven_robots.logic.Direction;

public interface IRobot {

    public Coord getCoord();

    public ChargeColor getChargeColor();

    public void setChargeColor(ChargeColor chargeColor);

    public void move(Direction dir);
}
