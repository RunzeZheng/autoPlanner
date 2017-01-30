package com.autoplanner;

import android.util.ArraySet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by zrzzg on 12/29/2016.
 */
public class sorter {

    public boolean doabilityCheck(Task t) {
        String endTime;
        String year, month, day, hour, minute;
        long deadline, currentTimeInMillis;
        double  duration;

        currentTimeInMillis = System.currentTimeMillis();

        //deadline to milliseconds
        endTime = t.getDeadline();//format: 2017/01/01 23:59
        year = endTime.substring(0, 4);//year
        month = endTime.substring(5, 7);//month
        day = endTime.substring(8, 10);//day
        hour = endTime.substring(11, 13);//hour
        minute = endTime.substring(14, 16);//minute
        String myDate = year+"/"+month+"/"+day+"/"+" "+hour+":"+minute;
        Date deadlineDate = new Date(endTime);

        //duration to milliseconds
        duration = t.getDuration();
        long durationInMilliseconds =(long) duration * 3600000;
        t.setDurationMilli(durationInMilliseconds);

        long deadlineTimeInMillis = deadlineDate.getTime();
        t.setDeadlindMilli(deadlineTimeInMillis);
        //don't allow to add task if deadline is in the past or time is not enough to finish the task
        return !(deadlineTimeInMillis < currentTimeInMillis || currentTimeInMillis + durationInMilliseconds > deadlineTimeInMillis);
    }


    public ArraySet<Task> optimizedSort(ArraySet<Task> taskList) {
        ArraySet<Task> workingTaskList = taskList, result = new ArraySet<>();
        ArrayList<Task> temp = new ArrayList<>(), temp2 = new ArrayList<>(), temp3 = new ArrayList<>();
        String endTime;
        long year, month, day, hour, minute, currentTime = System.currentTimeMillis();
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
                    if (temp2.size() > 1){
                        long smallestDuration = temp2.get(0).getDurationMilli();
                        for (int i = 0; i < temp2.size(); i++) {
                            if (temp2.get(i).getDurationMilli() < smallestDuration){
                                smallestDuration = temp2.get(i).getDurationMilli();
                                temp3.clear();
                            }else if (temp2.get(i).getDurationMilli() == smallestDuration){
                                temp3.add(temp2.get(i));
                                temp2.remove(i);
                            }
                        }
                    } else if (temp2.size() == 1){
                        result.add(temp2.get(0));
                        temp2.remove(0);
                    }
                }
            }
        }

        return workingTaskList;
    }

}
