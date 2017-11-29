package manager.love.i.hmmanager.common.widgets.recycle;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.utils.RvDecorationUtils;

// ┏┓　　　┏┓
// ┏┛┻━━━┛┻┓
// ┃　　　　　　　┃ 　
// ┃　　　━　　　┃
// ┃　┳┛　┗┳　┃
// ┃　　　　　　　┃
// ┃　　　┻　　　┃
// ┃　　　　　　　┃
// ┗━┓　　　┏━┛
// ┃　　　┃ 神兽保佑　　　　　　　　
// ┃　　　┃ 代码无BUG！
// ┃　　　┗━━━┓
// ┃　　　　　　　┣┓
// ┃　　　　　　　┏┛
// ┗┓┓┏━┳┓┏┛
// ┃┫┫　┃┫┫
// ┗┻┛　┗┻┛
public class ThreeRecycleColorView extends RecyclerView {
    private List<String> mData;
    private BaseRecycleAdapter<String> adapter;
    private SelectItem mSelectItem;
    private List<Boolean> booleens;

    public ThreeRecycleColorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mData = new ArrayList<>();
        booleens = new ArrayList<>();
        GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        adapter = new BaseRecycleAdapter<String>(getContext(), mData, R.layout.item_three_recycle) {
            @Override
            public void bindData(BaseViewHolder holder, final String s, final int position) {
                TextView sort = holder.getView(R.id.item_three_recycle_sort);
                sort.setText(s);
                if (booleens.get(position)) {
                    sort.setBackgroundResource(R.drawable.shape_radios_red_4dp);
                    sort.setTextColor(Color.parseColor("#ffffff"));
                } else {
                    sort.setBackgroundResource(R.drawable.shape_radios_gary_4dp);
                    sort.setTextColor(Color.parseColor("#000000"));
                }
                holder.getRootView().setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        booleens.clear();
                        if (mSelectItem != null)
                            mSelectItem.selectItem(s);
                        for (int i = 0; i < mData.size(); i++) {
                            if (i == position) {
                                booleens.add(true);
                            } else {
                                booleens.add(false);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        };
        this.setLayoutManager(manager);
        this.setAdapter(adapter);
        this.addItemDecoration(new RvDecorationUtils(10));
    }

    public void setFreshData(List<String> data, SelectItem selectItem) {
        if (data != null && selectItem != null) {
            mSelectItem = selectItem;
            mData.clear();
            mData.addAll(data);
            for (int i = 0; i < mData.size(); i++) {
                booleens.add(false);
            }
            adapter.notifyDataSetChanged();
        }
    }



    public interface SelectItem {
        void selectItem(String s);
    }

    public void reset(List<String> data) {
        mData.clear();
        booleens.clear();
        mData.addAll(data);
        for (int i = 0; i < mData.size(); i++) {
            booleens.add(false);
        }
        adapter.notifyDataSetChanged();
    }

    public void resetSort(List<String> data, String str) {
        mData.clear();
        booleens.clear();
        mData.addAll(data);
        for (int i = 0; i < mData.size(); i++) {
            if (str.equals(mData.get(i)))
                booleens.add(true);
            else
                booleens.add(false);
        }
        adapter.notifyDataSetChanged();
    }
}
