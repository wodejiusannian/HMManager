package manager.love.i.hmmanager.common.widgets.popupwindow;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.bean.ShopTag;
import manager.love.i.hmmanager.common.widgets.recycle.BaseRecycleAdapter;
import manager.love.i.hmmanager.common.widgets.recycle.BaseViewHolder;

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

public class SelectPopupWindow extends PopupWindow {
    private static final String TAG = "SelectPopupWindow";
    private List<ShopTag.BodyBean> mData;

    private SelectCategory mSelectCategory;

    private RecyclerView listView;

    private BaseRecycleAdapter<ShopTag.BodyBean> adapter;

    public SelectPopupWindow(Activity activity, SelectCategory selectCategory) {
        mData = new ArrayList<>();
        this.mSelectCategory = selectCategory;
        View contentView = LayoutInflater.from(activity).inflate(R.layout.popup_window_select, null);
        this.setContentView(contentView);
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm); // 获取手机屏幕的大小
        this.setWidth(dm.widthPixels);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        /*//* 设置背景显示 *//*
        setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.pop_bg));*/
        /* 设置触摸外面时消失 */
        setOutsideTouchable(true);
        setTouchable(true);
        setFocusable(true); /*设置点击menu以外其他地方以及返回键退出 */
        /**
         * 1.解决再次点击MENU键无反应问题
         */
        contentView.setFocusableInTouchMode(true);
        listView = (RecyclerView) contentView.findViewById(R.id.pop_list_select_category);
        adapter = new BaseRecycleAdapter<ShopTag.BodyBean>(activity, mData, R.layout.item_popupwindow_select) {
            @Override
            public void bindData(BaseViewHolder holder, final ShopTag.BodyBean stringInt, final int position) {
                TextView textView = holder.getView(R.id.item_pop_window_name);
                LinearLayout select = (LinearLayout) holder.getRootView();
                textView.setText(stringInt.getErji_name());

                select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSelectCategory.selectCategory(stringInt);
                        dismiss();
                    }
                });
            }
        };
        listView.setLayoutManager(new LinearLayoutManager(activity));
        listView.setAdapter(adapter);
    }

    public void setNotifyData(List<ShopTag.BodyBean> data) {
        mData.clear();
        if (data != null)
            mData.addAll(data);
        adapter.notifyDataSetChanged();
    }

    public interface SelectCategory {
        /*
        * 将选中的下标发送回去
        * params selectPositon
        * */
        void selectCategory(ShopTag.BodyBean selectPosition);
    }
}
