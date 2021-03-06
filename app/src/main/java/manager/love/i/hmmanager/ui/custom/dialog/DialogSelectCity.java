package manager.love.i.hmmanager.ui.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.ui.fragment.welcome.model.ManagerCityModel;

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

    private CityModelAapter adapter;

    private List<ManagerCityModel> mData;

    private OnResultCity onResultCity;

    public DialogSelectCity(@NonNull Context context, List<ManagerCityModel> data) {
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
        adapter = new CityModelAapter();
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onResultCity.onResultCity(mData.get(position));
                dismiss();
            }
        });
    }

    public interface OnResultCity {
        void onResultCity(ManagerCityModel city);
    }

    private class CityModelAapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mData.size() == 0 ? 0 : mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder h;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_dialog_city, null);
                h = new ViewHolder(convertView);
                convertView.setTag(h);
            } else {
                h = (ViewHolder) convertView.getTag();
            }
            h.te.setText(mData.get(position).name);
            return convertView;
        }
    }


    public static class ViewHolder {

        public TextView te;

        public ViewHolder(View view) {
            te = (TextView) view.findViewById(R.id.tv_item_dialog_city);
        }
    }


}
