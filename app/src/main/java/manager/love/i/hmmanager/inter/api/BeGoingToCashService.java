package manager.love.i.hmmanager.inter.api;

import manager.love.i.hmmanager.bean.BeGoingToCash;
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
public interface BeGoingToCashService {
    @POST("stu/wallet/money/gzsTixian.do")
    Observable<BeGoingToCash> beGoingToCash(@Query("studio_id") String studio_id, @Query("money_tx") String money_tx);
}
