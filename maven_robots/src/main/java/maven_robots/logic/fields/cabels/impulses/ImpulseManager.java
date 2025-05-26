package maven_robots.logic.fields.cabels.impulses;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import maven_robots.logic.ChargeColor;
import maven_robots.logic.fields.FieldObserver;
import maven_robots.logic.fields.IObservable;
import maven_robots.logic.fields.cabels.ICabelStorage;

public class ImpulseManager implements IImpulseManager {

    private final ICabelStorage cabelStorage;
    private final int maxChargeCapacity;
    private final AtomicInteger currentCharge;
    private volatile ConcurrentHashMap<ChargeColor, TimerTask> impulseMoveTasks;
    private volatile ConcurrentHashMap<ChargeColor, ImpulseTaskData> impulseDatas;
    private final Timer timer;

    private final List<FieldObserver> observers;

    public ImpulseManager(ICabelStorage cabelStorage, int totalChargeCapacity, List<FieldObserver> observers) {
        this.observers = observers;
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
                    notifyObservers();
                    return;
                }
                if (impulseDatas.get(color).getImpulsePosition() != 0) {
                    currentCharge.addAndGet(impulseDatas.get(color).chargeVolume);
                    notifyObservers();
                }
                impulseDatas.remove(color);
                this.cancel();
            }
        };
        impulseMoveTasks.put(color, newColorTask);
        timer.schedule(newColorTask, 10,
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

    @Override
    public void addObserver(FieldObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(FieldObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (FieldObserver observer : observers) {
            observer.onFieldChanged();
        }
    }
}
