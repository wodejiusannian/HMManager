package manager.love.i.hmmanager.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.base.BaseActivity;
import manager.love.i.hmmanager.bean.Evaluate;
import manager.love.i.hmmanager.bean.InDoorOrder;
import manager.love.i.hmmanager.bean.UserInfo;
import manager.love.i.hmmanager.common.widgets.dialog.RefuseDialog;
import manager.love.i.hmmanager.inter.Network;
import manager.love.i.hmmanager.inter.NetworkCopy;
import manager.love.i.hmmanager.utils.ActivityUtils;
import manager.love.i.hmmanager.utils.PermissionsUtils;
import manager.love.i.hmmanager.utils.SPUtils;
import manager.love.i.hmmanager.utils.ToastUtils;
import manager.love.i.hmmanager.utils.TokenUtils;
import manager.love.i.hmmanager.utils.glideutils.GlideCircleTransform;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailsActivity extends BaseActivity {

    @BindViews({R.id.tv_details_name, R.id.tv_details_number,
            R.id.tv_details_phone, R.id.tv_details_go_time,
            R.id.tv_details_address, R.id.tv_details_place_time,
            R.id.tv_details_name_customer, R.id.tv_details_evluate_content})
    TextView[] customView;

    @BindView(R.id.big_size_text_tip)
    TextView tip;

    @BindViews({R.id.tv_details_photo, R.id.iv_details_photo_customer})
    ImageView[] customerPhotos;

    @BindView(R.id.ll_details_evaluate)
    LinearLayout evaluate;

    @BindViews({R.id.btn_details_left, R.id.btn_details_middle, R.id.btn_details_right})
    Button[] buttons;

    @BindView(R.id.rb_details_evluate_star)
    RatingBar ratingBar;

    private InDoorOrder.BodyBean customerInfo;

    private String stuId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

    }

    @Override
    protected void setListener() {
        String eva = customerInfo.getEvaluate();
        String state = customerInfo.getState();
        if (TextUtils.equals("1", state) || TextUtils.equals("11", state)) {
            buttons[0].setVisibility(View.VISIBLE);
            buttons[1].setVisibility(View.GONE);
            buttons[2].setVisibility(View.VISIBLE);
            buttons[0].setText("进行中");
            buttons[2].setText("服务中");
            evaluate.setVisibility(View.GONE);
        } else if (TextUtils.equals("3", state) || TextUtils.equals("100", state) || TextUtils.equals("4", state) || TextUtils.equals("110", state)) {
            buttons[0].setVisibility(View.VISIBLE);
            buttons[1].setVisibility(View.GONE);
            buttons[2].setVisibility(View.VISIBLE);
            buttons[0].setText("进行中");
            buttons[2].setText("退款中");
            evaluate.setVisibility(View.GONE);
        } else if (TextUtils.equals("6", state) || TextUtils.equals("1001", state) || TextUtils.equals("1101", state)) {
            buttons[0].setVisibility(View.VISIBLE);
            buttons[1].setVisibility(View.GONE);
            buttons[2].setVisibility(View.VISIBLE);
            buttons[0].setText("已完成");
            buttons[2].setText("已退款");
            evaluate.setVisibility(View.GONE);
        } else if (TextUtils.equals("5", state) || TextUtils.equals("101", state)) {
            buttons[0].setVisibility(View.VISIBLE);
            buttons[1].setVisibility(View.GONE);
            buttons[2].setVisibility(View.VISIBLE);
            buttons[0].setText("已完成");
            buttons[2].setText("已拒绝");
            evaluate.setVisibility(View.GONE);
        } else if (TextUtils.equals("10", state) || TextUtils.equals("0", state)) {
            buttons[0].setVisibility(View.VISIBLE);
            buttons[1].setVisibility(View.GONE);
            buttons[2].setVisibility(View.VISIBLE);
            evaluate.setVisibility(View.GONE);
        } else if (TextUtils.equals("2", state) || TextUtils.equals("12", state) || TextUtils.equals("33", state)) {
            if (TextUtils.equals("1", eva)) {
                evaluate.setVisibility(View.VISIBLE);
                buttons[1].setVisibility(View.VISIBLE);
                buttons[1].setText("已评价");
                getEvaluate();
            } else if (TextUtils.equals("0", eva)) {
                buttons[1].setVisibility(View.VISIBLE);
                buttons[1].setText("未评价");
                evaluate.setVisibility(View.GONE);
            } else if ("-1".equals(eva)) {
                buttons[1].setVisibility(View.VISIBLE);
                buttons[1].setText("已投诉");
            }
        }
    }

    @Override
    protected void initData() {
        customerInfo = (InDoorOrder.BodyBean) getIntent().getSerializableExtra("customerInfo");
        stuId = SPUtils.getStuId(this);

    }

    @Override
    protected void setData() {
        customView[0].setText(customerInfo.getJd_name());
        String orderType = customerInfo.getOrderType();
        if ("1".equals(orderType)) {
            tip.setText("除螨：");
            customView[1].setText("标准" + customerInfo.getClothes_num() + "件");
        } else {
            customView[1].setText(customerInfo.getClothes_num());
        }
        customView[2].setText(customerInfo.getJd_phone());
        customView[3].setText(customerInfo.getJd_time());
        customView[4].setText(customerInfo.getJd_address());
        customView[5].setText(customerInfo.getXd_time());
        Glide.with(this)
                .load(customerInfo.getApp_user_head_pic())
                .error(R.mipmap.hm_main_drawer_photo_default)
                .crossFade(1000)
                .transform(new GlideCircleTransform(this))
                .into(customerPhotos[0]);
    }

    @OnClick({R.id.mp_details_tip, R.id.iv_details_small_phone, R.id.iv_details_big_phone, R.id.btn_details_right, R.id.btn_details_left})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.mp_details_tip:
                Map map = new HashMap();
                map.put("address", customerInfo.getJd_address());
                ActivityUtils.switchTo(this, MapActivity.class, map);
                break;
            case R.id.iv_details_big_phone:
                goPhone(customView[2].getText().toString().trim().replace(" ", ""));
                break;
            case R.id.iv_details_small_phone:
                goPhone(customView[2].getText().toString().trim().replace(" ", ""));
                break;
            case R.id.btn_details_right:
                String state = customerInfo.getState();
                if ("0".equals(state) || "10".equals(state)) {
                    accept();
                }
                break;
            case R.id.btn_details_left:
                String state1 = customerInfo.getState();
                if ("0".equals(state1) || "10".equals(state1)) {
                    refuse();
                }
                break;
            default:
                break;
        }
    }

    public void back(View view) {
        finish();
    }


    private void goPhone(String phone) {
        if (ActivityUtils.isEmpty(phone)) {
            if (PermissionsUtils.isPermission(this, PermissionsUtils.PER_CALL_PHONE)) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + phone);
                intent.setData(data);
                startActivity(intent);
            } else {
                PermissionsUtils.applyPermission(this, PermissionsUtils.PER_CALL_PHONE);
            }
        }
    }


    /*
     * 用户评价
     * */
    private void getEvaluate() {
        Network.userEvaluateService().userEvaluateService(stuId, customerInfo.getOrder_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userInfoServer);
    }

    /*
    * 处理用户评价
    * */
    Observer<Evaluate> userInfoServer = new Observer<Evaluate>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            ToastUtils.Toa(DetailsActivity.this, "网络中断，请重试" + e.getMessage());
        }

        @Override
        public void onNext(Evaluate evaluate) {
            Evaluate.BodyBean bean = evaluate.getBody();
            customView[6].setText(bean.getApp_user_name());
            customView[7].setText(bean.getContent());
            ratingBar.setNumStars(strToInt(bean.getStar()));
            Glide.with(DetailsActivity.this)
                    .load(bean.getApp_user_head_pic())
                    .error(R.mipmap.hm_main_drawer_photo_default)
                    .crossFade(1000)
                    .transform(new GlideCircleTransform(DetailsActivity.this))
                    .into(customerPhotos[1]);
        }
    };

    private int strToInt(String str) {
        if (ActivityUtils.isEmpty(str))
            return Integer.parseInt(str);
        else
            return 5;
    }


    /*
     * 工作室接单
     * */
    private void accept() {
        if ("1".equals(customerInfo.getOrderType())) {
            NetworkCopy.userAcceptOrderService().acceptOrder(TokenUtils.token, customerInfo.getOrder_id(), "1")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(acceptOrder);

        } else {
            Network.userAcceptOrderService().acceptOrder(stuId, customerInfo.getOrder_id(), "0", "11")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(acceptOrder);
        }
    }

    /*
     * 工作室的欣然接受订单处理
     * */
    Observer<UserInfo> acceptOrder = new Observer<UserInfo>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            ToastUtils.Toa(DetailsActivity.this, "网络中断，请重试" + e.getMessage());
        }

        @Override
        public void onNext(UserInfo userInfo) {
            if (TextUtils.equals("0", userInfo.getRet())) {
                ToastUtils.Toa(DetailsActivity.this, "接单成功");
            } else {
                ToastUtils.Toa(DetailsActivity.this, "接单失败，请重试");
            }
        }
    };


    /*
     * 工作室的残忍拒绝订单处理
     * */
    Observer<UserInfo> refuseOrder = new Observer<UserInfo>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            ToastUtils.Toa(DetailsActivity.this, "网络中断，请重试" + e.getMessage());
        }

        @Override
        public void onNext(UserInfo userInfo) {
            if (TextUtils.equals("0", userInfo.getRet())) {
                ToastUtils.Toa(DetailsActivity.this, "拒绝接单成功");
            } else {
                ToastUtils.Toa(DetailsActivity.this, "拒绝接单失败，请重试");
            }
        }
    };

    /*
      * 将数据发送到服务器
      * */
    private void refuseInter(String reason) {
        if ("1".equals(customerInfo.getOrderType())) {
            NetworkCopy.userRefuseOrderService().refuseOrder(TokenUtils.token, "1", customerInfo.getOrder_id(), reason)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(refuseOrder);
        } else {
            Network.userRefuseOrderService().refuseOrder(stuId, customerInfo.getOrder_id(), reason)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(refuseOrder);
        }
    }

    /*
    * 处理残忍拒绝的点击事件
    * */
    private void refuse() {
        RefuseDialog refuseDialog = new RefuseDialog(this);
        refuseDialog.setLogin(new RefuseDialog.Refuse() {
            @Override
            public void refuse(String reason) {
                refuseInter(reason);
            }
        });
        refuseDialog.show();
    }
}
