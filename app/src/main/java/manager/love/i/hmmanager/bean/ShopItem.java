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
public class ShopItem {

    /**
     * ret : 0
     * body : [{"stock":"32","color":"黄色","stock_id":"48","size":"M码"},{"stock":"35","color":"黄色","stock_id":"49","size":"L码"},{"stock":"26","color":"黄色","stock_id":"50","size":"XL码"},{"stock":"24","color":"黄色","stock_id":"51","size":"XXL码"},{"stock":"25","color":"紫色 ","stock_id":"52","size":"Ｍ码"},{"stock":"25","color":"紫色","stock_id":"53","size":"Ｌ码"},{"stock":"25","color":"紫色","stock_id":"54","size":"XL码"},{"stock":"25","color":"紫色","stock_id":"55","size":"XXL码"}]
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
         * stock : 32
         * color : 黄色
         * stock_id : 48
         * size : M码
         */

        private String stock;
        private String color;
        private String stock_id;
        private String size;

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getStock_id() {
            return stock_id;
        }

        public void setStock_id(String stock_id) {
            this.stock_id = stock_id;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }
    }
}
