package maven_robots.logic.cells.controllers;

import maven_robots.logic.Coord;
import maven_robots.logic.cells.ICell;
import maven_robots.logic.robots.IRobot;

public interface ICellController {

    public Boolean isRobotAllowedToEnter(IRobot robot, ICell cell, Coord cellCoord);

    public void moveRobotOn(IRobot robot, ICell cell, Coord cellCoord);
}
