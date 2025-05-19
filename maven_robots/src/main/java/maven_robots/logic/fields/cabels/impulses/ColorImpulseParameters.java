package maven_robots.logic.fields.cabels.impulses;

import java.util.HashMap;

import maven_robots.logic.ChargeColor;

public class ColorImpulseParameters {

    private static final HashMap<ChargeColor, ImpulseParameters> params = init();

    private static final ImpulseParameters defaultParams 
        = new ImpulseParameters(100, 1);

    private static HashMap<ChargeColor, ImpulseParameters> init() {
        HashMap<ChargeColor, ImpulseParameters> res = new HashMap<>();
        res.put(ChargeColor.BLUE, new ImpulseParameters(200, 1));
        res.put(ChargeColor.RED, new ImpulseParameters(100, 2));
        res.put(ChargeColor.GREEN, new ImpulseParameters(200, 2));
        res.put(ChargeColor.PURPLE, new ImpulseParameters(300, 2));
        return res;
    }

    public static ImpulseParameters getByColorOrDefault(ChargeColor color) {
        if (params.containsKey(color)) {
            return params.get(color);
        }
        return defaultParams;
    }

}
