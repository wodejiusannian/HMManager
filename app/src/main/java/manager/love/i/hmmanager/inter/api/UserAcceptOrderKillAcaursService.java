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
public interface UserAcceptOrderKillAcaursService {

    @POST("admiral/app/hmgls/service/order/acceptOrder.htm")
    Observable<UserInfo> acceptOrder(@Query("token") String token, @Query("orderId") String orderId, @Query("orderType") String orderType);
}
