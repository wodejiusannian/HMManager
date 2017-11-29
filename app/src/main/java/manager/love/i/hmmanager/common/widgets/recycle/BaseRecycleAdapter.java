package manager.love.i.hmmanager.common.widgets.recycle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;


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
public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    private List<T> mData;
    private int layoutId;
    private LayoutInflater inflater;

    public BaseRecycleAdapter(Context mContext, List<T> mData, int layoutId) {
        this.mData = mData;
        this.layoutId = layoutId;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = new BaseViewHolder(inflater.inflate(layoutId, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        bindData(holder, mData.get(position), position);
    }

    public abstract void bindData(BaseViewHolder holder, T t, int position);

    @Override
    public int getItemCount() {
        return mData.size() == 0 ? 0 : mData.size();
    }
}
