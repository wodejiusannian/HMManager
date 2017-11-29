package manager.love.i.hmmanager.inter.api;

import manager.love.i.hmmanager.bean.ShopList;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 *
 */
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
public interface ShopListService {


    /**
     * @param page_now 页数
     * @param sex 性别
     * @param season 季节
     * @param type  分类
     * @param style 款式
     * @param color 颜色
     * @param type_ck 衣服查看分类 1-推荐服饰（无上门整理套装）  2-商城购买（无小蓝盒x10/组）
     * @param sort 排序方式 价格-1:从底到高，2:从高到底    销量-3:从底到高，4:从高到底
     * @return
     */
    @POST("stu/mall/clothes/getClothesList.do")
    Observable<ShopList> getShopList(@Query("page_now") int page_now,
                                     @Query("sex") String sex,
                                     @Query("season") String season,
                                     @Query("type") String type,
                                     @Query("style") String style,
                                     @Query("color") String color,
                                     @Query("type_ck") int type_ck,
                                     @Query("sort") String sort);

}
