package manager.love.i.hmmanager.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import butterknife.BindViews;
import butterknife.OnClick;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.base.BaseActivity;
import manager.love.i.hmmanager.bean.UserInfo;
import manager.love.i.hmmanager.inter.Network;
import manager.love.i.hmmanager.utils.ActivityUtils;
import manager.love.i.hmmanager.utils.SPUtils;
import manager.love.i.hmmanager.utils.ToastUtils;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DrawerFeedBackActivity extends BaseActivity {

    @BindViews({R.id.et_feed_back_content, R.id.et_feed_back_phone})
    EditText[] advice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_feed_back);

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

    @OnClick({R.id.btn_feed_back_advice})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.btn_feed_back_advice:
                String stuId = SPUtils.getStuId(this);
                String ad = advice[0].getText().toString().trim();
                String phone = advice[1].getText().toString().trim();
                if (ActivityUtils.isEmpty(ad) && ActivityUtils.isEmpty(phone)) {
                    Network.addAdviceService().addAdvice(stuId, phone, ad, "2")
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(feedBack);
                } else {
                    ToastUtils.Toa(this, "请补全信息，谢谢");
                }
                break;
            default:
                break;
        }
    }


    /*
   * 工作室的订单列表处理
   * */
    Observer<UserInfo> feedBack = new Observer<UserInfo>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            ToastUtils.Toa(DrawerFeedBackActivity.this, "网络中断，请重试");
        }

        @Override
        public void onNext(UserInfo userInfo) {
            String ret = userInfo.getRet();
            if (TextUtils.equals("0", ret)) {
                ToastUtils.Toa(DrawerFeedBackActivity.this, "反馈成功");
            } else {
                ToastUtils.Toa(DrawerFeedBackActivity.this, "反馈失败");
            }
        }
    };
}
