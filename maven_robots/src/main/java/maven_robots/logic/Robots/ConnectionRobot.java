package maven_robots.logic.Robots;

import maven_robots.logic.ChargeColor;
import maven_robots.logic.Direction;
import maven_robots.logic.Coord;

public class ConnectionRobot implements IRobot {

    private Coord pos;
    private ChargeColor chargeColor;

    public ConnectionRobot(Coord pos)
    {
        this.pos = pos;
        chargeColor = ChargeColor.EMPTY;
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
        Coord vec = Direction.getDir(dir);
        pos = new Coord(vec.x + pos.x, vec.y + pos.y);
    }
}
