package manager.love.i.hmmanager.inter.api;

import manager.love.i.hmmanager.bean.QbYue;
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
public interface QbYueService {
    //type：18：上门服务；27：小蓝盒；36：衣服补差价；56:365购买；74：用户购收纳盒；1：除螨服务；
    @POST("stu/wallet/money/getQbYueV1.do")
    Observable<QbYue> getQbYue(@Query("studio_id") String studio_id);
}
