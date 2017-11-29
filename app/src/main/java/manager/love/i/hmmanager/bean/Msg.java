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
public class Msg {
    public String studio_id;
    public String title;
    public String content;
    public int isLookup;
    public String time;
    public String type_1;
    public String type_2;
    public String ope_name;

    public Msg(String studio_id, String title, String content, int isLookup, String time, String type_1, String type_2, String ope_name) {
        this.studio_id = studio_id;
        this.title = title;
        this.content = content;
        this.isLookup = isLookup;
        this.time = time;
        this.type_1 = type_1;
        this.type_2 = type_2;
        this.ope_name = ope_name;
    }
}
