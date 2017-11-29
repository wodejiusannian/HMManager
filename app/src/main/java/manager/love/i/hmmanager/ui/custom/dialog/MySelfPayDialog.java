package manager.love.i.hmmanager.ui.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import manager.love.i.hmmanager.R;

/**
 * Created by 小五 on 2016/12/26.
 */
public class MySelfPayDialog extends Dialog {
    private Button mButtonNo, mButtonYes;

    private String mNo, mYes, mTitle;
    private OnNoClickListener mNoListener;
    private OnYesClickListener mYesListener;
    private TextView mTextViewTitle;
    private String isALiOrWeChat;
    private RadioGroup mRg;

    public void setOnNoListener(String no, OnNoClickListener noListener) {
        if (no != null) {
            mNo = no;
        }
        mNoListener = noListener;
    }

    public void setOnYesListener(String yes, OnYesClickListener yesListener) {
        if (yes != null) {
            mYes = yes;
        }
        mYesListener = yesListener;
    }


    public void setTitle(String title) {
        if (title != null) {
            mTitle = title;
        }
    }


    public MySelfPayDialog(Context context) {
        super(context, R.style.MySelfDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_self_dialog);
        setCanceledOnTouchOutside(false);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        mButtonNo = (Button) findViewById(R.id.my_self_no);
        mButtonYes = (Button) findViewById(R.id.my_self_yes);
        mTextViewTitle = (TextView) findViewById(R.id.my_self_title);
        mRg = (RadioGroup) findViewById(R.id.rg_pay);
    }

    private void initData() {

        if (mTitle != null) {
            mTextViewTitle.setText(mTitle);
        }
        if (mNo != null) {
            mButtonNo.setText(mNo);
        }
        if (mYes != null) {
            mButtonYes.setText(mYes);
        }
    }

    private void setListener() {

        mButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNoListener != null) {
                    mNoListener.onClick();
                }
                dismiss();
            }
        });
        mButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mYesListener != null) {
                    mYesListener.onClick(isALiOrWeChat);
                }
                dismiss();
            }
        });

        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_main_wechat:
                        isALiOrWeChat = "2";
                        break;
                    case R.id.rb_main_alipay:
                        isALiOrWeChat = "1";
                        break;
                    default:
                        break;
                }
            }
        });
    }


    public interface OnNoClickListener {
        void onClick();
    }

    public interface OnYesClickListener {
        void onClick(String tag);
    }

}
