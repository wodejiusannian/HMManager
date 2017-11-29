package manager.love.i.hmmanager.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindViews;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.base.BaseActivity;

public class MsgDetailsActivity extends BaseActivity {
    @BindViews({R.id.tv_content, R.id.tv_title})
    TextView[] tViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_details);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        tViews[0].setText(intent.getStringExtra("content"));
        tViews[1].setText(intent.getStringExtra("title"));
    }

    @Override
    protected void setData() {

    }
}
