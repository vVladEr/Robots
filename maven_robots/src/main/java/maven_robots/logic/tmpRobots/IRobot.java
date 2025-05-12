package maven_robots.logic.tmpRobots;

import java.util.Optional;

import maven_robots.logic.ChargeColor;
import maven_robots.logic.Coord;
import maven_robots.logic.Direction;
import maven_robots.logic.tmpCells.ICell;

public interface IRobot {

    public Coord getCoord();

    public Optional<Coord> getLastTakenPowerPoint();

    public ChargeColor getChargeColor();

    public void setChargeColor(ChargeColor chargeColor);

    public void move(Direction dir);

    public void startCabel(ICell cell, Coord powerPointCoord);

    public void finishCabel();

    public boolean getIsCableFinished();

    public void resetCurrentCabel();

    public Coord[] getCurrentCabel();

    public Optional<Coord> getPreviousCabelPos();

    public Boolean isMovingBackward(Coord newPos);
}
