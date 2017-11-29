package manager.love.i.hmmanager;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import manager.love.i.hmmanager.base.BaseActivity;
import manager.love.i.hmmanager.bean.IMToken;
import manager.love.i.hmmanager.bean.IMUser;
import manager.love.i.hmmanager.bean.InDoorOrder;
import manager.love.i.hmmanager.bean.UserInfo;
import manager.love.i.hmmanager.common.widgets.dialog.AcceptDialog;
import manager.love.i.hmmanager.common.widgets.dialog.BottomDialog;
import manager.love.i.hmmanager.common.widgets.dialog.LoginDialog;
import manager.love.i.hmmanager.common.widgets.dialog.RefuseDialog;
import manager.love.i.hmmanager.common.widgets.recycle.BaseRecycleAdapter;
import manager.love.i.hmmanager.common.widgets.recycle.BaseViewHolder;
import manager.love.i.hmmanager.inter.Network;
import manager.love.i.hmmanager.sql.SQLIMDao;
import manager.love.i.hmmanager.ui.activity.DetailsActivity;
import manager.love.i.hmmanager.ui.activity.Drawer365Activity;
import manager.love.i.hmmanager.ui.activity.DrawerOrderActivity;
import manager.love.i.hmmanager.ui.activity.DrawerOverActivity;
import manager.love.i.hmmanager.ui.activity.DrawerPhotoActivity;
import manager.love.i.hmmanager.ui.activity.DrawerSettingActivity;
import manager.love.i.hmmanager.ui.activity.DrawerTimeManagerActivity;
import manager.love.i.hmmanager.ui.activity.DrawerWalletManagerActivity;
import manager.love.i.hmmanager.ui.activity.MsgActivity;
import manager.love.i.hmmanager.ui.activity.ShopActivity;
import manager.love.i.hmmanager.ui.activity.register.CheckInfoAfterActivity;
import manager.love.i.hmmanager.utils.ActivityUtils;
import manager.love.i.hmmanager.utils.AsyncHttpUtils;
import manager.love.i.hmmanager.utils.HttpUtils;
import manager.love.i.hmmanager.utils.RvDecorationUtils;
import manager.love.i.hmmanager.utils.SPUtils;
import manager.love.i.hmmanager.utils.ToastUtils;
import manager.love.i.hmmanager.utils.glideutils.GlideCircleTransform;
import manager.love.i.hmmanager.utils.glideutils.GlideRoundTransform;
import manager.love.i.hmmanager.utils.overlayutil.HttpCallBack;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static manager.love.i.hmmanager.utils.SPUtils.getStuId;

public class MainActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener, RongIM.UserInfoProvider {

    private static final int SET_REQUEST = 0;
    private static final int DETAILS_REQUEST = 1;

    private static final String TAG = "MainActivity";

    @BindView(R.id.dl_main_person)
    DrawerLayout person;

    @BindView(R.id.rv_main_order)
    RecyclerView rvOrder;

    @BindView(R.id.bga_main_refresh)
    BGARefreshLayout refresh;

    @BindView(R.id.nav_view)
    NavigationView navigationView;


    @BindView(R.id.tv_main_state)
    ImageView userState;

    @BindView(R.id.iv_main_is_login)
    ImageView isLogin;

    @BindView(R.id.rl_main_no_order)
    RelativeLayout noOrder;

    @BindView(R.id.tv_main_name)
    TextView title;

    private ImageView photo, stuSexIv;

    private TextView stuNameTv;

    private BaseRecycleAdapter<InDoorOrder.BodyBean> adapter;

    private List<InDoorOrder.BodyBean> mData = new ArrayList<>();

    private String stuId, stuSta;

    private int pos;

