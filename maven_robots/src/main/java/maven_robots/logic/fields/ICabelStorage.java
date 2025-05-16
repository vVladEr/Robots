package maven_robots.logic.fields;

import java.util.EmptyStackException;
import java.util.HashMap;

import maven_robots.logic.ChargeColor;
import maven_robots.logic.Coord;

public interface ICabelStorage {

    public HashMap<ChargeColor, Coord[]> getCabels();

    public boolean isAllCabelsCreated();

    public void saveCabel(Coord[] coords, ChargeColor color);

    public Coord[] resetLastCable() throws EmptyStackException;
}
