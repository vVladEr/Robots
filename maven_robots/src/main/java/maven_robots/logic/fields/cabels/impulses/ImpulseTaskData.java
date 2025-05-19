package maven_robots.logic.fields.cabels.impulses;

import maven_robots.logic.ChargeColor;

public final class ImpulseTaskData {

    public final ChargeColor color;

    public final int cabelLength;

    public final int chargeVolume;

    private int impulsePositionIndex;

    public ImpulseTaskData(ChargeColor color, int cabelLength, int chargeVolume) {
        this.color = color;
        this.cabelLength = cabelLength;
        this.chargeVolume = chargeVolume;
        impulsePositionIndex = 0;
    }

    public int getImpulsePosition() {
        return impulsePositionIndex;
    }

    public void moveImpulseForward() {
        if (impulsePositionIndex >= cabelLength - 1) {
            impulsePositionIndex = 0;
        } else {
            impulsePositionIndex += 1;
        }
    }

    public boolean isImpulseReachEndOfCabel() {
        return impulsePositionIndex == cabelLength - 1;
    }
}
