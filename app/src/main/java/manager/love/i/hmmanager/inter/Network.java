// (c)2016 Flipboard Inc, All Rights Reserved.

package manager.love.i.hmmanager.inter;

import android.content.Context;
import android.util.Log;

import manager.love.i.hmmanager.bean.IMInfo;
import manager.love.i.hmmanager.bean.IMUser;
import manager.love.i.hmmanager.inter.api.AddAdviceService;
import manager.love.i.hmmanager.inter.api.AddUserInfoService;
import manager.love.i.hmmanager.inter.api.BeGoingToCashService;
import manager.love.i.hmmanager.inter.api.CashRegisteService;
import manager.love.i.hmmanager.inter.api.Customer365Service;
import manager.love.i.hmmanager.inter.api.CustomerGoDoorService;
import manager.love.i.hmmanager.inter.api.HistoricalIncomeService;
import manager.love.i.hmmanager.inter.api.IMInfoService;
import manager.love.i.hmmanager.inter.api.IMTokenService;
import manager.love.i.hmmanager.inter.api.QbYueService;
import manager.love.i.hmmanager.inter.api.RecommendShopService;
import manager.love.i.hmmanager.inter.api.ShopItemService;
import manager.love.i.hmmanager.inter.api.ShopListService;
import manager.love.i.hmmanager.inter.api.ShopTAGService;
import manager.love.i.hmmanager.inter.api.StudioInfoService;
import manager.love.i.hmmanager.inter.api.StudioTimeManagerService;
import manager.love.i.hmmanager.inter.api.StudioTimeUpService;
import manager.love.i.hmmanager.inter.api.UserAcceptOrderService;
import manager.love.i.hmmanager.inter.api.UserEvaluateService;
import manager.love.i.hmmanager.inter.api.UserInDoorOrderService;
import manager.love.i.hmmanager.inter.api.UserInfoService;
import manager.love.i.hmmanager.inter.api.UserStateService;
import manager.love.i.hmmanager.sql.SQLIMDao;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static io.rong.eventbus.EventBus.TAG;

public class Network {

    private static final String BASE_URL = "http://hmyc365.net:8084/HM/";
    private static UserInfoService userInfoService;
    private static UserStateService userStateService;
    private static UserAcceptOrderService userAcceptOrderService;
    private static UserEvaluateService userEvaluateService;
    private static StudioInfoService studioInfoService;
    private static Customer365Service customer365;
    private static CustomerGoDoorService customerGoDoor;
    private static HistoricalIncomeService historicalIncomeService;
    private static UserInDoorOrderService userInDoorService;
    private static AddAdviceService addAdviceService;
    private static StudioTimeManagerService studioTimeManagerService;
    private static StudioTimeUpService studioTimeUpService;
    private static QbYueService qbYueService;
    private static IMInfoService imInfoService;
    private static BeGoingToCashService beGoingToCashService;
    private static CashRegisteService cashRegisteService;
    private static IMTokenService imTokenService;
    private static AddUserInfoService addUserInfoService;
    private static ShopListService shopService;
    private static ShopItemService shopItemService;
    private static RecommendShopService recommendShopService;
    private static ShopTAGService shopTAGService;
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    /*
    * 获取用户信息
    * */
    public static UserInfoService getUserInfo() {
        if (userInfoService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            userInfoService = retrofit.create(UserInfoService.class);
        }
        return userInfoService;
    }

    public static ShopTAGService shopTAGService() {
        if (shopTAGService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            shopTAGService = retrofit.create(ShopTAGService.class);
        }
        return shopTAGService;
    }

    /*
     * MainActivity 设置在线状态
     * */
    public static UserStateService userState() {

        if (userStateService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            userStateService = retrofit.create(UserStateService.class);
        }

        return userStateService;
    }

    public static ShopItemService shopItemService() {
        if (shopItemService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            shopItemService = retrofit.create(ShopItemService.class);
        }
        return shopItemService;
    }

    /*
     * MainActivity 获取订单
     * */
    public static UserInDoorOrderService userInDoorOrder() {

        if (userInDoorService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            userInDoorService = retrofit.create(UserInDoorOrderService.class);
        }

        return userInDoorService;
    }

