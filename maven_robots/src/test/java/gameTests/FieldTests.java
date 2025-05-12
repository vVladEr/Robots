package gameTests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import maven_robots.logic.ChargeColor;
import maven_robots.logic.Coord;
import maven_robots.logic.Direction;
import maven_robots.logic.tmpCells.Cell;
import maven_robots.logic.tmpCells.CellType;
import maven_robots.logic.tmpCells.ICell;
import maven_robots.logic.tmpCells.tmpControllers.tmpControllerManager.ControllerManager;
import maven_robots.logic.tmpFields.Field;
import maven_robots.logic.tmpFields.ICabelStorage;
import maven_robots.logic.tmpRobots.ConnectionRobot;
import maven_robots.logic.tmpRobots.IRobot;


@RunWith(Theories.class)
public class FieldTests extends Assert {

    @DataPoints
    public static Object[] RobotAndTarget = new Object[][] { 
        {Direction.DOWN },
        {Direction.UP}, 
        {Direction.LEFT}, 
        {Direction.RIGHT}
    };

    @Theory
    public void robotShouldNotMoveWhenOutOfBounds(final Object... testData) {
        Coord startCoord = new Coord(0, 0);
        IRobot robot = new ConnectionRobot(startCoord);
        Field field = new Field(
            new ICell[][] {{new Cell(CellType.CELL, ChargeColor.EMPTY)}},
            robot, 
            new ControllerManager());
        Direction robotDir = (Direction) testData[0];
        Boolean result = field.moveRobot(robotDir);
        Assert.assertFalse(result);
        Assert.assertEquals(startCoord, field.getRobot().getCoord());
    }

    @Test
    public void robotShouldMoveWhenEmpty() {
        Coord startCoord = new Coord(0, 0);
        IRobot robot = new ConnectionRobot(startCoord);
        Field field = new Field(
            new ICell[][] {{
                    new Cell(CellType.CELL, ChargeColor.EMPTY),
                    new Cell(CellType.CELL, ChargeColor.EMPTY),
                    new Cell(CellType.CELL, ChargeColor.RED)
                }},
            robot, 
            new ControllerManager());
        Boolean result = field.moveRobot(Direction.RIGHT);
        Assert.assertTrue(result);
        Assert.assertEquals(new Coord(1, 0), field.getRobot().getCoord());

        result = field.moveRobot(Direction.RIGHT);
        Assert.assertTrue(result);
        Assert.assertEquals(new Coord(2, 0), field.getRobot().getCoord());
    }

    @Test
    public void robotShouldGetColorWhenMoveOnPowerPoint() {
        Coord startCoord = new Coord(0, 0);
        IRobot robot = new ConnectionRobot(startCoord);
        Field field = new Field(
            new ICell[][] {
                {new Cell(CellType.CELL, ChargeColor.EMPTY),
                 new Cell(CellType.POWER_POINT, ChargeColor.PURPLE)}},
            robot, 
            new ControllerManager());
        Boolean result = field.moveRobot(Direction.RIGHT);
        Assert.assertTrue(result);

        Coord ppCoord = new Coord(1, 0);
        Assert.assertEquals(ppCoord, field.getRobot().getCoord());
        Assert.assertEquals(ChargeColor.PURPLE, field.getRobot().getChargeColor());
        Assert.assertTrue(field.getRobot().getLastTakenPowerPoint().isPresent());
        Assert.assertEquals(ppCoord, field.getRobot().getLastTakenPowerPoint().get());
    }

    @Test
    public void robotShouldPaintCellsWhenMoveWithColor() {
        Coord startCoord = new Coord(0, 0);
        IRobot robot = new ConnectionRobot(startCoord);
        Field field = new Field(
            new ICell[][] {
                {new Cell(CellType.CELL, ChargeColor.EMPTY),
                 new Cell(CellType.POWER_POINT, ChargeColor.PURPLE)}},
            robot, 
            new ControllerManager());
        Boolean result = field.moveRobot(Direction.RIGHT);
        Assert.assertTrue(result);

        result = field.moveRobot(Direction.LEFT);
        Assert.assertTrue(result);

        Assert.assertEquals(startCoord, field.getRobot().getCoord());
        Assert.assertEquals(ChargeColor.PURPLE, field.getField()[0][0].getColor());
    }

