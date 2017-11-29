package manager.love.i.hmmanager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import butterknife.BindView;
import manager.love.i.hmmanager.adapter.LoadingAdapter;
import manager.love.i.hmmanager.base.BaseActivity;

public class LoadingActivity extends BaseActivity {

    @BindView(R.id.vp_loading_isFirst)
    public ViewPager loadingImgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loding);
        initData();
    }


    @Override
    protected void setListener() {

    }

    protected void initData() {
        LoadingAdapter adapter = new LoadingAdapter(this);
        loadingImgs.setAdapter(adapter);
    }

    @Override
    protected void setData() {

    }
}
