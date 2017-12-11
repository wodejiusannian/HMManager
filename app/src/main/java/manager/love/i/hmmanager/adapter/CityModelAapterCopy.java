package manager.love.i.hmmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

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
public class CityModelAapterCopy extends BaseAdapter {
    List<ManagerCityModel> DATA;

    Context context;

    public CityModelAapterCopy(List<ManagerCityModel> DATAS, Context context) {
        DATA = DATAS;
        this.context = context;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_dialog_city, null);
            h = new ViewHolder(convertView);
            convertView.setTag(h);
        } else {
            h = (ViewHolder) convertView.getTag();
        }
        h.te.setText(DATA.get(position).name);
        return convertView;
    }

    public static class ViewHolder {

        public TextView te;

        public ViewHolder(View view) {
            te = (TextView) view.findViewById(R.id.tv_item_dialog_city);
        }
    }
}



