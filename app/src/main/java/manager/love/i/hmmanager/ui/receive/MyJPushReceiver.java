package manager.love.i.hmmanager.ui.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import manager.love.i.hmmanager.bean.Msg;
import manager.love.i.hmmanager.sql.SQLLiteDao;
import manager.love.i.hmmanager.utils.ActivityUtils;

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
public class MyJPushReceiver extends BroadcastReceiver {
    private static final String TAG = "MyJPushReceiver";

    /*public static final String ACTION_CONNECTION_CHANGE;
    public static final String ACTION_REGISTRATION_ID;
    public static final String ACTION_STOPPUSH;
    public static final String ACTION_RESTOREPUSH;
    public static final String ACTION_MESSAGE_RECEIVED;
    public static final String ACTION_NOTIFICATION_RECEIVED;
    public static final String ACTION_NOTIFICATION_OPENED;
    public static final String ACTION_NOTIFICATION_RECEIVED_PROXY;
    public static final String ACTION_STATUS;
    public static final String ACTION_ACTIVITY_OPENDED;
    public static final String EXTRA_CONNECTION_CHANGE;
    public static final String EXTRA_REGISTRATION_ID;
    public static final String EXTRA_APP_KEY;
    public static final String EXTRA_NOTIFICATION_DEVELOPER_ARG0;
    public static final String EXTRA_NOTIFICATION_TITLE;
    public static final String EXTRA_PUSH_ID;
    public static final String EXTRA_MSG_ID;
    public static final String EXTRA_NOTI_TYPE;
    public static final String EXTRA_ALERT;
    public static final String EXTRA_MESSAGE;
    public static final String EXTRA_CONTENT_TYPE;
    public static final String EXTRA_TITLE;
    public static final String EXTRA_EXTRA;
    public static final String EXTRA_NOTIFICATION_ID;
    public static final String EXTRA_ACTIVITY_PARAM;
    public static final String EXTRA_RICHPUSH_FILE_PATH;
    public static final String EXTRA_RICHPUSH_FILE_TYPE;
    public static final String EXTRA_RICHPUSH_HTML_PATH;
    public static final String EXTRA_RICHPUSH_HTML_RES;
    public static final String EXTRA_STATUS;*/
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        /*String content = (String) bundle.get(JPushInterface.EXTRA_ALERT);
        String message = (String) bundle.get(JPushInterface.EXTRA_MESSAGE);
        String one1 = (String) bundle.get("ACTION_MESSAGE_RECEIVED");
        String one2 = (String) bundle.get("ACTION_NOTIFICATION_RECEIVED");
        String one3 = (String) bundle.get("ACTION_NOTIFICATION_OPENED");
        String one4 = (String) bundle.get("EXTRA_EXTRA");*/

       /* String content1111 = bundle.getString(JPushInterface.EXTRA_MESSAGE);*/
        String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
        if (ActivityUtils.isEmpty(extra)) {
            SQLLiteDao dao = new SQLLiteDao(context);
            try {
                JSONObject object = new JSONObject(extra);
                String studio_id = object.getString("studio_id");
                String title = object.getString("title");
                String content = object.getString("content");
                String type_1 = object.getString("type_1");
                String type_2 = object.getString("type_2");
                String ope_name = object.getString("ope_name");
                Long time = System.currentTimeMillis();
                Msg msg = new Msg(studio_id, title, content, 0, time + "", type_1, type_2, ope_name);
                dao.addWeather(msg);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

       /* Log.e(TAG, *//*"onReceive: " + "--content--" + content +
                "--message--" + message + "--one1--"
                + one1 + "--one2--" + one2 + "--one3--" + one3 + "--one4--" + one4 + "--one5--" + content1111 +*//* "--one6--" + extra);

       *//* String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        Log.e(TAG, "onReceive: " + JPushInterface.ACTION_REGISTRATION_ID);

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
                .getAction())) {
            // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);

            *//*//**//**************解析推送过来的json数据并存放到集合中 begin******************
         Map<String, Object> map = new HashMap<String, Object>();
         JSONObject jsonObject;
         try {
         jsonObject = new JSONObject(extra);
         String type = jsonObject.getString("type");
         map.put("type", type);
         } catch (JSONException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
         }
         //获取接收到推送时的系统时间
         Calendar rightNow = Calendar.getInstance();
         SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
         String date = fmt.format(rightNow.getTime());
         map.put("date", date);
         *//*//**//**************解析推送过来的json数据并存放到集合中 end******************


         } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
         .getAction())) {
         Log.e(TAG, "onReceive: " + "收到了通知");
         // 在这里可以做些统计，或者做些其他工作
         } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
         .getAction())) {
         Log.e(TAG, "onReceive: " + "用户点击打开了通知");
         Intent i = new Intent(context, MainActivity.class); // 自定义打开的界面
         i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         context.startActivity(i);
         }*/
    }
}
