package manager.love.i.hmmanager.ui.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.base.BaseActivity;
import manager.love.i.hmmanager.bean.CheckInfo;
import manager.love.i.hmmanager.common.widgets.recycle.BaseRecycleAdapter;
import manager.love.i.hmmanager.common.widgets.recycle.BaseViewHolder;
import manager.love.i.hmmanager.ui.custom.dialog.MessageDialog;
import manager.love.i.hmmanager.utils.ActivityUtils;
import manager.love.i.hmmanager.utils.AsyncHttpUtils;
import manager.love.i.hmmanager.utils.HttpUtils;
import manager.love.i.hmmanager.utils.overlayutil.HttpCallBack;

public class CheckInfoAfterActivity extends BaseActivity {

    private static final String TAG = "CheckInfoActivity";

    private BaseRecycleAdapter<CheckInfo> adapter;

    private List<CheckInfo> mData;

    @BindView(R.id.rv_check_info_content)
    RecyclerView content;

    @BindView(R.id.iv_after_examine_state)
    ImageView state;

    @BindViews({R.id.tv_check_info_after_state, R.id.tv_go_updata_info, R.id.tv_check_info_refuse_reason})
    TextView[] tvs;

    private String update, studio_id, refuse_reason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_info_after);
    }

    @Override
    protected void setListener() {

    }


    public void back(View view) {
        finish();
    }


    @OnClick({R.id.tv_go_updata_info, R.id.tv_check_info_refuse_reason})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.tv_go_updata_info:
                Intent intent = new Intent(this, ServiceCityActivity.class);
                intent.putExtra("update", update);
                intent.putExtra("studio_id", studio_id);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_check_info_refuse_reason:
                if (ActivityUtils.isEmpty(refuse_reason)) {
                    MessageDialog dialog = new MessageDialog(this, refuse_reason);
                    dialog.show();
                } else {
                    MessageDialog dialog = new MessageDialog(this, "请联系官方客服");
                    dialog.show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        studio_id = intent.getStringExtra("studio_id");
        String phone = intent.getStringExtra("phone");
        String account_state = intent.getStringExtra("account_state");
        if ("22".equals(account_state)) {
            tvs[0].setText("审核失败");
            tvs[2].setText("失败原因");
            state.setImageResource(R.mipmap.hm_register_examine_fail);
            tvs[1].setVisibility(View.VISIBLE);
        } else {
            tvs[0].setText("审核中");
            state.setImageResource(R.mipmap.hm_register_examineing);
            tvs[1].setVisibility(View.GONE);
        }
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("studio_id", studio_id);
        final String json = HttpUtils.toJson(map);
        mData = new ArrayList<>();
        new AsyncHttpUtils(new HttpCallBack() {
            @Override
            public void onResponse(String result) {
                try {
                    update = result;
                    JSONObject objs = new JSONObject(result);
                    JSONObject obj = objs.getJSONObject("body");
                    mData.add(new CheckInfo("姓名:", obj.getString("name")));
                    mData.add(new CheckInfo("身份证号:", obj.getString("id_number")));
                    mData.add(new CheckInfo("手机号:", obj.getString("phone")));
                    mData.add(new CheckInfo("推荐人:", obj.getString("referee_name")));
                    mData.add(new CheckInfo("银行卡号:", obj.getString("card_no")));
                    mData.add(new CheckInfo("开户行:", obj.getString("card_bank")));
                    mData.add(new CheckInfo("地址信息:", obj.getString("address")));
                    int grade = obj.getInt("grade");
                    if (1 == grade)
                        mData.add(new CheckInfo("工作室等级:", "见习工作室"));
                    else
                        mData.add(new CheckInfo("工作室等级:", "认证工作室"));
                    adapter.notifyDataSetChanged();
                    refuse_reason = obj.getString("refuse_reason");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, this).execute("http://hmyc365.net/HM/bg/hmgls/login/register/data/getData.do", json);

    }


    @Override
    protected void setData() {
        adapter = new BaseRecycleAdapter<CheckInfo>(this, mData, R.layout.item_check_info_rv) {
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
}
