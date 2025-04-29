package maven_robots.logic.Robots;

import java.util.EmptyStackException;
import java.util.Optional;
import java.util.Stack;

import maven_robots.logic.ChargeColor;
import maven_robots.logic.Coord;
import maven_robots.logic.Direction;
import maven_robots.logic.Cells.ICell;

public class ConnectionRobot implements IRobot {

    private Coord pos;
    private ChargeColor chargeColor;
    private Stack<Coord> prevPositions;

    private Optional<Coord> lastTakenPowerPoint;
    private boolean isCableFinished = false;

    public ConnectionRobot(Coord pos)
    {
        this.pos = pos;
        chargeColor = ChargeColor.EMPTY;
        prevPositions = new Stack<>();
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
        Coord nextPos = dir.getPosInDirection(pos);
        updateStack(nextPos);
        pos = nextPos;
    }

    private void updateStack(Coord nextPos) {
        if (!lastTakenPowerPoint.isPresent()) 
            return;

        if (isMovingBackward(nextPos)) {
            prevPositions.pop();
        }
        else {
            prevPositions.add(pos);
        } 

    }

    public void startCabel(ICell cell, Coord powerPointCoord) {
        lastTakenPowerPoint = Optional.of(powerPointCoord);
        setChargeColor(cell.getColor());
    }

    public void finishCabel() {
        isCableFinished = true;
    }

    public boolean getIsCableFinished() {
        return isCableFinished;
    }

    public Coord[] getCurrentCabel() {
        return prevPositions.toArray(new Coord [0]);
    }

    public void resetCurrentCabel() {
        prevPositions.clear();
        setChargeColor(ChargeColor.EMPTY);
        lastTakenPowerPoint = Optional.empty();
        isCableFinished = false;
    }

    public Optional<Coord> getPreviousCabelPos() {
        try {
            return Optional.of(prevPositions.peek());
        } catch (EmptyStackException  e) {
            return Optional.empty();
        }
    }

    public Boolean isMovingBackward(Coord newPos) {
        Optional<Coord> prevPos = getPreviousCabelPos();
        return prevPos.isPresent() && prevPos.get().equals(newPos);
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