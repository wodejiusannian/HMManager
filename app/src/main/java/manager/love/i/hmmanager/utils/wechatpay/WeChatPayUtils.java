package manager.love.i.hmmanager.utils.wechatpay;

import android.content.Context;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import manager.love.i.hmmanager.wxapi.Constants;

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
public class WeChatPayUtils {

    public static void weChatPay(String result, Context context) {
        final IWXAPI mWxApi = WXAPIFactory.createWXAPI(context, Constants.APP_ID, true);
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