    @Test
    public void robotShouldNotMovesWhenCrossWithDifferentColor() {
        Coord startCoord = new Coord(0, 0);
        IRobot robot = new ConnectionRobot(startCoord);
        robot.setChargeColor(ChargeColor.BLUE);
        Field field = new Field(
            new ICell[][] {
                {new Cell(CellType.CELL, ChargeColor.EMPTY),
                 new Cell(CellType.CELL, ChargeColor.PURPLE)}},
            robot, 
            new ControllerManager());
        Boolean result = field.moveRobot(Direction.RIGHT);
        Assert.assertFalse(result);
        Assert.assertEquals(startCoord, field.getRobot().getCoord());
    }

    @Test
    public void robotShouldNotMovesWhenCrossWithSameColor() {
        Coord startCoord = new Coord(0, 0);
        IRobot robot = new ConnectionRobot(startCoord);
        robot.setChargeColor(ChargeColor.BLUE);
        Field field = new Field(
            new ICell[][] {
                {new Cell(CellType.CELL, ChargeColor.EMPTY),
                 new Cell(CellType.CELL, ChargeColor.BLUE)}},
            robot, 
            new ControllerManager());
        Boolean result = field.moveRobot(Direction.RIGHT);
        Assert.assertFalse(result);
        Assert.assertEquals(startCoord, field.getRobot().getCoord());
    }

    @Test
    public void robotShouldMovesWhenMoveBackwards() {
        Coord startCoord = new Coord(0, 0);
        IRobot robot = new ConnectionRobot(startCoord);
        Field field = new Field(
            new ICell[][] {{   
                    new Cell(CellType.CELL, ChargeColor.EMPTY),
                    new Cell(CellType.POWER_POINT, ChargeColor.PURPLE),
                    new Cell(CellType.CELL, ChargeColor.EMPTY),
                    new Cell(CellType.CELL, ChargeColor.EMPTY)
                }},
            robot, 
            new ControllerManager());
        Boolean result = field.moveRobot(Direction.RIGHT);
        Assert.assertTrue(result);

        result = field.moveRobot(Direction.RIGHT);
        Assert.assertTrue(result);

        result = field.moveRobot(Direction.RIGHT);
        Assert.assertTrue(result);

        Coord tempCoord = new Coord(3, 0);
        Assert.assertEquals(tempCoord, field.getRobot().getCoord());
        Assert.assertEquals(ChargeColor.PURPLE, field.getField()[0][3].getColor());

        result = field.moveRobot(Direction.LEFT);
        Assert.assertTrue(result);

        Assert.assertEquals(new Coord(2, 0), field.getRobot().getCoord());
        Assert.assertEquals(ChargeColor.EMPTY, field.getField()[0][3].getColor());
    }

    @Test
    public void robotShouldMovesWhenMoveBackwardsOnPowerPoint() {
        Coord startCoord = new Coord(0, 0);
        IRobot robot = new ConnectionRobot(startCoord);
        Field field = new Field(
            new ICell[][] {{   
                    new Cell(CellType.CELL, ChargeColor.EMPTY),
                    new Cell(CellType.POWER_POINT, ChargeColor.PURPLE),
                    new Cell(CellType.CELL, ChargeColor.EMPTY)
                }},
            robot, 
            new ControllerManager());
        Boolean result = field.moveRobot(Direction.RIGHT);
        Assert.assertTrue(result);

        result = field.moveRobot(Direction.RIGHT);
        Assert.assertTrue(result);

        result = field.moveRobot(Direction.LEFT);
        Assert.assertTrue(result);
    }


    @Test
    public void shouldSaveCabelWhenCabelFinished() {
        Coord startCoord = new Coord(0, 0);
        ChargeColor cabelColor = ChargeColor.PURPLE;
        IRobot robot = new ConnectionRobot(startCoord);
        Field field = new Field(
            new ICell[][] {{   
                    new Cell(CellType.CELL, ChargeColor.EMPTY),
                    new Cell(CellType.POWER_POINT, cabelColor),
                    new Cell(CellType.CELL, ChargeColor.EMPTY),
                    new Cell(CellType.POWER_POINT, cabelColor)
                }},
            robot, 
            new ControllerManager());
        Boolean result = field.moveRobot(Direction.RIGHT);
        Assert.assertTrue(result);

        result = field.moveRobot(Direction.RIGHT);
        Assert.assertTrue(result);

        result = field.moveRobot(Direction.RIGHT);
        Assert.assertTrue(result);
        
        ICabelStorage cabelStorage = field.getCabelStorage();
        Assert.assertEquals(1, cabelStorage.getCabels().size());
        Assert.assertTrue(cabelStorage.getCabels().containsKey(cabelColor));
        Coord[] expectedCabelCoords = new Coord[] {
            new Coord(1, 0),
            new Coord(2, 0),
            new Coord(3, 0)
        };
        Assert.assertArrayEquals(expectedCabelCoords, cabelStorage.getCabels().get(cabelColor));
        Assert.assertTrue(cabelStorage.isAllCabelsCreated());
    }

