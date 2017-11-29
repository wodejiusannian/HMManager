package manager.love.i.hmmanager.utils;

import android.content.Context;
import android.content.SharedPreferences;

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
public class SPUtils {

    public static String getStuSta(Context context) {
        SharedPreferences user = context.getSharedPreferences("userInfo", 0);
        String studio_state = user.getString("studio_state", "");
        return studio_state;
    }

    public static String getStuSex(Context context) {
        SharedPreferences user = context.getSharedPreferences("userInfo", 0);
        String studio_sex = user.getString("studio_sex", "");
        return studio_sex;
    }

    public static String getIMToken(Context context) {
        SharedPreferences user = context.getSharedPreferences("userInfo", 0);
        String imtoken = user.getString("imtoken", "");
        return imtoken;
    }

    public static void updateImToken(Context context, String imtoken) {
        SharedPreferences user = context.getSharedPreferences("userInfo", 0);
        SharedPreferences.Editor edit = user.edit();
        edit.putString("imtoken", imtoken);
        edit.commit();
    }

    public static String getStuName(Context context) {
        SharedPreferences user = context.getSharedPreferences("userInfo", 0);
        String studio_name = user.getString("studio_name", "");
        return studio_name;
    }

    public static String getStuPic(Context context) {
        SharedPreferences user = context.getSharedPreferences("userInfo", 0);
        String studio_head_pic_url = user.getString("studio_head_pic_url", "");
        return studio_head_pic_url;
    }

    public static String getStuId(Context context) {
        SharedPreferences user = context.getSharedPreferences("userInfo", 0);
        String studio_id = user.getString("studio_id", "");
        return studio_id;
    }

    public static void writeUserInfo(Context context, String studio_state, String studio_sex,
                                     String studio_name, String studio_head_pic_url, String studio_id) {
        SharedPreferences user = context.getSharedPreferences("userInfo", 0);
        SharedPreferences.Editor edit = user.edit();
        edit.putString("studio_state", studio_state);
        edit.putString("studio_sex", studio_sex);
        edit.putString("studio_name", studio_name);
        edit.putString("studio_head_pic_url", studio_head_pic_url);
        edit.putString("studio_id", studio_id);
        edit.commit();
    }

    public static void updateState(Context context, String studio_state) {
        SharedPreferences user = context.getSharedPreferences("userInfo", 0);
        SharedPreferences.Editor edit = user.edit();
        edit.putString("studio_state", studio_state);
        edit.commit();
    }


    public static void isFirst(Context context) {
        SharedPreferences user = context.getSharedPreferences("userInfo", 0);
        SharedPreferences.Editor edit = user.edit();
        edit.putBoolean("isFirst", false);
        edit.commit();
    }


    public static boolean first(Context context) {
        SharedPreferences user = context.getSharedPreferences("userInfo", 0);
        Boolean studio_sex = user.getBoolean("isFirst", true);
        return studio_sex;
    }

    public static void updatePhoto(Context context, String studio_head_pic_url) {
        SharedPreferences user = context.getSharedPreferences("userInfo", 0);
        SharedPreferences.Editor edit = user.edit();
        edit.putString("studio_head_pic_url", studio_head_pic_url);
        edit.commit();
    }

    public static void upDataSex(Context context, String studio_sex) {
        SharedPreferences user = context.getSharedPreferences("userInfo", 0);
        SharedPreferences.Editor edit = user.edit();
        edit.putString("studio_sex", studio_sex);
        edit.commit();
    }
}
