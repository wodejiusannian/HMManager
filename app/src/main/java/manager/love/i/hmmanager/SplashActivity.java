package manager.love.i.hmmanager;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import manager.love.i.hmmanager.base.BaseActivity;
import manager.love.i.hmmanager.bean.ShopTag;
import manager.love.i.hmmanager.inter.Network;
import manager.love.i.hmmanager.sql.SQLCLODao;
import manager.love.i.hmmanager.utils.ActivityUtils;
import manager.love.i.hmmanager.utils.SPUtils;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {
    private static final String TAG = "SplashActivity";
    @BindView(R.id.iv_splash_welcome)
    ImageView welcome;

    @BindView(R.id.tv_splash_jump)
    TextView jump;

    private SQLCLODao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @OnClick({R.id.tv_splash_jump})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.tv_splash_jump:
                goLoadingOrMainActivity();
                break;
            default:
                break;
        }
    }

    private CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            jump.setText("跳过(" + millisUntilFinished / 1000 + ")s");
        }

        @Override
        public void onFinish() {
            jump.setText("跳过(" + 0 + ")s");
            goLoadingOrMainActivity();
        }
    };

    private void goLoadingOrMainActivity() {
        if (SPUtils.first(this)) {
            ActivityUtils.switchTo(this, LoadingActivity.class);
            finish();
        } else {
            ActivityUtils.switchTo(this, MainActivity.class);
            finish();
        }
    }

    @Override
    protected void setListener() {
        dao = new SQLCLODao(SplashActivity.this);
        List<ShopTag.BodyBean> bean = dao.selectSort();
        if (bean != null && bean.size() == 0) {
            initInter();
        }
    }

    @Override
    protected void initData() {
        countDownTimer.start();
    }

    private void initInter() {
        Network.shopTAGService().getShopTag()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(shopTAGList);
    }

    /*
      * 工作室的残忍拒绝订单处理
      * */
    Observer<ShopTag> shopTAGList = new Observer<ShopTag>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "onNext: ---" + e.getMessage());
        }

        @Override
        public void onNext(ShopTag shopList) {
            List<ShopTag.BodyBean> data = shopList.getBody();
            Log.e(TAG, "onNext: ---" + data.size());
            for (ShopTag.BodyBean bean : data) {
                Log.e(TAG, "onNext: ---" + bean.getErji_name());
                dao.addCloTAG(bean);
            }
        }
    };

    @Override
    protected void setData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null)
            countDownTimer.cancel();
    }
}