    @Test
    public void shouldResetCurrentCabelCorrectly() {
        Coord startCoord = new Coord(0, 0);
        ChargeColor cabelColor = ChargeColor.PURPLE;
        IRobot robot = new ConnectionRobot(startCoord);
        Field field = new Field(
            new ICell[][] {{   
                    new Cell(CellType.CELL, ChargeColor.EMPTY),
                    new Cell(CellType.POWER_POINT, cabelColor),
                    new Cell(CellType.CELL, ChargeColor.EMPTY),
                    new Cell(CellType.CELL, ChargeColor.EMPTY)
                }},
            robot, 
            new ControllerManager());
        Boolean result = field.moveRobot(Direction.RIGHT);
        Assert.assertTrue(result);

        result = field.moveRobot(Direction.RIGHT);
        Assert.assertTrue(result);

        result = field.moveRobot(Direction.RIGHT);
        Assert.assertTrue(result);
        
        Coord[] curCabel = field.getRobot().getCurrentCabel();
        for (int i = 1; i < curCabel.length; i++) {
            Assert.assertEquals(cabelColor, 
                field.getField()[curCabel[i].y][curCabel[i].x].getColor());
        }
        field.resetCurrentCabel();
        
        for (int i = 1; i < curCabel.length; i++) {
            Assert.assertEquals(ChargeColor.EMPTY, 
                field.getField()[curCabel[i].y][curCabel[i].x].getColor());
        }
    }

    @Test
    public void shouldResetLastCabelCorrectly() {
        Coord startCoord = new Coord(0, 0);
        ChargeColor cabelColor = ChargeColor.PURPLE;
        IRobot robot = new ConnectionRobot(startCoord);
        Field field = new Field(
            new ICell[][] {{   
                    new Cell(CellType.CELL, ChargeColor.EMPTY),
                    new Cell(CellType.POWER_POINT, cabelColor),
                    new Cell(CellType.CELL, ChargeColor.EMPTY),
                    new Cell(CellType.POWER_POINT, cabelColor),
                    new Cell(CellType.CELL, ChargeColor.EMPTY)
                }},
            robot, 
            new ControllerManager());
        Boolean result = field.moveRobot(Direction.RIGHT);
        Assert.assertTrue(result);

        result = field.moveRobot(Direction.RIGHT);
        Assert.assertTrue(result);

        result = field.moveRobot(Direction.RIGHT);
        Assert.assertTrue(result);

        result = field.moveRobot(Direction.RIGHT);
        Assert.assertTrue(result);
        
        field.resertLastCabel();

        ICabelStorage cabelStorage = field.getCabelStorage();
        Assert.assertEquals(0, cabelStorage.getCabels().size());

        Assert.assertEquals(ChargeColor.EMPTY, field.getField()[0][2].getColor());
    }

    @Test
    public void shouldFinishTheGameThenAllCoveredAndAllPowerPointsConnected() {
        Coord startCoord = new Coord(1, 0);
        ChargeColor cabelColor = ChargeColor.PURPLE;
        IRobot robot = new ConnectionRobot(startCoord);
        Field field = new Field(
            new ICell[][] {{   
                    new Cell(CellType.POWER_POINT, cabelColor),
                    new Cell(CellType.CELL, ChargeColor.EMPTY),
                    new Cell(CellType.POWER_POINT, cabelColor)
                }},
            robot, 
            new ControllerManager());
        Boolean result = field.moveRobot(Direction.LEFT);
        Assert.assertTrue(result);

        result = field.moveRobot(Direction.RIGHT);
        Assert.assertTrue(result);

        Assert.assertFalse(field.isGameFinished());

        result = field.moveRobot(Direction.RIGHT);
        Assert.assertTrue(result);

        Assert.assertTrue(field.isGameFinished());
    }
}
