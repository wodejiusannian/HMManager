package manager.love.i.hmmanager.bean;

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
public class Customer365 {


    /**
     * body : [{"time_js":"2018-03-03","time_ks":"2017-03-03","user_name":"小五","user_phone":"","user_pic":"http://hmyc365.net:8080/images/photo/user55/f95220a3-ec96-4a0b-8733-7d7f9f524d28.jpg"},{"time_js":"2018-06-12","time_ks":"2017-06-12","user_name":"180测试账号","user_phone":"","user_pic":"http://hmyc365.net:8080/images/photo/user81/9556cd4e-8c69-4413-98cb-2263c6c349dc.jpg"}]
     * msg : SUCCESS
     * ret : 0
     */

    private String msg;
    private String ret;
    private List<BodyBean> body;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public List<BodyBean> getBody() {
        return body;
    }

    public void setBody(List<BodyBean> body) {
        this.body = body;
    }

    public static class BodyBean {
        /**
         * time_js : 2018-03-03
         * time_ks : 2017-03-03
         * user_name : 小五
         * user_phone :
         * user_pic : http://hmyc365.net:8080/images/photo/user55/f95220a3-ec96-4a0b-8733-7d7f9f524d28.jpg
         */

        private String time_js;
        private String time_ks;
        private String user_name;
        private String user_phone;
        private String user_pic;
        private String user_id;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getTime_js() {
            return time_js;
        }

        public void setTime_js(String time_js) {
            this.time_js = time_js;
        }

        public String getTime_ks() {
            return time_ks;
        }

        public void setTime_ks(String time_ks) {
            this.time_ks = time_ks;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getUser_pic() {
            return user_pic;
        }

        public void setUser_pic(String user_pic) {
            this.user_pic = user_pic;
        }
    }
}
