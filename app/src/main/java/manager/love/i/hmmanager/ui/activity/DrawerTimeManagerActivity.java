package manager.love.i.hmmanager.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.base.BaseActivity;
import manager.love.i.hmmanager.bean.StudioTime;
import manager.love.i.hmmanager.bean.Time;
import manager.love.i.hmmanager.bean.UserInfo;
import manager.love.i.hmmanager.common.widgets.recycle.BaseRecycleAdapter;
import manager.love.i.hmmanager.common.widgets.recycle.BaseViewHolder;
import manager.love.i.hmmanager.inter.Network;
import manager.love.i.hmmanager.utils.ActivityUtils;
import manager.love.i.hmmanager.utils.RvDecorationUtils;
import manager.love.i.hmmanager.utils.SPUtils;
import manager.love.i.hmmanager.utils.ToastUtils;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class DrawerTimeManagerActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "DrawerTimeManagerActivity";

    @BindView(R.id.rv_drawer_time_manager)
    RecyclerView recyclerView;

    @BindViews({R.id.tv_time_manager_y_m})
    TextView[] tViews;

    @BindViews({R.id.btn_drawer_time_manager_morning, R.id.btn_drawer_time_manager_afternoon,
            R.id.btn_drawer_time_manager_sure})
    Button[] btnViews;

    private BaseRecycleAdapter<Time> adapter;
    private Time t;
    private List<Time> mData = new ArrayList<>();
    private List<Boolean> booleans = new ArrayList<>();

    private void initTime() {
        String stuId = SPUtils.getStuId(this);
        Toast.makeText(this, "stuId" + stuId, Toast.LENGTH_SHORT).show();
        Network.studioTimeManagerService().timeManager(stuId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(studioInfoObserver);
    }

    /*
     * 处理日期
     * */
    Observer<StudioTime> studioInfoObserver = new Observer<StudioTime>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(StudioTime studioInfo) {
            String feture_days = studioInfo.getBody().getFeture_days();
            List<StudioTime.BodyBean.BusyTimeBean> bean = studioInfo.getBody().getBusy_time();
            if (bean != null && bean.get(0).getBusy_date() != null)
                tViews[0].setText(bean.get(0).getBusy_date().substring(0, 7));


            String[] days = feture_days.split(",");
            for (int i = 0; i < getWeek(); i++) {
                mData.add(new Time());
            }
            for (int i = 0; i < days.length; i++) {
                Time time = new Time();
                String day = days[i];
                time.ymd = day;
                String[] y_m_d = day.split("-");
                int size = bean.size();
                if (size == 0)
                    time.state = "4";

                for (int j = 0; j < size; j++) {
                    /*
                    * days里面有和集合里面的日期一样的
                    * */
                    StudioTime.BodyBean.BusyTimeBean timeBean = bean.get(j);
                    if (TextUtils.equals(day, timeBean.getBusy_date())) {
                        /*上午忙*/
                        if (TextUtils.equals("AM_BUSY", timeBean.getBusy_time_slot_tag())) {
                            time.state = "1";
                            break;
                        /*下午忙*/
                        } else if (TextUtils.equals("PM_BUSY", timeBean.getBusy_time_slot_tag())) {
                            time.state = "2";
                            break;
                        /*全天忙*/
                        } else if (TextUtils.equals("ALL_BUSY", timeBean.getBusy_time_slot_tag())) {
                            time.state = "3";
                            break;
                        }
                    /*今天没有事*/
                    } else {
                        time.state = "4";
                    }
                }
                /*设置年月日*/
                time.d = y_m_d[2];
                time.y = y_m_d[0];
                time.m = y_m_d[1];
                mData.add(time);
            }

            adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_time_manager);
    }

    @Override
    protected void setListener() {
        initTime();
    }

    @Override
    protected void initData() {
        if (mData == null)
            mData = new ArrayList<>();

        if (booleans == null)
            booleans = new ArrayList<>();

        adapter = new BaseRecycleAdapter<Time>(this, mData, R.layout.item_time_manager_rv) {
            @Override
            public void bindData(BaseViewHolder holder, Time time, int position) {

                itemOnClickListener(holder, time, position);
            }
        };
    }


    private void itemOnClickListener(BaseViewHolder holder, Time time, int position) {
        TextView date = holder.getView(R.id.tv_time_manager_date);
        ImageView star = holder.getView(R.id.tv_time_manager_star);

        date.setText(time.d);
        RelativeLayout rootView = (RelativeLayout) holder.getRootView();
        if (TextUtils.equals("4", time.state)) {
            star.setVisibility(View.GONE);
        } else if (!ActivityUtils.isEmpty(time.state)) {
            rootView.setVisibility(View.GONE);
        } else {
            star.setVisibility(View.VISIBLE);
        }
        if (booleans.size() != 0 && booleans.get(position)) {
            date.setBackgroundResource(R.drawable.shape_radios_red_cicle);
            date.setTextColor(Color.WHITE);
        } else {
            date.setBackgroundResource(R.drawable.shape_radios_white_cicle);
            date.setTextColor(Color.BLACK);
        }
        rootView.setOnClickListener(this);
        rootView.setTag(position);
    }


    @Override
    protected void setData() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 7, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RvDecorationUtils(20));
    }


    public void back(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_root_view_time_manager:

                btnViews[2].setEnabled(true);


                int position = (int) v.getTag();

                t = mData.get(position);
                tViews[0].setText(t.y + "年" + t.m + "月");
                String state = t.state;

                if (TextUtils.equals("1", state)) {
                    btnViews[0].setBackgroundResource(R.drawable.shape_radios_red_4dp);
                    btnViews[1].setBackgroundResource(R.drawable.shape_radios_gary_4dp);
                    btnViews[0].setTextColor(Color.parseColor("#ffffff"));
                    btnViews[1].setTextColor(Color.parseColor("#ac0000"));
                } else if (TextUtils.equals("2", state)) {
                    btnViews[0].setBackgroundResource(R.drawable.shape_radios_gary_4dp);
                    btnViews[1].setBackgroundResource(R.drawable.shape_radios_red_4dp);
                    btnViews[0].setTextColor(Color.parseColor("#ac0000"));
                    btnViews[1].setTextColor(Color.parseColor("#ffffff"));
                } else if (TextUtils.equals("3", state)) {
                    btnViews[0].setBackgroundResource(R.drawable.shape_radios_red_4dp);
                    btnViews[1].setBackgroundResource(R.drawable.shape_radios_red_4dp);
                    btnViews[0].setTextColor(Color.parseColor("#ffffff"));
                    btnViews[1].setTextColor(Color.parseColor("#ffffff"));
                } else {
                    btnViews[0].setBackgroundResource(R.drawable.shape_radios_gary_4dp);
                    btnViews[1].setBackgroundResource(R.drawable.shape_radios_gary_4dp);
                    btnViews[0].setTextColor(Color.parseColor("#ac0000"));
                    btnViews[1].setTextColor(Color.parseColor("#ac0000"));
                }

                booleans.clear();
                for (int i = 0; i < mData.size(); i++) {
                    if (i == position)
                        booleans.add(true);
                    else
                        booleans.add(false);
                }
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.btn_drawer_time_manager_morning, R.id.btn_drawer_time_manager_afternoon,
            R.id.btn_drawer_time_manager_sure})
    public void onEvent(View v) {
        if (t == null)
            return;

        String sta = t.state;

        switch (v.getId()) {
            case R.id.btn_drawer_time_manager_morning:
                if (TextUtils.equals("1", sta)) {
                    btnViews[0].setBackgroundResource(R.drawable.shape_radios_gary_4dp);
                    btnViews[0].setTextColor(Color.parseColor("#ac0000"));
                    t.state = "4";
                } else if (TextUtils.equals("2", sta)) {
                    btnViews[0].setBackgroundResource(R.drawable.shape_radios_red_4dp);
                    btnViews[0].setTextColor(Color.parseColor("#ffffff"));
                    t.state = "3";
                } else if (TextUtils.equals("3", sta)) {
                    btnViews[0].setBackgroundResource(R.drawable.shape_radios_gary_4dp);
                    btnViews[0].setTextColor(Color.parseColor("#ac0000"));
                    t.state = "2";
                } else {
                    btnViews[0].setBackgroundResource(R.drawable.shape_radios_red_4dp);
                    btnViews[0].setTextColor(Color.parseColor("#ffffff"));
                    t.state = "1";
                }
                break;
            case R.id.btn_drawer_time_manager_afternoon:
                if (TextUtils.equals("1", sta)) {
                    btnViews[1].setBackgroundResource(R.drawable.shape_radios_red_4dp);
                    btnViews[1].setTextColor(Color.parseColor("#ffffff"));
                    t.state = "3";
                } else if (TextUtils.equals("2", sta)) {
                    btnViews[1].setBackgroundResource(R.drawable.shape_radios_gary_4dp);
                    btnViews[1].setTextColor(Color.parseColor("#ac0000"));
                    t.state = "4";
                } else if (TextUtils.equals("3", sta)) {
                    btnViews[1].setBackgroundResource(R.drawable.shape_radios_gary_4dp);
                    btnViews[1].setTextColor(Color.parseColor("#ac0000"));
                    t.state = "1";
                } else {
                    btnViews[1].setBackgroundResource(R.drawable.shape_radios_red_4dp);
                    btnViews[1].setTextColor(Color.parseColor("#ffffff"));
                    t.state = "2";
                }
                break;
            case R.id.btn_drawer_time_manager_sure:
                upBusyTime();
                break;
            default:
                break;
        }
    }


    public int getWeek() {
        Calendar rightNow = Calendar.getInstance();
        int day = rightNow.get(rightNow.DAY_OF_WEEK);
        return day - 1;
    }

    /*
    * 上传修改的不愿意接单的时间
    * */
    private void upBusyTime() {
        String stuId = SPUtils.getStuId(this);
        JSONArray jsonArray = new JSONArray();
        //JSONObject o = new JSONObject();
        JSONObject objects = new JSONObject();
        JSONObject object = null;
        try {
            objects.put("date_end", mData.get(mData.size() - 1).ymd);
            objects.put("date_start", mData.get(getWeek()).ymd);
            objects.put("studio_id", stuId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (Time time : mData) {
            String state = time.state;
            try {
                if (!TextUtils.equals("4", state) && ActivityUtils.isEmpty(state)) {
                    object = new JSONObject();
                    object.put("busy_date", time.ymd);
                    if (TextUtils.equals("1", state)) {
                        object.put("busy_time_slot_tag", "AM_BUSY");
                    } else if (TextUtils.equals("2", state)) {
                        object.put("busy_time_slot_tag", "PM_BUSY");
                    } else if (TextUtils.equals("3", state)) {
                        object.put("busy_time_slot_tag", "ALL_BUSY");
                    }
                    jsonArray.put(object);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            objects.put("time_bean", jsonArray);
            //o.put("busy_time", objects);
        } catch (JSONException e) {
            e.printStackTrace();
        }

       /* RequestBody body = RequestBody.create(MediaType.parse("application/json"), o.toString());
        Call<ResponseBody> call = Network.studioTimeUpService().studioTimeUpService(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Toast.makeText(DrawerTimeManagerActivity.this, "" + response.body().string(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(DrawerTimeManagerActivity.this, "t" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/
        String a = objects.toString();
        RequestParams pa = new RequestParams("http://hmyc365.net:8084/HM/stu/time/time/updateStudioTime.do");
        pa.addBodyParameter("busy_time", a);
        x.http().post(pa, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    String ret = obj.getString("ret");
                    if (TextUtils.equals("0", ret)) {
                        ToastUtils.Toa(DrawerTimeManagerActivity.this, "设置成功");
                    } else {
                        ToastUtils.Toa(DrawerTimeManagerActivity.this, "设置失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.Toa(DrawerTimeManagerActivity.this, "设置失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /*
     * 处理日期
     * */
    Observer<UserInfo> stu = new Observer<UserInfo>() {
        @Override
        public void onCompleted() {
            Toast.makeText(DrawerTimeManagerActivity.this, "1111", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(DrawerTimeManagerActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(UserInfo userInfo) {
            Toast.makeText(DrawerTimeManagerActivity.this, "user" + userInfo, Toast.LENGTH_SHORT).show();
        }
    };
}
