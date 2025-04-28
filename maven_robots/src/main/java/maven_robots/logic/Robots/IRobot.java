package maven_robots.logic.Robots;

import java.util.Optional;

import maven_robots.logic.ChargeColor;
import maven_robots.logic.Coord;
import maven_robots.logic.Direction;
import maven_robots.logic.Cells.ICell;

public interface IRobot {

    public Coord getCoord();

    public Optional<Coord> getLastTakenPowerPoint();

    public ChargeColor getChargeColor();

    public void setChargeColor(ChargeColor chargeColor);

    public void move(Direction dir);

    public void startCabel(ICell cell, Coord powerPointCoord);

    public void resetRobotRoute();

    public Coord[] getCurrentRoute();

    public Optional<Coord> getPreviousCabelPos();
}
