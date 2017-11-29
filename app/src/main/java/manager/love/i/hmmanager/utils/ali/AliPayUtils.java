package manager.love.i.hmmanager.utils.ali;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import manager.love.i.hmmanager.utils.RxBusUtils;

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
public class AliPayUtils {

    private static final String TAG = "AliPayUtils";

    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private static Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000")) {
                        RxBusUtils.getDefault().post(9000);
                    } else {
                        RxBusUtils.getDefault().post(9001);
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    public static void aliPay(String data, final Activity activity) {
        try {
            JSONObject obj = new JSONObject(data);
            JSONObject body = obj.getJSONObject("body");
            final String sign = body.getString("sign");
            Runnable payRunnable = new Runnable() {
                @Override
                public void run() {
                    PayTask alipay = new PayTask(activity);
                    Map<String, String> result = alipay.payV2(sign, true);
                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }
            };
            Thread payThread = new Thread(payRunnable);
            payThread.start();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
