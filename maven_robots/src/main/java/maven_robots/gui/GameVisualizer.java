package maven_robots.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import maven_robots.logic.RobotData;
import maven_robots.logic.RobotLogic;

public class GameVisualizer extends JPanel implements ILocalizable {
    private final Timer m_timer = initTimer();
    private final RobotLogic robotLogic;
    
    private static Timer initTimer() {
        Timer timer = new Timer("events generator", true);
        return timer;
    }
  
    public GameVisualizer() {
        robotLogic = new RobotLogic();
        m_timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    onRedrawEvent();
                }
            }, 0, 50);
        m_timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    robotLogic.update(getSize());
                }
            }, 0, 10);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                robotLogic.setTargetPosition(e.getPoint());
                repaint();
            }
        });
        setDoubleBuffered(true);
    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g; 
        Point target = robotLogic.getTargetPosition();
        RobotData robot = robotLogic.getRobot();
        drawRobot(g2d, robot.x, robot.y, robot.direction);
        drawTarget(g2d, target.x, target.y);
    }
    
    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }
    
    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }
    
    private void drawRobot(Graphics2D g, int robotCenterX, int robotCenterY, double direction) {
        AffineTransform t = AffineTransform.getRotateInstance(direction,
             robotCenterX, robotCenterY); 
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        fillOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX  + 10, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX  + 10, robotCenterY, 5, 5);
    }
    
    private void drawTarget(Graphics2D g, int x, int y) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0); 
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }

    @Override
    public void changeLanguage() {
    }
}
