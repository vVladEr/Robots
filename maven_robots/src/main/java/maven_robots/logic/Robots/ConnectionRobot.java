package maven_robots.logic.Robots;

import java.util.EmptyStackException;
import java.util.Optional;
import java.util.Stack;

import maven_robots.logic.ChargeColor;
import maven_robots.logic.Direction;
import maven_robots.logic.Cells.ICell;
import maven_robots.logic.Coord;

public class ConnectionRobot implements IRobot {

    private Coord pos;
    private ChargeColor chargeColor;
    private Stack<Coord> prevPositions;
    private Optional<Coord> lastTakenPowerPoint;

    public ConnectionRobot(Coord pos)
    {
        this.pos = pos;
        chargeColor = ChargeColor.EMPTY;
        prevPositions = new Stack<Coord>();
        lastTakenPowerPoint = Optional.empty();
    }

    public Coord getCoord() {
        return pos;
    }

    public Optional<Coord> getLastTakenPowerPoint() {
        return lastTakenPowerPoint;
    }

    public ChargeColor getChargeColor() {
        return chargeColor;
    }

    public void setChargeColor(ChargeColor chargeColor) {
        this.chargeColor = chargeColor;
    }

    public void move(Direction dir) {
        Coord nextpos = dir.getPosInDirection(pos);
        if (lastTakenPowerPoint.isPresent()
            && !isRobotMoveBackward(nextpos)) {
            prevPositions.add(pos);
        }

        pos = nextpos;
    }

    public void startCabel(ICell cell, Coord powerPointCoord) {
        lastTakenPowerPoint = Optional.of(powerPointCoord);
        setChargeColor(cell.getColor());
    }

    public Coord[] getCurrentRoute() {
        return prevPositions.toArray(new Coord [0]);
    }

    public void resetRobotRoute() {
        prevPositions.clear();
        setChargeColor(ChargeColor.EMPTY);
        lastTakenPowerPoint = Optional.empty();
    }

    public Optional<Coord> getPreviousCabelPos()
    {
        try {
            return Optional.of(prevPositions.peek());
        } catch (EmptyStackException  e) {
            return Optional.empty();
        }
    }

    private Boolean isRobotMoveBackward(Coord newPos)
    {
        Optional<Coord> prevPos = getPreviousCabelPos();
        return prevPos.isPresent() && prevPos.get() == newPos;
    }


}

/*
 * r - робот
 * A - pp Red
 * a - red
 * 
 * AaaA
 * A    a   a   A#123121123
 * E    
 */