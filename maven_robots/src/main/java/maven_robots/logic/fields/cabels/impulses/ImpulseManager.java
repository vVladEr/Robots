package maven_robots.logic.fields.cabels.impulses;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import maven_robots.logic.ChargeColor;
import maven_robots.logic.fields.cabels.ICabelStorage;

public class ImpulseManager implements IImpulseManager {

    private final ICabelStorage cabelStorage;
    private final AtomicInteger totalChargeCapacity;
    private volatile ConcurrentHashMap<ChargeColor, TimerTask> impulseMoveTasks;
    private volatile ConcurrentHashMap<ChargeColor, ImpulseTaskData> impulseDatas;
    private final Timer timer;

    public ImpulseManager(ICabelStorage cabelStorage, int totalChargeCapacity) {
        this.cabelStorage = cabelStorage;
        this.totalChargeCapacity = new AtomicInteger(totalChargeCapacity);
        impulseMoveTasks = new ConcurrentHashMap<ChargeColor, TimerTask>();
        impulseDatas = new ConcurrentHashMap<ChargeColor, ImpulseTaskData>();
        timer = new Timer();
    }

    public void addImpulse(ChargeColor color) {
        impulseDatas.put(color, 
            new ImpulseTaskData(color,
                cabelStorage.getCabels().get(color).length,
                calculateTotalCharge(color)));

        TimerTask newColorTask = new TimerTask() {
            @Override
            public void run() {
                if (impulseMoveTasks.containsKey(color)) {
                    impulseTask(color);
                    return;
                }
                if (impulseDatas.get(color).getImpulsePosition() != 0) {
                    totalChargeCapacity.addAndGet(impulseDatas.get(color).chargeVolume);
                    impulseDatas.remove(color);
                    cancel();
                }
            }
        };
        impulseMoveTasks.put(color, newColorTask);
        timer.schedule(newColorTask, 0,
            ColorImpulseParameters.getByColorOrDefault(color).moveDelayMilliseconds);
    }

    public void removeImpulse(ChargeColor color) {
        impulseMoveTasks.remove(color);
    }

    public int getImpulsePosition(ChargeColor color) {
        return impulseDatas.get(color).getImpulsePosition();
    }

    private int calculateTotalCharge(ChargeColor color) {
        return cabelStorage.getCabels().get(color).length 
            * ColorImpulseParameters.getByColorOrDefault(color).costByCell;
    }


    private void impulseTask(ChargeColor color) {
        if (impulseDatas.get(color).getImpulsePosition() == 0) {
            int res = totalChargeCapacity.addAndGet(-impulseDatas.get(color).chargeVolume);
            if (res < 0) {
                throw new IllegalArgumentException(); //TODO нужен какой-то ивент
            } 
        }
        if (impulseDatas.get(color).isImpulseReachEndOfCabel()) {
            totalChargeCapacity.addAndGet(impulseDatas.get(color).chargeVolume);
        }
        impulseDatas.get(color).moveImpulseForward();
    }
}
