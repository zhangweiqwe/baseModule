package cn.wsgwz.basemodule.widgets.decoration;

import android.graphics.Rect;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import cn.wsgwz.basemodule.utilities.DensityUtil;
import cn.wsgwz.basemodule.utilities.LLog;


public class GridDividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "GridDividerItem";
    private float spacing;
    private boolean includeEdge;

    public GridDividerItemDecoration(float spacing) {
        this(spacing, false);
    }

    public GridDividerItemDecoration(float spacing, boolean includeEdge) {
        this.spacing = DensityUtil.dp2px( spacing);
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //Logger.t(TAG).d(outRect.left+"\t"+outRect.top+"\t"+outRect.right+"\t"+outRect.bottom  +"\n"+(outRect.bottom-outRect.top)+"\t"+(outRect.right-outRect.left));//319 456
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            int position = parent.getChildAdapterPosition(view); // item position

            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = (int) (spacing - column * spacing / spanCount); // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (int) ((column + 1) * spacing / spanCount); // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = (int) spacing;
                }
                outRect.bottom = (int) spacing; // item bottom
            } else {
                outRect.left = (int) (column * spacing / spanCount); // column * ((1f / spanCount) * spacing)
                outRect.right = (int) (spacing - (column + 1) * spacing / spanCount); // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = (int) spacing; // item top
                }
            }

            LLog.d(TAG,position + "\t\t" + outRect.left + "\t" + outRect.top + "\t" + outRect.right + "\t" + outRect.bottom);//319 456

        }


    }
}
