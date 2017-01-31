package com.autoplanner;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by zrzzg on 1/30/2017.
 */

public class TaskTouchHelper extends ItemTouchHelper.SimpleCallback {
    private RecyclerAdapter recyclerAdapter;

    public TaskTouchHelper(RecyclerAdapter recyclerAdapter){
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.recyclerAdapter = recyclerAdapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //TODO: Not implemented here
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //Remove item
        recyclerAdapter.remove(viewHolder.getAdapterPosition());
    }
}
