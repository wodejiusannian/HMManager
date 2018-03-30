package manager.love.i.hmmanager.ui.fragment.welcome;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.base.BaseFragment;
import manager.love.i.hmmanager.common.widgets.dialog.BottomListDialog;
import manager.love.i.hmmanager.ui.activity.register.WelcomeHMActivity;
import manager.love.i.hmmanager.ui.custom.dialog.DialogSelectCityCopy;
import manager.love.i.hmmanager.ui.fragment.welcome.model.ManagerCityModel;
import manager.love.i.hmmanager.utils.ActivityUtils;
import manager.love.i.hmmanager.utils.AsyncHttpUtils;
import manager.love.i.hmmanager.utils.HttpUtils;
import manager.love.i.hmmanager.utils.MathUtils;
import manager.love.i.hmmanager.utils.interutlis.StringInter;
import manager.love.i.hmmanager.utils.overlayutil.HttpCallBack;


public class WelRegisterInfoFragment extends BaseFragment {

    @BindView(R.id.mTogBtn)
    ToggleButton mToggle;

    @BindView(R.id.ll_welcome_recommend)
    LinearLayout lineRecommend;

    WelcomeHMActivity activity;

    @BindViews({R.id.tv_wel_recommend_person, R.id.et_welcome_edit_edu, R.id.et_welcome_edit_service_city})
    TextView[] tvs;

    @BindViews({R.id.et_wel_edit_name, R.id.et_welcome_edit_id,
            R.id.et_welcome_edit_phone})
    EditText[] ets;

    @BindView(R.id.iv_welcome_id_image)
    ImageView iv;

    private String id_pic, recommendid;

    @BindView(R.id.btn_wel_register_info_next)
    Button btnNext;

