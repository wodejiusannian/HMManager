package manager.love.i.hmmanager.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;

import butterknife.BindView;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.base.BaseActivity;
import manager.love.i.hmmanager.utils.ToastUtils;
import manager.love.i.hmmanager.utils.overlayutil.DrivingRouteOverlay;

public class MapActivity extends BaseActivity implements OnGetRoutePlanResultListener {

    boolean isFirstLoc = true;

    public BDLocationListener myListener = new MyLocationListener();

    @BindView(R.id.mp_map_address)
    MapView mMapView = null;

    BaiduMap mBaiduMap = null;
    RoutePlanSearch mSearch = null;

    private String address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);
        if (isPermission() || Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1)
            initLocation();
        else
            applyPermission();

    }

    @Override
    protected void setData() {
        address = getIntent().getStringExtra("address");
    }

    /*
    * 获取地址
    * */
    private void initLocation() {
        LocationClient mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult result) {

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult result) {

    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult result) {

        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            ToastUtils.Toa(this, "抱歉，未能规划路线");
        }

        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            return;
        }

        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(result.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
        }
    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }

    //定制RouteOverly
    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
        }
    }


    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        mSearch.destroy();
        mMapView.onDestroy();
        super.onDestroy();
    }


    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            if (location == null || mMapView == null)
                return;

            if (isFirstLoc) {
                isFirstLoc = false;
                mBaiduMap.clear();
                PlanNode stNode = PlanNode.withLocation(new LatLng(location.getLatitude(), location.getLongitude()));
                PlanNode enNode = PlanNode.withCityNameAndPlaceName(location.getCity(), address);
                mSearch.drivingSearch((new DrivingRoutePlanOption())
                        .from(stNode)
                        .to(enNode));
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }

        public void onReceivePoi(BDLocation poiLocation) {


        }
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
}
