package manager.love.i.hmmanager.ui.activity.register;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.base.BaseActivity;
import manager.love.i.hmmanager.ui.custom.dialog.DialogSelectCityCopy;
import manager.love.i.hmmanager.ui.fragment.welcome.model.ManagerCityModel;
import manager.love.i.hmmanager.utils.ActivityUtils;
import manager.love.i.hmmanager.utils.AsyncHttpUtils;
import manager.love.i.hmmanager.utils.overlayutil.HttpCallBack;

public class ServiceCityActivity extends BaseActivity {

    @BindView(R.id.tv_service_city_edit)
    TextView editCity;

    @BindView(R.id.tv_service_city_go)
    TextView go;

    @BindView(R.id.tv_service_city_date)
    TextView date;

    private List<ManagerCityModel> mData = new ArrayList<>();

    private Map<String, List<ManagerCityModel>> provinceAndCity = new HashMap<>();


    @OnClick({R.id.tv_service_city_edit, R.id.tv_service_city_go, R.id.tv_service_city_date})
    public void onEvent(View v) {

        switch (v.getId()) {
            case R.id.tv_service_city_edit:
                editCity();
                break;
            case R.id.tv_service_city_go:
                String city = editCity.getText().toString();
                String mDate = date.getText().toString();
                if (ActivityUtils.isEmpty(city)) {
                    Intent intent = new Intent(this, WelcomeHMActivity.class);
                    intent.putExtra("cityStudy", city);
                    intent.putExtra("studyTime", mDate);
                    intent.putExtra("update", update);
                    intent.putExtra("studio_id", studio_id);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "请选择基地", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_service_city_date:
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date.setText(String.format("%d-%d-%d", year, monthOfYear + 1, dayOfMonth));
                        canGo();
                    }
                }, 2016, 7, 8).show();
                break;
            default:
                break;
        }
    }

    private void editCity() {
        if (mData == null) {
            throw new NullPointerException("data is empty");
        }
        if (mData.size() > 0) {
            handler.sendEmptyMessage(1);
        } else {
            new AsyncHttpUtils(new HttpCallBack() {
                @Override
                public void onResponse(String result) {
                    try {
                        JSONObject obj = new JSONObject(result);
                        JSONArray body = obj.getJSONArray("body");
                        for (int i = 0; i < body.length(); i++) {
                            JSONObject jsonObject = body.getJSONObject(i);
                            ManagerCityModel model = new ManagerCityModel();
                            String pname = jsonObject.getString("pname");
                            model.name = pname;
                            model.code = jsonObject.getString("pcode");
                            mData.add(model);
                            JSONArray citys = jsonObject.getJSONArray("citys");
                            List<ManagerCityModel> data = new ArrayList<ManagerCityModel>();
                            for (int j = 0; j < citys.length(); j++) {
                                JSONObject o = citys.getJSONObject(j);
                                ManagerCityModel models = new ManagerCityModel();
                                models.name = o.getString("cname");
                                models.code = o.getString("ccode");
                                data.add(models);
                            }
                            provinceAndCity.put(pname, data);
                        }
                        handler.sendEmptyMessage(1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, this).execute("http://hmyc365.net/HM/bg/pub/city/info/getProCity.do", "");
        }
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what) {
                case 1:
                    showSelectCityDialog();
                    break;
                default:
                    break;
            }
        }
    };

    private void showSelectCityDialog() {
        DialogSelectCityCopy dialog = new DialogSelectCityCopy(this, provinceAndCity, mData);
        dialog.setOnResultCity(new DialogSelectCityCopy.OnResultCity() {
            @Override
            public void onResultCity(ManagerCityModel city) {
                editCity.setText(city.name);
                canGo();
            }
        });
        dialog.show();
    }

    private void canGo() {
        String city = editCity.getText().toString();
        String mDate = date.getText().toString();
        if (ActivityUtils.isEmpty(city) && !mDate.contains("点击")) {
            go.setEnabled(true);
        } else {
            go.setEnabled(false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_city);
    }

    @Override
    protected void setListener() {

    }

    private String update;
    private String studio_id;

    @Override
    protected void initData() {
        Intent intent = getIntent();
        update = intent.getStringExtra("update");
        studio_id = intent.getStringExtra("studio_id");
        if (ActivityUtils.isEmpty(update)) {
            try {
                JSONObject objs = new JSONObject(update);
                JSONObject obj = objs.getJSONObject("body");
                String studyTime = obj.getString("studyTime");
                String cityStudy = obj.getString("cityStudy");
                date.setText(studyTime);
                editCity.setText(cityStudy);
                go.setEnabled(true);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void setData() {

    }

    @Override
    protected int tintColor() {
        return R.color.serviceCityTopColor;
    }
}
