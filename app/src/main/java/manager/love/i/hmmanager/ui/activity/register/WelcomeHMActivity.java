package manager.love.i.hmmanager.ui.activity.register;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;

import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.base.BaseActivity;
import manager.love.i.hmmanager.bean.LyCommendPeople;
import manager.love.i.hmmanager.ui.custom.dialog.MessageDialog;
import manager.love.i.hmmanager.ui.custom.dialog.TipDialog;
import manager.love.i.hmmanager.ui.fragment.welcome.WelPerfectInfoFragment;
import manager.love.i.hmmanager.ui.fragment.welcome.WelRegisterInfoFragment;
import manager.love.i.hmmanager.ui.fragment.welcome.WelSignAgreementFragment;
import manager.love.i.hmmanager.utils.ActivityUtils;
import manager.love.i.hmmanager.utils.HttpUtils;
import manager.love.i.hmmanager.utils.PermissionsUtils;
import manager.love.i.hmmanager.utils.RxBusUtils;
import manager.love.i.hmmanager.utils.interutlis.StringInter;
import rx.functions.Action1;

import static manager.love.i.hmmanager.utils.PermissionsUtils.PER_STORAGE;

public class WelcomeHMActivity extends BaseActivity {

    private FragmentManager mManager;

    private Fragment mCurrentFragment;

    private int page = 0;

    private Map<String, String> map = new HashMap<>();

    private MessageDialog dialog;

    private TipDialog tipDialog;
    private StringInter stringInter;

