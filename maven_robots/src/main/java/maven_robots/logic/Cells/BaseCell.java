package maven_robots.logic.Cells;

import maven_robots.logic.ChargeColor;
import maven_robots.logic.Robots.IRobot;

public class BaseCell implements ICell {

    private ChargeColor color;

    @Override
    public Boolean isRobotAllowedToEnter(IRobot robot) {
        return color == ChargeColor.EMPTY || robot.getChargeColor() == ChargeColor.EMPTY;
    }

    @Override
    public void moveRobotOn(IRobot robot) {
        if (robot.getChargeColor() != ChargeColor.EMPTY) {
            color = robot.getChargeColor();
        }
    }


}
