package manager.love.i.hmmanager.utils;

import android.content.Context;

import com.iflytek.autoupdate.IFlytekUpdate;
import com.iflytek.autoupdate.IFlytekUpdateListener;
import com.iflytek.autoupdate.UpdateConstants;
import com.iflytek.autoupdate.UpdateErrorCode;
import com.iflytek.autoupdate.UpdateInfo;
import com.iflytek.autoupdate.UpdateType;

/**
 * Created by 小五 on 2017/5/3.
 */

public class UpdateUtils {
    private static UpdateUtils instance;
    private IFlytekUpdate updManager;
    private Context context;

    public static UpdateUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (UpdateUtils.class) {
                if (instance == null) {
                    instance = new UpdateUtils(context);
                }
            }
        }
        return instance;
    }

    public UpdateUtils(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
        init();
    }

    private void init() {
        updManager = IFlytekUpdate.getInstance(context);
        updManager.setDebugMode(true);
        updManager.setParameter(UpdateConstants.EXTRA_WIFIONLY, "false");
        updManager.setParameter(UpdateConstants.EXTRA_STYLE, UpdateConstants.UPDATE_UI_DIALOG);
    }

    /**
     * 如果是主动请求(如更多界面)，则initiatively传入true
     *
     * @param initiatively
     */
    public void update(boolean initiatively) {
        if (initiatively)
            updManager.autoUpdate(context, updateListener);
        else
            updManager.autoUpdate(context, null);
    }

    //升级版本
    private IFlytekUpdateListener updateListener = new IFlytekUpdateListener() {

        @Override
        public void onResult(int errorcode, UpdateInfo result) {
            if (errorcode == UpdateErrorCode.OK && result != null) {
                if (result.getUpdateType() == UpdateType.NoNeed) {
                    mU.update();
                    return;
                }
                updManager.showUpdateInfo(context, result);
            }
        }
    };

    private Update mU;

    public void setOnUpListener(Update u) {
        if (u != null) {
            mU = u;
        }
    }

    public interface Update {
        void update();
    }
}
