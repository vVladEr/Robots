package maven_robots.logic.fields.cabels;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Stack;

import maven_robots.logic.ChargeColor;
import maven_robots.logic.Coord;

public class CabelStorage implements ICabelStorage {

    private HashMap<ChargeColor, CabelPart[]> cabels;
    private Stack<ChargeColor> colorStack;
    private final int maxColorCount;

    public CabelStorage(int colorCount) {
        cabels = new HashMap<ChargeColor, CabelPart[]>();
        colorStack = new Stack<ChargeColor>();
        this.maxColorCount = colorCount;
    }

    @Override
    public HashMap<ChargeColor, CabelPart[]> getCabels() {
        return cabels;
    }

    @Override
    public boolean isAllCabelsCreated() {
        return maxColorCount == cabels.size();
    }

    @Override
    public void saveCabel(Coord[] coords, ChargeColor color) {
        cabels.put(color, ICabelStorage.fromCoordsToCabelParts(coords));
        colorStack.add(color);
    }

    @Override
    public CabelPart[] resetLastCable() throws EmptyStackException {
        ChargeColor lastColor = colorStack.pop();
        CabelPart[] cabel = cabels.get(lastColor);
        cabels.remove(lastColor);
        return cabel;
    }


}
