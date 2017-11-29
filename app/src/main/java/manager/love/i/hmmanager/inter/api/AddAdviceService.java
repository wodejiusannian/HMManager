package manager.love.i.hmmanager.inter.api;

import manager.love.i.hmmanager.bean.UserInfo;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

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
public interface AddAdviceService {

    @POST("stu/system/advice/addAdvice.do")
    Observable<UserInfo> addAdvice(@Query("user_id") String studio_id, @Query("phone") String phone, @Query("content") String content, @Query("tag") String tag);
}
