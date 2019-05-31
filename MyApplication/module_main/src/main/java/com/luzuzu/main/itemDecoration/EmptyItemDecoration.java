package com.luzuzu.main.itemDecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by fula on 2019/5/20.
 */

public class EmptyItemDecoration extends RecyclerView.ItemDecoration {
    private int paddingTop;

    public EmptyItemDecoration(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if (position!= 0) {
            outRect.top = paddingTop;
        }
    }
}
