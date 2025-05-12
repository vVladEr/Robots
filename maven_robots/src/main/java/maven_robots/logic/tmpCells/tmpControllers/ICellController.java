package maven_robots.logic.tmpCells.tmpControllers;

import maven_robots.logic.Coord;
import maven_robots.logic.tmpCells.ICell;
import maven_robots.logic.tmpRobots.IRobot;

public interface ICellController {

    public Boolean isRobotAllowedToEnter(IRobot robot, ICell cell, Coord cellCoord);

    public void moveRobotOn(IRobot robot, ICell cell, Coord cellCoord);
}
