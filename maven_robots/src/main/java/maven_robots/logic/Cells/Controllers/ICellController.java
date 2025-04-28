package maven_robots.logic.Cells.Controllers;

import maven_robots.logic.Coord;
import maven_robots.logic.Cells.ICell;
import maven_robots.logic.Robots.IRobot;

public interface ICellController {

    public Boolean isRobotAllowedToEnter(IRobot robot, ICell cell, Coord cellCoord);

    public void moveRobotOn(IRobot robot, ICell cell, Coord cellCoord);
}
