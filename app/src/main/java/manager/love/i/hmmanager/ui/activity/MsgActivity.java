package manager.love.i.hmmanager.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.rong.imkit.RongContext;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.adapter.ConversationListAdapterEx;
import manager.love.i.hmmanager.adapter.TAbFrAdapter;
import manager.love.i.hmmanager.base.BaseActivity;
import manager.love.i.hmmanager.ui.fragment.MsgFragment;


public class MsgActivity extends BaseActivity {

    @BindView(R.id.vp_msg_content)
    ViewPager vpMsg;

    @BindView(R.id.tb_msg_title)
    TabLayout tbMsg;

    private TAbFrAdapter adapter;

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    /**
     * 会话列表的fragment
     */
    private ConversationListFragment mConversationListFragment = null;

    private Conversation.ConversationType[] mConversationsTypes = null;

    private boolean isDebug = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        if (fragments == null)
            fragments = new ArrayList<>();
        if (titles == null)
            titles = new ArrayList<>();

        adapter = new TAbFrAdapter(getSupportFragmentManager(), fragments, titles);
        vpMsg.setAdapter(adapter);
        tbMsg.setupWithViewPager(vpMsg);
        String[] types = {"'100'", "'200'", "'300'"};
        for (int i = 0; i < 3; i++) {
            if (i == 1) {
                fragments.add(initConversationList());
            } else {
                MsgFragment fragment = new MsgFragment();
                Bundle bundle = new Bundle();
                bundle.putString("type", types[i]);
                fragment.setArguments(bundle);
                fragments.add(fragment);
            }
        }
        titles.add("预约消息");
        titles.add("365消息");
        titles.add("其它消息");
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void setData() {
        /*setUpTabBadge(2);*/
    }


    /**
     * 设置Tablayout上的标题的角标
     *//*
    private void setUpTabBadge(int position) {
        TabLayout.Tab tab = tbMsg.getTabAt(position);
        if (tab != null) {
            View view = LayoutInflater.from(this).inflate(R.layout.tab_title_layout, null);
            ((TextView) view.findViewById(R.id.tv_title)).setText(titles.get(position));
            tab.setCustomView(view);
            *//*BadgeView badgeView = new BadgeView(this, (View) view.getParent());
            badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
            badgeView.setText(position+"");
            badgeView.show(true);*//*
        }
    }*/
    public void back(View view) {
        finish();
    }

    private Fragment initConversationList() {
        if (mConversationListFragment == null) {
            ConversationListFragment listFragment = new ConversationListFragment();
            listFragment.setAdapter(new ConversationListAdapterEx(RongContext.getInstance()));
            Uri uri;
            if (isDebug) {
                uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                        .appendPath("conversationlist")
                        .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                        .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
                        .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                        .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                        .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//系统
                        .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
                        .build();
                mConversationsTypes = new Conversation.ConversationType[]{Conversation.ConversationType.PRIVATE,
                        Conversation.ConversationType.GROUP,
                        Conversation.ConversationType.PUBLIC_SERVICE,
                        Conversation.ConversationType.APP_PUBLIC_SERVICE,
                        Conversation.ConversationType.SYSTEM,
                        Conversation.ConversationType.DISCUSSION
                };

            } else {
                uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                        .appendPath("conversationlist")
                        .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                        .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
                        .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                        .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                        .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                        .build();
                mConversationsTypes = new Conversation.ConversationType[]{Conversation.ConversationType.PRIVATE,
                        Conversation.ConversationType.GROUP,
                        Conversation.ConversationType.PUBLIC_SERVICE,
                        Conversation.ConversationType.APP_PUBLIC_SERVICE,
                        Conversation.ConversationType.SYSTEM
                };
            }
            listFragment.setUri(uri);
            mConversationListFragment = listFragment;
            return listFragment;
        } else {
            return mConversationListFragment;
        }
    }
}
