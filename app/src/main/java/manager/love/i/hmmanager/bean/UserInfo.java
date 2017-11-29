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
public class UserInfo {

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

        private String studio_state;
        private String studio_sex;
        private String studio_name;
        private String studio_head_pic_url;
        private String studio_id;

        public String getStudio_state() {
            return studio_state;
        }

        public void setStudio_state(String studio_state) {
            this.studio_state = studio_state;
        }

        public String getStudio_sex() {
            return studio_sex;
        }

        public void setStudio_sex(String studio_sex) {
            this.studio_sex = studio_sex;
        }

        public String getStudio_name() {
            return studio_name;
        }

        public void setStudio_name(String studio_name) {
            this.studio_name = studio_name;
        }

        public String getStudio_head_pic_url() {
            return studio_head_pic_url;
        }

        public void setStudio_head_pic_url(String studio_head_pic_url) {
            this.studio_head_pic_url = studio_head_pic_url;
        }

        public String getStudio_id() {
            return studio_id;
        }

        public void setStudio_id(String studio_id) {
            this.studio_id = studio_id;
        }
    }
}
