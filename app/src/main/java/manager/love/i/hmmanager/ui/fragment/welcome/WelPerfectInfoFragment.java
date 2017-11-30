package manager.love.i.hmmanager.ui.fragment.welcome;

import android.content.res.AssetManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.base.BaseFragment;
import manager.love.i.hmmanager.ui.activity.register.WelcomeHMActivity;
import manager.love.i.hmmanager.ui.custom.dialog.DialogSelectCity;
import manager.love.i.hmmanager.ui.fragment.welcome.model.CityModel;
import manager.love.i.hmmanager.ui.fragment.welcome.model.DistrictModel;
import manager.love.i.hmmanager.ui.fragment.welcome.model.ProvinceModel;
import manager.love.i.hmmanager.ui.fragment.welcome.xmlutlils.XmlParserHandler;
import manager.love.i.hmmanager.utils.ActivityUtils;
import manager.love.i.hmmanager.utils.MathUtils;
import manager.love.i.hmmanager.utils.interutlis.StringInter;


public class WelPerfectInfoFragment extends BaseFragment {

    private List<String> provinceData;

    private Map<String, List<String>> provinceCityData;

    private Map<String, List<String>> cityCountyData;

    private String strProvince, strCity, card_pic;

    @BindViews({R.id.tv_welcome_main_perfect_province, R.id.tv_welcome_main_perfect_city, R.id.tv_welcome_main_perfect_county})
    TextView[] cities;

    private WelcomeHMActivity activity;


    @BindViews({R.id.et_wel_bank_id, R.id.et_wel_bank_name, R.id.et_wel_address_details})
    EditText[] ets;

    @BindView(R.id.iv_wel_bank_pic)
    ImageView iv;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_wel_perfect_info;
    }

    @Override
    protected void initView() {
        provinceData = new ArrayList<>();
        provinceCityData = new HashMap<>();
        cityCountyData = new HashMap<>();
    }

    @Override
    protected void setData() {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = getActivity().getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            provinceList = handler.getDataList();
            for (int i = 0; i < provinceList.size(); i++) {
                String provinceName = provinceList.get(i).getName();
                provinceData.add(provinceName);
                List<CityModel> cityList = provinceList.get(i).getCityList();
                List<String> cityNameData = new ArrayList<>();
                for (int j = 0; j < cityList.size(); j++) {
                    String cityName = cityList.get(j).getName();
                    cityNameData.add(cityName);
                    provinceCityData.put(provinceName, cityNameData);
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    List<String> countryNameData = new ArrayList<>();
                    for (int k = 0; k < districtList.size(); k++) {
                        countryNameData.add(districtList.get(k).getName());
                        cityCountyData.put(cityName, countryNameData);
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }

    @Override
    protected void initData() {
        activity = (WelcomeHMActivity) getActivity();
        activity.upDateRegisterInfo(new StringInter() {
            @Override
            public void onResult(String result) {
                try {
                    JSONObject objs = new JSONObject(result);
                    JSONObject body = objs.getJSONObject("body");
                    card_pic = body.getString("card_pic");
                    Glide.with(getContext()).load(card_pic).error(R.mipmap.hm_welcome_bank).into(iv);
                    ets[0].setText(body.getString("card_no"));
                    ets[1].setText(body.getString("card_bank"));
                    String[] address = body.getString("address").split(" ");
                    cities[0].setText(address[0]);
                    cities[1].setText(address[1]);
                    cities[2].setText(address[2]);
                    StringBuffer str = new StringBuffer();
                    for (int i = 2; i < address.length; i++) {
                        str.append(address[i]);
                    }
                    ets[2].setText(str.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @OnClick({R.id.btn_wel_perfect_info_next, R.id.rl_welcome_main_perfect_province,
            R.id.rl_welcome_main_perfect_city, R.id.rl_welcome_main_perfect_county, R.id.iv_wel_bank_pic,
            R.id.iv_welcome_edit_bank_id_tip, R.id.iv_welcome_edit_bank_name_tip, R.id.tv_welcome_tip_bank})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.btn_wel_perfect_info_next:
                String bankId = ets[0].getText().toString();
                String bankName = ets[1].getText().toString();
                String detailsAddress = ets[2].getText().toString();
                String province = cities[0].getText().toString();
                String city = cities[1].getText().toString();
                String county = cities[2].getText().toString();
                if (!MathUtils.checkBankCard(bankId)) {
                    Toast.makeText(activity, "银行卡号不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!ActivityUtils.isEmpty(bankId) || !ActivityUtils.isEmpty(bankName) || !ActivityUtils.isEmpty(detailsAddress)
                        || !ActivityUtils.isEmpty(province) || !ActivityUtils.isEmpty(city) || !ActivityUtils.isEmpty(county)) {
                    Toast.makeText(activity, "请检查数据是否填写完整", Toast.LENGTH_SHORT).show();
                } else {
                    String[] split = card_pic.split("/");
                    card_pic = split[split.length - 1];
                    activity.perfectInfo(bankId, bankName, city, province + " " + city + " " + county + " " + detailsAddress, card_pic);
                    activity.flip(2);
                }
                break;
            case R.id.rl_welcome_main_perfect_province:
                showDialog(0, provinceData);
                break;
            case R.id.rl_welcome_main_perfect_city:
                if (strProvince != null) {
                    List<String> strings = provinceCityData.get(strProvince);
                    showDialog(1, strings);
                } else {
                    Toast.makeText(getContext(), "请选择省份", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rl_welcome_main_perfect_county:
                if (strCity != null) {
                    List<String> stringss = cityCountyData.get(strCity);
                    showDialog(2, stringss);
                } else {
                    Toast.makeText(getContext(), "请选择城市", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_wel_bank_pic:
                activity.initCamera(new StringInter() {
                    @Override
                    public void onResult(String result) {
                        card_pic = result;
                        Glide.with(activity).load(card_pic).into(iv);
                    }
                });
                break;
            case R.id.iv_welcome_edit_bank_name_tip:
                activity.notifyDialog("请输入该银行卡号开户行，例如：招商银行亦庄支行；");
                break;
            case R.id.iv_welcome_edit_bank_id_tip:
                activity.notifyDialog("1、请输入X位银行卡号；\n" +
                        "2、此银行卡号将作为结算作用，请认真填写；");
                break;
            case R.id.tv_welcome_tip_bank:
                activity.notifyPic(R.mipmap.hm_welcome_bank_tip);
                break;
            default:
                break;
        }
    }

    /**
     * @param cityType 0是省，1是市，2是县
     */
    private void showDialog(final int cityType, List<String> cityData) {
        final DialogSelectCity dialogSelectCity = new DialogSelectCity(getContext(), cityData);
        dialogSelectCity.setOnResultCity(new DialogSelectCity.OnResultCity() {
            @Override
            public void onResultCity(String city) {
                switch (cityType) {
                    case 0:
                        strProvince = city;
                        if (strProvince.equals("北京市") || strProvince.equals("天津市") || strProvince.equals("上海市") || strProvince.contains("重庆市")) {
                            strCity = strProvince;
                            cities[1].setText(strProvince);
                        } else {
                            cities[1].setText("请选择城市");
                        }
                        cities[0].setText(strProvince);
                        cities[2].setText("请选择区县");
                        strCity = null;
                        break;
                    case 1:
                        strCity = city;
                        cities[1].setText(city);
                        cities[2].setText("请选择区县");
                        break;
                    case 2:
                        cities[2].setText(city);
                        break;
                    default:
                        break;
                }
                dialogSelectCity.dismiss();
            }
        });
        dialogSelectCity.show();
    }
}
