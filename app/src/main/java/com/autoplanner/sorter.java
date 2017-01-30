package com.autoplanner;

import android.util.ArraySet;

import java.util.ArrayList;

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
        ArraySet<Task> workingTaskList = taskList;
        ArraySet<Task> result = new ArraySet<>();
        ArrayList<Task> temp = new ArrayList<>(), temp2 = new ArrayList<>(), temp3 = new ArrayList<>();
        String endTime;
        long year, month, day, hour, minute;
        long currentTime = System.currentTimeMillis();
        int order = 0;

        //update the variable timeLeftAfterDone and overDue
        for (Task t : workingTaskList
                ) {
            if (t.getDeadlindMilli() < currentTime) {
                t.setOverDue(true);
            } else {
                t.setOverDue(false);
            }

            t.setTimeLeftAfterDone(t.getDeadlindMilli() - currentTime - t.getDurationMilli());
        }


        while (!workingTaskList.isEmpty()) {
            long smallest = workingTaskList.valueAt(0).getTimeLeftAfterDone();
            temp.clear();
            temp2.clear();
            temp3.clear();

            //sort by timeLeftAfterDone first
            for (int i = 0; i < workingTaskList.size(); i++) {
                if (workingTaskList.valueAt(i).getTimeLeftAfterDone() < smallest) {
                    smallest = workingTaskList.valueAt(i).getTimeLeftAfterDone();
                    temp.clear();
                } else if (workingTaskList.valueAt(i).getTimeLeftAfterDone() == smallest) {
                    temp.add(workingTaskList.valueAt(i));
                    workingTaskList.removeAt(i);
                }
            }

            while (!temp.isEmpty()) {
                //then sort by deadline
                if (temp.size() > 1) {
                    long smallestDeadline = temp.get(0).getDeadlindMilli();
                    for (int i = 0; i < temp.size(); i++) {
                        if (temp.get(i).getDeadlindMilli() < smallestDeadline) {
                            smallestDeadline = temp.get(i).getDeadlindMilli();
                            temp2.clear();
                        } else if (temp.get(i).getDeadlindMilli() == smallestDeadline) {
                            temp2.add(temp.get(i));
                            temp.remove(i);
                        }
                    }
                } else if (temp.size() == 1) {
                    result.add(temp.get(0));
                    temp.remove(0);
                }

                while (!temp2.isEmpty()) {
                    //lastly sort by duration
                }
            }
        }

        return workingTaskList;
    }

}
