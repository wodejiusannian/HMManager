package manager.love.i.hmmanager.ui.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import manager.love.i.hmmanager.R;

// ┏┓　　　┏┓
// ┏┛┻━━━┛┻┓
// ┃　　　　　　　┃ 　
// ┃　　　━　　　┃
// ┃　┳┛　┗┳　┃
// ┃　　　　　　　┃
// ┃　　　┻　　　┃
// ┃　　　　　　　┃
// ┗━┓　　　┏━┛
// ┃　　　┃ 神兽保佑　　　　　　　　
// ┃　　　┃ 代码无BUG！
// ┃　　　┗━━━┓
// ┃　　　　　　　┣┓
// ┃　　　　　　　┏┛
// ┗┓┓┏━┳┓┏┛
// ┃┫┫　┃┫┫
// ┗┻┛　┗┻┛
public class TipDialog extends Dialog {

    @BindView(R.id.iv_tip)
    ImageView tip;

    private int mMsg;

    public TipDialog(@NonNull Context context, int msg) {
        super(context, R.style.MyDialogStyle);
        mMsg = msg;
    }


    public void notifyPic(int msg) {
        if (tip != null) {
            tip.setImageResource(msg);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tip);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(true);
        if (tip != null) {
            tip.setImageResource(mMsg);
        }
    }
/*
    @Override
    public void dismiss() {
        super.dismiss();
    }*/

   /* @OnClick({R.id.btn_dialog_message_sure})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.btn_dialog_message_sure:
                this.dismiss();
                break;
            default:
                break;
        }
    }*/
}
