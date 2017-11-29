package manager.love.i.hmmanager.common.widgets.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import manager.love.i.hmmanager.R;


/**
 * Created by 小五 on 2016/12/26.
 */
public class NormalDialog extends Dialog {

    private Button btnCancel, btnSure;

    private TextView tvTitle, tvMsg;

    private String cancel, sure, msg, title;

    private View.OnClickListener mOnClickListener;


    public void setOnYesListener(String sure, View.OnClickListener onClickListener) {
        if (sure != null) {
            this.sure = sure;
        }
        mOnClickListener = onClickListener;
    }

    public void setCancel(String cancel) {
        if (cancel != null) {
            this.cancel = cancel;
        }
    }

    public void setMsg(String msg) {
        if (msg != null) {
            this.msg = msg;
        }
    }

    public void setTitle(String title) {
        if (title != null) {
            this.title = title;
        }
    }


    public NormalDialog(Context context) {
        super(context, R.style.ActionSheetDialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_normal);
        setCanceledOnTouchOutside(false);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        btnCancel = (Button) findViewById(R.id.tv_normal_cancel);
        btnSure = (Button) findViewById(R.id.tv_normal_sure);
        tvTitle = (TextView) this.findViewById(R.id.tv_normal_title);
        tvMsg = (TextView) this.findViewById(R.id.tv_normal_msg);
    }

    private void initData() {
        if (btnCancel != null) {
            btnCancel.setText(cancel);
        }
        if (btnSure != null) {
            btnSure.setText(sure);
        }
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
        if (tvMsg != null) {
            tvMsg.setText(msg);
        }
    }

    private void setListener() {

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnSure.setOnClickListener(mOnClickListener);
    }


}
