package manager.love.i.hmmanager.ui.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

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
public class DialogSelectCity extends Dialog {


    @BindView(R.id.lv_dialog_select_city)
    ListView lv;

    private ArrayAdapter<String> adapter;

    private List<String> mData;

    private OnResultCity onResultCity;

    public DialogSelectCity(@NonNull Context context, List<String> data) {
        super(context, R.style.MyDialogStyle);
        if (data != null)
            mData = data;
    }

    public void setOnResultCity(OnResultCity onResultCity) {
        if (onResultCity != null) {
            this.onResultCity = onResultCity;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_city);
        ButterKnife.bind(this);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, mData);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onResultCity.onResultCity(mData.get(position));
            }
        });
    }

    public interface OnResultCity {
        void onResultCity(String city);
    }

}