    /*
    * MainActivity 欣然接受
    * */
    public static UserAcceptOrderService userAcceptOrderService() {

        if (userAcceptOrderService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            userAcceptOrderService = retrofit.create(UserAcceptOrderService.class);
        }

        return userAcceptOrderService;
    }


    /*
   * 获取工作室评价
   * */
    public static UserEvaluateService userEvaluateService() {

        if (userEvaluateService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            userEvaluateService = retrofit.create(UserEvaluateService.class);
        }

        return userEvaluateService;
    }

    public static ShopListService shopService() {
        if (shopService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            shopService = retrofit.create(ShopListService.class);
        }
        return shopService;
    }

    /*
     * 获取工作室信息
     * */
    public static StudioInfoService studioInfoService() {

        if (studioInfoService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            studioInfoService = retrofit.create(StudioInfoService.class);
        }

        return studioInfoService;
    }

    /*
    * 获取365客户订单列表
    * */
    public static Customer365Service customer365() {

        if (customer365 == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            customer365 = retrofit.create(Customer365Service.class);
        }

        return customer365;
    }

    /*
    * 获取预约客户订单列表
    * */
    public static CustomerGoDoorService customerGoDoor() {

        if (customerGoDoor == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            customerGoDoor = retrofit.create(CustomerGoDoorService.class);
        }

        return customerGoDoor;
    }


    /*
    * 处理时间设置
    * */
    public static StudioTimeManagerService studioTimeManagerService() {

        if (studioTimeManagerService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            studioTimeManagerService = retrofit.create(StudioTimeManagerService.class);
        }

        return studioTimeManagerService;
    }

    public static StudioTimeUpService studioTimeUpService() {

        if (studioTimeUpService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .build();
            studioTimeUpService = retrofit.create(StudioTimeUpService.class);
        }

        return studioTimeUpService;
    }


    /*
     * 设置中的意见反馈
     * */
    public static AddAdviceService addAdviceService() {

        if (addAdviceService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            addAdviceService = retrofit.create(AddAdviceService.class);
        }

        return addAdviceService;
    }


    /*
     * 钱包首页的接口
     * */
    public static QbYueService qbYueService() {

        if (qbYueService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            qbYueService = retrofit.create(QbYueService.class);
        }

        return qbYueService;
    }


    /*
     * 钱包首页的接口
     * */
    public static HistoricalIncomeService historicalIncome() {

        if (historicalIncomeService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            historicalIncomeService = retrofit.create(HistoricalIncomeService.class);
        }

        return historicalIncomeService;
    }


    /*
   * 提现记录的接口
   * */
    public static CashRegisteService cashRegisteService() {

        if (cashRegisteService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            cashRegisteService = retrofit.create(CashRegisteService.class);
        }

        return cashRegisteService;
    }


    /*
     * 提现记录的接口
     * */
    public static BeGoingToCashService beGoingToCashService() {

        if (beGoingToCashService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            beGoingToCashService = retrofit.create(BeGoingToCashService.class);
        }

        return beGoingToCashService;
    }


    /*
    * 上传个人信息
    * */
    public static AddUserInfoService addUserInfoService() {

        if (addUserInfoService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            addUserInfoService = retrofit.create(AddUserInfoService.class);
        }

        return addUserInfoService;
    }


    public static RecommendShopService recommendShopService() {
        if (recommendShopService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            recommendShopService = retrofit.create(RecommendShopService.class);
        }
        return recommendShopService;
    }

    public static IMTokenService imTokenService() {
        if (imTokenService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            imTokenService = retrofit.create(IMTokenService.class);
        }
        return imTokenService;
    }

    /*public static IMInfoService imInfoService() {
        if (imInfoService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            imInfoService = retrofit.create(IMInfoService.class);
        }
        return imInfoService;
    }*/

    public static void getIMInfo(final Context context, final String user_id) {
        if (imInfoService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            imInfoService = retrofit.create(IMInfoService.class);
        }
        String userID = user_id.split("_")[1];
        imInfoService.inInfo(1, userID).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IMInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(IMInfo imInfo) {
                        IMInfo.BodyBean bean = imInfo.getBody();
                        SQLIMDao dao = new SQLIMDao(context);
                        dao.addWeather(new IMUser(user_id, bean.getUser_name(), bean.getUser_pic()));
                    }
                });
    }


}
