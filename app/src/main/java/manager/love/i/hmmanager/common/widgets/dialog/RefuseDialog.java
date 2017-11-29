package manager.love.i.hmmanager.common.widgets.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.utils.ActivityUtils;


/**
 * Created by 小五 on 2017/5/18.
 */

public class RefuseDialog extends Dialog {
    private TextView cancel;
    private TextView login;

    private EditText account;
    private Refuse re;

    public RefuseDialog(Context context) {
        super(context, R.style.ActionSheetDialogStyle);

    }

    public void setLogin(Refuse re) {
        if (re != null) {
            this.re = re;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_refuse);
        initView();
        setListener();
    }

    private void setListener() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountStr = account.getText().toString().trim();
                if (ActivityUtils.isEmpty(accountStr)) {
                    re.refuse(accountStr);
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "请将信息输入完整", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        cancel = (TextView) this.findViewById(R.id.tv_login_dialog_cancel);
        login = (TextView) this.findViewById(R.id.tv_login_dialog_login);
        account = (EditText) this.findViewById(R.id.et_login_dialog_account);
    }

    public interface Refuse {
        void refuse(String reason);
    }
}
