package maven_robots.logic.cells.controllers.controllerManager;

import maven_robots.logic.cells.CellType;
import maven_robots.logic.cells.controllers.ICellController;

public interface IControllerManager {

    public ICellController getCellController(CellType cellType);
}
