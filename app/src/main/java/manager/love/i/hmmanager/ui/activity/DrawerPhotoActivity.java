package manager.love.i.hmmanager.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.base.BaseActivity;
import manager.love.i.hmmanager.bean.StudioInfo;
import manager.love.i.hmmanager.inter.Network;
import manager.love.i.hmmanager.utils.ActivityUtils;
import manager.love.i.hmmanager.utils.SPUtils;
import manager.love.i.hmmanager.utils.glideutils.GlideCircleTransform;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DrawerPhotoActivity extends BaseActivity {

    @BindView(R.id.iv_drawer_photo)
    ImageView ivPhoto;

    @BindView(R.id.iv_drawer_photo_bg)
    ImageView ivPhotoBg;

    @BindViews({R.id.tv_photo_order_count, R.id.tv_photo_365_count,
            R.id.tv_photo_recommend_count, R.id.tv_photo_date, R.id.iv_studio_name, R.id.iv_drawer_city})
    TextView[] tViews;

    @BindView(R.id.rb_photo_star)
    RatingBar ratingBar;

    private StudioInfo.BodyBean.InfoBean body;

    private static final int EDIT_REQUEST = 0;

    private void initStudioInfo() {
        String stuId = SPUtils.getStuId(this);

        String stuPic = SPUtils.getStuPic(this);
        Glide.with(this)
                .load(stuPic)
                .error(R.mipmap.hm_main_drawer_photo_default)
                .crossFade(1000)
                .transform(new GlideCircleTransform(this))
                .into(ivPhoto);

        Glide.with(this)
                .load(stuPic)
                .error(R.mipmap.hm_main_drawer_photo_default_bg)
                .crossFade(1000)
                .bitmapTransform(new BlurTransformation(this, 5, 4))
                .into(ivPhotoBg);
        Network.studioInfoService().studioInfo(stuId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(studioInfoObserver);
    }


    /*
   * 处理工作室的信息
   * */
    Observer<StudioInfo> studioInfoObserver = new Observer<StudioInfo>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(StudioInfo studioInfo) {
            StudioInfo.BodyBean score = studioInfo.getBody();
            body = score.getInfo();
            tViews[0].setText(score.getCount_kh_yy());
            tViews[1].setText(score.getCount_kh_365());
            tViews[2].setText(score.getCount_cs_tj());
            tViews[5].setText(body.getStudio_city());
            tViews[4].setText(body.getStudio_name());
            tViews[3].setText(body.getTime());
            ratingBar.setRating(strToFloat(body.getStart()));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_photo);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void initData() {


        initStudioInfo();
    }

    @Override
    protected void setData() {

    }

    public void back(View view) {
        finish();
    }

    @OnClick({R.id.iv_drawer_photo_edit})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.iv_drawer_photo_edit:
                Intent intent = new Intent();
                intent.setClass(this, DrawerPhotoEditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bodyBean", body);
                intent.putExtras(bundle);
                startActivityForResult(intent, EDIT_REQUEST);
                break;
            default:
                break;
        }
    }


    private Float strToFloat(String str) {
        if (ActivityUtils.isEmpty(str))
            return Float.parseFloat(str);
        else
            return Float.valueOf(5);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case EDIT_REQUEST:
                initStudioInfo();
                break;
            default:
                break;
        }
    }
}
