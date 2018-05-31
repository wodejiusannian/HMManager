package manager.love.i.hmmanager.utils;

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
public interface NetConfig {
    String BASEURL = "http://hmyc365.net";

    String TOKEN = "82D5FBD40259C743ADDEF14D0E22F347";

    //我的订单中的订单状态
    String ORDER_STATE = BASEURL + "/admiral/app/hmgls/order/info/listOrder.htm?token=";
    //
    String SHOPCAR_SING_SHOP = BASEURL + "/admiral/common/pay/gwc/sign.htm";
    //上门服务补差价的接口
    String GODOOR_CLOSEPRICE_GETPRICE = BASEURL + "/admiral/app/hmyc/order/service/getOrdPriceBcj.htm?token=82D5FBD40259C743ADDEF14D0E22F347";

    //每个城市除螨下单的价格
    String PRICE_ACARUS_KILLING = BASEURL + "/admiral/common/service/price/listPrice.htm";

    //除螨服务补差价获取签名接口
    String SIGN_CLOSE_MONEY_ACARUS_KILLING = BASEURL + "/admiral/common/pay/cmfwbcj/sign.htm";

    //上门服务补差价下单接口
    String GOODOOR_CLOSERPRICE_GETSIGN = BASEURL + "/admiral/app/hmyc/order/service/addOrderBcj.htm?token=82D5FBD40259C743ADDEF14D0E22F347";

    //除螨服务补差价下单接口
    String ORDER_CLOSE_MONEY_ACARUS_KILLING = BASEURL + "/admiral/app/hmyc/service/order/addOrderCmfwBcj.htm";

    //APP更新接口
    String APP_ISHAVEFRESH = BASEURL + "/admiral/system/version/versionCheck.htm?token=82D5FBD40259C743ADDEF14D0E22F347";

    //完成订单
    String ORDER_SURE = BASEURL + "/admiral/order/info/completeOrder.htm?token=82D5FBD40259C743ADDEF14D0E22F347";
}
