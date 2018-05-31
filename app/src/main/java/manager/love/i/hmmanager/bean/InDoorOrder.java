package manager.love.i.hmmanager.bean;

import java.io.Serializable;
import java.util.List;

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
public class InDoorOrder {


    private String ret;
    private String msg;
    private List<BodyBean> body;

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<BodyBean> getBody() {
        return body;
    }

    public void setBody(List<BodyBean> body) {
        this.body = body;
    }

    public static class BodyBean implements Serializable {
     /*   *//**//**
                *xd_time :2017-06-09 16:08:40
                *app_user_head_pic :http://wx.qlogo.cn/mmopen/pjJMDc8yQuceTatt845IXNU6dD2xibjj8rQnzJ420Gibsohlqhxhvl9e4BQOIF1olukEGVe7rjjaR1ydqMibzGP7UNZIEOHoxnQ/0
                *evaluate :0
                *jd_address :安徽省,安庆市,枞阳县烤鸡腿
         *jd_time :2017-06-11上午
         *order_id :9281295720149691
                *jd_city :北京市
         *jd_name :小五
         *clothes_money :298
                *othclo_money :0
                *jd_phone :554584
                *box_money :0
                *clothes_num :100
                *//**//**/

        private String xd_time;
        private String app_user_head_pic;
        private String evaluate;
        private String jd_address;
        private String jd_time;
        private String order_id;
        private String jd_city;
        private String jd_name;
        private String clothes_money;
        private String othclo_money;
        private String jd_phone;
        private String box_money;
        private String clothes_num;
        private String state;
        private String orderType;

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getXd_time() {
            return xd_time;
        }

        public void setXd_time(String xd_time) {
            this.xd_time = xd_time;
        }

        public String getApp_user_head_pic() {
            return app_user_head_pic;
        }

        public void setApp_user_head_pic(String app_user_head_pic) {
            this.app_user_head_pic = app_user_head_pic;
        }

        public String getEvaluate() {
            return evaluate;
        }

        public void setEvaluate(String evaluate) {
            this.evaluate = evaluate;
        }

        public String getJd_address() {
            return jd_address;
        }

        public void setJd_address(String jd_address) {
            this.jd_address = jd_address;
        }

        public String getJd_time() {
            return jd_time;
        }

        public void setJd_time(String jd_time) {
            this.jd_time = jd_time;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getJd_city() {
            return jd_city;
        }

        public void setJd_city(String jd_city) {
            this.jd_city = jd_city;
        }

        public String getJd_name() {
            return jd_name;
        }

        public void setJd_name(String jd_name) {
            this.jd_name = jd_name;
        }

        public String getClothes_money() {
            return clothes_money;
        }

        public void setClothes_money(String clothes_money) {
            this.clothes_money = clothes_money;
        }

        public String getOthclo_money() {
            return othclo_money;
        }

        public void setOthclo_money(String othclo_money) {
            this.othclo_money = othclo_money;
        }

        public String getJd_phone() {
            return jd_phone;
        }

        public void setJd_phone(String jd_phone) {
            this.jd_phone = jd_phone;
        }

        public String getBox_money() {
            return box_money;
        }

        public void setBox_money(String box_money) {
            this.box_money = box_money;
        }

        public String getClothes_num() {
            return clothes_num;
        }

        public void setClothes_num(String clothes_num) {
            this.clothes_num = clothes_num;
        }
    }
}
