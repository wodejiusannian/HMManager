package manager.love.i.hmmanager.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.base.BaseActivity;
import manager.love.i.hmmanager.bean.HistoricalIncome;
import manager.love.i.hmmanager.common.widgets.recycle.MultiLayoutAdapter;
import manager.love.i.hmmanager.inter.Network;
import manager.love.i.hmmanager.utils.SPUtils;
import manager.love.i.hmmanager.utils.ToastUtils;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HistoricalIncomeActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(R.id.rv_msg_show)
    RecyclerView rvMsg;

    @BindView(R.id.bga_main_refresh)
    BGARefreshLayout refresh;

    private MultiLayoutAdapter adapter;

    private List<HistoricalIncome.BodyBean> mData = new ArrayList<>();

    private String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_historical_income);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        initRefresh();
        if (mData == null)
            mData = new ArrayList<>();


        adapter = new MultiLayoutAdapter(mData, this);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvMsg.setLayoutManager(manager);
        rvMsg.setAdapter(adapter);
    }

    @Override
    protected void setData() {
        refresh.beginRefreshing();
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
        handler.sendEmptyMessageDelayed(0, 5000);
        initHistoricalIncome();
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

    public void back(View view) {
        finish();
    }

    private void initHistoricalIncome() {
        String stuId = SPUtils.getStuId(this);
        Network.historicalIncome().historicalInCome(stuId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(historicalIncome);
    }


    /*
    * 处理历史收入的接口
    * */
    Observer<HistoricalIncome> historicalIncome = new Observer<HistoricalIncome>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            ToastUtils.Toa(HistoricalIncomeActivity.this, e.getMessage());
        }

        @Override
        public void onNext(HistoricalIncome historicalIncome) {
            mData.clear();

            List<HistoricalIncome.BodyBean> data = historicalIncome.getBody();

            if (data.size() != 0 && data != null) {
                String[] s = data.get(0).getTime().split("-");
                tag = s[1];
            }

            for (int i = 0; i < data.size(); i++) {

                HistoricalIncome.BodyBean body = new HistoricalIncome.BodyBean();
                HistoricalIncome.BodyBean bodyBean = data.get(i);
                String time = bodyBean.getTime();

                String[] strs = time.split("-");
                String mon = strs[1];

                body.setT(1);
                body.setMoney(bodyBean.getMoney());
                body.setMoney_ye(bodyBean.getMoney_ye());
                body.setOrder_id(bodyBean.getOrder_id());
                body.setTime(time);
                body.setType(bodyBean.getType());
                mData.add(body);
                if (!TextUtils.equals(tag, mon)) {
                    mData.get(i - 1).setT(2);
                    tag = mon;
                    mData.get(i).setT(0);
                }
                mData.get(0).setT(0);
            }
            if (refresh != null) {
                refresh.endRefreshing();
                refresh.endLoadingMore();
            }
            adapter.notifyDataSetChanged();
        }
    };
}
