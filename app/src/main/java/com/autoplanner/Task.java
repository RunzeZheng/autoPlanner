package com.autoplanner;

/**
 * Created by zrzzg on 12/27/2016.
 */

public class Task {
    private String what, where, deadline;
    private double duration, durationMilli, timeLeftAfterDone, deadlineMilli;
    private boolean overDue = false, mode = false;

    public Task(){
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public double getTimeLeftAfterDone() {
        return timeLeftAfterDone;
    }

    public void setTimeLeftAfterDone(double timeLeftAfterDone) {
        this.timeLeftAfterDone = timeLeftAfterDone;
    }

    public double getDurationMilli() {
        return durationMilli;
    }

    public void setDurationMilli(double durationMilli) {
        this.durationMilli = durationMilli;
    }

    public double getDeadlineMilli() {
        return deadlineMilli;
    }

    public void setDeadlineMilli(double deadlineMilli) {
        this.deadlineMilli = deadlineMilli;
    }

    public boolean isOverDue() {
        return overDue;
    }

    public void setOverDue(boolean overDue) {
        this.overDue = overDue;
    }

    public boolean isMode() {
        return mode;
    }

    public void setMode(boolean mode) {
        this.mode = mode;
    }
}
