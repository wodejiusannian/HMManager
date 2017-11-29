package manager.love.i.hmmanager.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Bob on 2016/8/10.
 */
public class RvDecorationUtils extends RecyclerView.ItemDecoration {

    private int mPadding;

    public RvDecorationUtils(int padding){
        mPadding = padding;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = mPadding;
        outRect.top = mPadding;
        outRect.right = mPadding;
        outRect.left = mPadding;
    }

}
