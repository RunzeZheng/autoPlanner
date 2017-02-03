package com.autoplanner;

import android.util.ArraySet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by zrzzg on 12/29/2016.
 */
public class Sorter {

    public boolean doabilityCheck(Task t) {
        String endTime, year, month, day, hour, minute;
        long currentTimeInMillis;
        double  duration;
        boolean switchedMode;

        currentTimeInMillis = System.currentTimeMillis();

        //deadline to milliseconds
        endTime = t.getDeadline();//format: 2017/01/01 23:59
        year = endTime.substring(0, 4);//year
        month = endTime.substring(5, 7);//month
        day = endTime.substring(8, 10);//day
        hour = endTime.substring(11, 13);//hour
        minute = endTime.substring(14, 16);//minute
        switchedMode = t.isMode();//startingAt or doneBy mode
        String myDate = year+"/"+month+"/"+day+"/"+" "+hour+":"+minute;
        Date deadlineDate = new Date(myDate);
        long deadlineTimeInMillis = deadlineDate.getTime();
        t.setDeadlineMilli(deadlineTimeInMillis);

        //duration to milliseconds
        duration = t.getDuration();
        double durationInMilliseconds = duration * 3600000;
        t.setDurationMilli(durationInMilliseconds);

        if (switchedMode){
            return !(deadlineTimeInMillis < currentTimeInMillis);
        }else {
            //don't allow to add task if deadline is in the past or time is not enough to finish the task
            return !(deadlineTimeInMillis < currentTimeInMillis || currentTimeInMillis + durationInMilliseconds > deadlineTimeInMillis);
        }
    }


    public ArrayList<Task> optimizedSort(ArrayList<Task> taskList) {
        ArrayList<Task> workingTaskList = taskList, result = new ArrayList<>();
        ArrayList<Task> temp = new ArrayList<>(), temp2 = new ArrayList<>(), temp3 = new ArrayList<>();
        ArrayList<Integer> trashIndex = new ArrayList<>();
        double currentTime = System.currentTimeMillis();
        int order = 0;

        //update the variable timeLeftAfterDone and overDue
        for (Task t : workingTaskList
                ) {
            if (t.getDeadlineMilli() < currentTime) {
                t.setOverDue(true);
            } else {
                t.setOverDue(false);
            }

            t.setTimeLeftAfterDone(t.getDeadlineMilli() - currentTime - t.getDurationMilli());
        }

        while (!workingTaskList.isEmpty()) {
            double smallest = workingTaskList.get(0).getTimeLeftAfterDone();
            temp.clear();
            temp2.clear();
            temp3.clear();

            //sort by timeLeftAfterDone first
            int workingTaskListSize = workingTaskList.size();
            for (int i = 0; i < workingTaskListSize; i++) {
                if (workingTaskList.get(i).getTimeLeftAfterDone() < smallest) {
                    smallest = workingTaskList.get(i).getTimeLeftAfterDone();
                    temp.clear();
                    trashIndex.clear();
                    temp.add(workingTaskList.get(i));
                    trashIndex.add(i);
                } else if (workingTaskList.get(i).getTimeLeftAfterDone() == smallest) {
                    temp.add(workingTaskList.get(i));
                    trashIndex.add(i);
                    //workingTaskList.remove(i);
                }
            }
            workingTaskList = removeTrash(workingTaskList, trashIndex);
            trashIndex.clear();

                while (!temp.isEmpty()) {
                    //then sort by deadline second
                    if (temp.size() > 1) {
                        double smallestDeadline = temp.get(0).getDeadlineMilli();
                        int tempSize = temp.size();
                        for (int i = 0; i < tempSize; i++) {
                            if (temp.get(i).getDeadlineMilli() < smallestDeadline) {
                                smallestDeadline = temp.get(i).getDeadlineMilli();
                                temp2.clear();
                                trashIndex.clear();
                                temp2.add(workingTaskList.get(i));
                                trashIndex.add(i);
                            } else if (temp.get(i).getDeadlineMilli() == smallestDeadline) {
                                temp2.add(temp.get(i));
                                trashIndex.add(i);
                            }
                        }
                        temp = removeTrash(temp, trashIndex);
                        trashIndex.clear();
                    } else if (temp.size() == 1) {
                        result.add(temp.get(0));
                        temp.remove(0);
                    }

                    while (!temp2.isEmpty()) {
                        //sort by duration third
                        if (temp2.size() > 1) {
                            double smallestDuration = temp2.get(0).getDurationMilli();
                            int temp2Size = temp2.size();
                            for (int i = 0; i < temp2Size; i++) {
                                if (temp2.get(i).getDurationMilli() < smallestDuration) {
                                    smallestDuration = temp2.get(i).getDurationMilli();
                                    temp3.clear();
                                    trashIndex.clear();
                                    temp3.add(workingTaskList.get(i));
                                    trashIndex.add(i);
                                } else if (temp2.get(i).getDurationMilli() == smallestDuration) {
                                    temp3.add(temp2.get(i));
                                    trashIndex.add(i);
                                }
                            }
                            temp2 = removeTrash(temp2, trashIndex);
                            trashIndex.clear();
                        } else if (temp2.size() == 1) {
                            result.add(temp2.get(0));
                            temp2.remove(0);
                        }
                    }
                }
        }

        return result;
    }

    private ArrayList<Task> removeTrash(ArrayList<Task> working,ArrayList<Integer> trash){
        Collections.sort(trash);
        for (int i = trash.size()-1; i >= 0; i--) {
            working.remove((int) trash.get(i));
        }
        return working;
    }
}
