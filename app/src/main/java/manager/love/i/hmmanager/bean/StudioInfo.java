package manager.love.i.hmmanager.bean;

import java.io.Serializable;

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
public class StudioInfo {


    /**
     * ret : 0
     * body : {"count_kh_yy":"0","count_kh_365":"0","info":{"time":"2016-11-25 15:42:49","start":"4.928571428571429","studio_sex":"1","studio_city":"北京市","introduction":"我们是慧美衣橱管理专家：会美，学会基础的生活美学，在生活和工作中穿着不出错；汇美，汇集美丽，在各种场合展示独特的魅力，穿出多姿多彩；慧美，智慧管理，穿出自己美。","studio_head_pic_url":"http://192.168.1.182:8084/file/pic/43/14984653360360509859408.jpg","studio_name":"莉莉安工作室","studio_id":"43"},"count_cs_tj":"66"}
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
         * count_kh_yy : 0
         * count_kh_365 : 0
         * info : {"time":"2016-11-25 15:42:49","start":"4.928571428571429","studio_sex":"1","studio_city":"北京市","introduction":"我们是慧美衣橱管理专家：会美，学会基础的生活美学，在生活和工作中穿着不出错；汇美，汇集美丽，在各种场合展示独特的魅力，穿出多姿多彩；慧美，智慧管理，穿出自己美。","studio_head_pic_url":"http://192.168.1.182:8084/file/pic/43/14984653360360509859408.jpg","studio_name":"莉莉安工作室","studio_id":"43"}
         * count_cs_tj : 66
         */

        private String count_kh_yy;
        private String count_kh_365;
        private InfoBean info;
        private String count_cs_tj;

        public String getCount_kh_yy() {
            return count_kh_yy;
        }

        public void setCount_kh_yy(String count_kh_yy) {
            this.count_kh_yy = count_kh_yy;
        }

        public String getCount_kh_365() {
            return count_kh_365;
        }

        public void setCount_kh_365(String count_kh_365) {
            this.count_kh_365 = count_kh_365;
        }

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public String getCount_cs_tj() {
            return count_cs_tj;
        }

        public void setCount_cs_tj(String count_cs_tj) {
            this.count_cs_tj = count_cs_tj;
        }

        public static class InfoBean implements Serializable{
            /**
             * time : 2016-11-25 15:42:49
             * start : 4.928571428571429
             * studio_sex : 1
             * studio_city : 北京市
             * introduction : 我们是慧美衣橱管理专家：会美，学会基础的生活美学，在生活和工作中穿着不出错；汇美，汇集美丽，在各种场合展示独特的魅力，穿出多姿多彩；慧美，智慧管理，穿出自己美。
             * studio_head_pic_url : http://192.168.1.182:8084/file/pic/43/14984653360360509859408.jpg
             * studio_name : 莉莉安工作室
             * studio_id : 43
             */

            private String time;
            private String start;
            private String studio_sex;
            private String studio_city;
            private String introduction;
            private String studio_head_pic_url;
            private String studio_name;
            private String studio_id;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getStart() {
                return start;
            }

            public void setStart(String start) {
                this.start = start;
            }

            public String getStudio_sex() {
                return studio_sex;
            }

            public void setStudio_sex(String studio_sex) {
                this.studio_sex = studio_sex;
            }

            public String getStudio_city() {
                return studio_city;
            }

            public void setStudio_city(String studio_city) {
                this.studio_city = studio_city;
            }

            public String getIntroduction() {
                return introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }

            public String getStudio_head_pic_url() {
                return studio_head_pic_url;
            }

            public void setStudio_head_pic_url(String studio_head_pic_url) {
                this.studio_head_pic_url = studio_head_pic_url;
            }

            public String getStudio_name() {
                return studio_name;
            }

            public void setStudio_name(String studio_name) {
                this.studio_name = studio_name;
            }

            public String getStudio_id() {
                return studio_id;
            }

            public void setStudio_id(String studio_id) {
                this.studio_id = studio_id;
            }
        }
    }
}
