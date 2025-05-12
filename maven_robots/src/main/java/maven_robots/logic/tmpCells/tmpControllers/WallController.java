package maven_robots.logic.tmpCells.tmpControllers;

import maven_robots.logic.Coord;
import maven_robots.logic.tmpCells.ICell;
import maven_robots.logic.tmpRobots.IRobot;

public class WallController implements ICellController {

    @Override
    public Boolean isRobotAllowedToEnter(IRobot robot, ICell cell, Coord cellCoord) {
        return false;
    }

    @Override
    public void moveRobotOn(IRobot robot, ICell cell, Coord cellCoord) {
        return;
    }

}
