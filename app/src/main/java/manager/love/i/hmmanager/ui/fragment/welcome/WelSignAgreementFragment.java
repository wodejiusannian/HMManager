package manager.love.i.hmmanager.ui.fragment.welcome;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.base.BaseFragment;
import manager.love.i.hmmanager.ui.activity.register.LyDetailsWebActivity;
import manager.love.i.hmmanager.ui.activity.register.WelcomeHMActivity;
import manager.love.i.hmmanager.utils.AsyncHttpUtils;
import manager.love.i.hmmanager.utils.interutlis.InfoInter;
import manager.love.i.hmmanager.utils.overlayutil.HttpCallBack;


public class WelSignAgreementFragment extends BaseFragment {

    @BindView(R.id.wb_sign_agreement_content)
    WebView mWeb;

    @BindView(R.id.cb_wel_isread_agreement)
    CheckBox checkBox;


    @BindView(R.id.btn_wel_sign_agree_ok)
    Button btn_wel_sign_agree_ok;

    private WelcomeHMActivity activity;

    private boolean isMove = true;

    private float mY;

    private String url = "http://hmyc365.net/file/html/app/studio/contractAgreement/protocol.html?name=%s&tel=%s&idCardNo=%s&address=%s&cardBank=%s&cardNo=%s";

    @Override
    public int getContentViewId() {
        return R.layout.fragment_wel_sign_agreement;
    }

    @Override
    protected void initView() {
        activity = (WelcomeHMActivity) getActivity();
    }

    @Override
    protected void setData() {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                btn_wel_sign_agree_ok.setEnabled(isChecked);
            }
        });
        mWeb.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                ((WebView) v).requestDisallowInterceptTouchEvent(true);
                if (ev.getAction() == MotionEvent.ACTION_UP) {
                    if (isMove) {
                        Intent in = new Intent(getContext(), LyDetailsWebActivity.class);
                        in.putExtra("url", url);
                        startActivity(in);
                    }
                } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
                    if (Math.abs(ev.getY() - mY) > 5) {
                        isMove = false;
                    } else {
                        isMove = true;
                    }
                } else if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                    isMove = true;
                    mY = ev.getY();
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        activity.getInfo(new InfoInter() {
            @Override
            public void onResult(String name, String phone, String id, String address, String bankId, String bankName) {
                url = String.format(url, name, phone, id, address, bankName, bankId);
                loadingUrl(url);
            }
        });
        activity.again();
    }

    private void loadingUrl(String url) {
        WebSettings webSettings = mWeb.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDisplayZoomControls(true);
        mWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        WebChromeClient wvcc = new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {


            }

        };
        mWeb.setWebChromeClient(wvcc);
        if (url != null) {
            mWeb.loadUrl(url);
        }
    }

    @OnClick({R.id.btn_wel_sign_agree_ok, R.id.tip})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.btn_wel_sign_agree_ok:
                activity.welSingAgreement();
                break;
            case R.id.tip:
                new AsyncHttpUtils(new HttpCallBack() {
                    @Override
                    public void onResponse(String result) {
                        try {
                            JSONObject obj = new JSONObject(result);
                            JSONObject body = obj.getJSONObject("body");
                            String priIntro = body.getString("priIntro");
                            activity.notifyDialog(priIntro);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, activity).execute("http://hmyc365.net/HM/bg/pub/studio/regist/price/getPriIntro.do", "");
                break;
            default:
                break;
        }
    }
}
