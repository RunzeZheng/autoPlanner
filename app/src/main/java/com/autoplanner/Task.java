package com.autoplanner;

/**
 * Created by zrzzg on 12/27/2016.
 */

public class Task {
    private String what, where, deadline;
    private int order;
    private double duration;
    private long deadlindMilli, durationMilli, timeLeftAfterDone;
    private boolean overDue;

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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public long getTimeLeftAfterDone() {
        return timeLeftAfterDone;
    }

    public void setTimeLeftAfterDone(long timeLeftAfterDone) {
        this.timeLeftAfterDone = timeLeftAfterDone;
    }

    public long getDurationMilli() {
        return durationMilli;
    }

    public void setDurationMilli(long durationMilli) {
        this.durationMilli = durationMilli;
    }

    public long getDeadlindMilli() {
        return deadlindMilli;
    }

    public void setDeadlindMilli(long deadlindMilli) {
        this.deadlindMilli = deadlindMilli;
    }

    public boolean isOverDue() {
        return overDue;
    }

    public void setOverDue(boolean overDue) {
        this.overDue = overDue;
    }
}
