package maven_robots.logic.fields.cabels;

import java.util.EmptyStackException;
import java.util.HashMap;

import maven_robots.logic.ChargeColor;
import maven_robots.logic.Coord;
import maven_robots.logic.Direction;

public interface ICabelStorage {

    public HashMap<ChargeColor, CabelPart[]> getCabels();

    public boolean isAllCabelsCreated();

    public void saveCabel(CabelPart[] coords, ChargeColor color);

    public CabelPart[] resetLastCable() throws EmptyStackException;

    public static CabelPart[] fromCoordsToCabelParts(Coord[] coords) {
        CabelPart[] res = new CabelPart[coords.length];
        res[0] = new CabelPart(coords[0]);
        for (int i = 1; i < coords.length; i++) {
            Coord diff = Coord.getDiff(coords[i], coords[i - 1]);
            Direction dir  = Direction.toDirection(diff);
            res[i - 1].setTo(dir);
            res[i] = new CabelPart(coords[i], dir.getOpposite());
        }
        return res;
    }

    public static Coord[] fromCabelPartsToCoords(CabelPart[] parts) {
        Coord[] res = new Coord[parts.length];
        for (int i = 0; i < parts.length; i++) {
            res[i] = parts[i].getCoord();
        }
        return res;
    }
}
