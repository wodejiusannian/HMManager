package manager.love.i.hmmanager.inter.api;

import manager.love.i.hmmanager.bean.InDoorOrder;
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
public interface UserInDoorOrderService {
    @POST("admiral/app/hmgls/order/info/listOrder.htm?token=")
    Observable<InDoorOrder> getInDoorOrder(@Query("token") String token, @Query("sellerUserId") String sellerUserId, @Query("deleteStatusPj") String deleteStatus,@Query("orderType") String orderType);

}