    private boolean isUpdate;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_wel_register_info;
    }

    @Override
    protected void initView() {
        activity = (WelcomeHMActivity) getActivity();
        activity.upDateRegisterInfo(new StringInter() {
            @Override
            public void onResult(String result) {
                try {
                    JSONObject objs = new JSONObject(result);
                    JSONObject body = objs.getJSONObject("body");
                    id_pic = body.getString("id_pic");
                    recommendid = body.getString("referee_id");
                    Glide.with(getContext()).load(id_pic).error(R.mipmap.hm_welcome_id).into(iv);
                    ets[0].setText(body.getString("name"));
                    ets[1].setText(body.getString("id_number"));
                    ets[2].setText(body.getString("phone"));
                    tvs[0].setText(body.getString("referee_name"));
                    tvs[1].setText(body.getString("education"));
                    tvs[2].setText(body.getString("city"));
                    btnNext.setEnabled(true);
                    isUpdate = true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    protected void setData() {
        mToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lineRecommend.setVisibility(View.VISIBLE);
                } else {
                    lineRecommend.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    protected void initData() {
        ets[2].addTextChangedListener(watcher);
    }

    @OnClick({R.id.btn_wel_register_info_next, R.id.iv_welcome_id_image,
            R.id.tv_wel_recommend_person, R.id.iv_welcome_edit_name_tip,
            R.id.iv_welcome_edit_id_tip, R.id.iv_welcome_edit_phone_tip,
            R.id.iv_welcome_edit_recommend_person_tip, R.id.tv_welcome_tip_id,
            R.id.et_welcome_edit_edu, R.id.et_welcome_edit_service_city})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.tv_welcome_tip_id:
                activity.notifyPic(R.mipmap.hm_wlecome_id_tip);
                break;
            case R.id.btn_wel_register_info_next:
                String name = ets[0].getText().toString();
                String id = ets[1].getText().toString();
                String phone = ets[2].getText().toString();
                final String recommend = tvs[0].getText().toString();
                String edusss = tvs[1].getText().toString();
                String serviceCity = tvs[2].getText().toString();
                if (!ActivityUtils.isEmpty(edusss)) {
                    Toast.makeText(activity, "请选择学历", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!ActivityUtils.isEmpty(serviceCity)) {
                    Toast.makeText(activity, "请选择服务城市", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!MathUtils.isLegalId(id)) {
                    Toast.makeText(activity, "身份证号格式不正确，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!MathUtils.isLegalName(name)) {
                    Toast.makeText(activity, "姓名格式不正确，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!MathUtils.isTelPhoneNumber(phone)) {
                    Toast.makeText(activity, "手机号码格式不正确，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!ActivityUtils.isEmpty(edusss) || !ActivityUtils.isEmpty(serviceCity) || !ActivityUtils.isEmpty(name) || !ActivityUtils.isEmpty(id) || !ActivityUtils.isEmpty(phone) || !ActivityUtils.isEmpty(id_pic)) {
                    Toast.makeText(activity, "请检查数据是否填写完整", Toast.LENGTH_SHORT).show();
                } else {
                    String[] split = id_pic.split("/");
                    id_pic = split[split.length - 1];
                    activity.registerInfo(name, id, phone, id_pic, recommend, recommendid, edusss, serviceCity);
                    activity.flip(1);
                }
                break;
            case R.id.iv_welcome_id_image:
                activity.initCamera(new StringInter() {
                    @Override
                    public void onResult(String result) {
                        id_pic = result;
                        Glide.with(activity).load(id_pic).into(iv);
                    }
                });
                break;
            case R.id.tv_wel_recommend_person:
                activity.jumpAddress(new StringInter() {
                    @Override
                    public void onResult(String result) {
                        String[] split = result.split(",");
                        tvs[0].setText(split[0]);
                        recommendid = split[1];
                    }
                });
                break;
            case R.id.iv_welcome_edit_name_tip:
                activity.notifyDialog("1.姓名录入应与身份证保持一致，不得录入乳名/小名/艺名；\n" +
                        "2.姓名应该录入简体中文，不得录入其它特殊字符；\n" +
                        "3.姓名中间不得加入空格");
                break;
            case R.id.iv_welcome_edit_id_tip:
                activity.notifyDialog("请正确填写身份证号码，应与身份证上保持一致；");
                break;
            case R.id.iv_welcome_edit_phone_tip:
                activity.notifyDialog("该手机号将登记为公司联系您的唯一手机号，请认真填写；");
                break;
            case R.id.iv_welcome_edit_recommend_person_tip:
                activity.notifyDialog("推荐人只能选择现有的基地/工作室");
                break;
            case R.id.et_welcome_edit_edu:
                RequestParams pa = new RequestParams("http://hmyc365.net/admiral/common/tag/getTag.htm");
                pa.addBodyParameter("token", "82D5FBD40259C743ADDEF14D0E22F347");
                pa.addBodyParameter("type", "2");
                x.http().post(pa, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            JSONObject obj = new JSONObject(result);
                            JSONArray body = obj.getJSONArray("body");
                            String[] edus = new String[body.length()];
                            for (int i = 0; i < body.length(); i++) {
                                edus[i] = body.getJSONObject(i).getString("name");
                            }
                            BottomListDialog dialog = new BottomListDialog(getContext(), edus);
                            dialog.setOnItemClickListener(new BottomListDialog.OnResultEdu() {
                                @Override
                                public void onResultEdu(String edu) {
                                    tvs[1].setText(edu);
                                }
                            });
                            dialog.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
                break;
            case R.id.et_welcome_edit_service_city:
                editCity();
                break;
            default:
                break;
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
    private Map<String, List<ManagerCityModel>> provinceAndCity = new HashMap<>();

    private void showSelectCityDialog() {
        DialogSelectCityCopy dialog = new DialogSelectCityCopy(getContext(), provinceAndCity, mData);
        dialog.setOnResultCity(new DialogSelectCityCopy.OnResultCity() {
            @Override
            public void onResultCity(ManagerCityModel city) {
                tvs[2].setText(city.name);
            }
        });
        dialog.show();
    }

    private List<ManagerCityModel> mData = new ArrayList<>();

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
            }, getActivity()).execute("http://hmyc365.net/HM/bg/pub/city/info/getProCity.do", "");
        }
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().length() == 11) {
                Map<String, String> map = new HashMap<>();
                map.put("phone", s.toString());
                String json = HttpUtils.toJson(map);
                new AsyncHttpUtils(new HttpCallBack() {
                    @Override
                    public void onResponse(String result) {
                        try {
                            JSONObject obj = new JSONObject(result);
                            String ret = obj.getString("ret");
                            if (isUpdate) {
                                btnNext.setEnabled(true);
                            } else if ("0".equals(ret)) {
                                btnNext.setEnabled(true);
                            } else {
                                Toast.makeText(activity, "该手机号已经注册，修改可以忽略", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, getActivity()).execute("http://hmyc365.net/HM/bg/hmgls/login/info/phoneCheck.do", json);
            } else {
                btnNext.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
