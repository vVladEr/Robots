package maven_robots.gui.frames.internalFrames;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import maven_robots.logic.ChargeColor;
import maven_robots.logic.Coord;
import maven_robots.logic.Direction;
import maven_robots.logic.cells.CellType;
import maven_robots.logic.cells.ICell;
import maven_robots.logic.fields.Field;
import maven_robots.logic.fields.FieldObserver;
import maven_robots.logic.fields.cabels.CabelPart;
import maven_robots.logic.robots.IRobot;

public class GameField extends JPanel implements FieldObserver, KeyListener {
    private final Field field;
    private BufferedImage robotSprite;

    public GameField(Field field, String path) {
        this.field = field;
        this.field.addObserver(this);
        setFocusable(true);
        addKeyListener(this);

        try {
            loadRobotSprite(path);
        } catch (IOException e) {
            System.err.println("Ошибка загрузки спрайта робота: " + e.getMessage());
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
                field.resetCurrentCabel();
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        ICell[][] cells = field.getField();
        IRobot robot = field.getRobot();
        Coord robotCoord = robot.getCoord();
        HashMap<ChargeColor, CabelPart[]> completedCables = field.getCabelStorage().getCabels();
        ChargeColor currentChargeColor = robot.getChargeColor();
        CabelPart[] currentCable = robot.getCurrentCabel();

        int cellSize = 50;
        int borderSize = 2;
        int fieldWidth = cells[0].length * cellSize;
        int fieldHeight = cells.length * cellSize;
        int startX = (getWidth() - fieldWidth) / 2;
        int startY = (getHeight() - fieldHeight) / 2;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.fillRect(startX + j * cellSize, startY + i * cellSize, cellSize, cellSize);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(startX + j * cellSize, startY + i * cellSize, cellSize, cellSize);
            }
        }

        for (ChargeColor chargeColor : completedCables.keySet()) {
            for (CabelPart cablePart : completedCables.get(chargeColor)) {
                int i = cablePart.getCoord().x;
                int j = cablePart.getCoord().y;
                drawCabelPart(g2d, cablePart, startX + i * cellSize, startY + j * cellSize, cellSize, chargeColor);
            }
        }

