package maven_robots.logic.fields.cabels.impulses;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import maven_robots.logic.ChargeColor;
import maven_robots.logic.fields.cabels.ICabelStorage;

public class ImpulseManager implements IImpulseManager {

    private final ICabelStorage cabelStorage;
    private final int maxChargeCapacity;
    private final AtomicInteger currentCharge;
    private volatile ConcurrentHashMap<ChargeColor, TimerTask> impulseMoveTasks;
    private volatile ConcurrentHashMap<ChargeColor, ImpulseTaskData> impulseDatas;
    private final Timer timer;

    public ImpulseManager(ICabelStorage cabelStorage, int totalChargeCapacity) {
        this.cabelStorage = cabelStorage;
        maxChargeCapacity = totalChargeCapacity;
        currentCharge = new AtomicInteger(totalChargeCapacity);
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
                    currentCharge.addAndGet(impulseDatas.get(color).chargeVolume);
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
            int res = currentCharge.get();
            if (res < impulseDatas.get(color).chargeVolume) {
                throw new IllegalArgumentException(); //TODO нужен какой-то ивент
            } 
            currentCharge.addAndGet(-impulseDatas.get(color).chargeVolume);
        }
        if (impulseDatas.get(color).isImpulseReachEndOfCabel()) {
            currentCharge.addAndGet(impulseDatas.get(color).chargeVolume);
        }
        impulseDatas.get(color).moveImpulseForward();
    }

    public int getMaxChargeCapacity() {
        return maxChargeCapacity;
    }

    public int getCurrentCharge() {
        return currentCharge.get();
    }

    
}
