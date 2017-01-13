package com.autoplanner;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zrzzg on 12/14/2016.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private boolean verticalOrientation;
    private int space;

    public DividerItemDecoration(int value, boolean verticalOrientation) {
        this.space = value;
        this.verticalOrientation = verticalOrientation;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getChildAdapterPosition(view) != 0) {
            if (verticalOrientation) {
                outRect.set(10, 20, 20, 10);
            }
        }
    }
}
