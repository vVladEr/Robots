package maven_robots.logic.Fields;

import maven_robots.logic.ChargeColor;
import maven_robots.logic.Coord;
import maven_robots.logic.Direction;
import maven_robots.logic.Cells.ICell;
import maven_robots.logic.Cells.Controllers.ICellController;
import maven_robots.logic.Cells.Controllers.ControllerManager.IControllerManager;
import maven_robots.logic.Robots.IRobot;

public class Field {

    private final ICell[][] field;
    private final IRobot robot;
    private final int height;
    private final int width;
    private final IControllerManager controllerManager;
    private final ICabelStorage cabelStorage;

    public Field(ICell[][] field, IRobot robot, IControllerManager controllerManager) {
        height = field.length;
        width = field[0].length;
        this.field = field;
        this.robot = robot;
        this.controllerManager = controllerManager;
        cabelStorage = new CabelStorage();
    }

    public ICabelStorage getCabelStorage()
    {
        return cabelStorage;
    }

    public void moveRobot(Direction dir) {
        Coord nextPos = dir.getPosInDirection(robot.getCoord());
        if (!isCellInsideBorders(nextPos)) {
            return;
        }
        ICell nextCell = field[nextPos.y][nextPos.x];
        ICellController cellController = controllerManager.getCellController(nextCell.getType());
        if (!cellController.isRobotAllowedToEnter(robot, nextCell, nextPos))
            return; // добавить результат операции
        if (robot.isMovingBackward(nextPos)) {
            Coord robotPos = robot.getCoord();
            field[robotPos.y][robotPos.x].Reset();
        }
        robot.move(dir);
        cellController.moveRobotOn(robot, nextCell, nextPos);
        if (robot.getIsCableFinished()) {
            saveCabel();
        }
    }

    private void saveCabel() {
        Coord[] cabelRoute = robot.getCurrentRoute();
        ChargeColor cabelColour = robot.getChargeColor();
        robot.resetRobotRoute();
        cabelStorage.saveCabel(cabelRoute, cabelColour);
    }

    private Boolean isCellInsideBorders(Coord pos) {
        return pos.x < width && pos.x >= 0 && pos.y < height && pos.y >= 0;
    }
}
