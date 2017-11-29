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
import manager.love.i.hmmanager.bean.ShopTag;
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
public class FourRecycleView extends RecyclerView {
    private List<ShopTag.BodyBean> mData;
    private BaseRecycleAdapter<ShopTag.BodyBean> adapter;
    private SelectItem mSelectItem;
    private List<Boolean> booleens;

    public FourRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mData = new ArrayList<>();
        booleens = new ArrayList<>();
        GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        adapter = new BaseRecycleAdapter<ShopTag.BodyBean>(getContext(), mData, R.layout.item_three_recycle) {
            @Override
            public void bindData(BaseViewHolder holder, final ShopTag.BodyBean s, final int position) {
                TextView sort = holder.getView(R.id.item_three_recycle_sort);
                String erji_name = s.getErji_name();
                if (erji_name.length() > 3) {
                    sort.setTextSize(8);
                    sort.setText(erji_name);
                } else {
                    sort.setTextSize(13);
                    sort.setText(erji_name);
                }
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

    public void setFreshData(List<ShopTag.BodyBean> data, SelectItem selectItem) {
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
        void selectItem(ShopTag.BodyBean s);
    }

    public void reset(List<ShopTag.BodyBean> data) {
        mData.clear();
        booleens.clear();
        mData.addAll(data);
        for (int i = 0; i < mData.size(); i++) {
            booleens.add(false);
        }
        adapter.notifyDataSetChanged();
    }

    public void resetSort(List<ShopTag.BodyBean> data, String str) {
        mData.clear();
        booleens.clear();
        mData.addAll(data);
        for (int i = 0; i < mData.size(); i++) {
            String name = mData.get(i).getErji_name();
            if (str.equals(name))
                booleens.add(true);
            else
                booleens.add(false);
        }
        adapter.notifyDataSetChanged();
    }
}
