package maven_robots.logic.Fields;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Stack;

import maven_robots.logic.ChargeColor;
import maven_robots.logic.Coord;

public class CabelStorage implements ICabelStorage {

    private HashMap<ChargeColor, Coord[]> cabels;
    private Stack<ChargeColor> colorStack;

    public CabelStorage() {
        cabels = new HashMap<ChargeColor, Coord[]>();
        colorStack = new Stack<ChargeColor>();
    }

    @Override
    public HashMap<ChargeColor, Coord[]> getCabels() {
        return cabels;
    }

    @Override
    public void saveCabel(Coord[] coords, ChargeColor color) {
        cabels.put(color, coords);
        colorStack.add(color);
    }

    @Override
    public void resetLastCable() throws EmptyStackException {
        ChargeColor lastColor = colorStack.pop();
        cabels.remove(lastColor);
    }


}
