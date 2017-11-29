package manager.love.i.hmmanager.ui.activity;

import android.os.Bundle;
import android.view.View;

import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.base.BaseActivity;

public class DrawerForUsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_for_us);
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
}
