package maven_robots.logic.Cells.Controllers;

import java.util.Optional;

import maven_robots.logic.ChargeColor;
import maven_robots.logic.Coord;
import maven_robots.logic.Cells.ICell;
import maven_robots.logic.Robots.IRobot;

public class BaseCellController implements ICellController {

    @Override
    public Boolean isRobotAllowedToEnter(IRobot robot, ICell cell, Coord cellCoord) {
        if (cell.getColor() == ChargeColor.EMPTY 
            || robot.getChargeColor() == ChargeColor.EMPTY) {
                return true; 
        }
        Optional<Coord> prevCabelPos = robot.getPreviousCabelPos();
        return (robot.getChargeColor() == cell.getColor() 
                && prevCabelPos.isPresent()
                && prevCabelPos.get() == cellCoord);
    }

    @Override
    public void moveRobotOn(IRobot robot, ICell cell, Coord cellCoord) {
        if (robot.getChargeColor() != ChargeColor.EMPTY) {
            cell.setColor(robot.getChargeColor());
        }
    }
}
