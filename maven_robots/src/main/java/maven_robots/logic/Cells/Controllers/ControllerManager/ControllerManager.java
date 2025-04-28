package maven_robots.logic.Cells.Controllers.ControllerManager;

import java.util.HashMap;

import maven_robots.logic.Cells.CellType;
import maven_robots.logic.Cells.Controllers.BaseCellController;
import maven_robots.logic.Cells.Controllers.ICellController;
import maven_robots.logic.Cells.Controllers.PowerPointController;
import maven_robots.logic.Cells.Controllers.WallController;

public class ControllerManager implements IControllerManager {

    private final HashMap<CellType, ICellController> controllers;
        

    public ControllerManager()
    {
        controllers = new HashMap<CellType, ICellController>();
        controllers.put(CellType.CELL, new BaseCellController());
        controllers.put(CellType.POWER_POINT, new PowerPointController());
        controllers.put(CellType.WALL, new WallController());
    }

    @Override
    public ICellController getCellController(CellType cellType) {
        return controllers.get(cellType);
    }

}
