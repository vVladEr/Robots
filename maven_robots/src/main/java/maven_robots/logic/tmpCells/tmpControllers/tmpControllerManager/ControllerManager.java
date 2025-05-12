package maven_robots.logic.tmpCells.tmpControllers.tmpControllerManager;

import java.util.HashMap;

import maven_robots.logic.tmpCells.CellType;
import maven_robots.logic.tmpCells.tmpControllers.BaseCellController;
import maven_robots.logic.tmpCells.tmpControllers.ICellController;
import maven_robots.logic.tmpCells.tmpControllers.PowerPointController;
import maven_robots.logic.tmpCells.tmpControllers.WallController;

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
