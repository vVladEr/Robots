package maven_robots.logic.Cells;

import maven_robots.logic.Robots.IRobot;

public interface ICell {

    public Boolean isRobotAllowedToEnter(IRobot robot);

    public void moveRobotOn(IRobot robot);
}
