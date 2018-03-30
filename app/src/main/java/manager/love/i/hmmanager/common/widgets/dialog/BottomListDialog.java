package manager.love.i.hmmanager.common.widgets.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.adapter.EduAdapter;


/**
 * Created by 小五 on 2017/5/18.
 */

public class BottomListDialog extends Dialog {


    private Unbinder unbinder;

    @BindView(R.id.lv_bottom_dialog)
    ListView lv;

    private String[] mData;

    public BottomListDialog(Context context, String[] data) {
        super(context, R.style.ActionSheetDialogStyle);
        mData = data;
    }

    private OnResultEdu onResultEdu;

    public void setOnItemClickListener(OnResultEdu onItemClickListener) {
        onResultEdu = onItemClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_list);
        Window dialogWindow = getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);
        unbinder = ButterKnife.bind(this);
        EduAdapter adapter = new EduAdapter(mData, getContext());
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onResultEdu.onResultEdu(mData[position]);
                dismiss();
            }
        });
    }

    public interface OnResultEdu {
        void onResultEdu(String edu);
    }

    @OnClick({R.id.cancel})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                this.dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        unbinder.unbind();
    }
}
