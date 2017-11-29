package manager.love.i.hmmanager;


import android.support.multidex.MultiDexApplication;

import com.baidu.mapapi.SDKInitializer;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;
import io.rong.imkit.RongIM;
import io.rong.push.RongPushClient;


public class App extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ShareSDK.initSDK(this);
        SDKInitializer.initialize(this);
        JPushInterface.init(this);
        RongPushClient.registerHWPush(this);
        RongPushClient.registerMiPush(this, "2882303761517529506", "5811752989506");
        RongIM.init(this);
        x.Ext.init(this);
    }

}