package manager.love.i.hmmanager.common.widgets.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.ui.activity.register.ServiceCityActivity;
import manager.love.i.hmmanager.utils.ActivityUtils;


/**
 * Created by 小五 on 2017/5/18.
 */

public class LoginDialog extends Dialog {
    private TextView login;

    private EditText account;
    private EditText psd;
    private Login lo;

    private TextView welcome;

    public LoginDialog(Context context) {
        super(context, R.style.ActionSheetDialogStyle);

    }

    public void setLogin(Login lo) {
        if (lo != null) {
            this.lo = lo;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_login);
        initView();
        setListener();
    }

    private void setListener() {

        welcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), ServiceCityActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountStr = account.getText().toString().trim();
                String pwdStr = psd.getText().toString().trim();
                if (ActivityUtils.isEmpty(accountStr) || ActivityUtils.isEmpty(pwdStr)) {
                    lo.getInfo(accountStr, pwdStr);
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "请将信息输入完整", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        login = (TextView) this.findViewById(R.id.tv_login_dialog_login);
        account = (EditText) this.findViewById(R.id.et_login_dialog_account);
        psd = (EditText) this.findViewById(R.id.et_login_dialog_psd);
        welcome = (TextView) this.findViewById(R.id.tv_login_dialog_welcome);
    }

    public interface Login {
        void getInfo(String account, String psd);
    }
}
