package manager.love.i.hmmanager.bean;

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
public class IMToken {

    /**
     * ret : 0
     * body : {"token":"cR6mpFmC8BHBnIyzpAQ45vYo3NGt3miaENmwEVAnpkXyi4p4LW46QiLFuRFPK4s39wPX40hugJbPkGzm7dV3xw=="}
     * msg : SUCCESS
     */

    private String ret;
    private BodyBean body;
    private String msg;

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class BodyBean {
        /**
         * token : cR6mpFmC8BHBnIyzpAQ45vYo3NGt3miaENmwEVAnpkXyi4p4LW46QiLFuRFPK4s39wPX40hugJbPkGzm7dV3xw==
         */

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
