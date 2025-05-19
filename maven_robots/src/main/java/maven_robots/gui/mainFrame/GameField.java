package maven_robots.gui.mainFrame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import maven_robots.logic.Cells.ICell;
import maven_robots.logic.ChargeColor;
import maven_robots.logic.Coord;
import maven_robots.logic.Direction;
import maven_robots.logic.Fields.Field;
import maven_robots.logic.Fields.FieldObserver;

public class GameField extends JPanel implements FieldObserver, KeyListener {
    private final Field field;

    public GameField(Field field) {
        this.field = field;
        this.field.addObserver(this);
        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ICell[][] cells = field.getField();
        Coord robotCoord = field.getRobot().getCoord();

        int cellSize = 50;
        int fieldWidth = cells[0].length * cellSize;
        int fieldHeight = cells.length * cellSize;
        int startX = (getWidth() - fieldWidth) / 2;
        int startY = (getHeight() - fieldHeight) / 2;

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                ChargeColor color = cells[i][j].getColor();
                g.setColor(color.getAwtColor());
                g.fillRect(startX + j * cellSize, startY + i * cellSize, cellSize, cellSize);
            }
        }

        g.setColor(Color.BLACK);
        g.fillOval(startX + robotCoord.x * cellSize, startY + robotCoord.y * cellSize,
                    cellSize, cellSize);

        if (field.isGameFinished()) {
            g.setColor(Color.YELLOW);
            g.setFont(g.getFont().deriveFont(80f));
            g.drawString("Победа!", getWidth() / 2 - 160, getHeight() / 2);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                field.moveRobot(Direction.UP);
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                field.moveRobot(Direction.DOWN);
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                field.moveRobot(Direction.LEFT);
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                field.moveRobot(Direction.RIGHT);
                break;
            case KeyEvent.VK_C:
                field.resertCurrentCabel();
                break;
            case KeyEvent.VK_R:
                field.resertLastCabel();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void onFieldChanged() {
        repaint();
    }
}