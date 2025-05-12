package maven_robots.data.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import maven_robots.logic.ChargeColor;
import maven_robots.logic.Coord;
import maven_robots.logic.cells.Cell;
import maven_robots.logic.cells.CellType;
import maven_robots.logic.cells.ICell;
import maven_robots.logic.cells.controllers.controllerManager.ControllerManager;
import maven_robots.logic.fields.Field;
import maven_robots.logic.robots.ConnectionRobot;
import maven_robots.logic.robots.IRobot;

public class Parser {
    private String basePath;

    public Parser() {
        try {
            String classPath = Parser.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            basePath = classPath.substring(1) + "maven_robots/data/levels";
        } catch (Exception e) {
            e.printStackTrace();
            basePath = "maven_robots/data/levels";
        }
    }

    public Field parseLevel(int levelNumber) {
        System.out.println(basePath);
        String currentPath = String.format("%s/%d.txt", basePath, levelNumber);
        System.out.println(currentPath);
        List<ICell[]> cells = new ArrayList<>();
        IRobot robot = new ConnectionRobot(new Coord(0, 0));
        int y = 0;

        try {
            List<String> lines = Files.readAllLines(Paths.get(currentPath));
            for (String line : lines) {
                int x = 0;
                String[] cellsData = line.split(" ");
                List<ICell> row = new ArrayList<>();
                for (String cellData : cellsData) {
                    String[] cellDataParts = cellData.split("#");

                    if (cellDataParts.length == 3) {
                        robot = new ConnectionRobot(new Coord(x, y));
                    }

                    switch (cellDataParts[0]) {
                        case "E":
                            row.add(new Cell(CellType.CELL, ChargeColor.getChargeColorByCode(cellDataParts[1])));
                            break;
                        case "P":
                            row.add(new Cell(CellType.POWER_POINT, ChargeColor.getChargeColorByCode(cellDataParts[1])));
                            break;
                    }

                    System.out.println(cellData);

                    x += 1;
                }
                cells.add(row.toArray(new ICell[0]));

                y += 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Field(cells.toArray(new ICell[0][]), robot, new ControllerManager());
    }
}