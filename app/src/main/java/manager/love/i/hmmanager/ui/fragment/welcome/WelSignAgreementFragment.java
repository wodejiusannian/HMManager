package manager.love.i.hmmanager.ui.fragment.welcome;

import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.base.BaseFragment;
import manager.love.i.hmmanager.ui.activity.register.WelcomeHMActivity;


public class WelSignAgreementFragment extends BaseFragment {

    @BindView(R.id.wb_sign_agreement_content)
    WebView mWeb;

    @BindView(R.id.cb_wel_isread_agreement)
    CheckBox checkBox;

    private boolean isCheck = false;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_wel_sign_agreement;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setData() {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheck = isChecked;
            }
        });
        mWeb.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent ev) {

                ((WebView) v).requestDisallowInterceptTouchEvent(true);

                return false;
            }
        });

    }

    @Override
    protected void initData() {
        loadingUrl("https://www.baidu.com/?tn=98012088_5_dg&ch=12");
    }

    private void loadingUrl(String url) {
        WebSettings webSettings = mWeb.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
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

    @OnClick({R.id.btn_wel_sign_agree_ok})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.btn_wel_sign_agree_ok:
                if (isCheck) {
                    WelcomeHMActivity activity = (WelcomeHMActivity) getActivity();
                    activity.welSingAgreement();
                } else {
                    Toast.makeText(context, "同意本协议后才能进入下一步", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