        if (currentChargeColor != ChargeColor.EMPTY) {
            for (CabelPart cabelPart : currentCable) {
                int i = cabelPart.getCoord().x;
                int j = cabelPart.getCoord().y;
                drawCabelPart(g2d, cabelPart, startX + i * cellSize, startY + j * cellSize, cellSize, currentChargeColor);
            }
        }

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j].getType() == CellType.POWER_POINT) {
                    boolean isCharged = false;
                    ChargeColor chargeColor = cells[i][j].getColor();

                    if (currentChargeColor == chargeColor) {
                        for (CabelPart cabelPart : currentCable) {
                            if (cabelPart.getCoord().x == j && cabelPart.getCoord().y == i) {
                                isCharged = true;
                                break;
                            }
                        }
                    } else if (completedCables.containsKey(chargeColor)) {
                        isCharged = true;
                    }

                    drawPowerPoint(g2d, chargeColor, i, j, cellSize, borderSize, startX, startY, isCharged);
                }
            }
        }

        if (robotSprite != null) {
            int x = startX + robotCoord.x * cellSize;
            int y = startY + robotCoord.y * cellSize;
            g2d.drawImage(robotSprite, x, y, cellSize, cellSize, this);
        } else {
            g2d.setColor(Color.BLACK);
            g2d.fillOval(startX + robotCoord.x * cellSize, startY + robotCoord.y * cellSize, cellSize, cellSize);
        }

        if (field.isGameFinished()) {
            g.setColor(Color.YELLOW);
            g.setFont(g.getFont().deriveFont(80f));
            g.drawString("Победа!", getWidth() / 2 - 160, getHeight() / 2);
        }
    }

    private void loadRobotSprite(String path) throws IOException {
        String robotSpritePath = path + "/assets/dark_robot.png";
        robotSprite = ImageIO.read(new File(robotSpritePath));
    }

    private void drawCabelPart(Graphics2D g2d, CabelPart part, int x, int y, int cellSize, ChargeColor chargeColor) {
        int centerX = x + cellSize / 2;
        int centerY = y + cellSize / 2;
        int cableWidth = 10;
        int borderWidth = 4;

        Optional<Direction> from = part.getFrom();
        Optional<Direction> to = part.getTo();

        List<Optional<Direction>> directions = new ArrayList<>();
        directions.add(from);
        directions.add(to);



        for (Optional<Direction> direction : directions) {
            setDrawOptions(g2d, ChargeColor.BLACK, borderWidth);

            if (direction.isPresent()) {
                switch (direction.get()) {
                    case UP:
                        g2d.drawLine(centerX + cableWidth / 2, y + borderWidth / 2, centerX + cableWidth / 2, centerY + borderWidth);
                        g2d.drawLine(centerX - cableWidth / 2, y + borderWidth / 2, centerX - cableWidth / 2, centerY + borderWidth);
                        break;
                    case DOWN:
                        g2d.drawLine(centerX + cableWidth / 2, y + cellSize - borderWidth / 2, centerX + cableWidth / 2, centerY - borderWidth);
                        g2d.drawLine(centerX - cableWidth / 2 , y + cellSize - borderWidth / 2, centerX - cableWidth / 2, centerY - borderWidth);
                        break;
                    case RIGHT:
                        g2d.drawLine(x + cellSize - borderWidth / 2, centerY + cableWidth / 2, centerX - borderWidth, centerY + cableWidth / 2);
                        g2d.drawLine(x + cellSize - borderWidth / 2, centerY - cableWidth / 2, centerX - borderWidth, centerY - cableWidth / 2);
                        break;
                    case LEFT:
                        g2d.drawLine(x + borderWidth / 2, centerY + cableWidth / 2, centerX + borderWidth, centerY + cableWidth / 2);
                        g2d.drawLine(x + borderWidth / 2, centerY - cableWidth / 2, centerX + borderWidth, centerY - cableWidth / 2);
                        break;
                }
            }
        }

        for (Optional<Direction> direction : directions) {
            setDrawOptions(g2d, chargeColor, cableWidth);

            if (direction.isPresent()) {
                switch (direction.get()) {
                    case UP:
                        g2d.drawLine(centerX, y + cableWidth / 2, centerX, centerY);
                        break;
                    case DOWN:
                        g2d.drawLine(centerX, y + cellSize - cableWidth / 2, centerX, centerY);
                        break;
                    case RIGHT:
                        g2d.drawLine(x + cellSize - cableWidth / 2, centerY, centerX, centerY);
                        break;
                    case LEFT:
                        g2d.drawLine(x + cableWidth / 2, centerY, centerX, centerY);
                        break;
                }
            }
        }
    }

    private void setDrawOptions(Graphics2D g2d, ChargeColor chargeColor, int lineWidth) {
        g2d.setColor(chargeColor.getAwtColor());
        g2d.setStroke(new BasicStroke(lineWidth));
    }

    private void drawPowerPoint(Graphics2D g2d, ChargeColor chargeColor, int i, int j, int cellSize, int borderSize, int startX, int startY, boolean isCharged) {
        int centerX = startX + j * cellSize + cellSize / 2;
        int centerY = startY + i * cellSize + cellSize / 2;
        int outerRadius = cellSize / 2 - borderSize;
        int innerBorderRadius = outerRadius / 2;
        int innerRadius = outerRadius / 2 - borderSize - 1;

        setDrawOptions(g2d, ChargeColor.BLACK, borderSize);
        g2d.fillOval(centerX - outerRadius - borderSize / 2, centerY - outerRadius - borderSize / 2, 2 * outerRadius + borderSize, 2 * outerRadius + borderSize);

        setDrawOptions(g2d, chargeColor, borderSize);
        g2d.fillOval(centerX - outerRadius, centerY - outerRadius, 2 * outerRadius, 2 * outerRadius);

        setDrawOptions(g2d, ChargeColor.BLACK, borderSize);
        g2d.fillOval(centerX - innerBorderRadius, centerY - innerBorderRadius, 2 * innerBorderRadius, 2 * innerBorderRadius);

        if (isCharged) {
            setDrawOptions(g2d, ChargeColor.LIGHT_BLUE, borderSize);
        } else {
            setDrawOptions(g2d, ChargeColor.GRAY, borderSize);
        }

        g2d.fillOval(centerX - innerRadius, centerY - innerRadius, 2 * innerRadius, 2 * innerRadius);
    }
}