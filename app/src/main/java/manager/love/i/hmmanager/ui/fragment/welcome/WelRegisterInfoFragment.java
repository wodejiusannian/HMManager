package manager.love.i.hmmanager.ui.fragment.welcome;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.base.BaseFragment;
import manager.love.i.hmmanager.ui.activity.register.WelcomeHMActivity;
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

    @BindViews({R.id.tv_wel_recommend_person})
    TextView[] tvs;

    @BindViews({R.id.et_wel_edit_name, R.id.et_welcome_edit_id,
            R.id.et_welcome_edit_phone})
    EditText[] ets;

    @BindView(R.id.iv_welcome_id_image)
    ImageView iv;

    private String id_pic;

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
                    id_pic = body.getString("card_pic");
                    Glide.with(getContext()).load(id_pic).into(iv);
                    ets[0].setText(body.getString("name"));
                    ets[1].setText(body.getString("id_number"));
                    ets[2].setText(body.getString("phone"));
                    tvs[0].setText(body.getString("referee_name"));
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
            R.id.iv_welcome_edit_recommend_person_tip})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.btn_wel_register_info_next:
                String name = ets[0].getText().toString();
                String id = ets[1].getText().toString();
                String phone = ets[2].getText().toString();
                final String recommend = tvs[0].getText().toString();
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
                if (!ActivityUtils.isEmpty(name) || !ActivityUtils.isEmpty(id) || !ActivityUtils.isEmpty(phone) || !ActivityUtils.isEmpty(id_pic)) {
                    Toast.makeText(activity, "请检查数据是否填写完整", Toast.LENGTH_SHORT).show();
                } else {
                    String[] split = id_pic.split("/");
                    id_pic = split[split.length - 1];
                    activity.registerInfo(name, id, phone, recommend, id_pic);
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
                        tvs[0].setText(result);
                    }
                });
                break;
            case R.id.iv_welcome_edit_name_tip:
                activity.notifyDialog("1.姓名录入应与身份证保持一致，不得录入乳名/小名/艺名；\n" +
                        "2.姓名应该录入简体中文，不得录入其它特殊字符；\n" +
                        "3.姓名中间不得加入空格");
                break;
            case R.id.iv_welcome_edit_id_tip:
                activity.notifyDialog("身份证号码提示：\n" +
                        "请正确填写身份证号码，应与身份证上保持一致；");
                break;
            case R.id.iv_welcome_edit_phone_tip:
                activity.notifyDialog("手机号提示：\n" +
                        "该手机号将登记为公司联系您的唯一手机号，请认真填写；");
                break;
            case R.id.iv_welcome_edit_recommend_person_tip:
                activity.notifyDialog("推荐人就是推荐你加入的那个人可以点击选择，亲亲您");
                break;
            default:
                break;
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
                        } catch (JSONException e) {
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
