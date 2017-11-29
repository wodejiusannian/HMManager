package manager.love.i.hmmanager.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.adapter.TAbFrAdapter;
import manager.love.i.hmmanager.base.BaseActivity;
import manager.love.i.hmmanager.ui.fragment.DrawerOverFragment;


public class DrawerOverActivity extends BaseActivity {

    @BindView(R.id.vp_msg_content)
    ViewPager vpMsg;

    @BindView(R.id.tb_msg_title)
    TabLayout tbMsg;

    private TAbFrAdapter adapter;

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        if (fragments == null)
            fragments = new ArrayList<>();
        if (titles == null)
            titles = new ArrayList<>();

        adapter = new TAbFrAdapter(getSupportFragmentManager(), fragments, titles);
        vpMsg.setAdapter(adapter);
        tbMsg.setupWithViewPager(vpMsg);
        for (int i = 0; i < 2; i++) {
            DrawerOverFragment fragment = new DrawerOverFragment();
            Bundle bundle = new Bundle();
            if (i == 0) {
                bundle.putString("state_pj", "11,100,110");
                fragment.setArguments(bundle);
            } else {
                bundle.putString("state_pj", "1001,12,1101,101,33");
                fragment.setArguments(bundle);
            }
            fragments.add(fragment);
        }
        titles.add("未完成");
        titles.add("已完成");
        adapter.notifyDataSetChanged();
        int page = getIntent().getIntExtra("page", 0);
        vpMsg.setCurrentItem(page);
    }

    @Override
    protected void setData() {

    }


    public void back(View view) {
        finish();
    }
}
