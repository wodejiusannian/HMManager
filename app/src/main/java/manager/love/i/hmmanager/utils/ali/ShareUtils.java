package manager.love.i.hmmanager.utils.ali;

import android.content.Context;

import manager.love.i.hmmanager.common.onekeyshare.OnekeyShare;


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
public class ShareUtils {
    public static void showShare(final Context context, String titleUrl,
                                  String content, String picUrl) {
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle(content);
        oks.setTitleUrl(titleUrl);
        oks.setText(content);
        oks.setUrl(titleUrl);
        oks.setComment(content);
        oks.setImageUrl(picUrl);
        oks.show(context);
    }
}
