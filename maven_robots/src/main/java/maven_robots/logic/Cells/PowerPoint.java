package maven_robots.logic.Cells;

import maven_robots.logic.ChargeColor;
import maven_robots.logic.Robots.IRobot;

public class PowerPoint implements ICell {

    private final ChargeColor color;

    public PowerPoint(ChargeColor color){
        this.color = color;
    }

    @Override
    public Boolean isRobotAllowedToEnter(IRobot robot) {
        return robot.getChargeColor() == ChargeColor.EMPTY;
    }

    @Override
    public void moveRobotOn(IRobot robot) {
        robot.setChargeColor(color);
    }
}
