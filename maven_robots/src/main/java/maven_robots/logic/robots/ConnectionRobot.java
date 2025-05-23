package maven_robots.logic.robots;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Optional;
import java.util.Stack;

import maven_robots.logic.ChargeColor;
import maven_robots.logic.Coord;
import maven_robots.logic.Direction;
import maven_robots.logic.cells.ICell;
import maven_robots.logic.fields.cabels.CabelPart;

public class ConnectionRobot implements IRobot {

    private Coord pos;
    private ChargeColor chargeColor;
    private Stack<CabelPart> prevPositions;

    private Optional<Coord> lastTakenPowerPoint;
    private boolean isCableFinished = false;

    public ConnectionRobot(Coord pos) {
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
        if (!lastTakenPowerPoint.isPresent()) {
            return;
        }
        
        if (isMovingBackward(nextPos)) {
            prevPositions.pop();
        } else {
            Optional<CabelPart> curTop = getPreviousCabelPos();
            if (curTop.isPresent()) {
                Coord diff = Coord.getDiff(pos, curTop.get().getCoord());
                Direction dir  = Direction.toDirection(diff);
                curTop.get().setTo(dir);
                prevPositions.add(new CabelPart(pos, dir.getOpposite()));
            } else {
                prevPositions.add(new CabelPart(pos));
            }
            
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

    public CabelPart[] getCurrentCabel() {
        ArrayList<CabelPart> cabel = new ArrayList<CabelPart>(prevPositions);
        if (lastTakenPowerPoint.isPresent()) {
            if (cabel.size() == 0) {
                cabel.add(new CabelPart(pos));
            } else {
                Coord prevPartCoord = cabel.get(cabel.size() - 1).getCoord();
                Coord diff = Coord.getDiff(pos, prevPartCoord);
                Direction dir  = Direction.toDirection(diff);
                cabel.get(cabel.size() - 1).setTo(dir);
                cabel.add(new CabelPart(pos, dir.getOpposite()));
            }
        }
        return cabel.toArray(new CabelPart[0]);
    }

    public void resetCurrentCabel() {
        prevPositions.clear();
        setChargeColor(ChargeColor.EMPTY);
        lastTakenPowerPoint = Optional.empty();
        isCableFinished = false;
    }

    public Optional<CabelPart> getPreviousCabelPos() {
        try {
            return Optional.of(prevPositions.peek());
        } catch (EmptyStackException  e) {
            return Optional.empty();
        }
    }

    public Boolean isMovingBackward(Coord newPos) {
        Optional<CabelPart> prevPos = getPreviousCabelPos();
        return prevPos.isPresent() && prevPos.get().getCoord().equals(newPos);
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