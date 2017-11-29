package manager.love.i.hmmanager.ui.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
public class MessageDialog extends Dialog {

    @BindView(R.id.tv_dialog_message_tip)
    TextView tip;

    private String msg;

    public MessageDialog(@NonNull Context context, String dialogMsg) {
        super(context, R.style.MyDialogStyle);
        this.msg = dialogMsg;
    }

    public MessageDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    public void notifyMessage(String msg) {
        if (msg != null && tip != null) {
            tip.setText(msg);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_message);
        ButterKnife.bind(this);
        if (msg != null && tip != null) {
            tip.setText(msg);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @OnClick({R.id.btn_dialog_message_sure})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.btn_dialog_message_sure:
                this.dismiss();
                break;
            default:
                break;
        }
    }
}
