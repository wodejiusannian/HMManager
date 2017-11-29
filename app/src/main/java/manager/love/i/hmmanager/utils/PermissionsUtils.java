package manager.love.i.hmmanager.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

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
public class PermissionsUtils {
    public static final int PER_STORAGE = 0;
    public static final int PER_LOCATION = 1;
    public static final int PER_CALL_PHONE = 2;

    public static void applyPermission(Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA
        }, requestCode);
    }


    public static boolean isPermission(Activity activity, int permissionCode) {

        String[] permission = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA};

        if (ContextCompat.checkSelfPermission(activity,
                permission[permissionCode])
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }
}
