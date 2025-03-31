package RobotLogicTests;
import java.awt.Dimension;
import java.awt.Point;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import maven_robots.logic.RobotData;
import maven_robots.logic.RobotLogic;

@RunWith(Theories.class)
public class RobotBouncingTest  extends Assert{

    @DataPoints
    public static Object[][] RobotAndTarget = new Object[][] {
        { new RobotData( 1, 0, 0), new Point(2, 0)},
        { new RobotData(0, 1, Math.PI), new Point(-1, 0)},
        { new RobotData(1, 1, Math.PI / 2), new Point(1, -1)},
        { new RobotData(0, 0, (Math.PI * 3) / 2), new Point(0, 2)}
    };

    private static RobotLogic robotLogic;
    private static final double eps = 0.00001;

    @BeforeClass
    public static void Setup(){
        robotLogic = new RobotLogic();
    }

    @Theory
    public void testBounce(final Object... testData) {
        RobotData startRobot = (RobotData)testData[0];
        robotLogic.setTargetPosition((Point)testData[1]);
        robotLogic.setRobotPosition(startRobot);
        robotLogic.update(new Dimension(1, 1));
        RobotData actualRobot = robotLogic.getRobot();
        Assert.assertEquals(startRobot.x, actualRobot.x);
        Assert.assertEquals(startRobot.y, actualRobot.y);
        Assert.assertTrue(Math.abs(actualRobot.direction - startRobot.direction) - Math.PI < eps);
  }
}
