package manager.love.i.hmmanager.inter.api;

import manager.love.i.hmmanager.bean.Evaluate;
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
public interface UserEvaluateService {

    @POST("stu/order/service/order/getOrderEvaluate.do")
    Observable<Evaluate> userEvaluateService(@Query("studio_id") String studio_id, @Query("order_id") String order_id);
}
