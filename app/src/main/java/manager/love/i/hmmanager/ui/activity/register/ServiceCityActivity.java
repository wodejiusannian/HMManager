package manager.love.i.hmmanager.ui.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
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

    private List<ManagerCityModel> mData = new ArrayList<>();

    private Map<String, List<ManagerCityModel>> provinceAndCity = new HashMap<>();


    @OnClick({R.id.tv_service_city_edit, R.id.tv_service_city_go})
    public void onEvent(View v) {

        switch (v.getId()) {
            case R.id.tv_service_city_edit:
                editCity();
                break;
            case R.id.tv_service_city_go:
                String city = editCity.getText().toString();
                if (ActivityUtils.isEmpty(city)) {
                    Intent intent = new Intent(this, WelcomeHMActivity.class);
                    intent.putExtra("city", city);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "请选择基地", Toast.LENGTH_SHORT).show();
                }
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
                go.setEnabled(true);
            }
        });
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_city);
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

    @Override
    protected int tintColor() {
        return R.color.serviceCityTopColor;
    }
}
