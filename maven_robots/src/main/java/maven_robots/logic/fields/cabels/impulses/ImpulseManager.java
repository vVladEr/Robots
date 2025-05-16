package maven_robots.logic.fields.cabels.impulses;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import maven_robots.logic.ChargeColor;
import maven_robots.logic.fields.cabels.ICabelStorage;

public class ImpulseManager {

    private final HashMap<ChargeColor, ImpulseParameters> colorsParameters;
    private final ICabelStorage cabelStorage;
    private final AtomicInteger totalChargeCapacity;
    private HashMap<ChargeColor, TimerTask> impulseMoveTasks;
    private volatile ConcurrentHashMap<ChargeColor, Integer> impulsePositions;
    private final Timer timer;

    public ImpulseManager(ICabelStorage cabelStorage, AtomicInteger totalChargeCapacity) {
        colorsParameters = null;
        this.cabelStorage = cabelStorage;
        this.totalChargeCapacity = totalChargeCapacity;
        impulseMoveTasks = new HashMap<ChargeColor, TimerTask>();
        impulsePositions = new ConcurrentHashMap<ChargeColor, Integer>();
        timer = new Timer();
    }

    public void addImpulse(ChargeColor color) {
        impulsePositions.put(color, -1);
        TimerTask newColorTask = new TimerTask() {
            @Override
            public void run() {
                impulseTask(color);
            }
        };
        impulseMoveTasks.put(color, newColorTask);
        timer.schedule(newColorTask, colorsParameters.get(color).moveDelay);
    }

    public void removeImpulse(ChargeColor color) {
        impulseMoveTasks.get(color).cancel();
        impulseMoveTasks.remove(color);
        if (impulsePositions.get(color) != 1) {
            totalChargeCapacity.addAndGet(calculateTotalCharge(color));
        }
        impulsePositions.remove(color);
    }

    public int getImpulsePosition(ChargeColor color) {
        return impulsePositions.get(color);
    }

    private int calculateTotalCharge(ChargeColor color) {
        return cabelStorage.getCabels().get(color).length * colorsParameters.get(color).costByCell;
    }


    private void impulseTask(ChargeColor color) {
        if (impulsePositions.get(color) == -1) {
            int impulseCost = calculateTotalCharge(color);
            int res = totalChargeCapacity.addAndGet(-impulseCost);
            if (res < 0) {
                throw new IllegalArgumentException();
            }
        }
        if (impulsePositions.get(color) == cabelStorage.getCabels().get(color).length) {
            impulsePositions.put(color, -2);
            totalChargeCapacity.addAndGet(calculateTotalCharge(color));
        }
        impulsePositions.put(color, impulsePositions.get(color) + 1);
    }
}
