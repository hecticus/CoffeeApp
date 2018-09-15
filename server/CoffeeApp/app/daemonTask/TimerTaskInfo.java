package daemonTask;

import java.time.ZonedDateTime;
import java.util.Timer;

public class TimerTaskInfo {

    private ZonedDateTime timeUpdate;
    private boolean status;
    private int times = 0;
    private Timer originTimer;
    private int periodTime = 100000;
    private int delayTime = 50000;

    public TimerTaskInfo(ZonedDateTime timeUpdate, Timer originTimer) {
        this.timeUpdate = timeUpdate;
        this.originTimer = originTimer;
        this.status = true;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setTimes(int newTimes) {
        this.times = newTimes;
    }

    public int getTimes() {
        return this.times;
    }

    public ZonedDateTime getTimeUpdate() {
        return timeUpdate;
    }

    public void setTimeUpdate(ZonedDateTime timeUpdate) {
        this.timeUpdate = timeUpdate;
    }

    public Timer getOriginTimer() {
        return originTimer;
    }

    public void setOriginTimer(Timer originTimer) {
        this.originTimer = originTimer;
    }

    public void increment() {
        this.times++;
    }

    public int getPeriodTime() {
        return periodTime;
    }

    public void setPeriodTime(int periodTime) {
        this.periodTime = periodTime;
    }

    public int getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }
}
