package gameTests;

import org.junit.Assert;
import org.junit.Test;

import maven_robots.logic.ChargeColor;
import maven_robots.logic.Coord;
import maven_robots.logic.Direction;
import maven_robots.logic.Cells.Cell;
import maven_robots.logic.Cells.CellType;
import maven_robots.logic.Cells.ICell;
import maven_robots.logic.Robots.ConnectionRobot;
import maven_robots.logic.Robots.IRobot;

public class RobotTests {


    @Test
    public void whenRobotMoveWithoutCabelShouldNotSaveRoute() {
        IRobot robot = new ConnectionRobot(new Coord(0, 0));
        robot.move(Direction.RIGHT);
        robot.move(Direction.RIGHT);
        Assert.assertEquals(0, robot.getCurrentCabel().length); 
    }

    @Test
    public void whenRobotStartCabelShouldSaveLastPowerPoint() {

        ICell pp = new Cell(CellType.POWER_POINT, ChargeColor.BLUE);
        Coord ppCoord = new Coord(0, 0);
        IRobot robot = new ConnectionRobot(ppCoord);
        robot.startCabel(pp, ppCoord);
        robot.move(Direction.DOWN);
        Assert.assertEquals(ChargeColor.BLUE, pp.getColor());
        Assert.assertTrue(robot.getLastTakenPowerPoint().isPresent());
        Assert.assertEquals(ppCoord, robot.getLastTakenPowerPoint().get());
    }

    @Test
    public void whenRobotMoveWithCabelShouldSaveTrack() {

        ICell pp = new Cell(CellType.POWER_POINT, ChargeColor.BLUE);
        Coord ppCoord = new Coord(0, 0);
        IRobot robot = new ConnectionRobot(ppCoord);
        robot.startCabel(pp, ppCoord);
        robot.move(Direction.DOWN);
        robot.move(Direction.RIGHT);
        Coord[] track = robot.getCurrentCabel();
        Assert.assertEquals(3, track.length);
        Assert.assertArrayEquals(new Coord[] {ppCoord, new Coord(0, 1), new Coord(1, 1) }, track);
        Assert.assertEquals(new Coord(1, 1), robot.getCoord());
    }

    @Test
    public void whenRobotMoveBackWardWithCabelShouldRolloutTrack() {

        ICell pp = new Cell(CellType.POWER_POINT, ChargeColor.BLUE);
        Coord ppCoord = new Coord(0, 0);
        IRobot robot = new ConnectionRobot(ppCoord);
        robot.startCabel(pp, ppCoord);
        robot.move(Direction.DOWN);
        robot.move(Direction.RIGHT);
        robot.move(Direction.LEFT);
        Coord[] track = robot.getCurrentCabel();
        Assert.assertEquals(2, track.length);
        Assert.assertArrayEquals(new Coord[] {ppCoord, new Coord(0, 1)}, track);
    }

    @Test
    public void resetCableShouldResetCorrectly() {
        ICell pp = new Cell(CellType.POWER_POINT, ChargeColor.BLUE);
        Coord ppCoord = new Coord(0, 0);
        IRobot robot = new ConnectionRobot(ppCoord);
        robot.startCabel(pp, ppCoord);
        robot.move(Direction.DOWN);
        robot.move(Direction.RIGHT);
        Coord[] track = robot.getCurrentCabel();
        Assert.assertEquals(3, track.length);
        Assert.assertArrayEquals(new Coord[] {ppCoord, new Coord(0, 1), new Coord(1, 1) }, track);
        Assert.assertEquals(new Coord(1, 1), robot.getCoord());

        robot.resetCurrentCabel();
        Coord[] newTrack = robot.getCurrentCabel();
        Assert.assertEquals(0, newTrack.length);
        Assert.assertFalse(robot.getLastTakenPowerPoint().isPresent());
        Assert.assertEquals(ChargeColor.EMPTY, robot.getChargeColor());
    }
}
