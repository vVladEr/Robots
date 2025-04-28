package maven_robots.logic.Fields;

import java.util.EmptyStackException;
import java.util.HashMap;

import maven_robots.logic.ChargeColor;
import maven_robots.logic.Coord;

public interface ICabelStorage {

    public HashMap<ChargeColor, Coord[]> getCabels();

    public void saveCabel(Coord[] coords, ChargeColor color);

    public void resetLastCable() throws EmptyStackException;
}
