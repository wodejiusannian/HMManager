package manager.love.i.hmmanager.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.base.BaseActivity;
import manager.love.i.hmmanager.bean.StudioInfo;
import manager.love.i.hmmanager.bean.UserInfo;
import manager.love.i.hmmanager.inter.Network;
import manager.love.i.hmmanager.utils.ActivityUtils;
import manager.love.i.hmmanager.utils.PermissionsUtils;
import manager.love.i.hmmanager.utils.SPUtils;
import manager.love.i.hmmanager.utils.ToastUtils;
import manager.love.i.hmmanager.utils.glideutils.GlideCircleTransform;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static manager.love.i.hmmanager.utils.PermissionsUtils.PER_STORAGE;

public class DrawerPhotoEditActivity extends BaseActivity {

    @BindView(R.id.et_edit_introduce)
    EditText etIntroduce;


    @BindView(R.id.rg_edit_sex)
    RadioGroup rgSex;

    @BindView(R.id.iv_edit_photo)
    ImageView iPhoto;

    private String mSex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_photo_edit);
    }

    @Override
    protected void setListener() {
        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_photo_edit_boy:
                        mSex = "2";
                        break;
                    case R.id.rb_photo_edit_girl:
                        mSex = "1";
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {
        StudioInfo.BodyBean.InfoBean bodyBean = (StudioInfo.BodyBean.InfoBean) getIntent().getSerializableExtra("bodyBean");
        if (bodyBean != null) {
            String introduction = bodyBean.getIntroduction();
            String sex = bodyBean.getStudio_sex();
            if (ActivityUtils.isEmpty(introduction))
                etIntroduce.setText(introduction);

            if (ActivityUtils.isEmpty(sex)) {
                mSex = sex;
                if (TextUtils.equals("1", sex)) {
                    RadioButton girl = (RadioButton) rgSex.getChildAt(1);
                    girl.setChecked(true);
                } else {
                    RadioButton boy = (RadioButton) rgSex.getChildAt(0);
                    boy.setChecked(true);
                }
            }

            Glide.with(this)
                    .load(bodyBean.getStudio_head_pic_url())
                    .error(R.mipmap.hm_main_drawer_photo_default)
                    .crossFade(1000)
                    .transform(new GlideCircleTransform(this))
                    .into(iPhoto);
        }
    }

    @Override
    protected void setData() {

    }

    public void back(View view) {
        finish();
    }

    @OnClick({R.id.iv_drawer_photo_edit_save, R.id.iv_edit_photo})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.iv_edit_photo:
                if (PermissionsUtils.isPermission(this, PermissionsUtils.PER_STORAGE) || Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1)
                    initPhoto();
                else
                    applyPermission();
                break;
            case R.id.iv_drawer_photo_edit_save:
                String introduce = etIntroduce.getText().toString().trim();
                if (ActivityUtils.isEmpty(mSex) && ActivityUtils.isEmpty(introduce)) {
                    String stuId = SPUtils.getStuId(this);
                    upUserInfo(stuId, mSex, introduce);
                } else {
                    ToastUtils.Toa(this, "请将信息填写完整");
                }
                break;
            default:
                break;
        }
    }

    /*
    * sex 女男  1是女2是男
    * */
    private void upUserInfo(String stuId, String sex, String introduce) {
        Network.addUserInfoService().addUserInfo(stuId, sex, introduce).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(studioInfoObserver);
    }


    /*
  * 处理工作室的信息
  * */
    Observer<UserInfo> studioInfoObserver = new Observer<UserInfo>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(UserInfo studioInfo) {
            String ret = studioInfo.getRet();
            if (TextUtils.equals("0", ret)) {
                SPUtils.upDataSex(DrawerPhotoEditActivity.this, mSex);
                ToastUtils.Toa(DrawerPhotoEditActivity.this, "修改成功");
            } else {
                ToastUtils.Toa(DrawerPhotoEditActivity.this, "修改失败");
            }
        }
    };

    /*
    * 申请访问外部
    * */
    private void applyPermission() {
        PermissionsUtils.applyPermission(this, PER_STORAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            if (data != null) {
                loadingDialog();
                ArrayList<String> list = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                RequestParams params = new RequestParams("http://hmyc365.net:8084/HM/stu/userinfo/data/updateUserPic.do");
                params.addBodyParameter("studio_id", SPUtils.getStuId(DrawerPhotoEditActivity.this));
                params.addBodyParameter("img", new File(list.get(0)));
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            JSONObject object = new JSONObject(result);
                            String ret = object.getString("ret");
                            if (TextUtils.equals("0", ret)) {
                                JSONObject body = object.getJSONObject("body");
                                String pic_url = body.getString("pic_url");
                                Glide.with(DrawerPhotoEditActivity.this)
                                        .load(pic_url)
                                        .error(R.mipmap.hm_main_drawer_photo_default)
                                        .crossFade(1000)
                                        .transform(new GlideCircleTransform(DrawerPhotoEditActivity.this))
                                        .into(iPhoto);
                                SPUtils.updatePhoto(DrawerPhotoEditActivity.this, pic_url);
                                ToastUtils.Toa(DrawerPhotoEditActivity.this, "修改成功");
                            } else {
                                ToastUtils.Toa(DrawerPhotoEditActivity.this, "修改失败");
                            }
                            dismissDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        dismissDialog();
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        }
    }

    private void initPhoto() {
        PhotoPickerIntent intent = new PhotoPickerIntent(DrawerPhotoEditActivity.this);
        intent.setMaxTotal(1);
        intent.setShowCarema(true);
        startActivityForResult(intent, 1);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionsUtils.PER_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }
        }
    }
}
