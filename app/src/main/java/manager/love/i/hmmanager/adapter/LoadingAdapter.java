package manager.love.i.hmmanager.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import manager.love.i.hmmanager.MainActivity;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.utils.SPUtils;


/**
 * Created by 小五 on 2017/3/15.
 */

public class LoadingAdapter extends PagerAdapter {

    private List<ImageView> images;

    private int anInt[] = {R.mipmap.hm_loading_pic_one, R.mipmap.hm_loading_pic_two, R.mipmap.hm_loading_pic_three};


    public LoadingAdapter(final Activity activity) {
        images = new ArrayList<>();
        for (int i = 0; i < anInt.length; i++) {
            ImageView iv = new ImageView(activity);
            iv.setImageResource(anInt[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            images.add(iv);
        }
        images.get(anInt.length - 1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.isFirst(activity);
                activity.startActivity(new Intent(activity, MainActivity.class));
                activity.finish();
            }
        });
    }

    @Override
    public int getCount() {
        return images.size() == 0 ? 0 : images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(images.get(position));
        return images.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(images.get(position));
    }
}
