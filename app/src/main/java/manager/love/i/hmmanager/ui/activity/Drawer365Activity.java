package manager.love.i.hmmanager.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import io.rong.imkit.RongIM;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.base.BaseActivity;
import manager.love.i.hmmanager.bean.Customer365;
import manager.love.i.hmmanager.common.widgets.recycle.BaseRecycleAdapter;
import manager.love.i.hmmanager.common.widgets.recycle.BaseViewHolder;
import manager.love.i.hmmanager.inter.Network;
import manager.love.i.hmmanager.utils.ActivityUtils;
import manager.love.i.hmmanager.utils.RvDecorationUtils;
import manager.love.i.hmmanager.utils.SPUtils;
import manager.love.i.hmmanager.utils.glideutils.GlideCircleTransform;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Drawer365Activity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(R.id.bga_main_refresh)
    BGARefreshLayout refresh;

    @BindView(R.id.rv_main_order)
    RecyclerView recyclerView;

    private BaseRecycleAdapter<Customer365.BodyBean> adapter = null;

    private List<Customer365.BodyBean> mData = new ArrayList<>();


    private void init365() {
        String stuId = SPUtils.getStuId(this);
        Network.customer365().customer365(stuId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(customer365);
    }


    /*
   * 处理工作室的信息
   * */
    Observer<Customer365> customer365 = new Observer<Customer365>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(Customer365 customer365) {
            mData.clear();
            if (refresh != null) {
                refresh.endRefreshing();
                refresh.endLoadingMore();
            }

            mData.addAll(customer365.getBody());
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_365);
    }

    @Override
    protected void setListener() {
        refresh.beginRefreshing();
    }

    @Override
    protected void initData() {
        initRefresh();
        if (mData == null)
            mData = new ArrayList<>();
        adapter = new BaseRecycleAdapter<Customer365.BodyBean>(this, mData, R.layout.item_drawer_365) {
            @Override
            public void bindData(BaseViewHolder holder, final Customer365.BodyBean bodyBean, int position) {
                holder.getRootView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityUtils.switchTo(Drawer365Activity.this, ShopActivity.class);
                    }
                });
                holder.getView(R.id.iv_drawer_365_chat_single).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RongIM im = RongIM.getInstance();
                        if (im != null) {
                            RongIM.getInstance().startPrivateChat(Drawer365Activity.this, "hmyc_" + bodyBean.getUser_id(), bodyBean.getUser_name());
                        }
                    }
                });
                ImageView photo = holder.getView(R.id.iv_order_customer_photo);
                TextView name = holder.getView(R.id.tv_order_customer_name);
                TextView phone = holder.getView(R.id.tv_order_customer_phone);
                TextView time = holder.getView(R.id.tv_order_customer_time);
                Glide.with(Drawer365Activity.this)
                        .load(bodyBean.getUser_pic())
                        .error(R.mipmap.hm_main_drawer_photo_default)
                        .crossFade(1000)
                        .transform(new GlideCircleTransform(Drawer365Activity.this))
                        .into(photo);
                name.setText(bodyBean.getUser_name());
                phone.setText(bodyBean.getUser_phone());
                time.setText(bodyBean.getTime_ks() + "-" + bodyBean.getTime_js());
            }
        };
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RvDecorationUtils(10));
    }

    @Override
    protected void setData() {
        refresh.beginRefreshing();
        loadMore();
    }

    /*
      * 数据获取
      * */
    private void loadMore() {

    }

    public void back(View view) {
        finish();
    }

    /*
   * 初始化上拉加载，下拉刷新
   * */
    private void initRefresh() {
        refresh.setDelegate(this);
        //DefineBAGRefreshWithLoadView defineloadview =
        // new DefineBAGRefreshWithLoadView(this, true, true);
        BGANormalRefreshViewHolder holder = new BGANormalRefreshViewHolder(this, true);
        refresh.setRefreshViewHolder(holder);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout bgaRefreshLayout) {
        handler.sendEmptyMessageDelayed(0, 3000);
        init365();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout bgaRefreshLayout) {
        handler.sendEmptyMessageDelayed(1, 3000);
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


}
