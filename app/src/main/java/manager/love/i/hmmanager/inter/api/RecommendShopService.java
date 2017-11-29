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
public interface RecommendShopService {

    @POST("stu/rec/clothes/addRecClothes.do")
    Observable<UserInfo> recommendShop(@Query("studio_id") String studio_id,
                                       @Query("user_id") String user_id,
                                       @Query("clothes_id") String clothes_id,
                                       @Query("stock_id") String stock_id,
                                       @Query("reason") String reason,
                                       @Query("color_name") String color_name,
                                       @Query("size_name") String size_name,
                                       @Query("clothes_cz") String clothes_cz);

}
