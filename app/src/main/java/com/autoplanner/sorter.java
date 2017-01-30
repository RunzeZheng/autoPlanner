package com.autoplanner;

import android.util.ArraySet;

/**
 * Created by zrzzg on 12/29/2016.
 */
public class sorter {
    private String endTime;

    public boolean doabilityCheck(Task t) {
        long year, month, day, hour, minute;
        long deadline, duration, currentTime;

        currentTime = System.currentTimeMillis();

        //deadline to milliseconds
        endTime = t.getDeadline();//format: 2017/01/01 23:59
        year = Integer.parseInt(endTime.substring(0, 4));//year
        month = Integer.parseInt(endTime.substring(5, 7));//month
        day = Integer.parseInt(endTime.substring(8, 10));//day
        hour = Integer.parseInt(endTime.substring(11, 13));//hour
        minute = Integer.parseInt(endTime.substring(14, 16));//minute
        deadline = (year - 1970) * 31556952000L + (month - 1) * 2592000000L + (day - 1) * 86400000L
                + hour * 3600000 + minute * 60000;
        t.setDeadlindMilli(deadline);

        //duration to milliseconds
        duration = t.getDuration();
        long durationInMilliseconds = duration * 3600000;
        t.setDurationMilli(durationInMilliseconds);

        //don't allow to add task if deadline is in the past or time is not enough to finish the task
        return !(deadline < currentTime || currentTime + durationInMilliseconds > deadline);
    }


    public ArraySet<Task> optimizedSort(ArraySet<Task> taskList) {
        ArraySet<Task> result = taskList;
        String endTime;
        long year, month, day, hour, minute;
        long currentTime = System.currentTimeMillis();

        for (Task task : taskList
                ) {

            task.setTimeLeftAfterDone(task.getDeadlindMilli() - currentTime - task.getDurationMilli());
        }
        return result;
    }
}