    @BindView(R.id.tv_no_login)
    TextView noLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @TargetApi(19)
    private void setTintManager() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        SystemBarTintManager mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setStatusBarTintColor(Color.parseColor("#20000000"));
    }

    @Override
    protected void setListener() {
        initUser();
    }

    @Override
    protected void initData() {
        setTintManager();
        initRefresh();
        stuSta = SPUtils.getStuSta(this);

        if (!ActivityUtils.isEmpty(SPUtils.getIMToken(this))) {
            SPUtils.writeUserInfo(this, null, null, null, null, null);
        } else {
            connect(SPUtils.getIMToken(this));
        }

        adapter = new BaseRecycleAdapter<InDoorOrder.BodyBean>(MainActivity.this, mData, R.layout.item_main_rv) {
            @Override
            public void bindData(BaseViewHolder holder, InDoorOrder.BodyBean bodyBean, final int position) {
                bindListener(holder, bodyBean, position);
            }
        };

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvOrder.setLayoutManager(manager);
        rvOrder.setAdapter(adapter);
        rvOrder.addItemDecoration(new RvDecorationUtils(10));

    }

    @Override
    protected void setData() {
        navigationView.setItemIconTintList(null);
        initNavigationView();
        refresh.beginRefreshing();
    }

    /*
    * 处理左滑的按钮
    * */
    private void initNavigationView() {
        View view = navigationView.getHeaderView(0);
        photo = (ImageView) view.findViewById(R.id.iv_drawer_stu_photo);
        stuSexIv = (ImageView) view.findViewById(R.id.iv_drawer_stu_sex);
        stuNameTv = (TextView) view.findViewById(R.id.tv_drawer_stu_name);
        view.findViewById(R.id.btn_drawer_no_over).setOnClickListener(this);
        view.findViewById(R.id.btn_drawer_over).setOnClickListener(this);
        view.findViewById(R.id.ll_drawer_365).setOnClickListener(this);
        view.findViewById(R.id.iv_drawer_stu_photo).setOnClickListener(this);
        view.findViewById(R.id.ll_drawer_setting).setOnClickListener(this);
        view.findViewById(R.id.ll_drawer_order).setOnClickListener(this);
        view.findViewById(R.id.ll_drawer_time).setOnClickListener(this);
        view.findViewById(R.id.ll_drawer_setting).setOnClickListener(this);
        view.findViewById(R.id.ll_drawer_wallet).setOnClickListener(this);
        view.findViewById(R.id.ll_drawer_shop).setOnClickListener(this);

    }

    /*
    * 处理当前activity每个item的点击事件
    * */
    private void bindListener(BaseViewHolder holder, InDoorOrder.BodyBean bodyBean, final int position) {
        TextView customerName = holder.getView(R.id.tv_item_rv_customer_name);
        TextView customerNumber = holder.getView(R.id.tv_item_rv_customer_number);
        TextView customerTime = holder.getView(R.id.tv_item_rv_customer_time);
        TextView customerAddress = holder.getView(R.id.tv_item_rv_customer_address);
        ImageView customerPhoto = holder.getView(R.id.tv_item_rv_customer_photo);
        TextView customerPlace = holder.getView(R.id.tv_item_rv_customer_place_time);
        customerName.setText(bodyBean.getJd_name());
        customerAddress.setText(bodyBean.getJd_address());
        customerTime.setText(bodyBean.getJd_time());
        customerNumber.setText(bodyBean.getClothes_num());
        customerPlace.setText(bodyBean.getXd_time());

        Glide.with(MainActivity.this)
                .load(bodyBean.getApp_user_head_pic())
                .error(R.mipmap.hm_main_drawer_photo_default)
                .crossFade(1000)
                .transform(new GlideRoundTransform(MainActivity.this))
                .into(customerPhoto);

        Button btnAccept = holder.getView(R.id.btn_item_main_accept);
        Button btnRefuse = holder.getView(R.id.btn_item_main_refuse);
        RelativeLayout rlDetails = (RelativeLayout) holder.getRootView();

        rlDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDetails(position);
            }
        });
        btnRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refuse(position);
            }
        });
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = position;
                accept(position);
            }
        });
    }

    /*
    * 处理残忍拒绝的点击事件
    * */
    private void refuse(final int pos) {
        RefuseDialog refuseDialog = new RefuseDialog(this);
        refuseDialog.setLogin(new RefuseDialog.Refuse() {
            @Override
            public void refuse(String reason) {
                refuseInter(pos, reason);
            }
        });
        refuseDialog.show();
    }

    /*
    * 将数据发送到服务器
    * */
    private void refuseInter(int pos, String reason) {
        InDoorOrder.BodyBean body = mData.get(pos);
        Network.userRefuseOrderService().refuseOrder(stuId, body.getOrder_id(), reason)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(refuseOrder);
    }

    /*
    * 处理点击查看详情的事件
    * */
    private void showAcceptDialog(final int position) {
        final AcceptDialog accept = new AcceptDialog(MainActivity.this);
        accept.setOnCancel(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initInDoorOrderData();
                accept.dismiss();
            }
        });
        accept.setOnDetailsListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDetails(position);
                accept.dismiss();
            }
        });
        accept.show();
    }


    @OnClick({R.id.tv_main_state, R.id.tv_main_msg, R.id.tv_main_person, R.id.tv_state_online_unline})
    public void onEvent(View v) {
        if (ActivityUtils.isEmpty(stuId)) {
            switch (v.getId()) {
                case R.id.tv_main_state:
                    state();
                    break;
                case R.id.tv_main_msg:
                    ActivityUtils.switchTo(MainActivity.this, MsgActivity.class);
                    break;
                case R.id.tv_main_person:
                    person.openDrawer(Gravity.LEFT);
                    break;
                case R.id.tv_state_online_unline:
                    state();
                    break;
                default:
                    break;
            }
        } else {
            goLogin();
        }
    }

    private void state() {
        BottomDialog dialog = new BottomDialog(this);
        dialog.setOnCenter("离线", new BottomDialog.OnCenter() {
            @Override
            public void onClick() {
                stuSta = "2";
                goOnLine();
            }
        });
        dialog.setTop("上线", new BottomDialog.OnTop() {
            @Override
            public void onClick() {
                stuSta = "1";
                goOnLine();
            }
        });
        dialog.show();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout bgaRefreshLayout) {
        initInDoorOrderData();
        handler.sendEmptyMessageDelayed(0, 5000);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout bgaRefreshLayout) {
        handler.sendEmptyMessageDelayed(1, 5000);
        return true;
    }

    /*
    * 如果5s获取不到数据，刷新和加载自动消失
    * */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (refresh != null)
                        refresh.endRefreshing();
                    break;
                case 1:
                    if (refresh != null)
                        refresh.endLoadingMore();
                    break;
                case 2:
                    if (refresh != null)
                        refresh.endLoadingMore();
                    break;
                case 4:
                    Toast.makeText(MainActivity.this, "登录--22222-成功", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(MainActivity.this, CheckInfoAfterActivity.class);
                 /*   in.putExtra("account_state", account_state);
                    in.putExtra("studio_id", obj.getString("studio_id"));
                    in.putExtra("phone", account);*/
                    startActivity(in);
                    break;
                default:
                    break;

            }
        }
    };

    /*
    * 点击进入详情，进入详情
    * */
    private void goDetails(int position) {
        InDoorOrder.BodyBean customerInfo = mData.get(position);
        Intent intent = new Intent();
        intent.setClass(this, DetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("customerInfo", customerInfo);
        intent.putExtras(bundle);
        startActivityForResult(intent, DETAILS_REQUEST);
    }

    /*
    * 初始化上拉加载，下拉刷新
    * */
    private void initRefresh() {
        refresh.setDelegate(this);
        //DefineBAGRefreshWithLoadView defineloadview =
        // new DefineBAGRefreshWithLoadView(this, true, true);
        BGANormalRefreshViewHolder holder = new BGANormalRefreshViewHolder(this, true);
        refresh.setRefreshViewHolder(holder);
    }

    @Override
    public void onClick(View v) {
        if (ActivityUtils.isEmpty(stuId)) {
            switch (v.getId()) {
                case R.id.ll_drawer_365:
                    ActivityUtils.switchTo(this, Drawer365Activity.class);
                    break;
                case R.id.ll_drawer_order:
                    ActivityUtils.switchTo(this, DrawerOrderActivity.class);
                    break;
                case R.id.ll_drawer_setting:
                    startActivityForResult(new Intent(MainActivity.this, DrawerSettingActivity.class), SET_REQUEST);
                    break;
                case R.id.ll_drawer_time:
                    ActivityUtils.switchTo(this, DrawerTimeManagerActivity.class);
                    break;
                case R.id.ll_drawer_wallet:
                    ActivityUtils.switchTo(this, DrawerWalletManagerActivity.class);
                    break;
                case R.id.btn_drawer_no_over:
                    Map<String, Object> map = new HashMap<>();
                    map.put("page", 0);
                    ActivityUtils.switchTo(this, DrawerOverActivity.class, map);
                    break;
                case R.id.btn_drawer_over:
                    Map<String, Object> maps = new HashMap<>();
                    maps.put("page", 1);
                    ActivityUtils.switchTo(this, DrawerOverActivity.class, maps);
                    break;
                case R.id.iv_drawer_stu_photo:
                    startActivityForResult(new Intent(MainActivity.this, DrawerPhotoActivity.class), SET_REQUEST);
                    break;
                case R.id.ll_drawer_shop:
                    startActivity(new Intent(MainActivity.this, ShopActivity.class));
                    break;
                default:
                    break;
            }
        } else {
            goLogin();
        }
    }

    /*
   * 登录
   * */
    private void goLogin() {
        LoginDialog loginDialog = new LoginDialog(this);
        loginDialog.setLogin(new LoginDialog.Login() {
            @Override
            public void getInfo(String account, String pwd) {
                String regId = JPushInterface.getRegistrationID(MainActivity.this);
                goLoginAfter(account, pwd, regId);
               /* Network.getUserInfo().userInfo(account, pwd, regId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(userInfoServer);*/
            }
        });
        loginDialog.show();

    }

    private void goLoginAfter(final String account, String pwd, final String regID) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", account);
        map.put("pwd", pwd);
        map.put("regist_id", regID);
        String json = HttpUtils.toJson(map);
        new AsyncHttpUtils(new HttpCallBack() {
            @Override
            public void onResponse(String result) {
                try {
                    Log.e(TAG, "onResponse: ---------" + result);

                    JSONObject obj = new JSONObject(result);
                    String ret = obj.getString("ret");
                    if ("0".equals(ret)) {
                        JSONObject body = obj.getJSONObject("body");
                        String account_state = body.getString("account_state");
                        String studio_id = body.getString("studio_id");
                        if ("10".equals(account_state)) {
                            SPUtils.writeUserInfo(MainActivity.this, body.getString("state"), body.getString("sex"), body.getString("name"), body.getString("pic_url"), body.getString("studio_id"));
                            initUser();
                            initRongIM();
                            Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        } else {
                            //handler.sendEmptyMessage(4);
                            Intent in = new Intent(MainActivity.this, CheckInfoAfterActivity.class);
                            in.putExtra("account_state", account_state);
                            in.putExtra("phone", account);
                            in.putExtra("studio_id", studio_id);
                            startActivity(in);
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "账号或者密码错误", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, this).execute("http://hmyc365.net/HM/bg/hmgls/login/info/login.do", json);
    }

    /*
    * 登录，获取用户信息
    * */
    Observer<UserInfo> userInfoServer = new Observer<UserInfo>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            ToastUtils.Toa(MainActivity.this, "网络中断，请重试");
        }

        @Override
        public void onNext(UserInfo userInfo) {
            String ret = userInfo.getRet();
            if (TextUtils.equals("0", ret)) {
                UserInfo.BodyBean u = userInfo.getBody();
                SPUtils.writeUserInfo(MainActivity.this, u.getStudio_state(), u.getStudio_sex(), u.getStudio_name(), u.getStudio_head_pic_url(), u.getStudio_id());
                initUser();
                initRongIM();
                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "账号或者密码错误", Toast.LENGTH_SHORT).show();
            }
        }
    };


    /*
   * 处理上下线
   * */
    private void goOnLine() {
        Network.userState().userState(stuId, stuSta)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userStateServer);
    }

    /*
    * 设置工作室状态设置
    * */
    Observer<UserInfo> userStateServer = new Observer<UserInfo>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            ToastUtils.Toa(MainActivity.this, "网络中断，请重试");
        }

        @Override
        public void onNext(UserInfo userInfo) {
            if (TextUtils.equals("0", userInfo.getRet())) {
                if (TextUtils.equals("1", stuSta))
                    userState.setImageResource(R.mipmap.hm_main_online);
                else
                    userState.setImageResource(R.mipmap.hm_main_unline);

                SPUtils.updateState(MainActivity.this, stuSta);

                Toast.makeText(MainActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "设置失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    /*
    * 工作室的订单列表处理
    * */
    Observer<InDoorOrder> inDoorOrder = new Observer<InDoorOrder>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(InDoorOrder inDoorOrders) {
            mData.clear();
            refresh.endRefreshing();
            refresh.endLoadingMore();
            mData.addAll(inDoorOrders.getBody());
            adapter.notifyDataSetChanged();
            if (mData.size() > 0) {
                noOrder.setVisibility(View.GONE);
            } else {
                noOrder.setVisibility(View.VISIBLE);
            }
        }
    };

    /*
     * 工作室接单
     * */
    private void accept(int pos) {
        InDoorOrder.BodyBean body = mData.get(pos);
        Network.userAcceptOrderService().acceptOrder(stuId, body.getOrder_id(), "0", "11")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(acceptOrder);
    }

    /*
     * 工作室的欣然接受订单处理
     * */
    Observer<UserInfo> acceptOrder = new Observer<UserInfo>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            ToastUtils.Toa(MainActivity.this, "网络中断，请重试" + e.getMessage());
        }

        @Override
        public void onNext(UserInfo userInfo) {
            if (TextUtils.equals("0", userInfo.getRet())) {
                showAcceptDialog(pos);
            } else {
                ToastUtils.Toa(MainActivity.this, "接单失败，请重试");
            }
        }
    };


    /*
     * 工作室的残忍拒绝订单处理
     * */
    Observer<UserInfo> refuseOrder = new Observer<UserInfo>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            ToastUtils.Toa(MainActivity.this, "网络中断，请重试" + e.getMessage());
        }

        @Override
        public void onNext(UserInfo userInfo) {
            if (TextUtils.equals("0", userInfo.getRet())) {
                initInDoorOrderData();
                ToastUtils.Toa(MainActivity.this, "拒绝接单成功");
            } else {
                ToastUtils.Toa(MainActivity.this, "拒绝接单失败，请重试");
            }
        }
    };

    /*
    *  获取当前工作室的订单
    * */
    private void initInDoorOrderData() {
        stuId = getStuId(this);
        Network.userInDoorOrder().getInDoorOrder(stuId, "10")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(inDoorOrder);
    }


    private void initUser() {
        stuId = getStuId(this);
        String stuName = SPUtils.getStuName(this);
        String stuPic = SPUtils.getStuPic(this);
        String stuSex = SPUtils.getStuSex(this);
        String stuSta = SPUtils.getStuSta(this);

        if (ActivityUtils.isEmpty(stuId)) {
            isLogin.setImageResource(R.mipmap.hm_main_no_order);
            noLogin.setVisibility(View.GONE);
        } else {
            isLogin.setImageResource(R.mipmap.hm_main_no_login);
            noLogin.setVisibility(View.VISIBLE);
        }
        if (TextUtils.equals("1", stuSta))
            userState.setImageResource(R.mipmap.hm_main_online);
        else
            userState.setImageResource(R.mipmap.hm_main_unline);

        if (TextUtils.equals("2", stuSex))
            stuSexIv.setImageResource(R.mipmap.hm_drawer_photo_sex_man_select);
        else if (TextUtils.equals("1", stuSex)) {
            stuSexIv.setImageResource(R.mipmap.hm_drawer_photo_sex_woman_select);
        }

        if (ActivityUtils.isEmpty(stuName)) {
            title.setText(stuName);
            stuNameTv.setText(stuName);
        } else {
            stuNameTv.setText("");
            title.setText("未登录");
        }
        Glide.with(MainActivity.this)
                .load(stuPic)
                .error(R.mipmap.hm_main_studio_photo_bg)
                .crossFade(1000)
                .transform(new GlideCircleTransform(MainActivity.this))
                .into(photo);

        /*Glide.with(MainActivity.this)
                .load(stuPic)
                .error(R.mipmap.hm_main_drawer_photo_default_bg)
                .crossFade(1000)
                .bitmapTransform(new BlurTransformation(MainActivity.this, 5, 4))
                .into(blurTrans);*/
        initInDoorOrderData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SET_REQUEST:
                initUser();
                String stuId = SPUtils.getStuId(this);
                if (!ActivityUtils.isEmpty(stuId)) {
                    mData.clear();
                    adapter.notifyDataSetChanged();
                }
                break;
            case DETAILS_REQUEST:
                initInDoorOrderData();
                break;
            default:
                break;
        }
    }

    private void initRongIM() {
        Network.imTokenService().imToken(2, SPUtils.getStuId(this), SPUtils.getStuName(this), SPUtils.getStuPic(this))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(imTokenObserver);
    }

    /*
    * 获取登录token
    * */
    Observer<IMToken> imTokenObserver = new Observer<IMToken>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            ToastUtils.Toa(MainActivity.this, "网络中断，请重试" + e.getMessage());
        }

        @Override
        public void onNext(IMToken imToken) {
            String token = imToken.getBody().getToken();
            SPUtils.updateImToken(MainActivity.this, token);
            connect(token);
        }
    };

    /**
     * <p>如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。</p>
     *
     * @param token 从服务端获取的用户身份令牌（Token）。
     * @return RongIM  客户端核心类的实例。
     */
    private void connect(String token) {

        RongIM.connect(token, new RongIMClient.ConnectCallback() {

            /**
             * Token 错误。可以从下面两点检查
             * 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             * 2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            @Override
            public void onTokenIncorrect() {

            }

            /**
             * 连接融云成功
             * @param userid 当前 token 对应的用户 id
             */
            @Override
            public void onSuccess(String userid) {
                RongIM.setUserInfoProvider(MainActivity.this, true);
                RongIM.setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {
                    @Override
                    public void onChanged(ConnectionStatus connectionStatus) {
                        switch (connectionStatus) {
                            case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SPUtils.writeUserInfo(MainActivity.this, null, null, null, null, null);
                                        Toast.makeText(MainActivity.this, "您的账号在其他手机登录，请确认后重新登录", Toast.LENGTH_SHORT).show();
                                        initUser();
                                        String stuId = SPUtils.getStuId(MainActivity.this);
                                        if (!ActivityUtils.isEmpty(stuId)) {
                                            goLogin();
                                            mData.clear();
                                            adapter.notifyDataSetChanged();
                                        }
                                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                                    }
                                });
                                break;
                            default:
                                break;
                        }

                    }
                });
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }

    @Override
    public io.rong.imlib.model.UserInfo getUserInfo(String s) {
        if (s.split("_")[1].equals(SPUtils.getStuId(MainActivity.this))) {
            return new io.rong.imlib.model.UserInfo(s, SPUtils.getStuName(MainActivity.this), Uri.parse(SPUtils.getStuPic(MainActivity.this)));
        } else {
            SQLIMDao dao = new SQLIMDao(MainActivity.this);
            IMUser u = dao.selectPerson(s);
            if (u != null && u.url != null) {
                return new io.rong.imlib.model.UserInfo(s, u.name, Uri.parse(u.url));
            } else {
                Network.getIMInfo(MainActivity.this, s);
                return null;
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }
}
