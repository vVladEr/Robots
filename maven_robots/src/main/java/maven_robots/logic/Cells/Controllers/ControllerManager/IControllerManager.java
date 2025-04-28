package maven_robots.logic.Cells.Controllers.ControllerManager;

import maven_robots.logic.Cells.CellType;
import maven_robots.logic.Cells.Controllers.ICellController;

public interface IControllerManager {

    public ICellController getCellController(CellType cellType);
}
