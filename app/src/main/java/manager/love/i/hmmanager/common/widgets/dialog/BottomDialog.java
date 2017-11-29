package manager.love.i.hmmanager.common.widgets.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import manager.love.i.hmmanager.R;


/**
 * Created by 小五 on 2017/5/18.
 */

public class BottomDialog extends Dialog {
    private Button tvOne, tvTwo, tvCancel;

    private String mTop, mCenter;

    private OnTop mOnTop;
    private OnCenter mOnCenter;

    private boolean invisibal = false;

    public BottomDialog(Context context) {
        super(context, R.style.ActionSheetDialogStyle);

    }

    public void setInVisibal(Boolean invisibal) {
        this.invisibal = invisibal;
    }

    public void setTop(String top, OnTop ontop) {
        if (top != null) {
            mTop = top;
        }
        mOnTop = ontop;
    }

    public void setOnCenter(String center, OnCenter oncenter) {
        if (center != null) {
            mCenter = center;
        }
        mOnCenter = oncenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom);
        initView();

    }

    private void initView() {
        tvOne = (Button) this.findViewById(R.id.takePhoto);
        tvTwo = (Button) this.findViewById(R.id.choosePhoto);
        tvCancel = (Button) this.findViewById(R.id.cancel);
        Window dialogWindow = getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);
        if (tvOne != null) {
            tvOne.setText(mTop);
        }

        if (tvTwo != null) {
            tvTwo.setText(mCenter);
        }

        if (invisibal) {
            tvTwo.setVisibility(View.INVISIBLE);
        }
        tvOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnTop != null) {
                    mOnTop.onClick();
                }
                dismiss();
            }
        });
        tvTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnCenter != null) {
                    mOnCenter.onClick();
                }
                dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public interface OnTop {
        void onClick();
    }

    public interface OnCenter {
        void onClick();
    }
}
