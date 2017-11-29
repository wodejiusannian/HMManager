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
public class ShopList {


    /**
     * ret : 0
     * body : [{"clothes_pic":"http://hmyc365.net:8082/file/hm/pic/365/1.jpg","clothes_xl":41,"clothes_price_nb":"70","clothes_price_yh":"150","clothes_tag":"12","clothes_id":"12","clothes_name":"小蓝盒 x10/组"},{"clothes_pic":"http://hmyc365.net:8082/file/factory/pic/36514900587747622177615062/14901523504215266696780.jpg","clothes_xl":1,"clothes_price_nb":"2319","clothes_price_yh":"3649","clothes_tag":"","clothes_id":"8436514901523520048065245839","clothes_name":"18K金海水珍珠三珠耳环，白珍珠7mm"},{"clothes_pic":"http://hmyc365.net:8082/file/factory/pic/36514900587747622177615062/14901526020541221911320.jpg","clothes_xl":1,"clothes_price_nb":"4379","clothes_price_yh":"4859","clothes_tag":"","clothes_id":"8436514901526039378900825744","clothes_name":"18K金海水珍珠双珠耳环，白珍珠7mm，金珍珠9-10mm"},{"clothes_pic":"http://hmyc365.net:8082/file/factory/pic/36514900587747622177615062/14901526881874820773521.jpg","clothes_xl":1,"clothes_price_nb":"1769","clothes_price_yh":"1969","clothes_tag":"","clothes_id":"8436514901526897947407494052","clothes_name":"日本机芯电镀白金镶钻手表"},{"clothes_pic":"http://hmyc365.net:8082/file/factory/pic/36514900587747622177615062/14901527567669564582101.jpg","clothes_xl":1,"clothes_price_nb":"2279","clothes_price_yh":"2529","clothes_tag":"","clothes_id":"8436514901527583660085335002","clothes_name":"日本机芯电镀白金镶钻手表"},{"clothes_pic":"http://hmyc365.net:8082/file/factory/pic/36514900587747622177615062/14901528370889191793671.jpg","clothes_xl":1,"clothes_price_nb":"379","clothes_price_yh":"419","clothes_tag":"","clothes_id":"8436514901528371631870329618","clothes_name":"珐琅彩镶钻胸针"},{"clothes_pic":"http://hmyc365.net:8082/file/factory/pic/36514900587747622177615062/14901528990715752304600.jpg","clothes_xl":1,"clothes_price_nb":"589","clothes_price_yh":"659","clothes_tag":"","clothes_id":"8436514901528991387304849419","clothes_name":"珐琅彩镶钻猫头鹰胸针"},{"clothes_pic":"http://hmyc365.net:8082/file/factory/pic/36514900587747622177615062/14901529724554342355349.jpg","clothes_xl":1,"clothes_price_nb":"409","clothes_price_yh":"449","clothes_tag":"","clothes_id":"8436514901529725193645861693","clothes_name":"珐琅彩吉祥鸟胸针"},{"clothes_pic":"http://hmyc365.net:8082/file/factory/pic/36514900587747622177615062/14901530372436926521434.jpg","clothes_xl":1,"clothes_price_nb":"599","clothes_price_yh":"659","clothes_tag":"","clothes_id":"8436514901530373106834168657","clothes_name":"珐琅彩报喜鸟胸针"},{"clothes_pic":"http://hmyc365.net:8082/file/factory/pic/36514902408416175540437163/14902563119025068806358.jpg","clothes_xl":1,"clothes_price_nb":"2329","clothes_price_yh":"2580","clothes_tag":"","clothes_id":"8436514902563124745627809033","clothes_name":"蚕丝睡衣女家居套装"}]
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

    public static class BodyBean {
        /**
         * clothes_pic : http://hmyc365.net:8082/file/hm/pic/365/1.jpg
         * clothes_xl : 41
         * clothes_price_nb : 70
         * clothes_price_yh : 150
         * clothes_tag : 12
         * clothes_id : 12
         * clothes_name : 小蓝盒 x10/组
         */

        private String clothes_pic;
        private int clothes_xl;
        private String clothes_price_nb;
        private String clothes_price_yh;
        private String clothes_tag;
        private String clothes_id;
        private String clothes_name;
        private String mianliao_id;

        public String getMianliao_id() {
            return mianliao_id;
        }

        public void setMianliao_id(String mianliao_id) {
            this.mianliao_id = mianliao_id;
        }

        public String getClothes_pic() {
            return clothes_pic;
        }

        public void setClothes_pic(String clothes_pic) {
            this.clothes_pic = clothes_pic;
        }

        public int getClothes_xl() {
            return clothes_xl;
        }

        public void setClothes_xl(int clothes_xl) {
            this.clothes_xl = clothes_xl;
        }

        public String getClothes_price_nb() {
            return clothes_price_nb;
        }

        public void setClothes_price_nb(String clothes_price_nb) {
            this.clothes_price_nb = clothes_price_nb;
        }

        public String getClothes_price_yh() {
            return clothes_price_yh;
        }

        public void setClothes_price_yh(String clothes_price_yh) {
            this.clothes_price_yh = clothes_price_yh;
        }

        public String getClothes_tag() {
            return clothes_tag;
        }

        public void setClothes_tag(String clothes_tag) {
            this.clothes_tag = clothes_tag;
        }

        public String getClothes_id() {
            return clothes_id;
        }

        public void setClothes_id(String clothes_id) {
            this.clothes_id = clothes_id;
        }

        public String getClothes_name() {
            return clothes_name;
        }

        public void setClothes_name(String clothes_name) {
            this.clothes_name = clothes_name;
        }
    }
}
