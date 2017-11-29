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
public class CashRegiste {

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

    public static class BodyBean {
        /**
         * time : 2017-06-22 15
         * money_sq : 20
         * money_sj : 0
         * money_ye : 0
         * money_fp : 20
         */

        private String time;
        private String money_sq;
        private String money_sj;
        private String money_ye;
        private String money_fp;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getMoney_sq() {
            return money_sq;
        }

        public void setMoney_sq(String money_sq) {
            this.money_sq = money_sq;
        }

        public String getMoney_sj() {
            return money_sj;
        }

        public void setMoney_sj(String money_sj) {
            this.money_sj = money_sj;
        }

        public String getMoney_ye() {
            return money_ye;
        }

        public void setMoney_ye(String money_ye) {
            this.money_ye = money_ye;
        }

        public String getMoney_fp() {
            return money_fp;
        }

        public void setMoney_fp(String money_fp) {
            this.money_fp = money_fp;
        }
    }
}