    private String update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_hm);

    }

    @Override
    protected void setListener() {
        RxBusUtils.getDefault().toObservable(Integer.class)
                .subscribe(new Action1<Integer>() {
                               @Override
                               public void call(Integer userEvent) {
                                   if (userEvent == 0 || userEvent == 9000) {
                                       WelcomeHMActivity.this.finish();
                                   }
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                // TODO: 处理异常
                            }
                        });
        notifyDialog("1、慧美衣橱工作室网上申请步骤均需申请人本人亲自完成，否则，一经发现公司有权冻结申请人的账号使用权\n2、建议提前准备好一下电子版资料以保证申请流程顺畅：\n-本人二代居民身份证\n-银行账户资料\n3、审核成功后用短信方式告知【账号】和【密码】");
    }

    @Override
    protected void initData() {
        mManager = getSupportFragmentManager();
        showAndHide(R.id.rl_welcome_content, WelRegisterInfoFragment.class);
    }


    public void back(View view) {
        finish();
    }

    @Override
    protected void setData() {
        update = getIntent().getStringExtra("update");
        String studio_id = getIntent().getStringExtra("studio_id");
        map.put("studio_id", studio_id);
    }


    public void upDateRegisterInfo(StringInter stringInter) {
        if (ActivityUtils.isEmpty(update) && stringInter != null) {
            stringInter.onResult(update);
        }
    }

    /**
     * @param page 要显示页 0是显示注册页，1完善资料页，2是签署协议页
     */
    public void flip(int page) {
        this.page = page;
        switch (page) {
            case 0:
                showAndHide(R.id.rl_welcome_content, WelRegisterInfoFragment.class);
                break;
            case 1:
                showAndHide(R.id.rl_welcome_content, WelPerfectInfoFragment.class);
                break;
            case 2:
                showAndHide(R.id.rl_welcome_content, WelSignAgreementFragment.class);
                break;
            default:
                break;
        }
    }


    /**
     * @param contentID 占位布局的id
     * @param clazz     要显示的fragment
     */
    private void showAndHide(int contentID, Class<? extends Fragment> clazz) {
        //判断当前显示的fragment是否和将要显示的fragment是同一个
        //如果是同一个那么久不向下走了，如果不是同一个就向下走
        if (mCurrentFragment != null && mCurrentFragment.getClass().getSimpleName().equals(clazz.getSimpleName())) {
            return;
        }
        //判断是否将fragment添加到事务管理器中
        FragmentTransaction transaction = mManager.beginTransaction();
        Fragment fragment = null;
        try {
            /*
            * 获取下当前要显示的frgament
            * */
            Fragment fragmentByTag = mManager.findFragmentByTag(clazz.getSimpleName());
            //如果不为空说明已经添加到了实物管理器中，如果为空需要重新添加
            if (fragmentByTag != null) {
                transaction.show(fragmentByTag);
                transaction.hide(mCurrentFragment);
                mCurrentFragment = fragmentByTag;
            } else {
                //说明将要显示的fragment为空，我们要创建
                //通过无参的 公开的构造函数来创建fragment实例
                fragment = clazz.newInstance();
                //将当前的fragment添加的事物管理器中
                transaction.add(contentID, fragment, clazz.getSimpleName());
                if (mCurrentFragment != null) {
                    transaction.hide(mCurrentFragment);
                }
                mCurrentFragment = fragment;
            }
            transaction.commit();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /*
    * 监听返回键，当时一页的时候就执行关闭页面，不是第一页的时候返回上一页
    * */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (page == 0) {
                return super.onKeyDown(keyCode, event);
            } else {
                flip(page - 1);
                return false;
            }
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void initCamera(StringInter stringInter) {
        if (stringInter != null) {
            this.stringInter = stringInter;
            if (PermissionsUtils.isPermission(this, PermissionsUtils.PER_STORAGE) || Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
                PhotoPickerIntent intent = new PhotoPickerIntent(WelcomeHMActivity.this);
                intent.setMaxTotal(1);
                intent.setShowCarema(true);
                startActivityForResult(intent, 1);
            } else {
                applyPermission();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (data != null) {
                loadingDialog();
                final ArrayList<String> list = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString("url", list.get(0));
                msg.setData(bundle);
                handler.sendMessage(msg);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String imageUrl = HttpUtils.uploadFile(new File(list.get(0)), "http://hmyc365.net/HM/bg/system/file/picture/picUpload.do");
                            imageUrl = imageUrl.substring(2, imageUrl.length() - 2);
                            Message msg = Message.obtain();
                            Bundle bundle = new Bundle();
                            bundle.putString("url", imageUrl);
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        } else if (requestCode == 100) {
            try {
                LyCommendPeople.BodyBean bodyBean = (LyCommendPeople.BodyBean) data.getSerializableExtra("id");
                stringInter.onResult(bodyBean.getName_gzs() + "," + bodyBean.getName_gzs());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            stringInter.onResult(data.getString("url"));
            dismissDialog();
        }
    };


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PER_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }
        }
    }


    /*
    * 申请访问外部
    * */
    private void applyPermission() {
        PermissionsUtils.applyPermission(this, PER_STORAGE);
    }


    public void registerInfo(String name, String id, String phone, String id_pic, String referee_name, String referee_id) {
        map.put("name", name);
        map.put("phone", phone);
        map.put("id_number", id);
        map.put("referee_name", referee_name);
        map.put("id_pic", id_pic);
        map.put("referee_id", referee_id);
    }

    public void perfectInfo(String bankId, String bankName, String city, String detailsAddress, String card_pic) {
        map.put("card_no", bankId);
        map.put("card_bank", bankName);
        map.put("city", city);
        map.put("address", detailsAddress);
        map.put("card_pic", card_pic);
    }


    public void welSingAgreement() {
        String json = HttpUtils.toJson(map);
        Intent intent = new Intent(WelcomeHMActivity.this, CheckInfoActivity.class);
        if (ActivityUtils.isEmpty(update)) {
            intent.putExtra("update", "update");
        }
        intent.putExtra("json", json);
        startActivity(intent);
    }

    public void jumpAddress(StringInter stringInter) {
        if (stringInter != null) {
            this.stringInter = stringInter;
            Intent intent = new Intent(this, RecoPersonActivity.class);
            startActivityForResult(intent, 100);
        }
    }

    public void notifyDialog(String dialogMessage) {
        if (dialog == null) {
            dialog = new MessageDialog(this, dialogMessage);
        } else {
            dialog.notifyMessage(dialogMessage);
        }
        dialog.show();
    }

    public void notifyPic(int dialogMessage) {
        if (tipDialog == null) {
            tipDialog = new TipDialog(this,dialogMessage);
        } else {
            tipDialog.notifyPic(dialogMessage);
        }
        tipDialog.show();
    }
}
