package maven_robots.logic.fields.cabels.impulses;

import maven_robots.logic.ChargeColor;

public interface IImpulseManager {

    public void addImpulse(ChargeColor color);

    public void removeImpulse(ChargeColor color);

    public int getImpulsePosition(ChargeColor color);
}
