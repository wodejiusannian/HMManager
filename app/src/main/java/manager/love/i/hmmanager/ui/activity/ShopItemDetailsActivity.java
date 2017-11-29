package manager.love.i.hmmanager.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.OnClick;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.base.BaseActivity;
import manager.love.i.hmmanager.common.widgets.dialog.RecommendDialog;

import static android.webkit.WebSettings.LOAD_NO_CACHE;

public class ShopItemDetailsActivity extends BaseActivity {

    @BindView(R.id.wb_item_show)
    WebView mShow;

    private RecommendDialog dialog;


    private String clothes_id, clothes_cz;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
    }


    @Override
    public void initData() {
        Intent intent = getIntent();
        clothes_id = intent.getStringExtra("id");
        clothes_cz = intent.getStringExtra("clothes_cz");
        dialog = new RecommendDialog(this, clothes_id, clothes_cz);
        String url = "http://hmyc365.net:8084/file/html/clothes/index.html?clothes_id=" + clothes_id;
        loadindUrl(url);
    }


    @Override
    public void setData() {

    }

    @Override
    public void setListener() {

    }


    public void back(View view) {
        finish();
    }


    private void loadindUrl(String url) {
        WebSettings webSettings = mShow.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(LOAD_NO_CACHE);
        webSettings.setJavaScriptEnabled(true);
        mShow.setWebViewClient(new WebViewClient() {
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
        mShow.setWebChromeClient(wvcc);
        if (url != null) {
            mShow.loadUrl(url);
        }
    }

    @OnClick({R.id.shop_item_btn_recommend})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.shop_item_btn_recommend:
                dialog.show();
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
