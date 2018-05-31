package manager.love.i.hmmanager.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.base.BaseFragment;
import manager.love.i.hmmanager.bean.InDoorOrder2;
import manager.love.i.hmmanager.common.widgets.recycle.BaseRecycleAdapter;
import manager.love.i.hmmanager.common.widgets.recycle.BaseViewHolder;
import manager.love.i.hmmanager.ui.activity.DetailsActivity;
import manager.love.i.hmmanager.utils.NetConfig;
import manager.love.i.hmmanager.utils.NetUtils;
import manager.love.i.hmmanager.utils.RvDecorationUtils;
import manager.love.i.hmmanager.utils.SPUtils;
import manager.love.i.hmmanager.utils.glideutils.GlideRoundTransform;

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
public class DrawerOverFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(R.id.rv_msg_show)
    RecyclerView rvMsg;

    @BindView(R.id.bga_main_refresh)
    BGARefreshLayout refresh;

    private BaseRecycleAdapter<InDoorOrder2.BodyBean> adapter;

    private List<InDoorOrder2.BodyBean> mData = new ArrayList<>();


    private NetUtils net = NetUtils.getInstance();

    private Map<String, String> map = new HashMap<>();

    @Override
    public int getContentViewId() {
        return R.layout.fragment_drawer_over;
    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        String state_pj = bundle.getString("state_pj");
        String orderType = bundle.getString("orderType");
        String stuId = SPUtils.getStuId(getContext());
        map.put("sellerUserId", stuId);
        map.put("orderTypePj", orderType);
        map.put("deleteStatusPj", state_pj);
    }

    @Override
    protected void setData() {
        initRefresh();
        refresh.beginRefreshing();
    }

    @Override
    protected void initData() {
        if (mData == null)
            mData = new ArrayList<>();


        adapter = new BaseRecycleAdapter<InDoorOrder2.BodyBean>(getActivity(), mData, R.layout.item_drawer_over) {
            @Override
            public void bindData(BaseViewHolder holder, InDoorOrder2.BodyBean bodyBean, final int position) {
                Button stateTop = holder.getView(R.id.btn_state_top);
                Button stateBottom = holder.getView(R.id.btn_state_bottom);
                TextView customerName = holder.getView(R.id.tv_item_rv_customer_name);
                TextView customerTip = holder.getView(R.id.big_size_text_tip);
                TextView customerNumber = holder.getView(R.id.tv_item_rv_customer_number);
                TextView customerTime = holder.getView(R.id.tv_item_rv_customer_time);
                TextView customerAddress = holder.getView(R.id.tv_item_rv_customer_address);
                ImageView customerPhoto = holder.getView(R.id.tv_item_rv_customer_photo);
                TextView customerPlace = holder.getView(R.id.tv_item_rv_customer_place_time);
                InDoorOrder2.BodyBean.OrderInfoBean orderInfo = bodyBean.getOrderInfo().get(0);
                switch (orderInfo.getDeleteStatus()) {
                    case "1"://工作室已接单
                    {
                        stateTop.setText("进行中");
                        stateBottom.setText("服务中");
                    }
                    break;
                    case "2"://用户点击已完成订单（evaluate：0未评价；1已评价；-1已投诉）
                    {
                        stateTop.setText("已完成");
                        String eva = orderInfo.getEvaluateState();
                        if (TextUtils.equals("0", eva)) {
                            stateBottom.setText("已评价");
                        } else {
                            stateBottom.setText("未评价");
                        }
                    }
                    break;
                    case "3"://用户付款工作室未接单用户申请退款
                    {
                        stateTop.setText("进行中");
                        stateBottom.setText("退款中");
                    }
                    break;
                    case "4"://用户付款工作室已接单用户申请退款
                    {
                        stateTop.setText("进行中");
                        stateBottom.setText("退款中");
                    }
                    break;
                    case "5"://用户付款工作室拒绝接单,已退款
                    {
                        stateTop.setText("已完成");
                        stateBottom.setText("已拒绝");
                    }
                    break;

                    case "6"://用户付款工作室未接单用户申请退款,已退款
                    {
                        stateTop.setText("已完成");
                        stateBottom.setText("已退款");
                    }
                    break;
                    case "7"://用户付款工作室未接单用户申请退款,已退款
                    {
                        stateTop.setText("已完成");
                        stateBottom.setText("已结算");
                    }
                    break;
                }
                if ("1".equals(orderInfo.getOrderType())) {
                    customerTip.setText("除螨：");
                    customerNumber.setText("标准" + orderInfo.getOrderNumber() + "袋");
                } else {
                    customerTip.setText("服饰量：");
                    customerNumber.setText(orderInfo.getClothesNumber());
                }
                customerName.setText(orderInfo.getConsigneeName());
                customerAddress.setText(orderInfo.getConsigneeAddress());
                customerTime.setText(orderInfo.getConsigneeTime());
                customerPlace.setText(orderInfo.getPayTime());

                Glide.with(getContext())
                        .load(bodyBean.getBuyUserPic())
                        .error(R.mipmap.hm_main_drawer_photo_default)
                        .crossFade(1000)
                        .transform(new GlideRoundTransform(getContext()))
                        .into(customerPhoto);


                LinearLayout llDetails = (LinearLayout) holder.getRootView();

                llDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goDetails(position);
                    }
                });
            }
        };
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvMsg.setLayoutManager(manager);
        rvMsg.setAdapter(adapter);
        rvMsg.addItemDecoration(new RvDecorationUtils(10));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mData.clear();
    }


    /*
     * 初始化上拉加载，下拉刷新
     * */
    private void initRefresh() {
        refresh.setDelegate(this);
        BGANormalRefreshViewHolder holder = new BGANormalRefreshViewHolder(getContext(), true);
        refresh.setRefreshViewHolder(holder);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout bgaRefreshLayout) {
        handler.sendEmptyMessageDelayed(0, 5000);
        initInDoorOrder2Data();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout bgaRefreshLayout) {
        handler.sendEmptyMessageDelayed(1, 5000);
        return true;
    }


    /**
     * 模拟请求网络数据
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (refresh != null)
                        refresh.endRefreshing();
                    break;
                case 1:
                    if (refresh != null)
                        refresh.endLoadingMore();
                    break;
                case 2:
                    if (refresh != null)
                        refresh.endLoadingMore();
                    break;
                default:
                    break;

            }
        }
    };

    /*
     * 处理集合数据
     * */
    private void initInDoorOrder2Data() {
        net.post(NetConfig.ORDER_STATE, getContext(), map, new NetUtils.XCallBack() {
            @Override
            public void onResponse(String result) {
                mData.clear();
                refresh.endRefreshing();
                Gson gson = new Gson();
                InDoorOrder2 in = gson.fromJson(result, InDoorOrder2.class);
                mData.addAll(in.getBody());
                adapter.notifyDataSetChanged();
            }
        });
    }


    /*
   * 点击进入详情，进入详情
   * */
    private void goDetails(int position) {
        InDoorOrder2.BodyBean customerInfo = mData.get(position);
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("customerInfo", customerInfo);
        startActivity(intent);
    }
}
