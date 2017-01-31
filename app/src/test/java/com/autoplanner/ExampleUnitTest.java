package com.autoplanner;

import android.util.ArraySet;

import static org.mockito.Mockito.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    public ExampleUnitTest(){

    }

    public static void main(String args[]) {
        ArraySet<Task> mockedArraySet = mock(ArraySet.class);

        /*Task a = new Task();
        a.setWhat("task1");
        a.setDeadline("2017/01/30 21:00");
        a.setDuration(1);
        a.setWhere("place1");
        a.setOrder(-1);

        Task b = new Task();
        b.setWhat("task2");
        b.setDeadline("2017/01/30 22:00");
        b.setDuration(2);
        b.setWhere("place2");
        b.setOrder(-1);

        Task c = new Task();
        c.setWhat("task3");
        c.setDeadline("2017/01/30 23:59");
        c.setDuration(0.5);
        c.setWhere("place3");
        c.setOrder(-1);

        mockedArraySet.add(c);
        mockedArraySet.add(a);
        mockedArraySet.add(b);

        when(mockedArraySet.valueAt(0)).thenReturn(a);
        when(mockedArraySet.valueAt(1)).thenReturn(b);
        when(mockedArraySet.valueAt(2)).thenReturn(c);

        Sorter s = new Sorter();
        s.optimizedSort(mockedArraySet);*/
    }
}