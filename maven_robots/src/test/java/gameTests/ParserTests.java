package gameTests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import static maven_robots.gui.RobotsProgram.getPath;

import org.junit.Test;

import maven_robots.data.parser.IParser;
import maven_robots.data.parser.LevelParser;
import maven_robots.logic.ChargeColor;
import maven_robots.logic.Coord;
import maven_robots.logic.cells.Cell;
import maven_robots.logic.cells.CellType;
import maven_robots.logic.cells.ICell;
import maven_robots.logic.fields.Field;

public class ParserTests {

    @Test
    public void parserShouldParseFieldCorrectly() {
        String path = getPath();
        IParser parser = new LevelParser(path);
        Coord robotPos = new Coord(1, 0);
        ICell[][] expectedField = new ICell[][] {
            {
                new Cell(CellType.POWER_POINT, ChargeColor.RED),
                new Cell(CellType.CELL, ChargeColor.EMPTY),
                new Cell(CellType.CELL, ChargeColor.EMPTY),
                new Cell(CellType.POWER_POINT, ChargeColor.RED)
            },
            {
                new Cell(CellType.POWER_POINT, ChargeColor.GREEN),
                new Cell(CellType.CELL, ChargeColor.EMPTY),
                new Cell(CellType.CELL, ChargeColor.EMPTY),
                new Cell(CellType.POWER_POINT, ChargeColor.GREEN)
            },
        };

        Field actualField = parser.parseLevel(0);
        
        assertArrayEquals(expectedField, actualField.getField());
        assertEquals(robotPos, actualField.getRobot().getCoord());
    }
}


/*язык системы или последний который использовался 
 * 
 * общее хранилище заряда
*/
