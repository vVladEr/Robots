package gameTests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import maven_robots.data.parser.Parser;
import maven_robots.logic.ChargeColor;
import maven_robots.logic.Coord;
import maven_robots.logic.tmpCells.Cell;
import maven_robots.logic.tmpCells.CellType;
import maven_robots.logic.tmpCells.ICell;
import maven_robots.logic.tmpFields.Field;

public class ParserTests {

    @Test
    public void parserShouldParseFieldCorrectly() {
        Parser parser = new Parser();
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
