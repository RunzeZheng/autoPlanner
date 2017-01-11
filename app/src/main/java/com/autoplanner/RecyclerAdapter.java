package com.autoplanner;

/**
 * Created by zrzzg on 11/9/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.TaskHolder> {
    private ArraySet<String> when = new ArraySet<>();
    private ArraySet<String> what = new ArraySet<>();
    private ArraySet<String> where = new ArraySet<>();

    public static class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mItemWhat;
        private TextView mItemWhen;
        private TextView mItemWhere;

        public TaskHolder(View v) {
            super(v);

            mItemWhen = (TextView) v.findViewById(R.id.item_duration);
            mItemWhat = (TextView) v.findViewById(R.id.item_description);
            mItemWhere = (TextView) v.findViewById(R.id.item_location);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context = itemView.getContext();
            Intent showTaskIntent = new Intent(context, DetailView.class);
            showTaskIntent.putExtra("what", mItemWhat.getText());
            showTaskIntent.putExtra("when", mItemWhen.getText());
            showTaskIntent.putExtra("where", mItemWhere.getText());
            context.startActivity(showTaskIntent);
        }

        public void bindTask(String what, String when, String where) {
            mItemWhat.setText(what);
            mItemWhen.setText(when);
            mItemWhere.setText(where);
        }
    }

    public RecyclerAdapter(ArraySet<Task> taskList) {
        if (taskList.size() != 0) {
            for (int i = 0; i < taskList.size(); i++) {
                what.add(taskList.valueAt(i).getWhat());
                when.add(taskList.valueAt(i).getWhen());
                where.add(taskList.valueAt(i).getWhere());
            }
        }
    }

    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_row, parent, false);
        return new TaskHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(TaskHolder holder, int position) {
        try {
            holder.bindTask(what.valueAt(position), when.valueAt(position), where.valueAt(position));
        }catch (ArrayIndexOutOfBoundsException e){
            holder.bindTask(what.valueAt(position), null, null);
        }
    }

    @Override
    public int getItemCount() {
        return what.size();
    }
}
