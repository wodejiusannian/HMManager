package manager.love.i.hmmanager.ui.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.base.BaseActivity;
import manager.love.i.hmmanager.bean.CheckInfo;
import manager.love.i.hmmanager.common.widgets.recycle.BaseRecycleAdapter;
import manager.love.i.hmmanager.common.widgets.recycle.BaseViewHolder;
import manager.love.i.hmmanager.ui.custom.dialog.MySelfPayDialog;
import manager.love.i.hmmanager.utils.ActivityUtils;
import manager.love.i.hmmanager.utils.AsyncHttpUtils;
import manager.love.i.hmmanager.utils.HttpUtils;
import manager.love.i.hmmanager.utils.RxBusUtils;
import manager.love.i.hmmanager.utils.ali.AliPayUtils;
import manager.love.i.hmmanager.utils.overlayutil.HttpCallBack;
import rx.functions.Action1;

public class CheckInfoActivity extends BaseActivity {

    private static final String TAG = "CheckInfoActivity";

    private String json, phone;

    private List<CheckInfo> mData;

    @BindView(R.id.rv_check_info_content)
    RecyclerView content;


    private String order_id, studio_id, mTag, update;

    @BindView(R.id.btn_check_info_go_pay)
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_info);
    }

    @Override
    protected void setListener() {

        RxBusUtils.getDefault().toObservable(Integer.class)
                .subscribe(new Action1<Integer>() {
                               @Override
                               public void call(Integer userEvent) {
                                   if (userEvent == 0) {
                                       CheckInfoActivity.this.finish();
                                   } else if (userEvent == 9000) {
                                       Intent intent = new Intent(CheckInfoActivity.this, CheckInfoAfterActivity.class);
                                       intent.putExtra("account_state", "21");
                                       intent.putExtra("phone", phone);
                                       intent.putExtra("studio_id", studio_id);
                                       startActivity(intent);
                                       finish();
                                   } else if (userEvent == 9002) {
                                       Toast.makeText(CheckInfoActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                                   }
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                // TODO: 处理异常
                            }
                        });
    }

    @Override
    protected void initData() {
        json = getIntent().getStringExtra("json");
        update = getIntent().getStringExtra("update");
        if (ActivityUtils.isEmpty(update)) {
            btn.setText("确认修改");
        }
        mData = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(json);
            phone = obj.getString("phone");
            mData.add(new CheckInfo("姓名:", obj.getString("name")));
            mData.add(new CheckInfo("身份证号:", obj.getString("id_number")));
            mData.add(new CheckInfo("手机号:", phone));
            mData.add(new CheckInfo("推荐人:", obj.getString("referee_name")));
            mData.add(new CheckInfo("银行卡号:", obj.getString("card_no")));
            mData.add(new CheckInfo("开户行:", obj.getString("card_bank")));
            mData.add(new CheckInfo("地址信息:", obj.getString("address")));
            studio_id = obj.getString("studio_id");
        } catch (Exception e) {
            e.printStackTrace();
            studio_id = "";
        }
    }

    @Override
    protected void setData() {
        BaseRecycleAdapter<CheckInfo> adapter = new BaseRecycleAdapter<CheckInfo>(this, mData, R.layout.item_check_info_rv) {
            @Override
            public void bindData(BaseViewHolder holder, CheckInfo checkInfo, int position) {
                TextView name = holder.getView(R.id.tv_item_check_info_name);
                TextView nameValue = holder.getView(R.id.tv_item_check_info_name_value);
                name.setText(checkInfo.getName());
                nameValue.setText(checkInfo.getNameValue());
            }
        };
        content.setLayoutManager(new LinearLayoutManager(this));
        content.setAdapter(adapter);
    }

    @OnClick({R.id.btn_check_info_go_pay})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.btn_check_info_go_pay:
                if (ActivityUtils.isEmpty(update)) {
                    new AsyncHttpUtils(new HttpCallBack() {
                        @Override
                        public void onResponse(String result) {
                            try {
                                JSONObject obj = new JSONObject(result);
                                String ret = obj.getString("ret");
                                if ("0".equals(ret)) {
                                    Intent intent = new Intent(CheckInfoActivity.this, CheckInfoAfterActivity.class);
                                    intent.putExtra("phone", phone);
                                    intent.putExtra("account_state", "21");
                                    intent.putExtra("studio_id", studio_id);
                                    startActivity(intent);
                                    RxBusUtils.getDefault().post(0);
                                    finish();
                                } else {
                                    Toast.makeText(CheckInfoActivity.this, "请重试", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, this).execute("http://hmyc365.net/HM/bg/hmgls/login/register/data/updateData.do", json);
                } else {
                    goPay();
                }
                break;
            default:
                break;
        }
    }


    private void goPay() {
        new AsyncHttpUtils(new HttpCallBack() {
            @Override
            public void onResponse(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    String ret = object.getString("ret");
                    if ("0".equals(ret)) {
                        JSONObject body = object.getJSONObject("body");
                        studio_id = body.getString("studio_id");
                        order_id = body.getString("order_id");
                        MySelfPayDialog mySelfPayDialog = new MySelfPayDialog(CheckInfoActivity.this);
                        mySelfPayDialog.setOnNoListener("取消", null);
                        mySelfPayDialog.setOnYesListener("确定", new MySelfPayDialog.OnYesClickListener() {
                            @Override
                            public void onClick(String tag) {
                                mTag = tag;
                                selectGoPay();
                            }
                        });
                        mySelfPayDialog.show();
                    } else {
                        Toast.makeText(CheckInfoActivity.this, "请重试", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, this).execute("http://hmyc365.net/HM/bg/hmgls/login/register/info/registerAccount.do", json);
    }

    private void selectGoPay() {
        Map<String, String> map = new HashMap<>();
        map.put("studio_id", studio_id);
        map.put("order_id", order_id);
        map.put("pay_type", mTag);
        String jsonGetSign = HttpUtils.toJson(map);
        new AsyncHttpUtils(new HttpCallBack() {
            @Override
            public void onResponse(String result) {
                if ("1".equals(mTag)) {
                    AliPayUtils.aliPay(result, CheckInfoActivity.this);
                } else if ("2".equals(mTag)) {
                    Toast.makeText(CheckInfoActivity.this, "微信支付", Toast.LENGTH_SHORT).show();
                }
            }
        }, this).execute("http://hmyc365.net/HM/bg/hmgls/login/register/pay/getSign.do", jsonGetSign);
    }
}
