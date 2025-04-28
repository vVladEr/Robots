package maven_robots.logic.Robots;

import java.util.Stack;

import maven_robots.logic.ChargeColor;
import maven_robots.logic.Direction;
import maven_robots.logic.Coord;

public class ConnectionRobot implements IRobot {

    private Coord pos;
    private ChargeColor chargeColor;
    private Stack<Coord> prevPositions;

    public ConnectionRobot(Coord pos)
    {
        this.pos = pos;
        chargeColor = ChargeColor.EMPTY;
        prevPositions = new Stack<Coord>();
    }

    public Coord getCoord() {
        return pos;
    }

    public ChargeColor getChargeColor() {
        return chargeColor;
    }

    public void setChargeColor(ChargeColor chargeColor) {
        this.chargeColor = chargeColor;
    }

    public void move(Direction dir) {
        pos = dir.getPosInDirection(pos);
    }

    public Coord[] resetRobotRoute() {
        // разбить на 2 метода
        Coord [] res = prevPositions.toArray(new Coord [0]);
        prevPositions.clear();
        setChargeColor(ChargeColor.EMPTY);
        return res;
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