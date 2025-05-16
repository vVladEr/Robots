package maven_robots.logic.fields.cabels.impulses;

import maven_robots.logic.ChargeColor;

public class ImpulseParameters {

    public final ChargeColor color;
    public final long moveDelay;
    public final int costByCell;

    public ImpulseParameters(ChargeColor color, long moveDelay, int costByCell) {
        this.color = color;
        this.moveDelay = moveDelay;
        this.costByCell = costByCell;
    }
}
