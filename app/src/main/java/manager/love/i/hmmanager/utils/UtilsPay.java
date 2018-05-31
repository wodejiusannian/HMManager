package manager.love.i.hmmanager.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import java.util.Map;

import manager.love.i.hmmanager.inter.IsSuccess;
import manager.love.i.hmmanager.utils.ali.PayResult;
import manager.love.i.hmmanager.wxapi.Constants;

/**
 * Created by 小五 on 2017/2/14.
 */

public class UtilsPay {

    private static final int SDK_PAY_FLAG = 1;

    private Context mContext;

    private IsSuccess mSuccess;

    public void isSuccess(IsSuccess success) {
        mSuccess = success;
    }

    public UtilsPay(Context mContext) {
        this.mContext = mContext;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000")) {
                        mSuccess.isSuccess(9000);
                    } else {
                        mSuccess.isSuccess(9001);
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    public void aliPay(final String data) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask((Activity) mContext);
                Map<String, String> result = alipay.payV2(data, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    public void weChatPay(String result) {

        final IWXAPI mWxApi = WXAPIFactory.createWXAPI(mContext, Constants.APP_ID, true);

        mWxApi.registerApp(Constants.APP_ID);

        PayReq req = new PayReq();

        try {
            JSONObject object = new JSONObject(result);
            JSONObject body = object.getJSONObject("body");
            //应用ID
            req.appId = Constants.APP_ID;
            //商户号
            String partnerId = body.getString("partnerid");
            req.partnerId = partnerId;
            //预支付交易会话ID
            String prepayId = body.getString("prepayid");
            req.prepayId = prepayId;
            //随机字符串
            String nonceStr = body.getString("noncestr");
            req.nonceStr = nonceStr;
            //时间戳*
            String timeStamp = body.getString("timestamp");
            req.timeStamp = timeStamp;
            //扩展字段
            req.packageValue = "Sign=WXPay";
            //签名*
            String sign = body.getString("sign");
            req.sign = sign;
            mWxApi.sendReq(req);
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
    }


}
