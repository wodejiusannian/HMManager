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
public class CustomerGoDoor {


    /**
     * body : [{"state":"","time_cw":"2017-01-20 11:12:01","user_name":"张伟","user_phone":"","user_pic":"http://wx.qlogo.cn/mmopen/ajNVdqHZLLBCsFJicut3S5LDLZjfwFFbJE8bsjL7YKa5Is1tEFETtOASP9GNEWOtbia8kKrXsNOURwqe5gibvCYqQ/0"},{"state":"","time_cw":"2017-06-11 14:19:38","user_name":"","user_phone":"","user_pic":""}]
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
         * state :
         * time_cw : 2017-01-20 11:12:01
         * user_name : 张伟
         * user_phone :
         * user_pic : http://wx.qlogo.cn/mmopen/ajNVdqHZLLBCsFJicut3S5LDLZjfwFFbJE8bsjL7YKa5Is1tEFETtOASP9GNEWOtbia8kKrXsNOURwqe5gibvCYqQ/0
         */

        private String state;
        private String time_cw;
        private String user_name;
        private String user_phone;
        private String user_pic;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getTime_cw() {
            return time_cw;
        }

        public void setTime_cw(String time_cw) {
            this.time_cw = time_cw;
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
