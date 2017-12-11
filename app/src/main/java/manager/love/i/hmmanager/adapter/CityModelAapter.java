package manager.love.i.hmmanager.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import manager.love.i.hmmanager.R;
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
public class CityModelAapter extends BaseAdapter {
    List<ManagerCityModel> DATA;

    private List<Boolean> booleens;
    Context context;

    private StringInter mOnClickListener;

    public CityModelAapter(List<ManagerCityModel> DATAS, Context context, List<Boolean> booleens) {
        DATA = DATAS;
        this.context = context;
        this.booleens = booleens;
        for (int i = 0; i < DATA.size(); i++) {
            booleens.add(false);
        }
    }

    @Override
    public int getCount() {
        return DATA.size() == 0 ? 0 : DATA.size();
    }

    @Override
    public Object getItem(int position) {
        return DATA.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder h;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_dialog_city_copy, null);
            h = new ViewHolder(convertView);
            convertView.setTag(h);
        } else {
            h = (ViewHolder) convertView.getTag();
        }
        h.te.setText(DATA.get(position).name);
        h.te.setTag(position);
        h.te.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickListener.onResult(DATA.get(position).name);
                for (int i = 0; i < DATA.size(); i++) {
                    booleens.set(i, false);
                }
                booleens.set(position, true);
                notifyDataSetChanged();
            }
        });
        if (booleens != null && booleens.size() > 0 && booleens.get(position)) {
            h.te.setBackgroundColor(Color.GRAY);
        } else {
            h.te.setBackgroundColor(Color.WHITE);
        }
        return convertView;
    }

    public static class ViewHolder {

        public TextView te;

        public ViewHolder(View view) {
            te = (TextView) view.findViewById(R.id.tv_item_dialog_city);
        }
    }

    public void setOnClickListener(StringInter stringInter) {
        if (stringInter != null)
            mOnClickListener = stringInter;
    }
}



