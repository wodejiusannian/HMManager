package manager.love.i.hmmanager.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
public class SQLHelper extends SQLiteOpenHelper {

    public SQLHelper(Context context) {
        //这三个参数分别为上下文对象,数据库名称,游标,版本号
        super(context, "message.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table msg(studio_id varchar(50),title varchar(50),content varchar(400),isLookup integer,time varchar(50),type_1 varchar(50),type_2 varchar(50),ope_name varchar(50))");
        db.execSQL("create table mim(user_id varchar(50),name varchar(50),url varchar(400))");
        db.execSQL("create table clo(type varchar(50),yiji_name varchar(50),yiji_code varchar(50),erji_name varchar(50),erji_code varchar(50),erji_jc varchar(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 注：生产环境上不能做删除操作
        db.execSQL("DROP TABLE IF EXISTS person");
        onCreate(db);
    }
}
