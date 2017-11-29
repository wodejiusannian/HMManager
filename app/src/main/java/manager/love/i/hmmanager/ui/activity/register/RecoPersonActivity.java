package manager.love.i.hmmanager.ui.activity.register;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.base.BaseActivity;
import manager.love.i.hmmanager.bean.LyCommendPeople;
import manager.love.i.hmmanager.common.widgets.recycle.BaseRecycleAdapter;
import manager.love.i.hmmanager.common.widgets.recycle.BaseViewHolder;
import manager.love.i.hmmanager.utils.ActivityUtils;
import manager.love.i.hmmanager.utils.AsyncHttpUtils;
import manager.love.i.hmmanager.utils.HttpUtils;
import manager.love.i.hmmanager.utils.ToastUtils;
import manager.love.i.hmmanager.utils.overlayutil.HttpCallBack;

public class RecoPersonActivity extends BaseActivity {
    private static final String TAG = "LyCommendPeople";

    @BindView(R.id.rv_ly_commendpeople_content)
    RecyclerView rvContent;

    @BindViews({R.id.tv_ly_commendpeople_city})
    TextView[] tvs;

    private BaseRecycleAdapter<LyCommendPeople.BodyBean> adapter;

    private List<LyCommendPeople.BodyBean> mData = new ArrayList<>();
    private LocationClient mLocClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_person);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        adapter = new BaseRecycleAdapter<LyCommendPeople.BodyBean>(this, mData, R.layout.item_ly_reommend_people) {
            @Override
            public void bindData(BaseViewHolder holder, final LyCommendPeople.BodyBean lyCommendPeople, int position) {
                RelativeLayout root = holder.getView(R.id.ll_item_lycommendpeople_root);
                ImageView photo = holder.getView(R.id.iv_item_lycommendpeople_photo);
                TextView name = holder.getView(R.id.tv_item_lycommendpeople_name);
                TextView introduce = holder.getView(R.id.tv_item_lycommendpeople_introduce);
                Glide.with(RecoPersonActivity.this).load(lyCommendPeople.getPic_url()).into(photo);
                name.setText(lyCommendPeople.getName_gzs());
                introduce.setText(lyCommendPeople.getName_fzr());
                root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra("id", lyCommendPeople);
                        setResult(100, intent);
                        finish();
                    }
                });
            }
        };
        rvContent.setLayoutManager(new LinearLayoutManager(this));
        rvContent.setAdapter(adapter);
    }

    @OnClick({R.id.tv_ly_commendpeople_city})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.tv_ly_commendpeople_city:
                startActivityForResult(new Intent(this, LiJiYuYueStudioSelectCityActivity.class), 1000);
                break;
            default:
                break;
        }
    }


    @Override
    protected void setData() {
        if (isPermission() || Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1)
            initLocation();
        else
            applyPermission();
    }


    /*
     * 是否开启定位权限
     * */
    private boolean isPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    /*
 * 申请定位权限
 * */
    private void applyPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
    }

    /*
  * 处理权限设置后的操作
  * */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initLocation();
                } else {
                    ToastUtils.Toa(this, "亲，请打开定位权限");
                }
                return;
            }
        }
    }

    /*
  * 获取地址
  * */
    private void initLocation() {
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(new MyLocationListener());
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            if (location == null)
                return;

            final String city = location.getCity();
            if (ActivityUtils.isEmpty(city)) {
                RecoPersonActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvs[0].setText(city);
                    }
                });
                mLocClient.stop();
                Map<String, String> map = new HashMap<>();
                map.put("city", city);
                String s = HttpUtils.toJson(map);
                notifyData(s);
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }

        public void onReceivePoi(BDLocation poiLocation) {


        }
    }

    private void notifyData(String s) {
        new AsyncHttpUtils(new HttpCallBack() {
            @Override
            public void onResponse(String result) {
                try {
                    mData.clear();
                    Gson gson = new Gson();
                    LyCommendPeople lyCommendPeople = gson.fromJson(result, LyCommendPeople.class);
                    mData.addAll(lyCommendPeople.getBody());
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, this).execute("http://hmyc365.net/HM/bg/pub/studio/info/getStudioList.do", s);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1000:
                try {
                    String address = data.getStringExtra("address");
                    tvs[0].setText(address);
                    Map<String, String> map = new HashMap<>();
                    map.put("city", address);
                    String s = HttpUtils.toJson(map);
                    notifyData(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
}
