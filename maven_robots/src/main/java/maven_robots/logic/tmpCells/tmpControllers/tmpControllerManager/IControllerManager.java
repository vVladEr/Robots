package maven_robots.logic.tmpCells.tmpControllers.tmpControllerManager;

import maven_robots.logic.tmpCells.CellType;
import maven_robots.logic.tmpCells.tmpControllers.ICellController;

public interface IControllerManager {

    public ICellController getCellController(CellType cellType);
}
