package manager.love.i.hmmanager.ui.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.adapter.CityModelAapter;
import manager.love.i.hmmanager.adapter.CityModelAapterCopy;
import manager.love.i.hmmanager.ui.fragment.welcome.model.ManagerCityModel;
import manager.love.i.hmmanager.utils.interutlis.StringInter;

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
public class DialogSelectCityCopy extends Dialog {


    @BindView(R.id.lv_dialog_select_province)
    ListView lvProvince;

    @BindView(R.id.lv_dialog_select_city)
    ListView lvCity;

    private CityModelAapter adapter;

    private CityModelAapterCopy adapterCityssss;

    private List<ManagerCityModel> mData;

    private List<Boolean> booleans;
    private OnResultCity onResultCity;

    private Map<String, List<ManagerCityModel>> maps;

    private List<ManagerCityModel> mDataCitys;

    public DialogSelectCityCopy(@NonNull Context context, Map<String, List<ManagerCityModel>> map, List<ManagerCityModel> data) {
        super(context, R.style.MyDialogStyle);
        if (data != null)
            mData = data;
        if (map != null) {
            maps = map;
        }
    }

    public void setOnResultCity(OnResultCity onResultCity) {
        if (onResultCity != null) {
            this.onResultCity = onResultCity;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_city_copy);
        ButterKnife.bind(this);
        booleans = new ArrayList<>();
        mDataCitys = new ArrayList<>();
        adapter = new CityModelAapter(mData, getContext(), booleans);
        adapterCityssss = new CityModelAapterCopy(mDataCitys, getContext());
        lvCity.setAdapter(adapterCityssss);
        lvProvince.setAdapter(adapter);
        lvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onResultCity.onResultCity(mDataCitys.get(position));
                dismiss();
            }
        });
        adapter.setOnClickListener(new StringInter() {
            @Override
            public void onResult(String result) {
                mDataCitys.clear();
                mDataCitys.addAll(maps.get(result));
                adapterCityssss.notifyDataSetChanged();
            }
        });
    }

    public interface OnResultCity {
        void onResultCity(ManagerCityModel city);
    }

}
