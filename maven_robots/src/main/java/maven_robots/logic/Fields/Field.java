package maven_robots.logic.Fields;

import maven_robots.logic.Coord;
import maven_robots.logic.Direction;
import maven_robots.logic.Cells.ICell;
import maven_robots.logic.Robots.IRobot;

public class Field {

    private final ICell[][] field;
    private final IRobot robot;
    private final int height;
    private final int width;

    public Field(ICell[][] field, IRobot robot) {
        height = field.length;
        width = field[0].length;
        this.field = field;
        this.robot = robot;
    }

    public void moveRobot(Direction dir) {
        Coord dirVec = Direction.getDir(dir);
        Coord robotCoord = robot.getCoord();
        Coord nextPos = new Coord(robotCoord.x + dirVec.x, robotCoord.y + dirVec.y);
        ICell nextCell = field[nextPos.y][nextPos.x];
        if (!isCellInsideBorders(nextPos) 
            || !nextCell.isRobotAllowedToEnter(robot))
            return;
        robot.move(dir);
        nextCell.moveRobotOn(robot);
    }

    private Boolean isCellInsideBorders(Coord pos) {
        return pos.x < width && pos.x >= 0 && pos.y < height && pos.y >= 0;
    }
}
