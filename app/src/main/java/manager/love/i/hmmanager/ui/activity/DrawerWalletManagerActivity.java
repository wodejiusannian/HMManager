package manager.love.i.hmmanager.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.base.BaseActivity;
import manager.love.i.hmmanager.bean.QbYue;
import manager.love.i.hmmanager.common.widgets.popupwindow.CommonPopupWindow;
import manager.love.i.hmmanager.inter.Network;
import manager.love.i.hmmanager.utils.ActivityUtils;
import manager.love.i.hmmanager.utils.SPUtils;
import manager.love.i.hmmanager.utils.ToastUtils;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DrawerWalletManagerActivity extends BaseActivity {

    @BindView(R.id.iv_drawer_wallet_what)
    ImageView what;

    @BindViews({R.id.tv_waller_manager_all_money, R.id.tv_wallet_pending_settlement, R.id.tv_introduce})
    TextView[] tViews;

    @BindView(R.id.button_sure_wallet)
    Button button;

    private String allMoney = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_wallet_manager);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void setData() {

    }

    public void back(View view) {
        finish();
    }

    @OnClick({R.id.tv_wallet_detailed, R.id.iv_drawer_wallet_what, R.id.button_sure_wallet, R.id.tv_introduce, R.id.tv_wallet_history})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.tv_wallet_history:
                ActivityUtils.switchTo(DrawerWalletManagerActivity.this, CashRegisterActivity.class);
                break;
            case R.id.tv_wallet_detailed:
                ActivityUtils.switchTo(this, HistoricalIncomeActivity.class);
                break;
            case R.id.iv_drawer_wallet_what:
                showPopupWindow();
                break;
            case R.id.button_sure_wallet:
                Map<String, Object> map = new HashMap<>();
                map.put("allMoney", allMoney);
                ActivityUtils.switchTo(this, WalletCashActivity.class, map);
                break;
            case R.id.tv_introduce:
                showPopupWindowIntroduce();
                break;
            default:
                break;
        }
    }

    private void showPopupWindow() {
        CommonPopupWindow win = new CommonPopupWindow.Builder(this).
                setView(R.layout.popup_window_content).
                setBackGroundLevel(0.5f).
                setWidthAndHeight(300, 400).
                create();
        win.showAsDropDown(what, -300, 10);
    }

    private void showPopupWindowIntroduce() {
        CommonPopupWindow win = new CommonPopupWindow.Builder(this).
                setView(R.layout.popup_window_introduce).
                setBackGroundLevel(0.5f).
                setWidthAndHeight(300, 400).
                create();
        win.showAsDropDown(tViews[2], -150, 10);
    }

    private void initWallet() {
        String stuId = SPUtils.getStuId(this);
        Network.qbYueService().getQbYue(stuId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(qbYueObserver);
    }

    /*
    * 获取钱包首页的数据设置
    * */
    Observer<QbYue> qbYueObserver = new Observer<QbYue>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            ToastUtils.Toa(DrawerWalletManagerActivity.this, "网络中断，请重试");
        }

        @Override
        public void onNext(QbYue qbYue) {
            QbYue.BodyBean bodyBean = qbYue.getBody();
            allMoney = bodyBean.getMoney_ye();

            tViews[0].setText(allMoney);
            String tx = bodyBean.getMoney_tx();
            if (ActivityUtils.isEmpty(tx)) {
                tViews[1].setText(tx);
                button.setEnabled(false);
            } else {
                button.setEnabled(true);
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        initWallet();
    }
}
