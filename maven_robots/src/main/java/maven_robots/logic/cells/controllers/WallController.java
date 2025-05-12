package maven_robots.logic.cells.controllers;

import maven_robots.logic.Coord;
import maven_robots.logic.cells.ICell;
import maven_robots.logic.robots.IRobot;

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
