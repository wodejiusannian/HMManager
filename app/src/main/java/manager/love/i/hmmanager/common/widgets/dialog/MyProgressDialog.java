package manager.love.i.hmmanager.common.widgets.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import manager.love.i.hmmanager.R;


/**
 * Created by 小五 on 2017/2/8.
 */

public class MyProgressDialog extends ProgressDialog {


    public MyProgressDialog(Context context) {
        super(context, R.style.MyProgressTheme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init(getContext());
    }

    private void init(Context context) {
        //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
        setCancelable(false);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_progress);
        ImageView iv = (ImageView) findViewById(R.id.iv);
        Animation animationUtils = AnimationUtils.loadAnimation(getContext(), R.anim.progress);
        iv.setAnimation(animationUtils);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
