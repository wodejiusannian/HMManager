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
public class Evaluate {
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

        private String app_user_head_pic;//APP用户头像
        private String app_user_name;//APP用户名
        private String content;//评价内容
        private String star;//评星

        public String getApp_user_head_pic() {
            return app_user_head_pic;
        }

        public void setApp_user_head_pic(String app_user_head_pic) {
            this.app_user_head_pic = app_user_head_pic;
        }

        public String getApp_user_name() {
            return app_user_name;
        }

        public void setApp_user_name(String app_user_name) {
            this.app_user_name = app_user_name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getStar() {
            return star;
        }

        public void setStar(String star) {
            this.star = star;
        }
    }
}
