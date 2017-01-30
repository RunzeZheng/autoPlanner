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
    private ArraySet<String> deadline = new ArraySet<>();
    private ArraySet<Double> duration = new ArraySet<>();
    private ArraySet<String> what = new ArraySet<>();
    private ArraySet<String> where = new ArraySet<>();

    public RecyclerAdapter(ArraySet<Task> taskList) {
        if (taskList.size() != 0) {
            for (int i = 0; i < taskList.size(); i++) {
                what.add(taskList.valueAt(i).getWhat());
                deadline.add(taskList.valueAt(i).getDeadline());
                duration.add(taskList.valueAt(i).getDuration());
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
            holder.bindTask(what.valueAt(position), deadline.valueAt(position), duration.valueAt(position), where.valueAt(position));
        } catch (ArrayIndexOutOfBoundsException e) {
            holder.bindTask(what.valueAt(position), null, null, null);
        }
    }

    @Override
    public int getItemCount() {
        return what.size();
    }

    public static class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mItemWhat;
        private TextView mItemDeadline;
        private TextView mItemDuration;
        private TextView mItemWhere;

        public TaskHolder(View v) {
            super(v);

            mItemWhat = (TextView) v.findViewById(R.id.item_description);
            mItemDeadline = (TextView) v.findViewById(R.id.item_deadline);
            mItemDuration = (TextView) v.findViewById(R.id.item_duration);
            mItemWhere = (TextView) v.findViewById(R.id.item_location);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context = itemView.getContext();
            Intent showTaskIntent = new Intent(context, DetailView.class);
            showTaskIntent.putExtra("what", mItemWhat.getText());
            showTaskIntent.putExtra("deadline", mItemDeadline.getText());
            showTaskIntent.putExtra("duration", mItemDuration.getText());
            showTaskIntent.putExtra("where", mItemWhere.getText());
            context.startActivity(showTaskIntent);
        }

        public void bindTask(String what, String when, Double duration, String where) {
            mItemWhat.setText(what);
            mItemDeadline.setText(when);
            mItemDuration.setText(duration.toString() + "hours");
            mItemWhere.setText(where);
        }
    }
}
