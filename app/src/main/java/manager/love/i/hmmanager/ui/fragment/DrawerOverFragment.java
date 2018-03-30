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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.base.BaseFragment;
import manager.love.i.hmmanager.bean.InDoorOrder;
import manager.love.i.hmmanager.common.widgets.recycle.BaseRecycleAdapter;
import manager.love.i.hmmanager.common.widgets.recycle.BaseViewHolder;
import manager.love.i.hmmanager.inter.NetworkCopy;
import manager.love.i.hmmanager.ui.activity.DetailsActivity;
import manager.love.i.hmmanager.utils.RvDecorationUtils;
import manager.love.i.hmmanager.utils.SPUtils;
import manager.love.i.hmmanager.utils.ToastUtils;
import manager.love.i.hmmanager.utils.TokenUtils;
import manager.love.i.hmmanager.utils.glideutils.GlideRoundTransform;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

    private BaseRecycleAdapter<InDoorOrder.BodyBean> adapter;

    private List<InDoorOrder.BodyBean> mData = new ArrayList<>();

    private String stuId, state_pj;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_drawer_over;
    }

    @Override
    protected void initView() {
        stuId = SPUtils.getStuId(getContext());
        Bundle bundle = getArguments();
        state_pj = bundle.getString("state_pj");
        orderType = bundle.getString("orderType");
    }

    private String orderType;

    @Override
    protected void setData() {
        initRefresh();
        refresh.beginRefreshing();
    }

    @Override
    protected void initData() {
        if (mData == null)
            mData = new ArrayList<>();


        adapter = new BaseRecycleAdapter<InDoorOrder.BodyBean>(getActivity(), mData, R.layout.item_drawer_over) {
            @Override
            public void bindData(BaseViewHolder holder, InDoorOrder.BodyBean bodyBean, final int position) {
                Button stateTop = holder.getView(R.id.btn_state_top);
                Button stateBottom = holder.getView(R.id.btn_state_bottom);
                TextView customerName = holder.getView(R.id.tv_item_rv_customer_name);
                TextView customerTip = holder.getView(R.id.big_size_text_tip);
                TextView customerNumber = holder.getView(R.id.tv_item_rv_customer_number);
                TextView customerTime = holder.getView(R.id.tv_item_rv_customer_time);
                TextView customerAddress = holder.getView(R.id.tv_item_rv_customer_address);
                ImageView customerPhoto = holder.getView(R.id.tv_item_rv_customer_photo);
                TextView customerPlace = holder.getView(R.id.tv_item_rv_customer_place_time);
                switch (bodyBean.getState()) {
                    case "1":
                    case "11"://工作室已接单
                    {
                        stateTop.setText("进行中");
                        stateBottom.setText("服务中");
                    }
                    break;
                    case "3":
                    case "100"://用户付款工作室未接单用户申请退款
                    {
                        stateTop.setText("进行中");
                        stateBottom.setText("退款中");
                    }
                    break;
                    case "4":
                    case "110"://用户付款工作室已接单用户申请退款
                    {
                        stateTop.setText("进行中");
                        stateBottom.setText("退款中");
                    }
                    break;
                    case "2":
                    case "12"://用户点击已完成订单（evaluate：0未评价；1已评价；-1已投诉）
                    {
                        stateTop.setText("已完成");
                        String eva = bodyBean.getEvaluate();
                        if (TextUtils.equals("0", eva)) {
                            stateBottom.setText("未评价");

                        } else if (TextUtils.equals("1", eva)) {
                            stateBottom.setText("已评价");
                        } else if (TextUtils.equals("-1", eva)) {
                            stateBottom.setText("已投诉");
                        }
                    }
                    case "33": {
                        stateTop.setText("已完成");
                        String eva = bodyBean.getEvaluate();
                        if (TextUtils.equals("0", eva)) {
                            stateBottom.setText("未评价");

                        } else if (TextUtils.equals("1", eva)) {
                            stateBottom.setText("已评价");
                        } else if (TextUtils.equals("-1", eva)) {
                            stateBottom.setText("已投诉");
                        }
                    }
                    break;
                    case "6":
                    case "1001"://用户付款工作室未接单用户申请退款,已退款
                    {
                        stateTop.setText("已完成");
                        stateBottom.setText("已退款");
                    }
                    break;
                    case "1101"://用户付款工作室已接单用户申请退款,已退款
                    {
                        stateTop.setText("已完成");
                        stateBottom.setText("已退款");
                    }
                    break;
                    case "5":
                    case "101"://用户付款工作室拒绝接单,已退款
                    {
                        stateTop.setText("已完成");
                        stateBottom.setText("已拒绝");
                    }
                    break;

                    default:
                        break;
                }
                if ("1".equals(bodyBean.getOrderType())) {
                    customerTip.setText("除螨：");
                    customerNumber.setText("标准" + bodyBean.getClothes_num() + "袋");
                } else {
                    customerTip.setText("服饰量：");
                    customerNumber.setText(bodyBean.getClothes_num());
                }
                customerName.setText(bodyBean.getJd_name());
                customerAddress.setText(bodyBean.getJd_address());
                customerTime.setText(bodyBean.getJd_time());
                customerPlace.setText(bodyBean.getXd_time());

                Glide.with(getContext())
                        .load(bodyBean.getApp_user_head_pic())
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
        //DefineBAGRefreshWithLoadView defineloadview =
        // new DefineBAGRefreshWithLoadView(this, true, true);
        BGANormalRefreshViewHolder holder = new BGANormalRefreshViewHolder(getContext(), true);
        refresh.setRefreshViewHolder(holder);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout bgaRefreshLayout) {
        handler.sendEmptyMessageDelayed(0, 5000);
        initInDoorOrderData();
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
    private void initInDoorOrderData() {
        NetworkCopy.userInDoorOrder().getInDoorOrder(TokenUtils.token, stuId, state_pj, orderType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(inDoorOrder);
    }

    /*
    * 工作室的订单列表处理
    * */
    Observer<InDoorOrder> inDoorOrder = new Observer<InDoorOrder>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            ToastUtils.Toa(getActivity(), "网络中断，请重试");
        }

        @Override
        public void onNext(InDoorOrder inDoorOrders) {
            mData.clear();
            if (refresh != null)
                refresh.endRefreshing();
            if (refresh != null)
                refresh.endLoadingMore();

            mData.addAll(inDoorOrders.getBody());
            adapter.notifyDataSetChanged();
        }
    };


    /*
   * 点击进入详情，进入详情
   * */
    private void goDetails(int position) {
        InDoorOrder.BodyBean customerInfo = mData.get(position);
        Intent intent = new Intent();
        intent.setClass(getActivity(), DetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("customerInfo", customerInfo);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
