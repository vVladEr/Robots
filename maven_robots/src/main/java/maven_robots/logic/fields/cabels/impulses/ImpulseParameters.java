package maven_robots.logic.fields.cabels.impulses;

public class ImpulseParameters {
    public final long moveDelayMilliseconds;
    public final int costByCell;

    public ImpulseParameters(long moveDelayMilliseconds, int costByCell) {
        this.moveDelayMilliseconds = moveDelayMilliseconds;
        this.costByCell = costByCell;
    }
}
