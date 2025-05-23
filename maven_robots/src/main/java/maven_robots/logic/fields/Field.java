package maven_robots.logic.fields;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

import maven_robots.logic.ChargeColor;
import maven_robots.logic.Coord;
import maven_robots.logic.Direction;
import maven_robots.logic.cells.CellType;
import maven_robots.logic.cells.ICell;
import maven_robots.logic.cells.controllers.ICellController;
import maven_robots.logic.cells.controllers.controllerManager.IControllerManager;
import maven_robots.logic.fields.cabels.CabelPart;
import maven_robots.logic.fields.cabels.CabelStorage;
import maven_robots.logic.fields.cabels.ICabelStorage;
import maven_robots.logic.fields.cabels.impulses.IImpulseManager;
import maven_robots.logic.fields.cabels.impulses.ImpulseManager;
import maven_robots.logic.robots.IRobot;

public class Field {

    private final ICell[][] field;
    private final IRobot robot;
    private final int height;
    private final int width;
    private final IControllerManager controllerManager;
    private final ICabelStorage cabelStorage;
    private final IImpulseManager impulseManager;

    private List<FieldObserver> observers = new ArrayList<>();

    public Field(ICell[][] field, IRobot robot,
        IControllerManager controllerManager, int maxChargeCapacity) {
        height = field.length;
        width = field[0].length;
        this.field = field;
        this.robot = robot;
        this.controllerManager = controllerManager;
        int ppCount = countColors();
        cabelStorage = new CabelStorage(ppCount);
        impulseManager = new ImpulseManager(cabelStorage, maxChargeCapacity);
    }

    public Field(ICell[][] field, IRobot robot, IControllerManager controllerManager) {
        this(field, robot, controllerManager, 1000);
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

    public IImpulseManager getImpulseManager() {
        return impulseManager;
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
        CabelPart[] currentCabel = robot.getCurrentCabel();
        robot.resetCurrentCabel();
        for (CabelPart coord : currentCabel) {
            field[coord.getCoord().y][coord.getCoord().x].reset();
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
            CabelPart[] lastCabel = cabelStorage.resetLastCable();

            for (CabelPart part : lastCabel) {
                field[part.getCoord().y][part.getCoord().x].reset();
            }

            Coord startPointCoord = lastCabel[0].getCoord();
            ChargeColor cabelColor = field[startPointCoord.y][startPointCoord.x].getColor();
            impulseManager.removeImpulse(cabelColor);
            actualizeState(robot);
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
        CabelPart[] cabelRoute = robot.getCurrentCabel();
        ChargeColor cabelColour = robot.getChargeColor();
        robot.resetCurrentCabel();
        cabelStorage.saveCabel(cabelRoute, cabelColour);
        impulseManager.addImpulse(cabelColour);
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