package manager.love.i.hmmanager.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import manager.love.i.hmmanager.R;


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
public class PayListView extends ListView {

    private List<PayState> mData = new ArrayList<>();
    private List<Boolean> isCheckPay = new ArrayList<>();
    private PayAdapter adapter;
    private PayStates payState;

    public PayListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initListView();
        setListener();
    }

    public void getPos(PayStates mPayState) {
        payState = mPayState;
    }

    private void setListener() {

    }

    private void initListView() {
        if (mData == null)
            mData = new ArrayList<>();

        if (isCheckPay == null)
            isCheckPay = new ArrayList<>();
        adapter = new PayAdapter();
        setAdapter(adapter);

    }

    public void setData(List<PayState> data) {
        mData.addAll(data);
        for (int i = 0; i < mData.size(); i++) {
            if (i == 0) {
                isCheckPay.add(true);
            } else {
                isCheckPay.add(false);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private class PayAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mData.size() == 0 ? 0 : mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder h;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_listview_item, null);
                h = new ViewHolder(convertView);
                convertView.setTag(h);
            } else {
                h = (ViewHolder) convertView.getTag();
            }
            final PayState pa = mData.get(position);
            final String type = pa.type;

            h.isCheck.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (payState != null)
                        payState.state(type);

                    isCheckPay.clear();
                    for (int i = 0; i < mData.size(); i++) {
                        if (i == position) {
                            isCheckPay.add(true);
                        } else {
                            isCheckPay.add(false);
                        }
                    }
                    notifyDataSetChanged();
                }
            });
            if (isCheckPay.get(position)) {
                h.rb_is_check.setChecked(true);
            } else {
                h.rb_is_check.setChecked(false);
            }
            if (pa.pic != null && pa.pic.length() > 5) {
                h.picActive.setVisibility(VISIBLE);
                Glide.with(getContext()).load(pa.pic).into(h.picActive);
            } else {
                h.picActive.setVisibility(GONE);
            }
            switch (type) {
                case "1":
                    h.picState.setImageResource(R.mipmap.image_pay_ali);
                    h.te.setText("支付宝支付");
                    break;
                case "2":
                    h.picState.setImageResource(R.mipmap.image_pay_wechat);
                    h.te.setText("微信支付");
                    break;
                case "3":
                    h.picState.setImageResource(R.mipmap.image_pay_cmb);
                    h.te.setText("一网通银行卡支付");
                    break;
                default:
                    break;
            }
            return convertView;
        }
    }


    public static class ViewHolder {

        public TextView te;
        public RelativeLayout isCheck;
        public RadioButton rb_is_check;
        public ImageView picState, picActive;

        public ViewHolder(View view) {
            te = (TextView) view.findViewById(R.id.tv_custom_list_view_item_state);
            isCheck = (RelativeLayout) view.findViewById(R.id.rl_check);
            rb_is_check = (RadioButton) view.findViewById(R.id.rb_custom_list_view_item_state_is_is_check);
            picState = (ImageView) view.findViewById(R.id.iv_custom_list_view_item_state);
            picActive = (ImageView) view.findViewById(R.id.tv_custom_list_view_item_state_is_active);
        }
    }

    public interface PayStates {
        void state(String p);
    }
}
