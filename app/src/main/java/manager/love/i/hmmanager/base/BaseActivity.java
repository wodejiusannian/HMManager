package manager.love.i.hmmanager.base;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import manager.love.i.hmmanager.common.widgets.dialog.MyProgressDialog;

public abstract class BaseActivity extends AppCompatActivity {
    Unbinder unbinder;
    private static MyProgressDialog selfDialog;


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        unbinder = ButterKnife.bind(this);
        initData();
        setData();
        setListener();
    }

    protected abstract void setListener();

    protected abstract void initData();

    protected abstract void setData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        dismissDialog();
    }


    protected void loadingDialog() {
        if (selfDialog == null) {
            selfDialog = new MyProgressDialog(this);
        }
        selfDialog.show();
    }

    protected void dismissDialog() {
        if (selfDialog != null) {
            selfDialog.dismiss();
            selfDialog = null;
        }
    }


}
