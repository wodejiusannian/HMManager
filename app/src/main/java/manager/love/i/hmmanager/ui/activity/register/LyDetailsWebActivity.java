package manager.love.i.hmmanager.ui.activity.register;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import manager.love.i.hmmanager.R;


public class LyDetailsWebActivity extends AppCompatActivity {

    /* @BindView(R.id.tv_ly_detailsweb_title)
     TextView mTitle;
 */
    private WebView webView;

   /* @BindView(R.id.pr_ly_detailsweb_hint)
    ProgressBar progressBar;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ly_details_web);
        initView();
    }

    private void initView() {
        webView = (WebView) this.findViewById(R.id.wv_ly_detailsweb_content);
        String details_page = getIntent().getStringExtra("url");
        loadindUrl(details_page);
    }


    private void loadindUrl(String url) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        //支持屏幕缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webView.setWebViewClient(new WebViewClient() {
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
                //mTitle.setText(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {

               /* if (newProgress == 100) {
                    if (progressBar != null)
                        progressBar.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    if (progressBar != null)
                        progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                }*/

            }

        };
        webView.setWebChromeClient(wvcc);
        if (url != null) {
            webView.loadUrl(url);
        }
    }

    public void back(View view) {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
