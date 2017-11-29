package manager.love.i.hmmanager.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.base.BaseFragment;
import manager.love.i.hmmanager.bean.Msg;
import manager.love.i.hmmanager.common.widgets.dialog.NormalDialog;
import manager.love.i.hmmanager.common.widgets.recycle.BaseRecycleAdapter;
import manager.love.i.hmmanager.common.widgets.recycle.BaseViewHolder;
import manager.love.i.hmmanager.sql.SQLLiteDao;
import manager.love.i.hmmanager.ui.activity.MsgDetailsActivity;
import manager.love.i.hmmanager.utils.ActivityUtils;
import manager.love.i.hmmanager.utils.SPUtils;

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
public class MsgFragment extends BaseFragment /*implements BGARefreshLayout.BGARefreshLayoutDelegate*/ {
    @BindView(R.id.rv_msg_show)
    RecyclerView rvMsg;

    private BaseRecycleAdapter<Msg> adapter;

    private List<Msg> mData = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.fragment_msg;
    }

    @Override
    protected void initView() {


    }

    @Override
    protected void setData() {
        SQLLiteDao dao = new SQLLiteDao(getContext());
        String stuId = SPUtils.getStuId(getContext());
        mData.addAll(dao.selectPerson("'" + stuId + "'", getArguments().getString("type")));
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void initData() {
        if (mData == null)
            mData = new ArrayList<>();


        adapter = new BaseRecycleAdapter<Msg>(getActivity(), mData, R.layout.item_msg_rv) {
            @Override
            public void bindData(BaseViewHolder holder, Msg msg, final int position) {
                TextView textView = holder.getView(R.id.tv_msg_msg);
                final String content = msg.content;
                final String title = msg.title;
                textView.setText(content);
                LinearLayout rootView = (LinearLayout) holder.getRootView();
                rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("content", content);
                        map.put("title", title);
                        ActivityUtils.switchTo(getActivity(), MsgDetailsActivity.class, map);
                    }
                });
                rootView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        final NormalDialog dialog = new NormalDialog(getContext());
                        dialog.setTitle("确认删除吗");
                        dialog.setMsg("删除后，看不看此消息");
                        dialog.setCancel("取消");
                        dialog.setOnYesListener("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SQLLiteDao dao = new SQLLiteDao(getContext());
                                String time = mData.get(position).time;
                                dao.deletePerson(time);
                                mData.remove(position);
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        return false;
                    }
                });
            }
        };
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvMsg.setLayoutManager(manager);
        rvMsg.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mData.clear();
    }

}
