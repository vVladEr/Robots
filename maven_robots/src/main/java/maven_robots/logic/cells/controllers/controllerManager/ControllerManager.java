package maven_robots.logic.cells.controllers.controllerManager;

import java.util.HashMap;

import maven_robots.logic.cells.CellType;
import maven_robots.logic.cells.controllers.BaseCellController;
import maven_robots.logic.cells.controllers.ICellController;
import maven_robots.logic.cells.controllers.PowerPointController;
import maven_robots.logic.cells.controllers.WallController;

public class ControllerManager implements IControllerManager {

    private final HashMap<CellType, ICellController> controllers;
        

    public ControllerManager() {
        controllers = new HashMap<>();
        controllers.put(CellType.CELL, new BaseCellController());
        controllers.put(CellType.POWER_POINT, new PowerPointController());
        controllers.put(CellType.WALL, new WallController());
    }

    @Override
    public ICellController getCellController(CellType cellType) {
        return controllers.get(cellType);
    }

}
