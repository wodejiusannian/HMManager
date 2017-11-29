package manager.love.i.hmmanager.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import manager.love.i.hmmanager.bean.Msg;


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
public class SQLLiteDao {

    private SQLHelper helper = null;

    /**
     * 构造函数
     * 调用getWritableDatabase()或getReadableDatabase()方法后，会缓存SQLiteDatabase实例；
     * 因为这里是手机应用程序，一般只有一个用户访问数据库，所以建议不关闭数据库，保持连接状态。
     * getWritableDatabase()，getReadableDatabase的区别是当数据库写满时，调用前者会报错，调用后者不会，
     * 所以如果不是更新数据库的话，最好调用后者来获得数据库连接。
     * 对于熟悉SQL语句的程序员最好使用exeSQL(),rawQuery(),因为比较直观明了
     *
     * @param context
     */
    public SQLLiteDao(Context context) {
        helper = new SQLHelper(context);
    }

    //实现对该数据库的增加
    public void addWeather(Msg message) {
        //获取操作实例
        SQLiteDatabase db = helper.getWritableDatabase();
        //此方法推荐使用
        String sqlStr = "insert into msg(studio_id,title,content,isLookup,time,type_1,type_2,ope_name)values(?,?,?,?,?,?,?,?)";
        //执行SQL语句
        db.execSQL(sqlStr, new Object[]{message.studio_id, message.title, message.content,
                message.isLookup, message.time, message.type_1,
                message.type_2, message.ope_name});
        //关闭数据库
        db.close();
    }


    public void deletePerson(String time) {
        //获取数据库操作的实例
        SQLiteDatabase db = helper.getWritableDatabase();
        //创建SQL字符串
        String sqlStr = "delete from msg where time = ?";
        db.execSQL(sqlStr, new String[]{time});
        //关闭数据库
        db.close();
    }
    /*//实现对数据库的删除


    //实现对数据库的修改
    public void updatePerson(JavaBean javaBean) {
        //获取数据库的操作实例
        SQLiteDatabase db = helper.getWritableDatabase();
        //创建SQl字符串
        String sqlStr = "update person set name=?,phone=?where age=?";
        //执行SQL语句
        db.execSQL(sqlStr, new Object[]{JavaBean.getName(), JavaBean.getPhone(), JavaBean.getAge()});
        //关闭数据库
        db.close();
    }*/

    //实现对数据库的查询
    public List<Msg> selectPerson(String id, String str) {
        //创建集合
        List<Msg> javaBeans = new ArrayList<Msg>();
        //获取数据库操作实例
        SQLiteDatabase db = helper.getReadableDatabase();
        //创建Cursor对象
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("checkbox_style_select * from msg where studio_id = " + id + "and type_1 = " + str, null);
            while (cursor.moveToNext()) {
                String studio_id = cursor.getString(cursor.getColumnIndex("studio_id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                int isLookup = cursor.getInt(cursor.getColumnIndex("isLookup"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                String type_1 = cursor.getString(cursor.getColumnIndex("type_1"));
                String type_2 = cursor.getString(cursor.getColumnIndex("type_2"));
                String ope_name = cursor.getString(cursor.getColumnIndex("ope_name"));
                Msg javaBean = new Msg(studio_id, title, content, isLookup, time, type_1, type_2, ope_name);
                javaBeans.add(javaBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭相应的资源
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return javaBeans;
    }
}
