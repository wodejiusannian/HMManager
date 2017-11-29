package manager.love.i.hmmanager.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.base.BaseActivity;
import manager.love.i.hmmanager.bean.CashRegiste;
import manager.love.i.hmmanager.common.widgets.recycle.BaseRecycleAdapter;
import manager.love.i.hmmanager.common.widgets.recycle.BaseViewHolder;
import manager.love.i.hmmanager.inter.Network;
import manager.love.i.hmmanager.utils.RvDecorationUtils;
import manager.love.i.hmmanager.utils.SPUtils;
import manager.love.i.hmmanager.utils.ToastUtils;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CashRegisterActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    @BindView(R.id.rv_msg_show)
    RecyclerView rvMsg;

    @BindView(R.id.bga_main_refresh)
    BGARefreshLayout refresh;

    private BaseRecycleAdapter<CashRegiste.BodyBean> adapter;

    private List<CashRegiste.BodyBean> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_cash_register);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void initData() {
        initRefresh();
        if (mData == null)
            mData = new ArrayList<>();


        adapter = new BaseRecycleAdapter<CashRegiste.BodyBean>(this, mData, R.layout.item_time_wallet_history_rv) {
            @Override
            public void bindData(BaseViewHolder holder, CashRegiste.BodyBean beanBody, int position) {
                bindView(holder, beanBody, position);
            }
        };
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvMsg.setLayoutManager(manager);
        rvMsg.setAdapter(adapter);
        rvMsg.addItemDecoration(new RvDecorationUtils(10));
    }

    private void bindView(BaseViewHolder holder, CashRegiste.BodyBean beanBody, int position) {
        TextView date = holder.getView(R.id.tv_item_history_date);
        TextView cash = holder.getView(R.id.tv_item_history_money_cash);
        TextView invoice = holder.getView(R.id.tv_item_history_money_invoice);
        TextView actual = holder.getView(R.id.tv_item_history_money_actual);
        TextView year = holder.getView(R.id.tv_item_history_year);
        TextView month = holder.getView(R.id.tv_item_history_month);
        String time = beanBody.getTime();
        year.setText(time.substring(0, 4));
        month.setText(time.substring(5, 7));
        cash.setText(beanBody.getMoney_sq());
        invoice.setText(beanBody.getMoney_fp());
        actual.setText(beanBody.getMoney_ye());
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
        handler.sendEmptyMessageDelayed(0, 3000);
        initCashRegister();
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

    private void initCashRegister() {
        String stuId = SPUtils.getStuId(this);
        Network.cashRegisteService().cashRegiste(stuId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cashRegisteObserver);
    }


    /*
    * 处理历史收入的接口
    * */
    Observer<CashRegiste> cashRegisteObserver = new Observer<CashRegiste>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            ToastUtils.Toa(CashRegisterActivity.this, e.getMessage());
        }

        @Override
        public void onNext(CashRegiste cashRegiste) {
            mData.addAll(cashRegiste.getBody());
            if (refresh != null) {
                refresh.endRefreshing();
                refresh.endLoadingMore();
            }
            adapter.notifyDataSetChanged();
        }
    };
}
