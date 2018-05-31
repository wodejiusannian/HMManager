// (c)2016 Flipboard Inc, All Rights Reserved.

package manager.love.i.hmmanager.inter;

import manager.love.i.hmmanager.inter.api.ManagerLevelService;
import manager.love.i.hmmanager.inter.api.UserAcceptOrderKillAcaursService;
import manager.love.i.hmmanager.inter.api.UserInDoorOrderService;
import manager.love.i.hmmanager.inter.api.UserRefuseOrderService;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkCopy {

    private static final String BASE_URL = "http://hmyc365.net/";

    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    private static OkHttpClient okHttpClient = new OkHttpClient();

    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();

    private static UserInDoorOrderService userInDoorService;

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

    private static UserRefuseOrderService userRefuseOrderService;

    /*
    * MainActivity 除螨残忍拒绝
    * */
    public static UserRefuseOrderService userRefuseOrderService() {

        if (userRefuseOrderService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            userRefuseOrderService = retrofit.create(UserRefuseOrderService.class);
        }

        return userRefuseOrderService;
    }


    private static UserAcceptOrderKillAcaursService userAcceptOrderKillAcaursService;


    /*
    * MainActivity 除螨残忍拒绝
    * */
    public static UserAcceptOrderKillAcaursService userAcceptOrderService() {

        if (userAcceptOrderKillAcaursService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            userAcceptOrderKillAcaursService = retrofit.create(UserAcceptOrderKillAcaursService.class);
        }

        return userAcceptOrderKillAcaursService;
    }


    private static ManagerLevelService managerLevelService;


    /*
    * 检测管理师等级
    * */
    public static ManagerLevelService managerLevelService() {

        if (managerLevelService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            managerLevelService = retrofit.create(ManagerLevelService.class);
        }

        return managerLevelService;
    }
}
