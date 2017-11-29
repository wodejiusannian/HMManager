package manager.love.i.hmmanager.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import manager.love.i.hmmanager.bean.IMUser;


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
public class SQLIMDao {

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
    public SQLIMDao(Context context) {
        helper = new SQLHelper(context);
    }

    //实现对该数据库的增加
    public void addWeather(IMUser imUser) {
        //获取操作实例
        SQLiteDatabase db = helper.getWritableDatabase();
        //此方法推荐使用
        String sqlStr = "insert into mim(user_id,name,url)values(?,?,?)";
        //执行SQL语句
        db.execSQL(sqlStr, new Object[]{imUser.user_id, imUser.name, imUser.url});
        //关闭数据库
        db.close();
    }


    public void deletePerson(String user_id) {
        //获取数据库操作的实例
        SQLiteDatabase db = helper.getWritableDatabase();
        //创建SQL字符串
        String sqlStr = "delete from mim where user_id = ?";
        db.execSQL(sqlStr, new String[]{user_id});
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
    public IMUser selectPerson(String user_id) {
        IMUser imUser = new IMUser();
        //获取数据库操作实例
        SQLiteDatabase db = helper.getReadableDatabase();
        //创建Cursor对象
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("checkbox_style_select * from mim where user_id = " + "'" + user_id + "'", null);
            while (cursor.moveToNext()) {
                imUser.user_id = cursor.getString(0);
                imUser.name = cursor.getString(1);
                imUser.url = cursor.getString(2);
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            //关闭相应的资源
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return imUser;
    }
}
