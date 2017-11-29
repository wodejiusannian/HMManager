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
public class StudioTime {

    /**
     * ret : 0
     * body : {"busy_time":[{"busy_date":"2017-06-28","busy_time_slot_tag":"AM_BUSY"},{"busy_date":"2017-06-30","busy_time_slot_tag":"ALL_BUSY"}],"feture_days":"2017-06-21,2017-06-22,2017-06-23,2017-06-24,2017-06-25,2017-06-26,2017-06-27,2017-06-28,2017-06-29,2017-06-30,2017-07-01,2017-07-02,2017-07-03,2017-07-04,2017-07-05,2017-07-06,2017-07-07,2017-07-08,2017-07-09,2017-07-10,2017-07-11,2017-07-12,2017-07-13,2017-07-14,2017-07-15,2017-07-16,2017-07-17,2017-07-18,2017-07-19,2017-07-20"}
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
         * busy_time : [{"busy_date":"2017-06-28","busy_time_slot_tag":"AM_BUSY"},{"busy_date":"2017-06-30","busy_time_slot_tag":"ALL_BUSY"}]
         * feture_days : 2017-06-21,2017-06-22,2017-06-23,2017-06-24,2017-06-25,2017-06-26,2017-06-27,2017-06-28,2017-06-29,2017-06-30,2017-07-01,2017-07-02,2017-07-03,2017-07-04,2017-07-05,2017-07-06,2017-07-07,2017-07-08,2017-07-09,2017-07-10,2017-07-11,2017-07-12,2017-07-13,2017-07-14,2017-07-15,2017-07-16,2017-07-17,2017-07-18,2017-07-19,2017-07-20
         */

        private String feture_days;
        private List<BusyTimeBean> busy_time;

        public String getFeture_days() {
            return feture_days;
        }

        public void setFeture_days(String feture_days) {
            this.feture_days = feture_days;
        }

        public List<BusyTimeBean> getBusy_time() {
            return busy_time;
        }

        public void setBusy_time(List<BusyTimeBean> busy_time) {
            this.busy_time = busy_time;
        }

        public static class BusyTimeBean {
            /**
             * busy_date : 2017-06-28
             * busy_time_slot_tag : AM_BUSY
             */

            private String busy_date;
            private String busy_time_slot_tag;

            public String getBusy_date() {
                return busy_date;
            }

            public void setBusy_date(String busy_date) {
                this.busy_date = busy_date;
            }

            public String getBusy_time_slot_tag() {
                return busy_time_slot_tag;
            }

            public void setBusy_time_slot_tag(String busy_time_slot_tag) {
                this.busy_time_slot_tag = busy_time_slot_tag;
            }
        }
    }
}
