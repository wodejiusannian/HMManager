package manager.love.i.hmmanager.common.widgets.recycle;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.bean.HistoricalIncome;

import static android.R.attr.format;

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
public class MultiLayoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ONE = 0;
    private static final int TYPE_TWO = 1;
    private static final int TYPE_THREE = 2;

    private List<HistoricalIncome.BodyBean> mData;

    private Context context;

    public MultiLayoutAdapter(List<HistoricalIncome.BodyBean> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        HistoricalIncome.BodyBean bean = mData.get(position);
        if (0 == bean.getT()) {
            return TYPE_ONE;
        } else if (1 == bean.getT()) {
            return TYPE_THREE;
        } else {
            return TYPE_TWO;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_ONE:
                view = LayoutInflater.from(context).inflate(R.layout.item_wallet_income_type_one, parent, false);
                holder = new ViewHolderOne(view);
                break;
            case TYPE_TWO:
                view = LayoutInflater.from(context).inflate(R.layout.item_wallet_income_type_two, parent, false);
                holder = new ViewHolderTwo(view);
                break;
            case TYPE_THREE:
                view = LayoutInflater.from(context).inflate(R.layout.item_wallet_income_type_three, parent, false);
                holder = new ViewHolderThree(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderOne) {
            bindViewHolderOne((ViewHolderOne) holder, position);
        } else if (holder instanceof ViewHolderTwo) {
            bindViewHolderTwo((ViewHolderTwo) holder, position);
        } else if (holder instanceof ViewHolderThree) {
            bindViewHolderThree((ViewHolderThree) holder, position);
        }
    }

    private void bindViewHolderThree(ViewHolderThree three, int position) {
        HistoricalIncome.BodyBean bodyBean = mData.get(position);
        String type = bodyBean.getType();
        String time = bodyBean.getTime();
        three.tvThree.setText("+" + bodyBean.getMoney());
        try {
            three.tvDate.setText(time.substring(8, 10) + "\n" + dayForWeek(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (TextUtils.equals("18", type)) {
            three.tvThree.setTextColor(Color.parseColor("#ac0000"));
            three.ivThree.setImageResource(R.mipmap.hm_wallet_income_order);
            three.tvKind.setText("上门服务");
        } else if (TextUtils.equals("27", type)) {
            three.tvThree.setTextColor(Color.parseColor("#ac0000"));
            three.ivThree.setImageResource(R.mipmap.hm_history_add_moey_blue_box);
            three.tvKind.setText("收纳产品收入");
        } else if (TextUtils.equals("36", type)) {
            three.tvThree.setTextColor(Color.parseColor("#ac0000"));
            three.ivThree.setImageResource(R.mipmap.hm_history_add_moey_blue_clo);
            three.tvKind.setText("衣服补差价");
        } else if (TextUtils.equals("56", type)) {
            three.tvThree.setTextColor(Color.parseColor("#ffd703"));
            three.ivThree.setImageResource(R.mipmap.hm_wallet_income_365);
            three.tvKind.setText("365购买");
        } else {
            three.tvThree.setTextColor(Color.parseColor("#ffd703"));
            three.ivThree.setImageResource(R.mipmap.hm_wallet_income_365);
            three.tvKind.setText("收纳产品收入");
        }
    }

    private void bindViewHolderTwo(ViewHolderTwo two, int position) {
        HistoricalIncome.BodyBean bodyBean = mData.get(position);
        String time = bodyBean.getTime();
        String type = bodyBean.getType();
        two.tvTwo.setText("+" + bodyBean.getMoney());
        try {
            two.tvDate.setText(time.substring(8, 10) + "\n" + dayForWeek(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (TextUtils.equals("18", type)) {
            two.tvTwo.setTextColor(Color.parseColor("#ac0000"));
            two.ivTwo.setImageResource(R.mipmap.hm_wallet_income_order);
            two.tvKind.setText("上门服务");
        } else if (TextUtils.equals("27", type)) {
            two.tvTwo.setTextColor(Color.parseColor("#ac0000"));
            two.ivTwo.setImageResource(R.mipmap.hm_history_add_moey_blue_box);
            two.tvKind.setText("收纳产品收入");
        } else if (TextUtils.equals("36", type)) {
            two.tvTwo.setTextColor(Color.parseColor("#ac0000"));
            two.ivTwo.setImageResource(R.mipmap.hm_history_add_moey_blue_clo);
            two.tvKind.setText("衣服补差价");
        } else if (TextUtils.equals("56", type)) {
            two.tvTwo.setTextColor(Color.parseColor("#ffd703"));
            two.ivTwo.setImageResource(R.mipmap.hm_wallet_income_365);
            two.tvKind.setText("365购买");
        } else {
            two.tvTwo.setTextColor(Color.parseColor("#ffd703"));
            two.ivTwo.setImageResource(R.mipmap.hm_wallet_income_365);
            two.tvKind.setText("收纳产品收入");
        }
    }

    private void bindViewHolderOne(ViewHolderOne one, int position) {
        HistoricalIncome.BodyBean bodyBean = mData.get(position);
        String type = bodyBean.getType();
        String time = bodyBean.getTime();

        one.tvMonth.setText(time.substring(0, 7));
        one.tvMoney.setText("+" + bodyBean.getMoney());
        try {
            one.tvDate.setText(time.substring(8, 10) + "\n" + dayForWeek(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (TextUtils.equals("18", type)) {
            one.tvOne.setTextColor(Color.parseColor("#ac0000"));
            one.ivOne.setImageResource(R.mipmap.hm_wallet_income_order);
            one.tvKind.setText("上门服务");
        } else if (TextUtils.equals("27", type)) {
            one.tvOne.setTextColor(Color.parseColor("#ac0000"));
            one.ivOne.setImageResource(R.mipmap.hm_history_add_moey_blue_box);
            one.tvKind.setText("收纳产品收入");
        } else if (TextUtils.equals("36", type)) {
            one.tvOne.setTextColor(Color.parseColor("#ac0000"));
            one.ivOne.setImageResource(R.mipmap.hm_history_add_moey_blue_clo);
            one.tvKind.setText("衣服补差价");
        } else if (TextUtils.equals("56", type)) {
            one.tvOne.setTextColor(Color.parseColor("#ffd703"));
            one.ivOne.setImageResource(R.mipmap.hm_wallet_income_365);
            one.tvKind.setText("365购买");
        } else {
            one.tvOne.setTextColor(Color.parseColor("#ffd703"));
            one.ivOne.setImageResource(R.mipmap.hm_wallet_income_365);
            one.tvKind.setText("收纳产品收入");
        }
    }

    @Override
    public int getItemCount() {

        return mData.size() == 0 ? 0 : mData.size();
    }


    class ViewHolderOne extends RecyclerView.ViewHolder {

        ImageView ivOne;
        TextView tvOne, tvKind, tvMonth, tvMoney, tvDate;

        public ViewHolderOne(View itemView) {
            super(itemView);
            ivOne = (ImageView) itemView.findViewById(R.id.iv_item_wallet_income_one);
            tvOne = (TextView) itemView.findViewById(R.id.tv_item_wallet_income_one);
            tvKind = (TextView) itemView.findViewById(R.id.tv_wallet_income_kind_one);
            tvMonth = (TextView) itemView.findViewById(R.id.iv_item_wallet_income_month);
            tvMoney = (TextView) itemView.findViewById(R.id.tv_item_wallet_income_one);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }

    class ViewHolderTwo extends RecyclerView.ViewHolder {
        ImageView ivTwo;
        TextView tvTwo, tvKind, tvDate;

        public ViewHolderTwo(View itemView) {
            super(itemView);
            ivTwo = (ImageView) itemView.findViewById(R.id.iv_item_wallet_income_two);
            tvTwo = (TextView) itemView.findViewById(R.id.tv_item_wallet_income_two);
            tvKind = (TextView) itemView.findViewById(R.id.tv_wallet_income_kind_two);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }

    class ViewHolderThree extends RecyclerView.ViewHolder {
        ImageView ivThree;
        TextView tvThree, tvKind, tvDate;

        public ViewHolderThree(View itemView) {
            super(itemView);
            ivThree = (ImageView) itemView.findViewById(R.id.iv_item_wallet_income_three);
            tvThree = (TextView) itemView.findViewById(R.id.tv_item_wallet_income_three);
            tvKind = (TextView) itemView.findViewById(R.id.tv_wallet_income_kind_three);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }


    public static String dayForWeek(String pTime) throws Exception {
        String[] weeks = {"", "周一", "周二", "周三", "周四", "周五", "周六", "周日"};
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(pTime));
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return weeks[dayForWeek];
    }
}
