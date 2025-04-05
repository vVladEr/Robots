package RobotLogicTests;

import org.junit.Test;

import maven_robots.logic.RobotData;
import maven_robots.logic.RobotLogic;

import java.awt.Dimension;
import java.awt.Point;

import org.junit.Assert;
import org.junit.BeforeClass;

public class RobotTargetTests {

    private static RobotLogic robotLogic;
    private static final double eps = 0.00001;

    @BeforeClass
    public static void Setup(){
        robotLogic = new RobotLogic();
    }

    @Test
    public void StopsWhenReachTarget(){  
        RobotData startRobot = new RobotData(1, 1, Math.PI);
        Dimension dim = new Dimension(50, 50);
        robotLogic.setTargetPosition(new Point(1, 1));
        robotLogic.setRobotPosition(startRobot);
        for (int i =0; i < 5; i++){
            robotLogic.update(dim);
            RobotData actualRobot = robotLogic.getRobot();
            Assert.assertEquals(startRobot.x, actualRobot.x);
            Assert.assertEquals(startRobot.y, actualRobot.y);
            Assert.assertTrue(Math.abs(actualRobot.direction - startRobot.direction) < eps);
        }
    }
}
