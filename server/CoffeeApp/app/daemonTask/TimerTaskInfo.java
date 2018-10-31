package daemonTask;

import java.time.ZonedDateTime;
import java.util.Timer;

public class TimerTaskInfo {

    private Timer jobTimer;
    private Timer daemonTimer;
    private ZonedDateTime timeUpdate;
    private boolean status;
    private int times = 0;
    private int periodTime = 86400000;
    private int delayTime = 50000;

    public TimerTaskInfo(ZonedDateTime timeUpdate, Timer originTimer) {
        this.status = true;
        this.timeUpdate = timeUpdate;
        this.daemonTimer = originTimer;
        this.jobTimer = new Timer();
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Timer getDaemonTimer() {
        return daemonTimer;
    }

    public void setDaemonTimer(Timer daemonTimer) {
        this.daemonTimer = daemonTimer;
    }

    public Timer getJobTimer() {
        return jobTimer;
    }

    public void setJobTimer(Timer jobTimer) {
        this.jobTimer = jobTimer;
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

    public void stopTimerJob(){
        this.jobTimer.cancel();
        this.jobTimer.purge();
    }

    public void stopTimerDaemon(){
        this.daemonTimer.cancel();
        this.daemonTimer.purge();
    }


}
