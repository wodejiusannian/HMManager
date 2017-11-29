package manager.love.i.hmmanager.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.base.BaseActivity;
import manager.love.i.hmmanager.bean.Customer365;
import manager.love.i.hmmanager.bean.UserInfo;
import manager.love.i.hmmanager.common.widgets.dialog.NormalDialog;
import manager.love.i.hmmanager.common.widgets.recycle.BaseRecycleAdapter;
import manager.love.i.hmmanager.common.widgets.recycle.BaseViewHolder;
import manager.love.i.hmmanager.inter.Network;
import manager.love.i.hmmanager.utils.RvDecorationUtils;
import manager.love.i.hmmanager.utils.SPUtils;
import manager.love.i.hmmanager.utils.glideutils.GlideCircleTransform;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShopRecommendActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

    private static final String TAG = "ShopRecommendActivity";

    @BindView(R.id.shop_recommend_recycle_persons)
    RecyclerView recycle;

    private BaseRecycleAdapter<Customer365.BodyBean> adapter = null;

    private List<Customer365.BodyBean> mData = new ArrayList<>();

    private NormalDialog dialog;

    private String studio_id, user_id, clothes_id, stock_id, color_name, size_name, clothes_cz, reason;

    @BindView(R.id.bga_main_refresh)
    BGARefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_recommend);

    }

    @Override
    protected void setListener() {
        studio_id = SPUtils.getStuId(this);
    }

    @Override
    protected void initData() {
        if (mData == null)
            mData = new ArrayList<>();

        initNet();

        initRefresh();
        adapter = new BaseRecycleAdapter<Customer365.BodyBean>(this, mData, R.layout.item_shop_recommend) {
            @Override
            public void bindData(BaseViewHolder holder, final Customer365.BodyBean bean, final int position) {
                ImageView iv = holder.getView(R.id.shop_recommend_item_iv_photo);
                TextView name = holder.getView(R.id.shop_recommend_item_tv_name);
                name.setText(bean.getUser_name());
                Glide.with(ShopRecommendActivity.this)
                        .load(bean.getUser_pic())
                        .error(R.mipmap.hm_main_drawer_photo_default)
                        .crossFade(1000)
                        .transform(new GlideCircleTransform(ShopRecommendActivity.this))
                        .into(iv);
                final RelativeLayout check = (RelativeLayout) holder.getRootView();

                check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user_id = bean.getUser_id();
                        initDialog();
                    }
                });
            }
        };
        recycle.setLayoutManager(new LinearLayoutManager(this));
        recycle.setAdapter(adapter);
        recycle.addItemDecoration(new RvDecorationUtils(10));
    }

    private void initDialog() {
        dialog = new NormalDialog(this);
        dialog.setCancel("取消");
        dialog.setTitle("温馨提示");
        dialog.setOnYesListener("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Network.recommendShopService().recommendShop(studio_id, user_id, clothes_id, stock_id, reason, color_name, size_name, clothes_cz).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(up);
            }
        });
        dialog.setMsg("是否要推荐给此用户");
        dialog.show();
    }

    private void initNet() {
        String stuId = SPUtils.getStuId(this);
        Network.customer365().customer365(stuId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(customer365);
    }

    @Override
    protected void setData() {
        Intent intent = getIntent();
        clothes_id = intent.getStringExtra("clothes_id");
        stock_id = intent.getStringExtra("stock_id");
        size_name = intent.getStringExtra("size_name");
        clothes_cz = intent.getStringExtra("clothes_cz");
        reason = intent.getStringExtra("reason");
        color_name = intent.getStringExtra("color_name");
    }

    Observer<UserInfo> up = new Observer<UserInfo>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(UserInfo customer365) {
            if ("0".equals(customer365.getRet())) {
                Toast.makeText(ShopRecommendActivity.this, "推送成功", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ShopRecommendActivity.this, ShopActivity.class));
            } else if ("1080".equals(customer365.getRet())) {
                Toast.makeText(ShopRecommendActivity.this, "当前服饰已推荐" + getEndTime() + "小时后可再推荐", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ShopRecommendActivity.this, "推送失败，请重新推送", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        }
    };


    private int getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        int hour = todayEnd.get(Calendar.HOUR_OF_DAY);
        return 24 - hour;
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
        initNet();
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

    public void back(View view) {
        finish();
    }
}
