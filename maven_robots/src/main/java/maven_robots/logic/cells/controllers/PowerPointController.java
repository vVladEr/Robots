package maven_robots.logic.cells.controllers;

import java.util.Optional;

import maven_robots.logic.ChargeColor;
import maven_robots.logic.Coord;
import maven_robots.logic.cells.ICell;
import maven_robots.logic.robots.IRobot;

public class PowerPointController implements ICellController {

    @Override
    public Boolean isRobotAllowedToEnter(IRobot robot, ICell cell, Coord cellCoord) {
        return robot.getChargeColor() == ChargeColor.EMPTY 
            || robot.isMovingBackward(cellCoord)
            || isEnteringSecondPP(robot, cell, cellCoord); 
    }

    @Override
    public void moveRobotOn(IRobot robot, ICell cell, Coord cellCoord) {
        if (!isEnteringSecondPP(robot, cell, cellCoord)) {
            robot.startCabel(cell, cellCoord);
            return;
        }
        robot.finishCabel();
    }

    private Boolean isEnteringSecondPP(IRobot robot, ICell cell, Coord cellCoord) {
        Optional<Coord> lastTakenPP = robot.getLastTakenPowerPoint();
        return (robot.getChargeColor() == cell.getColor() 
            && lastTakenPP.isPresent()
            && !lastTakenPP.get().equals(cellCoord)); 
    }

}

/*
 * 
 * 
 * 
 * 
 */