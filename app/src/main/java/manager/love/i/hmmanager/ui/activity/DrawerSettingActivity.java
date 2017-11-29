package manager.love.i.hmmanager.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import butterknife.BindView;
import butterknife.OnClick;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.base.BaseActivity;
import manager.love.i.hmmanager.common.widgets.dialog.BottomDialog;
import manager.love.i.hmmanager.common.widgets.dialog.NormalDialog;
import manager.love.i.hmmanager.utils.ActivityUtils;
import manager.love.i.hmmanager.utils.PermissionsUtils;
import manager.love.i.hmmanager.utils.SPUtils;

public class DrawerSettingActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    @BindView(R.id.tb_setting_jpush)
    ToggleButton jpush;

    private SharedPreferences mShare;

    private int check;

    private NormalDialog normalDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_setting);
    }

    @Override
    protected void setListener() {
        jpush.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initData() {
        mShare = getSharedPreferences("isCheck", 0);
        check = mShare.getInt("check", 0);
        if (check == 0) {
            jpush.setChecked(true);
        } else {
            jpush.setChecked(false);
        }
    }

    @Override
    protected void setData() {

    }

    @OnClick({R.id.ll_setting_feed_back, R.id.ll_setting_feed_connect, R.id.ll_setting_feed_for_us, R.id.ll_setting_feed_exit})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.ll_setting_feed_back:
                ActivityUtils.switchTo(this, DrawerFeedBackActivity.class);
                break;
            case R.id.ll_setting_feed_connect:
                showDialog();
                break;
            case R.id.ll_setting_feed_for_us:
                ActivityUtils.switchTo(this, DrawerForUsActivity.class);
                break;
            case R.id.ll_setting_feed_exit:
                normalDialog = new NormalDialog(this);
                normalDialog.setCancel("取消");
                normalDialog.setMsg("退出后，将接收不到消息");
                normalDialog.setTitle("提示");
                normalDialog.setOnYesListener("确定", this);
                normalDialog.show();
                break;
            default:
                break;
        }
    }

    private void showDialog() {
        BottomDialog dialog = new BottomDialog(this);
        dialog.setTop("电话：010-87227397", new BottomDialog.OnTop() {
            @Override
            public void onClick() {
                goPhone("01087227397");
            }
        });
        dialog.setInVisibal(true);
        dialog.show();
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SharedPreferences.Editor edit = mShare.edit();
        if (isChecked) {
            edit.putInt("check", 0);
        } else {
            edit.putInt("check", 1);
        }
        edit.commit();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_normal_sure:
                SPUtils.writeUserInfo(this, null, null, null, null, null);
                Toast.makeText(this, "退出成功", Toast.LENGTH_SHORT).show();
                normalDialog.dismiss();
                finish();
                break;
            default:
                break;
        }
    }
}
