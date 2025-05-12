package maven_robots.logic.tmpCells.tmpControllers;

import maven_robots.logic.ChargeColor;
import maven_robots.logic.Coord;
import maven_robots.logic.tmpCells.ICell;
import maven_robots.logic.tmpRobots.IRobot;

public class BaseCellController implements ICellController {

    @Override
    public Boolean isRobotAllowedToEnter(IRobot robot, ICell cell, Coord cellCoord) {
        if (cell.getColor() == ChargeColor.EMPTY 
            || robot.getChargeColor() == ChargeColor.EMPTY) {
            return true; 
        }
        return (robot.getChargeColor() == cell.getColor() 
                && robot.isMovingBackward(cellCoord));
    }

    @Override
    public void moveRobotOn(IRobot robot, ICell cell, Coord cellCoord) {
        if (robot.getChargeColor() != ChargeColor.EMPTY) {
            cell.setColor(robot.getChargeColor());
        }
    }
}
