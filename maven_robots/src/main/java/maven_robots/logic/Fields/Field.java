package maven_robots.logic.Fields;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

import maven_robots.logic.ChargeColor;
import maven_robots.logic.Coord;
import maven_robots.logic.Direction;
import maven_robots.logic.Cells.CellType;
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

    private List<FieldObserver> observers = new ArrayList<>();

    public Field(ICell[][] field, IRobot robot, IControllerManager controllerManager) {
        height = field.length;
        width = field[0].length;
        this.field = field;
        this.robot = robot;
        this.controllerManager = controllerManager;
        int ppCount = countColors();
        cabelStorage = new CabelStorage(ppCount);
    }

    private int countColors() {
        int ppCount = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (field[y][x].getType() == CellType.POWER_POINT) {
                    ppCount += 1;
                }
                    
            }
        }
        return ppCount / 2;
    }

    public ICell[][] getField() {
        return field;
    }

    public IRobot getRobot() {
        return robot;
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
        if (!cellController.isRobotAllowedToEnter(robot, nextCell, nextPos)) {
            return false;
        }
            

        if (robot.isMovingBackward(nextPos)) {
            Coord robotPos = robot.getCoord();
            field[robotPos.y][robotPos.x].reset();
        }

        robot.move(dir);
        if (!isPowerPointConnected(nextCell)) {
            cellController.moveRobotOn(robot, nextCell, nextPos);
        }
        if (robot.getIsCableFinished()) {
            saveCabel();
        }

        notifyObservers();
        return true;
    }

    public void resetCurrentCabel() {
        Coord[] currentCabel = robot.getCurrentCabel();
        robot.resetCurrentCabel();
        for (Coord coord : currentCabel) {
            field[coord.y][coord.x].reset();
        }
        actualizeState(robot);
    }

    private void actualizeState(IRobot robot) {
        Coord robotPos = robot.getCoord();
        ICell robotCell = field[robotPos.y][robotPos.x];
        ICellController cellController = controllerManager.getCellController(robotCell.getType());
        if (!isPowerPointConnected(robotCell)) {
            cellController.moveRobotOn(robot, robotCell, robotPos);
        }
        notifyObservers();
    }

    public void resertLastCabel() {
        try {
            Coord[] lastCabel = cabelStorage.resetLastCable();
            for (Coord coord : lastCabel) {
                field[coord.y][coord.x].reset();
            }
            notifyObservers();
        } catch (EmptyStackException e) {
        }
    }

    public void resetAllCreatedCabels() {
        for (int i = 0; i < cabelStorage.getCabels().size(); i++) {
            resertLastCabel();
        }
    }
    
    public boolean isGameFinished() {
        if (!cabelStorage.isAllCabelsCreated()) {
            return false;
        }
            
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (field[y][x].getColor() == ChargeColor.EMPTY) {
                    return false;
                }
                    
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

    private Boolean isPowerPointConnected(ICell cell) {
        return cell.getType() == CellType.POWER_POINT
            && cabelStorage.getCabels().containsKey(cell.getColor());
    }

    public void addObserver(FieldObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(FieldObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (FieldObserver observer : observers) {
            observer.onFieldChanged();
        }
    }
}

/*
 * баги:
 *  - через точки можно ходить, но нельзя брать цвет
 *  - ресет на пп заново выдаёт цвет
 */