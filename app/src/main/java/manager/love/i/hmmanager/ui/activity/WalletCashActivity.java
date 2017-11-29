package manager.love.i.hmmanager.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.base.BaseActivity;
import manager.love.i.hmmanager.bean.BeGoingToCash;
import manager.love.i.hmmanager.inter.Network;
import manager.love.i.hmmanager.utils.ActivityUtils;
import manager.love.i.hmmanager.utils.SPUtils;
import manager.love.i.hmmanager.utils.ToastUtils;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WalletCashActivity extends BaseActivity {


    @BindViews({R.id.tv_waller_manager_all_money})
    TextView[] tViews;

    @BindView(R.id.et_wallet_money)
    EditText etMoney;

    @BindView(R.id.button_sure_wallet)
    Button sure;


    private String allMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_wallet_cash);
    }

    @Override
    protected void setListener() {
        etMoney.addTextChangedListener(textWatcher);
    }

    @Override
    protected void initData() {
        allMoney = getIntent().getStringExtra("allMoney");

        tViews[0].setText(allMoney);
    }

    @Override
    protected void setData() {

    }

    public void back(View view) {
        finish();
    }

    @OnClick({R.id.button_sure_wallet, R.id.tv_wallet_manager_all})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.button_sure_wallet:
                beToCash();
                break;
            case R.id.tv_wallet_manager_all:
                etMoney.setText(allMoney);
                break;
            default:
                break;
        }
    }


    private void beToCash() {
        String mon = etMoney.getText().toString().trim();
        if (ActivityUtils.isEmpty(mon)) {
            String stuId = SPUtils.getStuId(this);
            Network.beGoingToCashService().beGoingToCash(stuId, mon).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(beGoingToCashObserver);
        } else {
            ToastUtils.Toa(this, "请输入申请提现金额");
        }
    }

    /*
    * 获取钱包首页的数据设置
    * */
    Observer<BeGoingToCash> beGoingToCashObserver = new Observer<BeGoingToCash>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            ToastUtils.Toa(WalletCashActivity.this, "网络中断，请重试");
        }

        @Override
        public void onNext(BeGoingToCash beGoingToCash) {
            String ret = beGoingToCash.getRet();
            if (TextUtils.equals("0", ret)) {
                ToastUtils.Toa(WalletCashActivity.this, "申请提现成功");
                finish();
            } else if (TextUtils.equals("1001", ret)) {
                ToastUtils.Toa(WalletCashActivity.this, "已存在提现订单");
                finish();
            } else {
                ToastUtils.Toa(WalletCashActivity.this, "提现失败");
            }
        }
    };

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (strToDouble(etMoney.getText().toString().trim()) > strToDouble(allMoney)) {
                etMoney.setText(allMoney);
            }
            if (ActivityUtils.isEmpty(s.toString())) {
                sure.setEnabled(true);
            } else {
                sure.setEnabled(false);
            }
            if (s.toString().contains(".")) {
                //只能有一个小数点
                if (s.toString().lastIndexOf(".") != s.toString().indexOf(".")) {
                    s = s.toString().subSequence(0,
                            s.length() - 1);
                    etMoney.setText(s);
                    etMoney.setSelection(s.length());//todo
                }

                // 小数点后最多能输入两位
                if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                    s = s.toString().subSequence(0,
                            s.toString().indexOf(".") + 3);
                    etMoney.setText(s);
                    etMoney.setSelection(s.length());
                }
            }

            // 如果第一位是.则前面加上0.
            if (s.toString().trim().substring(0).equals(".")) {
                s = "0" + s;
                etMoney.setText(s);
                etMoney.setSelection(2);
            }

            // 如果第一位是0则后面必须输入点，否则不能输入
            if (s.toString().startsWith("0")
                    && s.toString().trim().length() > 1) {
                if (!s.toString().substring(1, 2).equals(".")) {
                    etMoney.setText(s.subSequence(0, 1));
                    etMoney.setSelection(1);
                    return;
                }
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private double strToDouble(String str) {
        if (ActivityUtils.isEmpty(str))
            return Double.parseDouble(str);
        else
            return 0;
    }
}
