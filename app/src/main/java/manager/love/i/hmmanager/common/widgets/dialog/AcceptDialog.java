package manager.love.i.hmmanager.common.widgets.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import manager.love.i.hmmanager.R;


/**
 * Created by 小五 on 2017/5/18.
 */

public class AcceptDialog extends Dialog {
    private Button btnCancel, btnDetails;

    private View.OnClickListener onClickListener;

    private View.OnClickListener onCancelListener;

    public AcceptDialog(Context context) {
        super(context, R.style.ActionSheetDialogStyle);
    }

    public void setOnDetailsListener(View.OnClickListener onDetailsListener) {
        onClickListener = onDetailsListener;
    }

    public void setOnCancel(View.OnClickListener onDetailsListener) {
        onCancelListener = onDetailsListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_accept);
        initView();

    }

    private void initView() {
        btnCancel = (Button) this.findViewById(R.id.btn_dialog_accept_cancel);
        btnDetails = (Button) this.findViewById(R.id.btn_dialog_accept_details);
        btnCancel.setOnClickListener(onCancelListener);
        btnDetails.setOnClickListener(onClickListener);
    }


}
