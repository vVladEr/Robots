package maven_robots.logic.Cells.Controllers;

import maven_robots.logic.Coord;
import maven_robots.logic.Cells.ICell;
import maven_robots.logic.Robots.IRobot;

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
