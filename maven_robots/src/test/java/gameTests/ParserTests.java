package gameTests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import maven_robots.data.parser.Parser;
import maven_robots.logic.ChargeColor;
import maven_robots.logic.Coord;
import maven_robots.logic.cells.Cell;
import maven_robots.logic.cells.CellType;
import maven_robots.logic.cells.ICell;
import maven_robots.logic.fields.Field;
import maven_robots.logic.fields.cabels.impulses.ColorImpulseParameters;

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
        int expectedMaxCharge = 4 
            * (ColorImpulseParameters.getByColorOrDefault(ChargeColor.RED).costByCell 
                + ColorImpulseParameters.getByColorOrDefault(ChargeColor.GREEN).costByCell); 
        
        assertArrayEquals(expectedField, actualField.getField());
        assertEquals(robotPos, actualField.getRobot().getCoord());
        assertEquals(expectedMaxCharge, actualField.getImpulseManager().getMaxChargeCapacity());
    }
}


/*язык системы или последний который использовался 
 * 
 * общее хранилище заряда
*/
