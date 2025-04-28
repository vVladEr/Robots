package maven_robots.logic.Fields;

import java.util.EmptyStackException;

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

    public ICabelStorage getCabelStorage() {
        return cabelStorage;
    }

    public Boolean moveRobot(Direction dir) {
        Coord nextPos = dir.getPosInDirection(robot.getCoord());
        if (!isCellInsideBorders(nextPos)) {
            return false;
        }
        ICell nextCell = field[nextPos.y][nextPos.x];
        ICellController cellController = controllerManager.getCellController(nextCell.getType());
        if (!cellController.isRobotAllowedToEnter(robot, nextCell, nextPos))
            return false;
        if (robot.isMovingBackward(nextPos)) {
            Coord robotPos = robot.getCoord();
            field[robotPos.y][robotPos.x].reset();
        }
        robot.move(dir);
        cellController.moveRobotOn(robot, nextCell, nextPos);
        if (robot.getIsCableFinished()) {
            saveCabel();
        }
        return true;
    }

    public void resertCurrentCabel() {
        Coord[] currentCabel = robot.getCurrentCabel();
        robot.resetCurrentCabel();
        for (Coord coord : currentCabel) {
            field[coord.y][coord.x].reset();
        }
    }

    public void resertLastCabel() {
        try {
            Coord[] lastCabel = cabelStorage.resetLastCable();
            for (Coord coord : lastCabel) {
                field[coord.y][coord.x].reset();
            }
        }
        catch (EmptyStackException e) {

        }
    }

    public boolean isGameFinished() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (field[y][x].getColor() == ChargeColor.EMPTY)
                    return false;
            }
        }
        return true;
    }

    private void saveCabel() {
        Coord[] cabelRoute = robot.getCurrentCabel();
        ChargeColor cabelColour = robot.getChargeColor();
        robot.resetCurrentCabel();
        cabelStorage.saveCabel(cabelRoute, cabelColour);
    }

    private Boolean isCellInsideBorders(Coord pos) {
        return pos.x < width && pos.x >= 0 && pos.y < height && pos.y >= 0;
    }
}
