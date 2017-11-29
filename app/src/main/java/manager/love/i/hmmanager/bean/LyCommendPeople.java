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
public class LyCommendPeople {


    /**
     * ret : 0
     * body : [{"name_fzr":"王彬","name_gzs":"彬彬工作室","pic_url":"http://hmyc365.net:8080/images/photo/manager285/b67fb004-7bfa-432d-8b4d-402996b8e0ed.jpg","studio_id":285},{"name_fzr":"杜文丽","name_gzs":"莉莉安工作室","pic_url":"http://hmyc365.net:8084/file/pic/43/14985463079564655272672.jpg","studio_id":43},{"name_fzr":"杜文丽","name_gzs":"莉莉安工作室","pic_url":"","studio_id":300},{"name_fzr":"熊琳","name_gzs":"熊宝宝工作室","pic_url":"http://101.201.36.18:8080/images/photo/manager45/9bccec6d-a1b5-4fe6-8ff7-e4c08b4bd26f.jpg","studio_id":45},{"name_fzr":"李静","name_gzs":"静静工作室","pic_url":"http://101.201.36.18:8080/images/photo/manager143/d1c8bdbb-f881-4c99-9fba-413f7d4a0ec6.jpg","studio_id":143},{"name_fzr":"刘芳","name_gzs":"刘芳工作室","pic_url":"","studio_id":174},{"name_fzr":"黄欢","name_gzs":"黄欢工作室","pic_url":"http://hmyc365.net:8080/images/photo/manager175/8e9f0eee-be3e-42b8-a7ba-47c35c4e625e.jpg","studio_id":175},{"name_fzr":"王丽","name_gzs":"王丽工作室","pic_url":"http://hmyc365.net:8084/file/pic/176/15044872721621.jpg","studio_id":176},{"name_fzr":"潘海连","name_gzs":"潘海连工作室","pic_url":"","studio_id":192},{"name_fzr":"何丽","name_gzs":"何丽工作室","pic_url":"http://hmyc365.net:8084/file/pic/224/15020694279121.jpg","studio_id":224},{"name_fzr":"王丽华","name_gzs":"艾美工作室","pic_url":"http://hmyc365.net:8080/images/photo/manager225/69c38d80-f997-425d-995b-323930bf0fd1.jpg","studio_id":225},{"name_fzr":"屈晓磊","name_gzs":"慧美测试工作室","pic_url":"http://hmyc365.net:8084/file/pic/241/14986125850820613635656.jpg","studio_id":241},{"name_fzr":"陈小雪","name_gzs":"小雪儿形象衣橱管理","pic_url":"http://hmyc365.net:8080/images/photo/manager244/1b61f778-f14a-4786-bc37-7289b8966915.jpg","studio_id":244},{"name_fzr":"张艳艳","name_gzs":"米豆工作室","pic_url":"","studio_id":246},{"name_fzr":"金静","name_gzs":"白镜子工作室","pic_url":"http://hmyc365.net:8080/images/photo/manager247/54cd2b09-1ea0-4f70-928f-ecbabb3d25bd.jpg","studio_id":247},{"name_fzr":"余清侠","name_gzs":"伊美工作室","pic_url":"http://hmyc365.net:8080/images/photo/manager250/823ca4f1-9549-432d-a3a3-c53354cbd852.jpg","studio_id":250}]
     * msg : SUCCESS
     */

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



        /**
         * name_fzr : 王彬
         * name_gzs : 彬彬工作室
         * pic_url : http://hmyc365.net:8080/images/photo/manager285/b67fb004-7bfa-432d-8b4d-402996b8e0ed.jpg
         * studio_id : 285
         */

        private String name_fzr;
        private String name_gzs;
        private String pic_url;
        private int studio_id;

        public String getName_fzr() {
            return name_fzr;
        }

        public void setName_fzr(String name_fzr) {
            this.name_fzr = name_fzr;
        }

        public String getName_gzs() {
            return name_gzs;
        }

        public void setName_gzs(String name_gzs) {
            this.name_gzs = name_gzs;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public int getStudio_id() {
            return studio_id;
        }

        public void setStudio_id(int studio_id) {
            this.studio_id = studio_id;
        }
    }
}
